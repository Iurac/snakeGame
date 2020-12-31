import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Random;

abstract class Snake implements ActionListener, KeyListener {
    public int[] snakex = new int[600];
    public int[] snakey = new int[600];
    Random random = new Random();

    public int score;//蛇的分数
    public int len;//蛇的长度
    public int speed;//蛇的速度
    public int lifes;//蛇的生命
    public String fx;//蛇的方向
    public boolean isSuicide;//蛇能否自杀

    public ActionEvent e;
    Font font3 = new Font("TimesRoman",Font.BOLD, Game.BLOCK_SIDE);

    Image[] direction = new Image[10];
    Image life = new ImageIcon(Snake.class.getResource("/img/life.png")).getImage();

    //初始化蛇
    public Snake() {
        direction[0] = new ImageIcon(Snake.class.getResource("img/up1.png")).getImage();
        direction[1] = new ImageIcon(Snake.class.getResource("img/down1.png")).getImage();
        direction[2] = new ImageIcon(Snake.class.getResource("img/left1.png")).getImage();
        direction[3] = new ImageIcon(Snake.class.getResource("img/right1.png")).getImage();
        direction[4] = new ImageIcon(Snake.class.getResource("img/body1.png")).getImage();

        direction[5] = new ImageIcon(Snake.class.getResource("img/up2.png")).getImage();
        direction[6] = new ImageIcon(Snake.class.getResource("img/down2.png")).getImage();
        direction[7] = new ImageIcon(Snake.class.getResource("img/left2.png")).getImage();
        direction[8] = new ImageIcon(Snake.class.getResource("img/right2.png")).getImage();
        direction[9] = new ImageIcon(Snake.class.getResource("img/body2.png")).getImage();

        this.score = 0;
        this.len = 3;
        this.speed = 0;
        this.lifes = 3;
        this.isSuicide = true;
    }

    //画蛇
    abstract void paint(Graphics g);

    //监听器
    public void actionPerformed(ActionEvent e) {
        //蛇的运动
        for (int i = len; i > 0 ; i--) {
            snakex[i] = snakex[i-1];
            snakey[i] = snakey[i-1];
        }
        if(fx == "R")
            snakex[0] += Game.BLOCK_SIDE;
        else if(fx == "L")
            snakex[0] -= Game.BLOCK_SIDE;
        else if(fx == "U")
            snakey[0] -= Game.BLOCK_SIDE;
        else if(fx == "D")
            snakey[0] += Game.BLOCK_SIDE;

      /*蛇可以穿墙*/
        /*if(snakex[0] > Against.BLOCK_SIDE*(Against.ROW-1)+1)
            snakex[0] = 1;
        if(snakex[0] < 1)
            snakex[0] = Against.BLOCK_SIDE*(Against.ROW-1)+1;
        if(snakey[0] > Against.BLOCK_SIDE*(Against.COL-4)+1)
            snakey[0] = 1+Against.BLOCK_SIDE;
        if(snakey[0] < Against.BLOCK_SIDE)
            snakey[0] = Against.BLOCK_SIDE*(Against.COL-4)+1;*/
    }

    //判断蛇是否吃了食物
    public boolean isEat(Food food , int i){
        if(snakex[0] == food.foodx[i] && snakey[0] == food.foody[i]) {
            food.count = 0;
            food.updateFood(food.x,food.y);
            return true;
        }
        else
            return false;
    }

    //蛇吃食物
    public void eatFood(Food food){
        if(isEat(food,0)) {
            score+=10;
            len++;
        }
        if(isEat(food,1)){
            score-=10;
            len--;
        }
        if(isEat(food,2)){
            score+=50;
            len+=5;
        }
        if(speed < 75)
            speed = len;
    }

    //得到时间的情况
    public void getTime(Time time){
        if(this.snakex[0] == time.clockx && this.snakey[0] == time.clocky){
            time.count+=5;
            time.appearClock = false;
            time.updateClock();
            time.timer();
        }
    }

    //蛇老死
    public void dieOfTime(Time time,Process process){
        if(time.count == 0)
            process.isFailed = true;
    }

    //蛇的死亡(长度为0 & 自己撞自己 & 对方撞自己 & 头撞头 & 自己撞墙）
    public void isDead(Snake snake,Wall wall,Process process){
        if(this.len == 0){
            this.lifes--;
            this.isFail(snake,process,wall);
        }
        if(isSuicide){
            for (int i = this.len; i > 0; i--) {
                if(this.snakex[0] == this.snakex[i] && this.snakey[0] == this.snakey[i]){
                    this.lifes--;
                    this.isFail(snake,process,wall);
                    break;
                }
            }
        }
        for (int i = this.len; i > 0; i--) {
            if(snake.snakex[0] == this.snakex[i] && snake.snakey[0] == this.snakey[i]) {
                snake.lifes--;
                snake.isFail(this, process,wall);
                break;
            }
        }
        if(this.snakex[0] == snake.snakex[0] && this.snakey[0] == snake.snakey[0]){
            this.lifes--;
            this.isFail(snake,process,wall);
        }
        for (int i = 0; i < wall.wallx.length; i++) {
            if(this.snakex[0] == wall.wallx[i] && this.snakey[0] == wall.wally[i]) {
                this.lifes--;
                this.isFail(snake,process,wall);
                break;
            }
        }
    }

    //判断游戏是否结束，若无则复活该蛇
    public void isFail(Snake snake,Process process,Wall wall){
        if(this.lifes == 0) {
            process.isFailed = true;
        }
        else {
            this.rebirth(snake,wall);
        }
    }

    //蛇的复活
    private void rebirth(Snake othersnake,Wall wall) {
        this.snakex = new int[600];
        this.snakey = new int[600];
        this.len = 3;
        int a = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.ROW-10)+5);
        int b = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.COL-12)+5);

        //防止复活蛇的位置在其它蛇身上
        while(true) {
            for (int i = 0; i < wall.wallx.length; i++) {
                if(a == wall.wallx[i] && b == wall.wally[i]){
                    rebirth(othersnake,wall);
                    break;
                }
            }
            for (int j = 0; j < othersnake.len; j++) {
                if(a == othersnake.snakex[j] && b == othersnake.snakey[j] ) {
                    rebirth(othersnake,wall);
                    break;
                }
            }

            this.snakex[0] = a;
            this.snakey[0] = b;
            break;
        }

    }

}
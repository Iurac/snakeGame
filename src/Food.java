import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class Food {
    /*
    0红苹果：长度加1格
    1紫苹果：长度减一个
    2鸡腿：长度加五格
    */
    int[] foodx = new int[3];
    int[] foody = new int[3];

    Random random = new Random();
    int x;
    int y;

    Image[] f = new Image[3];

    public int count = 0;
    /*计时器，设定timer每秒给其加1，每到6的倍数时刷新食物，如果蛇吃到食物将会重置该数
    * 即实现蛇5秒没吃到食物时，食物将会刷新*/

    //初始化食物
    public Food(){
        f[0] = new ImageIcon(Food.class.getResource("/img/food0.png")).getImage();
        f[1] = new ImageIcon(Food.class.getResource("/img/food1.png")).getImage();
        f[2] = new ImageIcon(Food.class.getResource("/img/food2.png")).getImage();
        x = 1;
        y = 2;
        updateFood(x,y);
        timer();
    }

    /*
    * 食物更新
    * x ~ [ x , ROW-1-x ]
    * y ~ [ y , COL-3-y ]
    * [Min, Max] = random(Max – Min +1) + Min
    */
    public void updateFood(int x , int y) {
        this.foodx[0] = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.ROW-2*x) + x);
        this.foody[0] = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.COL-2*y-2) + y);

        this.foodx[1] = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.ROW-2*x) + x);
        this.foody[1] = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.COL-2*y-2) + y);

        this.foodx[2] = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.ROW-2*x) + x);
        this.foody[2] = 1+ Game.BLOCK_SIDE*(random.nextInt(Game.COL-2*y-2) + y);

    }

    //画食物
    public void paint(Graphics g){
        g.drawImage(f[0],foodx[0],foody[0],null);
        g.drawImage(f[1],foodx[1],foody[1],null);
        g.drawImage(f[2],foodx[2],foody[2],null);
    }

    //防止生成的食物与蛇重合
    public boolean onBlock(Snake snake,Wall wall){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < snake.len; j++) {
                if(foodx[i] == snake.snakex[j] && foody[i] == snake.snakey[j])
                    return true;
            }
            for (int j = 0; j < wall.wallx.length; j++) {
                if(foodx[i] == wall.wallx[j] && foody[i] == wall.wally[j])
                    return true;
            }
        }
        return false;
    }


    public void timer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                count++;
                if(count % 5 == 0)
                    updateFood(x,y);
            }},1000,1000);
    }




}

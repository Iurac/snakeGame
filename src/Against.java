import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Against extends Frame {
    Player1 p1 = new Player1();
    Player2 p2 = new Player2();
    Food food = new Food();
    Wall wall = new Wall();
    Process process = new Process();
    MyThread myThread = new MyThread();
    WallThread wallThread = new WallThread();
    Music music = new Music();
    private static int count = 0;//刷圈次数
    private static int countdown = 60;//用于记刷圈倒计时
    static boolean flag = true;

    //定义窗口
    public void goSanke() {
        this.setTitle("贪吃蛇大作战");
        this.setSize(Game.ROW * Game.BLOCK_SIDE, Game.COL * Game.BLOCK_SIDE);
        this.setLocation(100, 50);
        this.setBackground(Color.BLACK);
        this.setResizable(false);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                music.stopMusic();
            }
        });
        this.addKeyListener(process);
        music.playMusic();
        myThread.start();//开始一个新线程控制该窗口
        if(flag) {
            wallThread.start();
            flag = false;
        }

    }

    //画布
    public void paint(Graphics g) {
        g.fillRect(0,0,Game.ROW * Game.BLOCK_SIDE, Game.COL * Game.BLOCK_SIDE);
        g.setColor(Color.WHITE);

        //辅助线
        /*for (int i = 0; i <= COL - 3; i++) {
            g.drawLine(0, i * BLOCK_SIDE, ROW * BLOCK_SIDE, i * BLOCK_SIDE);
        }
        for (int i = 0; i < ROW; i++) {
            g.drawLine(i * BLOCK_SIDE, 0, i * BLOCK_SIDE, BLOCK_SIDE * (COL - 3));
        }*/

        //界面分割线
        g.drawLine(0, (Game.COL - 3) * Game.BLOCK_SIDE, Game.ROW * Game.BLOCK_SIDE, (Game.COL - 3) * Game.BLOCK_SIDE);


        //分数胜负后先打印出胜利的一方
        //再打印出胜方相关信息
        if(process.isStarted && process.isFailed){
            //字体--胜利
            Font font1 = new Font("TimesRoman",Font.ITALIC, Game.BLOCK_SIDE*4);
            FontMetrics fm1 = g.getFontMetrics(font1);
            //字体--明细
            Font font2 = new Font("TimesRoman",Font.ITALIC, Game.BLOCK_SIDE);
            FontMetrics fm2 = g.getFontMetrics(font2);
            if(p1.score > p2.score){
                g.setColor(Color.GREEN);

                g.setFont(font1);
                int textWidth1 = fm1.stringWidth("Player1 WIN!!!");
                g.drawString("Player1 WIN!!!", (Game.ROW * Game.BLOCK_SIDE - textWidth1) / 2, Game.COL / 2 * Game.BLOCK_SIDE);

                g.setFont(font2);
                int textWidth2 = fm2.stringWidth("score:"+p1.score);
                g.drawString("score:"+p1.score, (Game.ROW * Game.BLOCK_SIDE - textWidth2) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+4));
                int textWidth3 = fm2.stringWidth("len:"+p1.len);
                g.drawString("len:"+p1.len, (Game.ROW * Game.BLOCK_SIDE - textWidth3) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+7));
            }
            else if(p1.score < p2.score){
                g.setColor(Color.BLUE);

                g.setFont(font1);
                int textWidth1 = fm1.stringWidth("Player2 WIN!!!");
                g.drawString("Player2 WIN!!!", (Game.ROW * Game.BLOCK_SIDE - textWidth1) / 2, Game.COL / 2 * Game.BLOCK_SIDE);

                g.setFont(font2);
                int textWidth2 = fm2.stringWidth("score:"+p2.score);
                g.drawString("score:"+p2.score, (Game.ROW * Game.BLOCK_SIDE - textWidth2) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+4));
                int textWidth3 = fm2.stringWidth("len:"+p2.len);
                g.drawString("len:"+p2.len, (Game.ROW * Game.BLOCK_SIDE - textWidth3) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+7));
            }
            else {
                g.setColor(Color.WHITE);

                g.setFont(font1);
                int textWidth1 = fm1.stringWidth("Deuce");
                g.drawString("Deuce", (Game.ROW * Game.BLOCK_SIDE - textWidth1) / 2, Game.COL / 2 * Game.BLOCK_SIDE);

                g.setFont(font2);
                int textWidth2 = fm2.stringWidth("score:"+p2.score);
                g.drawString("score:"+p2.score, (Game.ROW * Game.BLOCK_SIDE - textWidth2) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+4));
                int textWidth3 = fm2.stringWidth("len:"+p2.len);
                g.drawString("len:"+p2.len, (Game.ROW * Game.BLOCK_SIDE - textWidth3) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+7));

            }
        }

        //游戏未开始时初始化蛇，且为蛇添加监听器，为开始做准备
        if(!process.isStarted){
            p1 = new Player1();
            p2 = new Player2();
            food = new Food();
            wall = new Wall();
            this.addKeyListener(p1);
            this.addKeyListener(p2);
            count = 0;
            countdown = 60;
        }

        //游戏开始时
        if(process.isStarted && !process.isPaused && !process.isFailed){
            //蛇相关
            p1.eatFood(food);
            p2.eatFood(food);
            p1.isDead(p2,wall,process);
            p2.isDead(p1,wall,process);
            //防止食物生成时与蛇重合
            while (true) {
                if (food.onBlock(p1,wall) || food.onBlock(p2,wall))
                    food.updateFood(food.x,food.y);
                else{
                    food.paint(g);
                    break;
                }
            }
            p1.paint(g);
            p2.paint(g);
            wall.paint(g);

            g.setFont(new Font("arial",Font.BOLD,Game.BLOCK_SIDE*3));
            if(countdown <= 3)
                g.setColor(Color.RED);
            else if(countdown <= 30)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.WHITE);
            String s = countdown + " ";
            g.drawString(s, (Game.ROW*3/4) * Game.BLOCK_SIDE, (int)((Game.COL-0.5) * Game.BLOCK_SIDE));

        }

        process.paint(g);
    }

    //双重缓冲，防止画闪烁
    private Image offScreenImage = null;
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(Game.ROW * Game.BLOCK_SIDE, Game.COL * Game.BLOCK_SIDE);
        }
        Graphics offg = offScreenImage.getGraphics();
        paint(offg);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    //自动刷新
    public class MyThread extends Thread {
        public void run() {
            while (true){
                repaint();
                try{
                    if(p1.speed>p2.speed)
                        Thread.sleep(150-p1.speed);
                    else
                        Thread.sleep(150-p2.speed);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public class WallThread extends Thread {
        public void run() {
            while (true){
                if(!process.isPaused){
                    if(count<8)
                        countdown-=1;
                    if(countdown == 0) {
                        p1.lifes=1;
                        p2.lifes=1;//生命变为1
                        p1.isSuicide = false;
                        p2.isSuicide = false;//开始缩圈后 蛇允许撞自己
                        if(count < 8) {
                            wall.againstWall();
                            count++;
                            food.x = 1+2*count;
                            food.y = 2+count;//重新计算食物生成范围
                            countdown = 15;
                            if(count == 8)
                                countdown = 99;//缩圈8次后停止
                        }
                    }
                }
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

}


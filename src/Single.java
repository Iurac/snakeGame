import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Single extends Frame{
    Player1 p1 = new Player1();
    Player2 p2 = new Player2();
    Food food = new Food();
    Wall wall = new Wall();
    Time time = new Time();
    Process process = new Process();
    MyThread myThread = new MyThread();
    TimeThread timeThread = new TimeThread();
    Music music = new Music();

    public void goSanke() {
        this.setTitle("贪吃蛇大作战");
        this.setSize(Game.ROW * Game.BLOCK_SIDE, Game.COL * Game.BLOCK_SIDE);
        this.setLocation(300, 50);
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
    }

    public void timing(){
        timeThread.start();
        process.isTime = true;
    }

    //画布
    public void paint(Graphics g) {
        g.fillRect(0,0,Game.ROW * Game.BLOCK_SIDE, Game.COL * Game.BLOCK_SIDE);

        //界面分割线
        g.setColor(Color.WHITE);
        g.drawLine(0, (Game.COL - 3) * Game.BLOCK_SIDE, Game.ROW * Game.BLOCK_SIDE, (Game.COL - 3) * Game.BLOCK_SIDE);

        if(process.isStarted && process.isFailed){
            //字体--胜利
            Font font1 = new Font("TimesRoman",Font.ITALIC, Game.BLOCK_SIDE*4);
            FontMetrics fm1 = g.getFontMetrics(font1);
            g.setFont(font1);
            int textWidth1 = fm1.stringWidth("Game over");
            g.drawString("Game over", (Game.ROW * Game.BLOCK_SIDE - textWidth1) / 2, Game.COL / 2 * Game.BLOCK_SIDE);

            //字体--明细
            Font font2 = new Font("TimesRoman",Font.ITALIC, Game.BLOCK_SIDE);
            FontMetrics fm2 = g.getFontMetrics(font2);
            g.setFont(font2);
            int textWidth2 = fm2.stringWidth("score:"+p1.score);
            g.drawString("score:"+p1.score, (Game.ROW * Game.BLOCK_SIDE - textWidth2) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+4));
            int textWidth3 = fm2.stringWidth("len:"+p1.len);
            g.drawString("len:"+p1.len, (Game.ROW * Game.BLOCK_SIDE - textWidth3) / 2, Game.COL / 2 * (Game.BLOCK_SIDE+7));
        }

        //游戏未开始时初始化蛇，且为蛇添加监听器，为开始做准备
        if(!process.isStarted){
            p1 = new Player1();
            food = new Food();
            wall = new Wall();
            time = new Time();
            this.addKeyListener(p1);
            wall.singleWall();
        }

        //游戏开始时
        if(process.isStarted && !process.isPaused && !process.isFailed) {
            //蛇相关
            p1.eatFood(food);
            p1.isDead(p2, wall, process);

            //防止食物生成时与蛇重合
            while (true) {
                if (food.onBlock(p1,wall))
                    food.updateFood(food.x,food.y);
                else {
                    food.paint(g);
                    break;
                }
            }
            if (process.isTime) {
                p1.dieOfTime(time,process);
                p1.getTime(time);
                while (true) {
                    if (time.onBlock(wall))
                        time.updateClock();
                    else {
                        time.paint(g);
                        break;
                    }
                }
            }
            p1.paint(g);
            wall.paint(g);
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
                    Thread.sleep(150-p1.speed);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //倒计时
    public class TimeThread extends Thread {
        public void run() {
            while (true){
                if(!process.isPaused)
                    time.count--;
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

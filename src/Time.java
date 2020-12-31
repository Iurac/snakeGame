import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Time {
    int clockx;
    int clocky;
    int count;
    boolean appearClock;
    Random random = new Random();
    Image clock = new ImageIcon(Time.class.getResource("/img/time.png")).getImage();
//    Image clock= new ImageIcon(Time.class.getResource("/img/clock.png")).getImage();


    public Time(){
        appearClock = true;
        count = 60;
        clockx = 1+ Game.BLOCK_SIDE*(1+random.nextInt(Game.ROW-2));
        clocky = 1+ Game.BLOCK_SIDE*(2+random.nextInt(Game.COL-6));
    }

    public void updateClock(){
        clockx = 1+ Game.BLOCK_SIDE*(1+random.nextInt(Game.ROW-2));
        clocky = 1+ Game.BLOCK_SIDE*(2+random.nextInt(Game.COL-6));
    }

    public void paint(Graphics g){
        g.setFont(new Font("arial",Font.BOLD,Game.BLOCK_SIDE*3));
        if(count>40)
            g.setColor(Color.GREEN);
        else if(count>20)
            g.setColor(Color.ORANGE);
        else
            g.setColor(Color.RED);
        String s = count + " ";
        g.drawString(s, (Game.ROW - 7) * Game.BLOCK_SIDE, (int)((Game.COL-0.5) * Game.BLOCK_SIDE));

        if(appearClock)
            g.drawImage(clock,clockx,clocky,null);
    }

    public void timer(){
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                appearClock = true;
            }},20000);
    }

    public boolean onBlock(Wall wall){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < wall.wallx.length; j++) {
                if(clockx == wall.wallx[j] && clocky == wall.wally[j])
                    return true;
            }
        }
        return false;
    }
}

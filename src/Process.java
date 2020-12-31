import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Process implements KeyListener {
    boolean isStarted;
    boolean isPaused;
    boolean isFailed;
    boolean isTime;

    public Process() {
        isStarted = false;
        isFailed = false;
        isPaused = false;
        isTime = false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_P:
                if(isStarted == true && isFailed == false)
                    isPaused = !isPaused;
                break;
            case KeyEvent.VK_SPACE:
                if(isStarted == false) {
                    isStarted = true;
                    isFailed = false;
                }
                break;
            case KeyEvent.VK_R:
                if(isFailed == true){
                    isStarted = false;
                    isFailed = false;
                }
                break;
        }
    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        //帮助--字体
        Font font1 = new Font("arial",Font.BOLD,(int)(Game.BLOCK_SIDE*1.5));
        FontMetrics fm1 = g.getFontMetrics(font1);
        //屏幕中央\大字(VS)--字体
        Font font2 = new Font("TimesRoman",Font.ITALIC, Game.BLOCK_SIDE*4);
        FontMetrics fm2 = g.getFontMetrics(font2);
        //分数、长度等--字体

        if(isStarted){
            if(isFailed){
                g.setFont(font1);
                int textWidth = fm1.stringWidth("Press 'R' to restart");
                g.drawString("Press 'R' to restart",(Game.ROW* Game.BLOCK_SIDE-textWidth)/2,(Game.COL-1)* Game.BLOCK_SIDE);

            }
            else {
                if (!isPaused) {
                    g.setFont(font1);
                    int textWidth = fm1.stringWidth("Press 'P' to pause");
                    g.drawString("Press 'P' to pause", (Game.ROW * Game.BLOCK_SIDE - textWidth) / 2, (Game.COL - 1) * Game.BLOCK_SIDE);
                }
                else {
                    g.setFont(font1);
                    int textWidth = fm1.stringWidth("Press 'P' to continue");
                    g.drawString("Press 'P' to continue", (Game.ROW * Game.BLOCK_SIDE - textWidth) / 2, (Game.COL - 1) * Game.BLOCK_SIDE);
                    g.setFont(font2);
                    int textWidth2 = fm2.stringWidth("VS");
                    g.drawString("VS", (Game.ROW * Game.BLOCK_SIDE - textWidth2) / 2, Game.COL / 2 * Game.BLOCK_SIDE);
                }
            }
        }
        else {
            g.setFont(font1);
            int textWidth = fm1.stringWidth("Press 'SPACE' to start");
            g.drawString("Press 'SPACE' to start",(Game.ROW* Game.BLOCK_SIDE-textWidth)/2,(Game.COL-1)* Game.BLOCK_SIDE);
            g.setFont(font2);
            int textWidth2 = fm2.stringWidth("VS");
            g.drawString("VS",(Game.ROW* Game.BLOCK_SIDE-textWidth2)/2, Game.COL/2* Game.BLOCK_SIDE);

        }
    }

}

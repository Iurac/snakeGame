import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player2 extends Snake implements KeyListener {
    public Player2(){
        super();
        this.len = 3;
        this.snakex[0] = 4* Game.BLOCK_SIDE+1;
        this.snakey[0] = 8* Game.BLOCK_SIDE+1;
        this.snakex[1] = 3* Game.BLOCK_SIDE+1;
        this.snakey[1] = 8* Game.BLOCK_SIDE+1;
        this.snakex[2] = 2* Game.BLOCK_SIDE+1;
        this.snakey[2] = 8* Game.BLOCK_SIDE+1;
        this.fx = "R";
    }

    void paint(Graphics g) {
        if(fx == "U"){
            g.drawImage(direction[0],snakex[0],snakey[0],null);
        }if(fx == "D"){
            g.drawImage(direction[1],snakex[0],snakey[0],null);
        }if(fx == "L"){
            g.drawImage(direction[2],snakex[0],snakey[0],null);
        }if(fx == "R"){
            g.drawImage(direction[3],snakex[0],snakey[0],null);
        }
        for (int i = 1; i < len; i++) {
            g.drawImage(direction[4],snakex[i],snakey[i],null);
        }
        g.setFont(font3);
        g.setColor(Color.BLUE);
        g.drawString("score:"+score,(Game.ROW-5)* Game.BLOCK_SIDE,(Game.COL-2)* Game.BLOCK_SIDE);
        g.drawString("len:"+len,(Game.ROW-5)* Game.BLOCK_SIDE,(Game.COL-1)* Game.BLOCK_SIDE);

        for (int i = 0; i < lifes; i++) {
            g.drawImage(life,(Game.ROW-7-i)* Game.BLOCK_SIDE,(Game.COL-2)* Game.BLOCK_SIDE,null);
        }

        actionPerformed(e);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:if(fx != "R"){
                fx = "L";
            }
                break;
            case KeyEvent.VK_RIGHT:if(fx != "L"){
                fx = "R";
            }
                break;
            case KeyEvent.VK_UP:if(fx != "D"){
                fx = "U";
            }
                break;
            case KeyEvent.VK_DOWN:if(fx != "U"){
                fx = "D";
            }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}

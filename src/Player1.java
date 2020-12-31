import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player1 extends Snake implements KeyListener{
    public Player1(){
        super();
        this.len = 3;
        this.snakex[0] = 4* Game.BLOCK_SIDE+1;
        this.snakey[0] = 4* Game.BLOCK_SIDE+1;
        this.snakex[1] = 3* Game.BLOCK_SIDE+1;
        this.snakey[1] = 4* Game.BLOCK_SIDE+1;
        this.snakex[2] = 2* Game.BLOCK_SIDE+1;
        this.snakey[2] = 4* Game.BLOCK_SIDE+1;
        this.fx = "R";
    }

    void paint(Graphics g) {
        if(fx == "U"){
            g.drawImage(direction[5],snakex[0],snakey[0],null);
        }if(fx == "D"){
            g.drawImage(direction[6],snakex[0],snakey[0],null);
        }if(fx == "L"){
            g.drawImage(direction[7],snakex[0],snakey[0],null);
        }if(fx == "R"){
            g.drawImage(direction[8],snakex[0],snakey[0],null);
        }
        for (int i = 1; i < len; i++) {
            g.drawImage(direction[9],snakex[i],snakey[i],null);
        }

        g.setFont(font3);
        g.setColor(Color.GREEN);
        g.drawString("score:"+score, Game.BLOCK_SIDE,(Game.COL-2)* Game.BLOCK_SIDE);
        g.drawString("len:"+len, Game.BLOCK_SIDE,(Game.COL-1)* Game.BLOCK_SIDE);

        for (int i = 0; i < lifes; i++) {
            g.drawImage(life,(6+i)* Game.BLOCK_SIDE,(Game.COL-2)* Game.BLOCK_SIDE,null);
        }

        actionPerformed(e);
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:if(fx != "R"){
                fx = "L";
            }
                break;
            case KeyEvent.VK_D:if(fx != "L"){
                fx = "R";
            }
                break;
            case KeyEvent.VK_W:if(fx != "D"){
                fx = "U";
            }
                break;
            case KeyEvent.VK_S:if(fx != "U"){
                fx = "D";
            }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}

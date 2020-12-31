import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Help extends Frame {
    Image help = new ImageIcon(Help.class.getResource("/img/help.png")).getImage();

    public Help(){
        this.setTitle("帮助");
        this.setSize(361, 750);
        this.setLocation(500, 100);
        this.setBackground(Color.BLACK);
        this.setResizable(false);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void paint(Graphics g){
        g.drawImage(help,0,0,null);
        /*g.setColor(Color.WHITE);
        //玩家操作部分
        g.setFont(new Font("arial",Font.BOLD,25));

        g.drawImage(player1.direction[0],25,60,null);
        g.drawString("Player1",60,85);

        g.drawImage(player2.direction[5],25,130,null);
        g.drawString("Player2",60,155);

        g.setFont(new Font("arial",Font.BOLD,18));
        g.drawString("use 'W''S''A''D' to control",25,110);
        g.drawString("use '↑''↓''←''→' to control",25,180);

        //游戏部分--道具
        g.setFont(new Font("arial",Font.BOLD,18));
        g.drawImage(food.f[0],25,210,null);
        g.drawString("score +10 , len +1",60,230);//红苹果
        g.drawImage(food.f[1],25,250,null);
        g.drawString("score  -10 , len  -1",60,270);//紫苹果
        g.drawImage(food.f[2],25,290,null);
        g.drawString("score +50 , len +5",60,310);//鸡腿

        //游戏部分--玩法
        g.setFont(new Font("arial",Font.BOLD,15));
        g.drawString("Can't hit the other player's body",15,50);
*/
    }
}

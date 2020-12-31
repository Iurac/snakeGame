import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame{
    public static final int BLOCK_SIDE = 31;//方块边长
    public static int COL;//列
    public static int ROW;//行
    JPanel jPanel = new JPanel();

    Image title= new ImageIcon(Game.class.getResource("/img/title.png")).getImage();
    //    Image title = new ImageIcon("img/title.png").getImage();


    public Game(){
        this.setTitle("开始");
        this.setSize(250, 550);
        this.setLocation(720, 100);
        this.setBackground(Color.BLACK);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.validate();
        this.setVisible(true);
        this.add(jPanel);
    }

    public static void main(String[] args) {
        //开始界面
        Game game = new Game();
    }

    public void paint(Graphics g){
        g.drawImage(title,25,50,200,100,null);
        StartGame();

    }

    public void StartGame(){
        jPanel.setLayout(null);
        JButton button1 = new JButton("经典模式");
        JButton button2 = new JButton("无尽模式");
        JButton button3 = new JButton("对战模式");
        JButton button4 = new JButton("帮助");
        JButton button5 = new JButton("退出");

        button1.setBounds(25,150,200,60);
        button2.setBounds(25,225,200,60);
        button3.setBounds(25,300,200,60);
        button4.setBounds(25,375,200,60);
        button5.setBounds(25,450,200,60);

        jPanel.add(button1);
        jPanel.add(button2);
        jPanel.add(button3);
        jPanel.add(button4);
        jPanel.add(button5);


        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ROW = 40;
                COL = 30;
                Single single = new Single();
                single.goSanke();
                single.timing();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ROW = 40;
                COL = 30;
                Single single = new Single();
                single.goSanke();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ROW = 55;
                COL = 30;
                Against against = new Against();
                against.goSanke();
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Help help = new Help();//帮助界面
            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        button1.repaint();
        button2.repaint();
        button3.repaint();
        button4.repaint();
        button5.repaint();
        //重画按钮防止鼠标滑过按钮才出现
    }
}

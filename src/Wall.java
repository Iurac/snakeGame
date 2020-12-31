import javax.swing.*;
import java.awt.*;

public class Wall {
    int[] wallx = new int[500];
    int[] wally = new int[500];
    Image wall = new ImageIcon(Wall.class.getResource("/img/wall.png")).getImage();
//    Image wall= new ImageIcon(Game.class.getResource("/img/wall.png")).getImage();

    static int w;
    static int a;//初始围墙长度
    static int b;//初始围墙宽度
    static int count;//缩圈次数

    public Wall(){
        count=0;
        w =0;
        for (int i = 0; i < Game.ROW; i++) {
            wallx[w] = 1+i* Game.BLOCK_SIDE;
            wally[w] = 1+ Game.BLOCK_SIDE;
            w++;
        }
        a=w;
        for (int i = 0; i < Game.COL-3; i++) {
            wallx[w] = 1;
            wally[w] = 1+i* Game.BLOCK_SIDE;
            w++;
        }
        b=w-a;
        for (int i = 0; i < Game.ROW; i++) {
            wallx[w] = 1+i* Game.BLOCK_SIDE;
            wally[w] = 1+(Game.COL-4)* Game.BLOCK_SIDE;
            w++;
        }
        for (int i = 0; i < Game.COL-3; i++) {
            wallx[w] = 1+(Game.ROW-1)* Game.BLOCK_SIDE;
            wally[w] = 1+i* Game.BLOCK_SIDE;
            w++;
        }
    }

    public void singleWall(){
        for (int i = 0; i < 6; i++) {
            wallx[w] = 1+(2+i)* Game.BLOCK_SIDE;
            wally[w] = 1+8* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(Game.ROW-8+i)* Game.BLOCK_SIDE;
            wally[w] = 1+8* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(2+i)* Game.BLOCK_SIDE;
            wally[w] = 1+(Game.COL-11)* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(Game.ROW-8+i)* Game.BLOCK_SIDE;
            wally[w] = 1+(Game.COL-11)* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(Game.ROW-8+i)* Game.BLOCK_SIDE;
            wally[w] = 1+(Game.COL-11)* Game.BLOCK_SIDE;
            w++;
        }
        for (int i = 0; i < 10; i++) {
            wallx[w] = 1+15* Game.BLOCK_SIDE;
            wally[w] = 1+(9+i)* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(Game.ROW-16)* Game.BLOCK_SIDE;
            wally[w] = 1+(9+i)* Game.BLOCK_SIDE;
            w++;
        }
        for (int i = 0; i < 14; i++) {
            wallx[w] = 1+(13+i)* Game.BLOCK_SIDE;
            wally[w] = 1+4* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(13+i)* Game.BLOCK_SIDE;
            wally[w] = 1+(Game.COL-8)* Game.BLOCK_SIDE;
            w++;
        }
    }

    public void againstWall(){
        w=0;
        for (int i = 0; i < 2*(a+b); i++) {
            wallx[w] = 1000;
            wally[w] = 1000;
            w++;
        }
        w=0;
        a-=4;
        b-=2;
        count++;
        for (int i = 1; i < a; i++) {
            wallx[w] = 1+(2*count+i)*Game.BLOCK_SIDE;
            wally[w] = 1+(count+1)* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+((2*count+i)*Game.BLOCK_SIDE);
            wally[w] = 1+(Game.COL-4-count)* Game.BLOCK_SIDE;
            w++;
        }
        for (int i = 1; i < b; i++) {
            wallx[w] = 1+2*count* Game.BLOCK_SIDE;
            wally[w] = 1+(count+i)* Game.BLOCK_SIDE;
            w++;
            wallx[w] = 1+(Game.ROW-1-2*count)* Game.BLOCK_SIDE;
            wally[w] = 1+(count+i)* Game.BLOCK_SIDE;
            w++;
        }

    }

    public void paint(Graphics g){
        for (int i = 0; i < w; i++) {
            g.drawImage(wall,wallx[i],wally[i],null);
        }
    }


}

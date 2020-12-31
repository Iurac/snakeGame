import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Music{
    public URL bgmurl = this.getClass().getResource("/music/bgm.wav");
    AudioClip bgm = Applet.newAudioClip(bgmurl);

    public void playMusic(){
        bgm.loop();//开始播放
    }

    public void stopMusic(){
        bgm.stop();
    }

}
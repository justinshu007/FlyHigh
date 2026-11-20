import greenfoot.*;
import javax.sound.sampled.*;
import java.io.File;
/**
 * Write a description of class SFX here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SFX  
{
    private static GreenfootSound sfxBenar;
    private static GreenfootSound sfxSalah;
    private static GreenfootSound sfxGameOver;
    private static GreenfootSound sfxHover;
    private static GreenfootSound sfxClick;
    private static GreenfootSound sfxLevelUp; 
    private static GreenfootSound sfxLiveUp; 
    
    // Pool 4 Clip di RAM khusus hover agar pindah tombol secepat apa pun selalu bunyi
    private static Clip[] poolHover = new Clip[4];
    private static int idxHover = 0;
    static {
        try {
            sfxBenar    = new GreenfootSound("dingSFX.wav");
            sfxSalah    = new GreenfootSound("wrongSFX.wav");
            sfxGameOver = new GreenfootSound("gameOver.wav");
            sfxHover    = new GreenfootSound("hoverSFX.wav");    // Sesuaikan nama file audio hover
            sfxClick    = new GreenfootSound("clickSFX.wav");    // Sesuaikan nama file audio click
            sfxLevelUp    = new GreenfootSound("liveUpSFX.mp3");
            sfxLiveUp    = new GreenfootSound("levelUpSFX.mp3");
            
            // Muat hoverSFX.wav langsung ke RAM (4 channel bergantian)
            File fileHover = new File("sounds/hoverSFX.wav");
            if (fileHover.exists()) {
                for (int i = 0; i < poolHover.length; i++) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(fileHover);
                    poolHover[i] = AudioSystem.getClip();
                    poolHover[i].open(ais);
                }
            }
            setVolume(SetSFXVolume.volume);
            setVolume(SetSFXVolume.volume);
        } catch (Exception e) {
            // Aman jika salah satu file audio belum ada di folder sounds
        }
    }
    
    public static void playBenar()    { mainkan(sfxBenar); }
    public static void playSalah()    { mainkan(sfxSalah); }
    public static void playGameOver() { mainkan(sfxGameOver); }
    public static void playHover()    { 
        Clip c = poolHover[idxHover];
        idxHover = (idxHover + 1) % poolHover.length;
        if (c != null) {
            try {
                // Atur volume sesuai slider SFX (0 - 100)
                if (c.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gain = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                    float vol = SetSFXVolume.volume / 100.0f;
                    float dB = (vol <= 0.001f) ? gain.getMinimum() : (float)(20.0 * Math.log10(vol));
                    gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
                }
                c.stop();
                c.setFramePosition(0); // Reset instan ke milidetik ke-0 di RAM
                c.start();
            } catch (Exception e) {}
        }
    }
    public static void playClick()    { mainkan(sfxClick); }
    public static void playLevelUp()  { mainkan(sfxLevelUp); }
    public static void playLiveUp()  { mainkan(sfxLiveUp); } 
    private static void mainkan(GreenfootSound sound)
    {
        if (sound != null) {
            try {
                sound.setVolume(SetSFXVolume.volume);
                sound.stop();
                sound.play();
            } catch (Exception e) {}
        }
    }

    public static void setVolume(int vol)
    {
        if (sfxBenar != null)    sfxBenar.setVolume(vol);
        if (sfxSalah != null)    sfxSalah.setVolume(vol);
        if (sfxGameOver != null) sfxGameOver.setVolume(vol);
        if (sfxHover != null)    sfxHover.setVolume(vol);
        if (sfxClick != null)    sfxClick.setVolume(vol);
        if (sfxLevelUp != null)  sfxLevelUp.setVolume(vol); 
        if (sfxLiveUp != null)  sfxLiveUp.setVolume(vol); 
    }
}

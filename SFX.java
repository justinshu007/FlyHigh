import greenfoot.*;

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
    
    static {
        try {
            sfxBenar    = new GreenfootSound("dingSFX.wav");
            sfxSalah    = new GreenfootSound("wrongSFX.wav");
            sfxGameOver = new GreenfootSound("gameOver.wav");
            sfxHover    = new GreenfootSound("hoverSFX.wav");    // Sesuaikan nama file audio hover
            sfxClick    = new GreenfootSound("clickSFX.wav");    // Sesuaikan nama file audio click
            setVolume(SetSFXVolume.volume);
        } catch (Exception e) {
            // Aman jika salah satu file audio belum ada di folder sounds
        }
    }
    
    public static void playBenar()    { mainkan(sfxBenar); }
    public static void playSalah()    { mainkan(sfxSalah); }
    public static void playGameOver() { mainkan(sfxGameOver); }
    public static void playHover()    { mainkan(sfxHover); }
    public static void playClick()    { mainkan(sfxClick); }
    
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
    }
}

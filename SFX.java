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

    // Inisialisasi audio secara otomatis
    static {
        try {
            sfxBenar = new GreenfootSound("dingSFX.mp3");
            sfxSalah = new GreenfootSound("wrongSFX.mp3");
            sfxGameOver = new GreenfootSound("gameOver.mp3");
            
            // Set volume awal mengikuti SetSFXVolume
            setVolume(SetSFXVolume.volume);
        } catch (Exception e) {
            // Perlindungan jika file belum ada di folder sounds
        }
    }

    public static void playBenar()
    {
        mainkanAman(sfxBenar);
    }

    public static void playSalah()
    {
        mainkanAman(sfxSalah);
    }

    public static void playGameOver()
    {
        mainkanAman(sfxGameOver);
    }

    private static void mainkanAman(GreenfootSound sound)
    {
        if (sound != null) {
            try {
                sound.setVolume(SetSFXVolume.volume);
                sound.stop(); // Hentikan playback sebelumnya agar responsif saat beruntun
                sound.play();
            } catch (Exception e) {}
        }
    }

    public static void setVolume(int vol)
    {
        if (sfxBenar != null) sfxBenar.setVolume(vol);
        if (sfxSalah != null) sfxSalah.setVolume(vol);
        if (sfxGameOver != null) sfxGameOver.setVolume(vol);
    }
}

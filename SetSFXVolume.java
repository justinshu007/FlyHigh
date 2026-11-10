/**
 * Write a description of class SetSFXVolume here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SetSFXVolume
{
    // Nilai volume SFX global (0 - 100), default 70
    public static int volume = 70;

    public static void updateVolume()
    {
        SFX.setVolume(volume);
    }
}

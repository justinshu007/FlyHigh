/**
 * Write a description of class SetSFXVolume here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SetSFXVolume
{
    public static int volume = 100;

    public static void updateVolume()
    {
        SFX.setVolume(volume);
    }
}

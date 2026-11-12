import greenfoot.*;

public class SetVolume
{
    public static int volume = 100;

    public static void updateVolume()
    {
        Music.setVolume(volume);
    }
}
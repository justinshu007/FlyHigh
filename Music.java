import greenfoot.*;

public class Music
{
    private static GreenfootSound backsound =
        new GreenfootSound("backsound.mp3");

    public static void play()
    {
        if (!backsound.isPlaying())
        {
            backsound.playLoop();
        }
    }

    public static void setVolume(int volume)
    {
        backsound.setVolume(volume);
    }

    public static int getVolume()
    {
        return backsound.getVolume();
    }
}
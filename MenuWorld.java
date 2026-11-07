import greenfoot.*;

public class MenuWorld extends World
{
    private boolean musicStarted = false;

    public MenuWorld()
    {
        super(750, 420, 1);

        setBackground("background.png");

        addObject(new StartButton(), 375, 180);
        addObject(new SettingsButton(), 375, 270);
    }

    public void act()
    {
        if (!musicStarted)
        {
            Music.play();
            musicStarted = true;
        }
    }
}
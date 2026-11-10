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
        prepare();
    }

    public void act()
    {
        if (!musicStarted)
        {
            Music.play();
            musicStarted = true;
        }
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
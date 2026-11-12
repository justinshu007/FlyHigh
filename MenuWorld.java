import greenfoot.*;

public class MenuWorld extends World
{
    private boolean musicStarted = false;

    public MenuWorld()
    {
        super(750, 420, 1);

        setBackground("background.png");
        
        addObject(new Title(), 375,200);
        addObject(new StartButton(), 320, 300);
        addObject(new SettingsButton(), 470, 300);
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
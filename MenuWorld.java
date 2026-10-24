import greenfoot.*;

public class MenuWorld extends World
{
    public MenuWorld()
    {
        super(750, 420, 1);

        setBackground("background.png");

        addObject(new StartButton(), 375, 210);
    }
}


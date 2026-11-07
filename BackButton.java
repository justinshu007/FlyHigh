import greenfoot.*;

public class BackButton extends Actor
{
    public BackButton()
    {
        setImage("buttonBack.png");
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
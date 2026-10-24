import greenfoot.*;

public class StartButton extends Actor
{
    public StartButton()
    {
        setImage("buttonStart.png");
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new MyWorld());
        }
    }
}
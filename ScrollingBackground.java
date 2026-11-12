import greenfoot.*;

public class ScrollingBackground extends Actor
{
    private int speed = 2;

    public ScrollingBackground(String file)
    {
        GreenfootImage img = new GreenfootImage(file);
        img.scale(750, 420);
        setImage(img);
    }

    public void act()
    {
        setLocation(getX() - speed, getY());

        if(getX() <= -375)
        {
            setLocation(getX() + 1500, getY());
        }
    }
}
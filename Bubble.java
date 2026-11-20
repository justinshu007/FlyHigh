import greenfoot.*;

public class Bubble extends Actor
{
    private int speed;
    private int drift;

    public Bubble()
    {
        int pilih = Greenfoot.getRandomNumber(3);
    
        if (pilih == 0)
        {
            setImage("bubble1.png");
        }
        else if (pilih == 1)
        {
            setImage("bubble2.png");
        }
        else
        {
            setImage("bubble3.png");
        }
    
        // Perbesar 3x
        GreenfootImage img = getImage();
        img.scale(img.getWidth() * 3, img.getHeight() * 3);
        setImage(img);
    
        speed = Greenfoot.getRandomNumber(2);
        drift = Greenfoot.getRandomNumber(4) - 2;
    }

    public void act()
    {
        setLocation(
            getX() + drift,
            getY() - speed
        );

            if (isAtEdge())
        {
            getWorld().removeObject(this);
        }   
    }
}
import greenfoot.*;

public class Koi extends Actor
{
    private GreenfootImage[] frames = new GreenfootImage[8];
    private int currentFrame = 0;
    private int animationCounter = 0;

    private int startY;
    private int amplitude = 30;
    private int speed = 2;
    private int direction = 1;
    private int health = 3;

    public Koi()
    {
        frames[0] = new GreenfootImage("koi_fish0.png");
        frames[1] = new GreenfootImage("koi_fish1.png");
        frames[2] = new GreenfootImage("koi_fish2.png");
        frames[3] = new GreenfootImage("koi_fish3.png");
        frames[4] = new GreenfootImage("koi_fish4.png");
        frames[5] = new GreenfootImage("koi_fish5.png");
        frames[6] = new GreenfootImage("koi_fish6.png");
        frames[7] = new GreenfootImage("koi_fish7.png");
        
        for (int i = 0; i < frames.length; i++)
        {
            frames[i].scale(150, 77);
        }

        setImage(frames[0]);
    }

    protected void addedToWorld(World world)
    {
        startY = getY();
    }

    public void act()
    {
        animateKoi();
        moveByKeyboard();
    }

    private void animateKoi()
    {
        animationCounter++;

        if (animationCounter >= 5)
        {
            animationCounter = 0;
            currentFrame++;

            if (currentFrame >= frames.length)
            {
                currentFrame = 0;
            }

            setImage(frames[currentFrame]);
        }
    }
    
    private void moveByKeyboard()
    {
    int speed = 3;

        if (Greenfoot.isKeyDown("w"))
        {
            setLocation(getX(), getY() - speed);
        }

        if (Greenfoot.isKeyDown("s"))
        {
            setLocation(getX(), getY() + speed);
        }
    }
    
    public int getHealth()
    {
        return health;
    }

    public void damage()
    {
        health--;
    
        System.out.println("HP = " + health);
    
        if(health <= 0)
        {
            health = 0;
    
            System.out.println("GAME OVER");
    
            Greenfoot.stop();
        }
    }
}
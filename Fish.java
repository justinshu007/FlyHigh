import greenfoot.*;

public class Fish extends Actor
{
    private GreenfootImage[] frames;
    private int currentFrame = 0;
    private int animationCounter = 0;

    public Fish()
    {
        frames = new GreenfootImage[8];

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
            frames[i].scale(150, 80);
        }

        setImage(frames[0]);
    }

    public void act()
    {
        animate();
    }

    private void animate()
    {
        animationCounter++;

        if (animationCounter >= 8)
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
}
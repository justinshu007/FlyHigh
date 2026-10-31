import greenfoot.*;

public class MyWorld extends World
{
    private GreenfootImage bgA;
    private GreenfootImage bgB;

    private int scrollX = 0;
    private int speed = 2;

    public MyWorld()
    {
        super(750, 420, 1);

        bgA = new GreenfootImage("background.png");
        bgB = new GreenfootImage("background1.png");

        bgA.scale(750, 420);
        bgB.scale(750, 420);

        Koi fish = new Koi();
        addObject(fish, 150, 220);
    }

    public void act()
    {
        scrollBackground();
    }

    private void scrollBackground()
    {
        scrollX -= speed;

        if(scrollX <= -1500)
        {
            scrollX = 0;
        }

        GreenfootImage bg = getBackground();
        bg.clear();

        bg.drawImage(bgA, scrollX, 0);
        bg.drawImage(bgB, scrollX + 750, 0);

        bg.drawImage(bgA, scrollX + 1500, 0);
        bg.drawImage(bgB, scrollX + 2250, 0);
    }
}
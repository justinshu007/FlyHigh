import greenfoot.*;

public class MyWorld extends World
{
    private GreenfootImage bgA;
    private GreenfootImage bgB;
    private Koi fish;
    private Heart hearts;

    private int scrollX = 0;
    private int speed = 2;
    private int spawnCounter = 0;

    public MyWorld()
    {
        super(750, 420, 1);

        bgA = new GreenfootImage("background.png");
        bgB = new GreenfootImage("background1.png");

        bgA.scale(750, 420);
        bgB.scale(750, 420);

        Koi fish = new Koi();
        addObject(fish, 150, 220);
        
        hearts = new Heart(fish);
        addObject(hearts, 90, 35);
    }

    public void act()
    {
        scrollBackground();
        actPizza();
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
    
    public void actPizza()
    {
        spawnCounter++;

        if (spawnCounter >= 100)
        {
            int randomY = Greenfoot.getRandomNumber(340) + 40;
    
            addObject(new Pizza(), 780, randomY);
    
            spawnCounter = 0;
        }
    }
}
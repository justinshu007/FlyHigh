import greenfoot.*;

public class Heart extends Actor
{
    private Koi fish;

    public Heart(Koi fish)
    {
        this.fish = fish;
        updateImage();
    }

    public void act()
    {
        updateImage();
    }

    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(150, 50);

        for(int i = 0; i < fish.getHealth(); i++)
        {
            GreenfootImage heart = new GreenfootImage("heart.png");
            heart.scale(40, 40);

            img.drawImage(heart, i * 45, 0);
        }

        setImage(img);
    }
}
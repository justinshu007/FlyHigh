import greenfoot.*;

public class TutorialOverlay extends Actor
{
    public TutorialOverlay()
    {
        GreenfootImage img =
            new GreenfootImage(750, 420);

        img.setColor(new Color(0, 0, 0, 180));

        img.fill();

        setImage(img);
    }
}
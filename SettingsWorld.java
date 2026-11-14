import greenfoot.*;

public class SettingsWorld extends World
{
    private int bubbleTimer = 0;
    
    public SettingsWorld()
    {
        super(750, 420, 1);

        setBackground("background.png");
        
        addObject(new Box(), 375, 210);
        
        addObject(new SettingsWord(), 375, 80);

        addObject(new VolumeIcon(), 185, 200);

        addObject(new SliderBar(), 410, 200);
        
        VolumeSlider slider = new VolumeSlider();

        int startX =
            250 + SetVolume.volume * (500 - 250) / 100;

        addObject(slider, startX, 200);

        addObject(new BackButton(), 375, 320);
    }
    
    public void act()
    {
        bubbleTimer++;
        
        if (bubbleTimer >= 20)
        {
            bubbleTimer = 0;
        
            addObject(
                new Bubble(),
                Greenfoot.getRandomNumber(getWidth()),
                getHeight() + 10
            );
        }
    }
}
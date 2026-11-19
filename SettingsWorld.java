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

        // SLIDER BACKSOUND (ATAS)
        addObject(new VolumeIcon(), 185, 165);
        addObject(new SliderBar(), 410, 165);
        VolumeSlider sliderMusic = new VolumeSlider(false);
        int startXMusic = 250 + SetVolume.volume * (570 - 250) / 100;
        addObject(sliderMusic, startXMusic, 165);

        // SLIDER SFX (BAWAH)
        addObject(new SFXIcon(), 185, 240);
        addObject(new SliderBar(), 410, 245);
        VolumeSlider sliderSFX = new VolumeSlider(true);
        int startXSFX = 250 + SetSFXVolume.volume * (570 - 250) / 100;
        addObject(sliderSFX, startXSFX, 245);

        // TOMBOL BACK
        addObject(new BackButton(), 375, 335);
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
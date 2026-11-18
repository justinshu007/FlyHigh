import greenfoot.*;

public class VolumeSlider extends Actor
{
    private int minX = 250;
    private int maxX = 570;
    private boolean isSFX; // false = Music, true = SFX

    public VolumeSlider()
    {
        this(false);
    }

    public VolumeSlider(boolean isSFX)
    {
        this.isSFX = isSFX;
        setImage("sliderKnob.png");
        getImage().scale(28, 60);
    }

    public void act()
    {
        if (Greenfoot.mouseDragged(this))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null)
            {
                int newX = mouse.getX();
                if (newX < minX) newX = minX;
                if (newX > maxX) newX = maxX;

                setLocation(newX, getY());

                int volume = (newX - minX) * 100 / (maxX - minX);

                if (isSFX)
                {
                    SetSFXVolume.volume = volume;
                    SetSFXVolume.updateVolume();
                }
                else
                {
                    SetVolume.volume = volume;
                    SetVolume.updateVolume();
                }
            }
        }

        // Bunyikan contoh klik saat slider SFX selesai digeser
        if (isSFX && Greenfoot.mouseDragEnded(this))
        {
            SFX.playClick();
        }
    }
}
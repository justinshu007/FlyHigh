import greenfoot.*;

public class VolumeSlider extends Actor
{
    private int minX = 250;
    private int maxX = 570;

    public VolumeSlider()
    {
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

                if (newX < minX)
                {
                    newX = minX;
                }

                if (newX > maxX)
                {
                    newX = maxX;
                }

                setLocation(newX, getY());

                int volume = (newX - minX) * 100 / (maxX - minX);

                SetVolume.volume = volume;
                SetVolume.updateVolume();
            }
        }
    }
}
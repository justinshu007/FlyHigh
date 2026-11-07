import greenfoot.*;

public class SettingsButton extends Actor
{
    public SettingsButton()
    {
        setImage("buttonSetting.png");
    }

    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new SettingsWorld());
        }
    }
}
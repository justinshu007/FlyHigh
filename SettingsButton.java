import greenfoot.*;

public class SettingsButton extends InteractiveButton
{
    public SettingsButton()
    {
        setImage("buttonSetting.png");
        siapkanEfekVisual();
    }

    public void act()
    {
        jalankanEfekTombol();
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new SettingsWorld());
        }
    }
}
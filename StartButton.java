import greenfoot.*;
import java.util.List;

public class StartButton extends Actor
{
    public StartButton()
    {
        setImage("buttonStart.png");
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            World dunia = getWorld();
            
            // 2. Hapus tombol Start ini dari layar menu
            dunia.removeObject(this);
            
            List<SettingsButton> settings = dunia.getObjects(SettingsButton.class);

            if (!settings.isEmpty())
            {
                dunia.removeObject(settings.get(0));
            }
            
            dunia.addObject(new LevelBox(), 375, 200);
            // 1. Munculkan 3 tombol tingkat kesulitan di menu
            dunia.addObject(new BtnEasy(), dunia.getWidth() / 2, 145);
            dunia.addObject(new BtnMedium(), dunia.getWidth() / 2, 220);
            dunia.addObject(new BtnHard(), dunia.getWidth() / 2, 300);
            
            
            
            
        }
    }
}
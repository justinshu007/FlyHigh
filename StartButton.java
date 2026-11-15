import greenfoot.*;
import java.util.List;

//public class StartButton extends Actor
public class StartButton extends InteractiveButton
{
    public StartButton()
    {
        setImage("buttonStart.png");
        siapkanEfekVisual();
    }

    public void act()
    {
        jalankanEfekTombol(); //cek efek
        if (Greenfoot.mouseClicked(this))
        {
            World dunia = getWorld();
            
            List<SettingsButton> settings = dunia.getObjects(SettingsButton.class);
            if (!settings.isEmpty())
            {
                dunia.removeObject(settings.get(0));
            }
            
            dunia.addObject(new LevelBox(), 375, 200);
            dunia.addObject(new BtnEasy(), dunia.getWidth() / 2, 145);
            dunia.addObject(new BtnMedium(), dunia.getWidth() / 2, 220);
            dunia.addObject(new BtnHard(), dunia.getWidth() / 2, 300);
            
            // Hapus tombol Start di baris paling akhir setelah selesai menggunakan getWorld()
            dunia.removeObject(this);
        }
    }
}
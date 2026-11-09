import greenfoot.*;

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
            
            // 1. Munculkan 3 tombol tingkat kesulitan di menu
            dunia.addObject(new BtnEasy(), dunia.getWidth() / 2, 160);
            dunia.addObject(new BtnMedium(), dunia.getWidth() / 2, 240);
            dunia.addObject(new BtnHard(), dunia.getWidth() / 2, 320);
            
            // 2. Hapus tombol Start ini dari layar menu
            dunia.removeObject(this);
        }
    }
}
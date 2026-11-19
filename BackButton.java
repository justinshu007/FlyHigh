import greenfoot.*;

public class BackButton extends InteractiveButton
{
    public BackButton()
    {
        
        GreenfootImage gambar = getImage();
        gambar.scale(220, 60);
        setImage(gambar);
        siapkanEfekVisual();
    }

    public void act()
    {
        jalankanEfekTombol();
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
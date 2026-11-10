import greenfoot.*;

public class BackButton extends Actor
{
    public BackButton()
    {
        
        GreenfootImage gambar = getImage();
        gambar.scale(150, 60);
        setImage(gambar);
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
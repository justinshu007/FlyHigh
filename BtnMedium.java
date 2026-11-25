import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnMedium here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnMedium extends InteractiveButton
{
    public BtnMedium()
    {
        GreenfootImage img = getImage();
        img.scale(175, 75);
        setImage(img);
        siapkanEfekVisual();
        
    }
    public void act()
    {
        jalankanEfekTombol();
        if (Greenfoot.mouseClicked(this))
        {
            // Pindah ke MyWorld dengan batas angka 1-5 (Medium)
            Greenfoot.setWorld(new MyWorld(5));
        }
    }
}

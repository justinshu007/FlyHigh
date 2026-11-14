import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnMedium here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnMedium extends Actor
{
    public BtnMedium()
    {
        GreenfootImage img = getImage();
        img.scale(175, 75);
        setImage(img);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            // Pindah ke MyWorld dengan batas angka 1-3 (Easy)
            Greenfoot.setWorld(new MyWorld(5));
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnHard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnHard extends Actor
{
    public BtnHard()
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
            Greenfoot.setWorld(new MyWorld(10));
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnHard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnHard extends InteractiveButton
{
    public BtnHard()
    {
        GreenfootImage img = getImage();
        img.scale(175, 75);
        setImage(img);siapkanEfekVisual();
        
    }
    public void act()
    {
        jalankanEfekTombol();
        if (Greenfoot.mouseClicked(this))
        {
            // Pindah ke MyWorld dengan batas angka 1-3 (Easy)
            Greenfoot.setWorld(new MyWorld(10));
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnMedium here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnMedium extends Actor
{
    /**
     * Act - do whatever the BtnMedium wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            // Pindah ke MyWorld dengan batas angka 1-3 (Easy)
            Greenfoot.setWorld(new MyWorld(5));
        }
    }
}

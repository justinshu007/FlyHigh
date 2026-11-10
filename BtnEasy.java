import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnEasy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnEasy extends Actor
{
    /**
     * Act - do whatever the BtnEasy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BtnEasy(){
        
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            // Pindah ke MyWorld dengan batas angka 1-3 (Easy)
            Greenfoot.setWorld(new MyWorld(3));
            
        }
    }
}

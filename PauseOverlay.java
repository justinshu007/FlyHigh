import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseOverlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PauseOverlay extends Actor
{
    public PauseOverlay()
    {
        GreenfootImage gelap = new GreenfootImage(750, 420);
        gelap.setColor(new Color(0, 0, 0, 140)); // Tingkat kegelapan (0-255)
        gelap.fill();
        setImage(gelap);
    }
}

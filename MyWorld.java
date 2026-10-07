import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    private DialogueBox dialogue;
    private Fish fish;
    public MyWorld()
    {    
        super(750, 420, 1);

        setBackground("background1.png");
        
        fish = new Fish();
        addObject(fish, 150, 220);

    }
        
}

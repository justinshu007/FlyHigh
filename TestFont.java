import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.GraphicsEnvironment;
/**
 * Write a description of class testing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class TestFont extends Actor
{
    public static void printFonts()
    {
        String[] fonts =
            GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        for(String font : fonts)
        {
            System.out.println(font);
        }
    }
}

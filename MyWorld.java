import greenfoot.*;

public class MyWorld extends World
{
    private DialogueBox dialogue;
    private Koi fish;
    public MyWorld()
    {    
        super(750, 420, 1);

        setBackground("background.png");
        
        fish = new Koi();
        addObject(fish, 150, 220);

    }
        
}

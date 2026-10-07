import greenfoot.*;

public class DialogueBox extends Actor
{
    public DialogueBox(String name, String text)
    {
        GreenfootImage image = new GreenfootImage(1100, 190);

        // Main dialogue box
        image.setColor(Color.BLACK);
        image.setTransparency(200);
        image.fill();

        // Border
        image.setTransparency(255);
        image.setColor(Color.WHITE);
        image.drawRect(0, 0, 1099, 189);

        // Name plate
        image.setColor(Color.DARK_GRAY);
        image.fillRect(25, 10, 200, 40);

        // Name
        image.setColor(Color.WHITE);
        image.setFont(new Font("Arial", true, false, 24));
        image.drawString("name", 40, 38);

        // Dialogue text
        image.setFont(new Font("Arial", false, false, 22));
        image.drawString(text, 35, 90);

        // Continue indicator
        image.setFont(new Font("Arial", true, false, 25));
        image.drawString("▼", 1040, 165);

        setImage(image);
    }
}
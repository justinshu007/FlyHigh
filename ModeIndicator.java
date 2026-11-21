import greenfoot.*;

public class ModeIndicator extends Actor
{
    public void updateMode(CoinType mode)
    {
        GreenfootImage img = new GreenfootImage(300, 70);
        img.clear();

        Font pixelFont = new Font("Pixelify Sans", true, false, 20);
        img.setFont(pixelFont);

        String text = "MODE : " + mode.toString();

        // Outline biru tua
        Color outline = new Color(10, 20, 80);

        img.setColor(outline);

        img.drawString(text, 12, 32);
        img.drawString(text, 16, 32);
        img.drawString(text, 14, 30);
        img.drawString(text, 14, 34);

        // Teks utama
        if(mode == CoinType.GOLD)
        {
            img.setColor(new Color(255,215,0));
        }
        else if(mode == CoinType.SILVER)
        {
            img.setColor(new Color(220,220,220));
        }
        else
        {
            img.setColor(new Color(205,127,50));
        }

        img.drawString(text, 14, 32);

        setImage(img);
    }

    public void act()
    {
    }
}
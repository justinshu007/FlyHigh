import greenfoot.*;

public class FloatingMessage extends Actor
{
    private int timer = 90; // sekitar 1.5 detik

    public FloatingMessage(String pesan)
    {
        GreenfootImage img = new GreenfootImage(500, 80);

        Font font = new Font("Pixelify Sans", true, false, 28);

        img.setFont(font);

        Color outline = new Color(10, 20, 80);

        int x = 20;
        int y = 45;

        // Outline biru tua
        img.setColor(outline);

        img.drawString(pesan, x - 2, y);
        img.drawString(pesan, x + 2, y);
        img.drawString(pesan, x, y - 2);
        img.drawString(pesan, x, y + 2);

        img.drawString(pesan, x - 2, y - 2);
        img.drawString(pesan, x + 2, y - 2);
        img.drawString(pesan, x - 2, y + 2);
        img.drawString(pesan, x + 2, y + 2);

        // Isi putih
        img.setColor(Color.WHITE);
        img.drawString(pesan, x, y);

        setImage(img);
    }

    public void act()
    {
        timer--;

        // Sedikit naik ke atas
        setLocation(getX(), getY() - 1);

        if(timer <= 0)
        {
            getWorld().removeObject(this);
        }
    }
}
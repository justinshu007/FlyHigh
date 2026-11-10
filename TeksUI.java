import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TeksUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TeksUI extends Actor
{
    /**
     * Act - do whatever the TeksUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act()
    {
        // Add your action code here.
    }
    
    public TeksUI(String isiTeks, int ukuranFont, String namaFont)
    {
        ubahTeks(isiTeks, ukuranFont, namaFont);
    }
    public void ubahTeks(String teksBaru, int ukuranFont, String namaFont)
    {
        // Kanvas transparan
        GreenfootImage kanvas = new GreenfootImage(350, 60);
        Font fontKustom = new Font(namaFont, true, false, ukuranFont);
        kanvas.setFont(fontKustom);

        int posX = 10;
        int posY = ukuranFont + 10;

        // 1. Gambar outline/stroke hitam di sekeliling teks (offset 2 piksel)
        kanvas.setColor(Color.BLACK);
        kanvas.drawString(teksBaru, posX - 2, posY);
        kanvas.drawString(teksBaru, posX + 2, posY);
        kanvas.drawString(teksBaru, posX, posY - 2);
        kanvas.drawString(teksBaru, posX, posY + 2);
        kanvas.drawString(teksBaru, posX - 2, posY - 2);
        kanvas.drawString(teksBaru, posX + 2, posY - 2);
        kanvas.drawString(teksBaru, posX - 2, posY + 2);
        kanvas.drawString(teksBaru, posX + 2, posY + 2);

        // 2. Gambar teks utama berwarna merah di atas outline hitam
        kanvas.setColor(Color.WHITE);
        kanvas.drawString(teksBaru, posX, posY);

        setImage(kanvas);
    }
}

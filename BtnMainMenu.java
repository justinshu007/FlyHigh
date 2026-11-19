import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnMainMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnMainMenu extends InteractiveButton
{
    /**
     * Act - do whatever the BtnMainMenu wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BtnMainMenu(){
        setImage("btnMainMenu.png"); // Sesuaikan nama file aset tombol Back-mu
        //MENGUBAH UKURAN GAMBAR
        GreenfootImage img = getImage();
        int lebarBaru = (int)(img.getWidth() * 2.7);   // Ubah 0.6 sesuai selera (misal 0.5 untuk setengah)
        int tinggiBaru = (int)(img.getHeight() * 2.7);
        img.scale(Math.max(1, lebarBaru), Math.max(1, tinggiBaru));
        setImage(img);
        siapkanEfekVisual();
    }
    public void act()
    {
        jalankanEfekTombol();

        // Gunakan mouseClicked saja agar klik tidak bocor ke World berikutnya
        if (Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}

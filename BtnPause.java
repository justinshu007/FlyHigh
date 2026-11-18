import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnPause here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnPause extends InteractiveButton
{
    public BtnPause()
    {
        setImage("btnPause.png"); // Sesuaikan nama file aset tombol pause-mu
        //MENGUBAH UKURAN
        GreenfootImage img = getImage();
        int lebarBaru = (int)(img.getWidth() * 0.5);   // Ubah 0.6 sesuai selera (misal 0.5 untuk setengah)
        int tinggiBaru = (int)(img.getHeight() * 0.5);
        img.scale(Math.max(1, lebarBaru), Math.max(1, tinggiBaru));
        setImage(img);
        siapkanEfekVisual();
    }

    public void act()
    {
        jalankanEfekTombol();

        if (Greenfoot.mouseClicked(this))
        {
            MyWorld dunia = (MyWorld) getWorld();
            if (dunia != null && !dunia.isPaused() && !dunia.isGameOver()) {
                dunia.jedaPermainan();
            }
        }
    }
}

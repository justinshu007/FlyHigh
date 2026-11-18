import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnBackPause here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnBackPause extends InteractiveButton
{
    /**
     * Act - do whatever the BtnBackPause wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BtnBackPause(){
        setImage("btnBack.png"); // Sesuaikan nama file aset tombol Back-mu
        //MENGUBAH UKURAN GAMBAR
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

        if (Greenfoot.mouseClicked(this) || Greenfoot.mousePressed(this))
        {
            World w = getWorld();
            if (w instanceof MyWorld) {
                ((MyWorld) w).lanjutkanPermainan();
            }
        }
    }
}

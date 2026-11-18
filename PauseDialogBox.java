import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseDialogBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PauseDialogBox extends Actor
{
    /**
     * Act - do whatever the PauseDialogBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public PauseDialogBox()
    {
        setImage("dbGamePaused.png"); // Sesuaikan nama file aset dialog box-mu
        //MENGUBAH UKURAN GAMBAR
        GreenfootImage img = getImage();
        int lebarBaru = (int)(img.getWidth() * 0.4);   // Ubah 0.6 sesuai selera (misal 0.5 untuk setengah)
        int tinggiBaru = (int)(img.getHeight() * 0.4);
        img.scale(Math.max(1, lebarBaru), Math.max(1, tinggiBaru));
        setImage(img);
        
    }
    public void act()
    {
        // Add your action code here.
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InteractiveButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InteractiveButton extends Actor
{
    private GreenfootImage gambarNormal;
    private GreenfootImage gambarHover;
    private GreenfootImage gambarKlik;
    private boolean sedangHover = false;
    
    // Panggil method ini di dalam Constructor setiap tombol setelah setImage()
    protected void siapkanEfekVisual() {
        GreenfootImage asli = getImage();
        int w = asli.getWidth();
        int h = asli.getHeight();

        // 1. State Normal (100%)
        gambarNormal = new GreenfootImage(asli);

        // 2. State Hover: Membesar 8% (108%) saat kursor menyentuh tombol
        gambarHover = new GreenfootImage(asli);
        gambarHover.scale((int)(w * 1.08), (int)(h * 1.08));

        // 3. State Click: Mengecil 6% (94%) memberi sensasi tombol tertekan
        gambarKlik = new GreenfootImage(asli);
        gambarKlik.scale((int)(w * 0.9), (int)(h * 0.9));
    }
    
    protected void jalankanEfekTombol() {
        // Cek apakah mouse sedang menekan (hold) tombol ini
        if (Greenfoot.mousePressed(this)) {
            setImage(gambarKlik);
            SFX.playClick(); // <-- Bunyi SFX saat tombol diklik
            return;
        }

        // Cek apakah kursor berada di atas tombol (Hover)
        if (Greenfoot.mouseMoved(this)) {
            if (!sedangHover) {
                sedangHover = true;
                setImage(gambarHover);
                SFX.playHover(); // <-- Bunyi SFX sekali saat kursor menyentuh tombol
            }
        } else if (Greenfoot.mouseMoved(null) && sedangHover) {
            // Kursor keluar dari area tombol
            sedangHover = false;
            setImage(gambarNormal);
        }
    }    
    public void act()
    {
        // Add your action code here.
    }
}

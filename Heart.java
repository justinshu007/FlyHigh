import greenfoot.*;

public class Heart extends Actor
{
    private GreenfootImage gambarNormal;
    private GreenfootImage gambarTransparan;
    private int durasiKedip = 0;
    //private Koi fish;

    public Heart()
    {
        gambarNormal = new GreenfootImage("heart.png");
        gambarNormal.scale(32, 32);

        // Membuat versi transparan untuk efek kedip
        gambarTransparan = new GreenfootImage(32, 32); 

        setImage(gambarNormal);
    }

    public void act()
    {
        if (durasiKedip > 0) {
            durasiKedip--;

            // Berganti gambar setiap 4 frame agar efek kedap-kedip terasa jelas
            if ((durasiKedip / 4) % 2 == 0) {
                setImage(gambarTransparan);
            } else {
                setImage(gambarNormal);
            }

            // Kembalikan ke gambar normal setelah animasi selesai
            if (durasiKedip == 0) {
                setImage(gambarNormal);
            }
        }
    }
    // Dipanggil dari MyWorld untuk memicu animasi kedap-kedip
    public void mulaiKedip() {
        durasiKedip = 30; // Berkedip selama ~0.5 detik (30 frame)
    }
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(150, 50);

        
        setImage(img);
    }
}
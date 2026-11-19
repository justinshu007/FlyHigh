import greenfoot.*;

public class InteractiveButton extends Actor
{
    private GreenfootImage gambarNormal;
    private GreenfootImage gambarHover;
    private GreenfootImage gambarKlik;
    private boolean sedangHover = false;

    protected void siapkanEfekVisual() {
        GreenfootImage asli = getImage();
        int w = asli.getWidth();
        int h = asli.getHeight();

        gambarNormal = new GreenfootImage(asli);

        gambarHover = new GreenfootImage(asli);
        gambarHover.scale((int)(w * 1.03), (int)(h * 1.03));

        gambarKlik = new GreenfootImage(asli);
        gambarKlik.scale((int)(w * 0.95), (int)(h * 0.95));
    }

    protected void jalankanEfekTombol() {
        if (gambarNormal == null || getWorld() == null) return;

        MouseInfo mouse = Greenfoot.getMouseInfo();
        boolean kursorDiAtas = false;

        if (mouse != null) {
            int setengahLebar = gambarNormal.getWidth() / 2;
            int setengahTinggi = gambarNormal.getHeight() / 2;
            kursorDiAtas = Math.abs(mouse.getX() - getX()) <= setengahLebar &&
                           Math.abs(mouse.getY() - getY()) <= setengahTinggi;
        }

        // 1. State Klik
        if (Greenfoot.mousePressed(this)) {
            setImage(gambarKlik);
            SFX.playClick();
            return;
        }

        // 2. State Hover (Instan saat masuk & langsung reset saat keluar)
        if (kursorDiAtas) {
            if (!sedangHover) {
                sedangHover = true;
                setImage(gambarHover);
                SFX.playHover();
            }
        } else if (sedangHover) {
            sedangHover = false;
            setImage(gambarNormal);
        }
    }

    public void act()
    {
        jalankanEfekTombol();
    }
}
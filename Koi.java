import greenfoot.*;

public class Koi extends Actor
{    
    // --- 3 SET ANIMASI UNTUK MASING-MASING TAHAP IKAN ---
    private GreenfootImage[] framesKoi = new GreenfootImage[8];
    private GreenfootImage[] framesSwordfish = new GreenfootImage[10];
    private GreenfootImage[] framesHiu = new GreenfootImage[11];
    private GreenfootImage[] framesAktif;
    
    private int tahapIkan = 1; // 1 = Koi, 2 = Swordfish, 3 = Hiu
    private int currentFrame = 0;
    private int animationCounter = 0;

    private int startY;
    private int amplitude = 30;
    private int speed = 2;
    private int direction = 1;
    private int health = 3;
    
    // --- VARIABEL UNTUK GERAK SEJAJAR (SMOOTH EASING / LERP) ---
    private int targetY = -1;
    private double currentY;
    
    // --- VARIABEL GELOMBANG MENGAPUNG (BOBBING) ---
    private double sudutAnimasi = 0;
    private double kecepatanAyunan = 0.08;
    private double tinggiAyunan = 5.0;

    public Koi()
    {   
        // 1. Muat animasi Tahap 1: Koi
        for (int i = 0; i < framesKoi.length; i++) {
            framesKoi[i] = new GreenfootImage("koi_fish" + i + ".png");
            framesKoi[i].scale(150, 77);
        }
        // 2. Muat animasi Tahap 2: Swordfish
        for (int i = 0; i < framesSwordfish.length; i++) {
            framesSwordfish[i] = new GreenfootImage("sword_fish" + i + ".png");
            framesSwordfish[i].scale(165, 80);
        }
        // 3. Muat animasi Tahap 3: Hiu
        for (int i = 0; i < framesHiu.length; i++) {
            framesHiu[i] = new GreenfootImage("shark_" + i + ".png");
            framesHiu[i].scale(180, 90);
        }
        
        // Mulai dengan animasi Koi
        framesAktif = framesKoi;
        setImage(framesAktif[0]);
    }

    protected void addedToWorld(World world)
    {
        startY = getY();
        currentY = getY();
    }

    public void act()
    {
        animateKoi();
        geserMulusKeTarget();
        animasiMengapungHalus();
    }

    // Dipanggil oleh MyWorld setiap kali skor bertambah
    public void cekEvolusi(int skorSaatIni)
    {
        if (skorSaatIni >= 40 && tahapIkan < 3) {
            tahapIkan = 3;
            framesAktif = framesHiu;
            currentFrame = 0;
            setImage(framesAktif[0]);
            SFX.playLevelUp();
        } else if (skorSaatIni >= 20 && tahapIkan < 2) {
            tahapIkan = 2;
            framesAktif = framesSwordfish;
            currentFrame = 0;
            setImage(framesAktif[0]);
            SFX.playLevelUp();
        }
    }



    private void geserMulusKeTarget() {
        if (targetY != -1) {
            currentY += (targetY - currentY) * 0.1;
            setLocation(getX(), (int)currentY);
            if (Math.abs(targetY - currentY) < 1.0) {
                currentY = targetY;
                targetY = -1;
            }
        }
    }
    
    private void animasiMengapungHalus() {
        sudutAnimasi += kecepatanAyunan; 
        if (sudutAnimasi > Math.PI * 2) {
            sudutAnimasi -= Math.PI * 2;
        }

        double offsetSinus = Math.sin(sudutAnimasi);
        int tambahanY = (int)(offsetSinus * tinggiAyunan);
        setLocation(getX(), (int)currentY + tambahanY);

        int derajatMiring = (int)(-offsetSinus * 2);
        setRotation(derajatMiring);
    }

    private void animateKoi()
    {
        animationCounter++;

        if (animationCounter >= 5)
        {
            animationCounter = 0;
            currentFrame++;

            if (currentFrame >= framesAktif.length)
            {
                currentFrame = 0;
            }

            setImage(framesAktif[currentFrame]);
        }
    }
    
    public void tembak(int targetX, int targetYSoal) {
        this.targetY = targetYSoal;

        Meteor peluruBaru = new Meteor();
        getWorld().addObject(peluruBaru, getX() + 35, getY());
        peluruBaru.turnTowards(targetX, targetYSoal);
    }
}
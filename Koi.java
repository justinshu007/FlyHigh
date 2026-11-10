import greenfoot.*;

public class Koi extends Actor
{    
    private GreenfootImage[] frames = new GreenfootImage[8];
    private int currentFrame = 0;
    private int animationCounter = 0;

    private int startY;
    private int amplitude = 30;
    private int speed = 2;
    private int direction = 1;
    private int health = 3;
    
    // --- VARIABEL UNTUK GERAK SEJAJAR (SMOOTH EASING / LERP) ---
    private int targetY = -1;          // Ketinggian koin target (-1 jika sedang diam)
    private double currentY;           // Posisi Y presisi pecahan (double) agar tidak patah-patah
    
    // --- VARIABEL GELOMBANG MENGAPUNG (BOBBING) ---
    private double sudutAnimasi = 0;   // Sudut perulangan sinus
    private double kecepatanAyunan = 0.08; // Ombak lebih pelan dan santai
    private double tinggiAyunan = 5.0;     // Jarak naik-turun 5 piksel agar transisi piksel mulus


    public Koi()
    {
        frames[0] = new GreenfootImage("koi_fish0.png");
        frames[1] = new GreenfootImage("koi_fish1.png");
        frames[2] = new GreenfootImage("koi_fish2.png");
        frames[3] = new GreenfootImage("koi_fish3.png");
        frames[4] = new GreenfootImage("koi_fish4.png");
        frames[5] = new GreenfootImage("koi_fish5.png");
        frames[6] = new GreenfootImage("koi_fish6.png");
        frames[7] = new GreenfootImage("koi_fish7.png");
        
        for (int i = 0; i < frames.length; i++)
        {
            frames[i].scale(150, 77);
        }

        setImage(frames[0]);
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


    private void geserMulusKeTarget() {
        if (targetY != -1) {
            // Bergerak 10% dari sisa jarak setiap frame (meluncur cepat lalu mengerem pelan)
            currentY += (targetY - currentY) * 0.1;
            // 2. WAJIB: Pindahkan posisi visual ikan di layar Greenfoot!
            setLocation(getX(), (int)currentY);
            // Jika sudah sangat dekat (kurang dari 1 piksel), kunci posisinya
            if (Math.abs(targetY - currentY) < 1.0) {
                currentY = targetY;
                targetY = -1; // Selesai berpindah
            }
        }
    }
    
    private void animasiMengapungHalus() {
        sudutAnimasi += kecepatanAyunan; 
        if (sudutAnimasi > Math.PI * 2) {
            sudutAnimasi -= Math.PI * 2; // Cegah angka sudut membesar tak terbatas
        }

        // Hitung simpangan gelombang halus
        double offsetSinus = Math.sin(sudutAnimasi);
        int tambahanY = (int)(offsetSinus * tinggiAyunan);
        
        // Posisikan ikan di ketinggian halus
        setLocation(getX(), (int)currentY + tambahanY);

        // Efek kemiringan tipis: mendongak sedikit (-2 derajat) & menunduk (+2 derajat)
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

            if (currentFrame >= frames.length)
            {
                currentFrame = 0;
            }

            setImage(frames[currentFrame]);
        }
    }
    
    
    public void tembak(int targetX, int targetYSoal) {
        this.targetY = targetYSoal; // Kunci ketinggian koin target

        // Munculkan peluru (Meteor) dari depan mulut ikan
        //Meteor peluruBaru = new Meteor();
        //getWorld().addObject(peluruBaru, getX() + 35, getY());
        //peluruBaru.turnTowards(targetX, targetYSoal);
    }
    
}
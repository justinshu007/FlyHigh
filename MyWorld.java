import greenfoot.*;

public class MyWorld extends World
{
    private int batasAngkaLevel; 
    
    private GreenfootImage bgA;
    private GreenfootImage bgB;
    
    private Koi playerKoi;
    private Heart hearts;

    private int scrollX = 0;
    private int speed = 1;
    
    private int spawnCounter = 0;
    
    // --- VARIABLE SPAWN KOIN ---
    private int jedaSpawnKoin = 150;     // Jeda awal kemunculan koin (perlahan di awal)
    private int timerSpawn = 50;         // Waktu tunggu koin pertama keluar
    private int kecepatanKoinSaatIni = 2; // Kecepatan gerak awal koin melayang
    private int totalKoinHancur = 0;     // Penghitung koin yang berhasil dijawab[cite: 3]
    
    // --- SISTEM NYAWA (MAKSIMAL 5 HATI, AWAL 3) ---
    private int nyawa = 3; 
    private Heart[] daftarHati = new Heart[5]; // Slot kapasitas maksimal 5 hati
    private int comboBenarBerturut = 0;        // Counter untuk bonus hati
    
    

    
    


    public MyWorld(int batasAngka)
    {
        super(750, 420, 1);

        bgA = new GreenfootImage("background.png");
        bgB = new GreenfootImage("background1.png");

        bgA.scale(750, 420);
        bgB.scale(750, 420);

      
        // BATAS JML ANGKA (min.3) default
        this.batasAngkaLevel = (batasAngka <= 0) ? 3 : batasAngka;
    
        // Pastikan playerKoi benar-benar diinisialisasi
        this.playerKoi = new Koi();
        addObject(this.playerKoi, 115, 230);

        // 3 Hati awal di pojok kanan atas
        for (int i = 0; i < 3; i++) {
            daftarHati[i] = new Heart();
            addObject(daftarHati[i], 580 + (i * 38), 35);
        }
    }

    public void act()
    {
        scrollBackground();
        
    }

    private void scrollBackground()
    {
        scrollX -= speed;

        if(scrollX <= -1500)
        {
            scrollX = 0;
        }

        GreenfootImage bg = getBackground();
        bg.clear();

        bg.drawImage(bgA, scrollX, 0);
        bg.drawImage(bgB, scrollX + 750, 0);

        bg.drawImage(bgA, scrollX + 1500, 0);
        bg.drawImage(bgB, scrollX + 2250, 0);
    }
    
    
}
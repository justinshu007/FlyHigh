import greenfoot.*;
import java.util.List;

public class MyWorld extends World
{
    private int batasAngkaLevel; 
    private Koi playerKoi;
    private boolean isPaused = false;
    private GreenfootImage bgA;
    private GreenfootImage bgB;

    private int scrollX = 0;
    private int speed = 1;
    
    // --- STATUS GAME OVER ---
    private boolean isGameOver = false;
    
    // --- VARIABEL SPAWNER KOIN (PSIKOLOGI FLOW & RECOVERY) ---
    private int jedaSpawnKoin = 160;      // Jeda awal santai (~2,6 detik) untuk orientasi
    private int timerSpawn = 45;          // Koin pertama segera menyapa agar tidak menunggu lama
    private int kecepatanKoinSaatIni = 2; // Laju gerak awal lembut (2 px/frame)
    private int streakKecepatan = 0;      // Dihitung dari jawaban benar berturut-turut
    
    // --- JALUR RENANG KOIN (3 LANES ANTI-BERTUMPUK) ---
    private int[] jalurY = {115, 225, 330};
    private int jalurTerakhir = -1;
    
    // --- SISTEM NYAWA (MAKSIMAL 5 HATI, AWAL 3) ---
    private int nyawa = 3; 
    private Heart[] daftarHati = new Heart[5]; 
    private int comboBenarBerturut = 0;        
    
    // --- VARIABEL INPUT & UI ---
    private String jawabanDiketik = "";
    private TeksUI tampilanSkor;  
    
    // --- VARIABEL SKOR & BEST SCORE ---
    private int skorSaatIni = 0;
    private static int bestScore = 0; 
    
    // --- VARIABEL EFEK GETAR & BUBBLE ---
    private int timerGetar = 0; 
    private int bubbleTimer = 0;
    
    private CoinType modeAktif = CoinType.GOLD;
    ModeIndicator indicator;
    
    public MyWorld()
    {
        this(3);
    }

    public MyWorld(int batasAngka)
    {
        super(750, 420, 1);
        
        setPaintOrder(
            BtnBackPause.class,
            BtnMainMenu.class,
            GameOverDB.class,
            PauseDialogBox.class,
            PauseOverlay.class,
            BtnPause.class,
            TeksUI.class,
            Heart.class,
            Meteor.class,
            Koi.class,
            CoinSoal.class,
            Bubble.class
        );

        bgA = new GreenfootImage("background.png");
        bgB = new GreenfootImage("background1.png");
        bgA.scale(750, 420);
        bgB.scale(750, 420);

        this.batasAngkaLevel = (batasAngka <= 0) ? 3 : batasAngka;
    
        // 1. Koi Utama
        this.playerKoi = new Koi();
        addObject(this.playerKoi, 115, 230);

        // 2. Tombol Pause di Pojok Kiri Atas
        addObject(new BtnPause(), 55, 45);

        // 3. Skor Angka Saja di Tengah Atas
        tampilanSkor = new TeksUI(String.valueOf(skorSaatIni), 28, "Press Start 2P");
        addObject(tampilanSkor, 375, 45);
        
        // 3.5 Mode Indicator
        indicator = new ModeIndicator();
        addObject(indicator, 275, 55);
        indicator.updateMode(modeAktif);
        
        // 4. 3 Hati Awal di Pojok Kanan Atas
        for (int i = 0; i < 3; i++) {
            daftarHati[i] = new Heart();
            addObject(daftarHati[i], 540 + (i * 38), 45);
        }
    }

    public void act()
    {
        if (!isGameOver) {
            if (!isPaused) {
                scrollBackground();
                munculkanKoinBertahap();
                sistemMengetikDanMenembak();
                efekGetarObjek();
            }

            bubbleTimer++;
            if (bubbleTimer >= 20)
            {
                bubbleTimer = 0;
                addObject(
                    new Bubble(),
                    Greenfoot.getRandomNumber(getWidth()),
                    getHeight() + 10
                );
            }
        }
        
        if (Greenfoot.isKeyDown("g"))
        {
            modeAktif = CoinType.GOLD;
        
            if(indicator != null)
            {
                indicator.updateMode(modeAktif);
            }
        }
        
        if (Greenfoot.isKeyDown("s"))
        {
            modeAktif = CoinType.SILVER;
        
            if(indicator != null)
            {
                indicator.updateMode(modeAktif);
            }
        }
        
        if (Greenfoot.isKeyDown("b"))
        {
            modeAktif = CoinType.BRONZE;
        
            if(indicator != null)
            {
                indicator.updateMode(modeAktif);
            }
        }
    }

    // --- KONTROL PAUSE ---
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void tambahSkor() {
        skorSaatIni += 1;
        if (skorSaatIni > bestScore) {
            bestScore = skorSaatIni;
        }
        if (tampilanSkor != null) {
            tampilanSkor.ubahTeks(String.valueOf(skorSaatIni), 28, "Press Start 2P");
        }
        if (playerKoi != null) {
            playerKoi.cekEvolusi(skorSaatIni);
        }
    }

    public void jedaPermainan() {
        isPaused = true;
        Greenfoot.getKey();

        addObject(new PauseOverlay(), getWidth() / 2, getHeight() / 2);
        addObject(new PauseDialogBox(), getWidth() / 2, getHeight() / 2);
        addObject(new BtnBackPause(), 400, 195);
        addObject(new BtnMainMenu(), 400, 240);
    }

    public void lanjutkanPermainan() {
        isPaused = false;
        Greenfoot.getKey();

        removeObjects(getObjects(BtnBackPause.class));
        removeObjects(getObjects(BtnMainMenu.class));
        removeObjects(getObjects(PauseDialogBox.class));
        removeObjects(getObjects(PauseOverlay.class));
    }
    
    // --- SISTEM NYAWA & PEMICU GAME OVER ---
    public void kurangiNyawa() {
        if (isGameOver) return;

        if (nyawa > 0) {
            nyawa--;
            comboBenarBerturut = 0;
            if (daftarHati[nyawa] != null) {
                removeObject(daftarHati[nyawa]);
                daftarHati[nyawa] = null;
            }
        }

        if (nyawa <= 0) {
            isGameOver = true;
            SFX.playGameOver();

            removeObjects(getObjects(CoinSoal.class));
            removeObjects(getObjects(Meteor.class));
            
            if(playerKoi != null)
            {
                playerKoi.mulaiAnimasiMati();
            }

            addObject(new PauseOverlay(), getWidth() / 2, getHeight() / 2);
            addObject(new GameOverDB(skorSaatIni, bestScore), getWidth() / 2, getHeight() / 2);
            addObject(new BtnMainMenu(), getWidth() / 2, 275);
        }
    }

    public void periksaBonusNyawa() {
        comboBenarBerturut++;

        if (comboBenarBerturut >= 5) {
            comboBenarBerturut = 0;
            if (nyawa < 5) {
                daftarHati[nyawa] = new Heart();
                addObject(daftarHati[nyawa], 540 + (nyawa * 38), 45);
                daftarHati[nyawa].mulaiKedip();
                nyawa++;
                SFX.playLiveUp(); // <-- Memutar SFX khusus saat nyawa bertambah
            }
        }
    }

    public void koinTerlewat() {
        if (!isGameOver) {
            pemicuSalah();
            kurangiNyawa();

            // PSIKOLOGI RECOVERY: Saat koin terlewat, JANGAN dipercepat!
            // Reset streak kecepatan dan beri jeda napas agar anak tidak panik (hindari Death Spiral)
            streakKecepatan = 0;
            if (jedaSpawnKoin < 140) {
                jedaSpawnKoin += 20; // Longgarkan jarak antar koin berikutnya
            }
            if (kecepatanKoinSaatIni > 2) {
                kecepatanKoinSaatIni -= 1; // Perlambat sedikit laju koin
            }
            timerSpawn = Math.max(timerSpawn, 90); // Beri jeda minimal 1,5 detik sebelum koin baru masuk
        }
    }

    // --- SPAWNER KOIN (3 JALUR RAPI + MAX 3 KOIN + ANTI JAWABAN KEMBAR) ---
    private void munculkanKoinBertahap() {
        List<CoinSoal> daftarKoin = getObjects(CoinSoal.class);

        // 1. Batasi maksimal 3 koin aktif di layar agar tidak visual overload
        if (daftarKoin.size() >= 3) {
            return;
        }

        timerSpawn--;
        if (timerSpawn <= 0) {
            // 2. Pilih jalur Y yang berbeda dari koin terakhir agar tidak bertumpuk
            int indeksJalur = Greenfoot.getRandomNumber(jalurY.length);
            if (indeksJalur == jalurTerakhir) {
                indeksJalur = (indeksJalur + 1) % jalurY.length;
            }
            jalurTerakhir = indeksJalur;

            // 3. Buat koin dengan jawaban yang tidak kembar dengan koin yang sudah ada di layar
            CoinSoal koinBaru = null;
            for (int percobaan = 0; percobaan < 10; percobaan++) {
                CoinSoal kandidat = new CoinSoal(batasAngkaLevel, kecepatanKoinSaatIni, skorSaatIni);
                boolean jawabanKembar = false;
                for (CoinSoal koinAktif : daftarKoin) {
                    if (koinAktif.getJawaban() == kandidat.getJawaban()) {
                        jawabanKembar = true;
                        break;
                    }
                }
                if (!jawabanKembar) {
                    koinBaru = kandidat;
                    break;
                }
                koinBaru = kandidat;
            }

            addObject(koinBaru, 760, jalurY[indeksJalur]);
            timerSpawn = jedaSpawnKoin; 
        }
    }

    private void sistemMengetikDanMenembak() {
        String tombol = Greenfoot.getKey(); 
        if (tombol != null && tombol.matches("[0-9]")) {
            if (jawabanDiketik.length() < 2) {
                jawabanDiketik += tombol;
                prosesCekJawabanKonteks();
            } else {
                pemicuSalah();
            }
        }
    }

    private void prosesCekJawabanKonteks() {
        if (jawabanDiketik.isEmpty()) return;

        List<CoinSoal> daftarKoin = getObjects(CoinSoal.class);
        if (daftarKoin.isEmpty()) {
            jawabanDiketik = "";
            return;
        }

        int jawabanPemain;
        try {
            jawabanPemain = Integer.parseInt(jawabanDiketik);
        } catch (NumberFormatException e) {
            jawabanDiketik = "";
            return;
        }

        boolean adaYangBenar = false;
        boolean adaPotensiBenar = false;

        for (CoinSoal koin : daftarKoin) {
            if (koin.getJawaban() == jawabanPemain) {
                adaYangBenar = true;
                //SFX.playBenar();

                if (playerKoi != null) {
                    playerKoi.tembak(koin.getX(), koin.getY());
                }

                //tambahSkor();
                //periksaBonusNyawa();
                //periksaPeningkatanKecepatan();

                // Jika layar jadi kosong setelah koin ditembak, percepat sedikit koin berikutnya
                // agar anak tidak bosan menunggu layar kosong terlalu lama
                if (getObjects(CoinSoal.class).size() <= 1 && timerSpawn > 45) {
                    timerSpawn = 45;
                }

                jawabanDiketik = "";
                break;
            } else if (String.valueOf(koin.getJawaban()).startsWith(jawabanDiketik)) {
                adaPotensiBenar = true;
            }
        }

        if (!adaYangBenar && !adaPotensiBenar) {
            pemicuSalah();
        }
    }

    public void pemicuSalah()
    {
        SFX.playSalah();
    
        addObject(
            new FloatingMessage("JAWABAN SALAH!"),
            500,
            100
        );
    
        comboBenarBerturut = 0;
        streakKecepatan = 0;
    
        timerGetar = 10;
    
        jawabanDiketik = "";
    }
    
    public void efekSalahWarna()
    {
        SFX.playSalah();
    
        addObject(
            new FloatingMessage("WARNA TIDAK COCOK!"),
            480,
            100
        );

    
        comboBenarBerturut = 0;
        streakKecepatan = 0;
    
        timerGetar = 10;
    }

    // PSIKOLOGI FLOW STATE: Kecepatan hanya naik jika user berhasil menjawab berturut-turut!
    public void periksaPeningkatanKecepatan() {
        streakKecepatan++;
        
        // Setiap 4 jawaban benar berturut-turut tanpa salah/terlewat, naikkan tempo permainan
        if (streakKecepatan % 4 == 0) {
            if (jedaSpawnKoin > 65) {
                jedaSpawnKoin -= 15; // Koin baru muncul lebih rapat
            }
            if (kecepatanKoinSaatIni < 4) {
                kecepatanKoinSaatIni += 1; // Laju gerak naik bertahap (maksimal 4 agar tetap terbaca)
            }
        }
    }

    private void efekGetarObjek() {
        if (timerGetar > 0) {
            timerGetar--;
            int shakeX = Greenfoot.getRandomNumber(7) - 3;
            int shakeY = Greenfoot.getRandomNumber(7) - 3;

            if (playerKoi != null) {
                playerKoi.setLocation(115 + shakeX, playerKoi.getY() + shakeY);
            }

            List<CoinSoal> daftarKoin = getObjects(CoinSoal.class);
            for (CoinSoal koin : daftarKoin) {
                koin.setLocation(koin.getX() + shakeX, koin.getY() + shakeY);
            }

            if (timerGetar == 0 && playerKoi != null) {
                playerKoi.setLocation(115, playerKoi.getY());
            }
        }
    }

    private void scrollBackground()
    {
        scrollX -= speed;
        if (scrollX <= -1500) {
            scrollX = 0;
        }

        GreenfootImage bg = getBackground();
        bg.clear();
        bg.drawImage(bgA, scrollX, 0);
        bg.drawImage(bgB, scrollX + 750, 0);
        bg.drawImage(bgA, scrollX + 1500, 0);
        bg.drawImage(bgB, scrollX + 2250, 0);
    }
    
    public CoinType getModeAktif()
    {
        return modeAktif;
    }
}
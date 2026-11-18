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
    
    // --- STATUS GAME OVER & TIMER 3 DETIK (180 FRAME) ---
    private boolean isGameOver = false;
    private int timerKembaliKeMenu = 180;
    
    // --- VARIABEL SPAWNER KOIN ---
    private int jedaSpawnKoin = 150;     
    private int timerSpawn = 50;         
    private int kecepatanKoinSaatIni = 2; 
    private int totalKoinHancur = 0;     
    
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
    
    public MyWorld()
    {
        this(3);
    }

    public MyWorld(int batasAngka)
    {
        super(750, 420, 1);
        
        // Urutan layer dari paling depan (atas) ke paling belakang
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

        // 4. 3 Hati Awal di Pojok Kanan Atas (sejajar Y = 45)
        for (int i = 0; i < 3; i++) {
            daftarHati[i] = new Heart();
            addObject(daftarHati[i], 540 + (i * 38), 45);
        }
    }

    public void act()
    {
        if (!isGameOver) {
            // 1. Gameplay utama: HANYA berjalan jika TIDAK sedang pause
            if (!isPaused) {
                scrollBackground();
                munculkanKoinBertahap();
                sistemMengetikDanMenembak();
                efekGetarObjek();
            }

            // 2. Efek Ambient (Bubble): Tetap muncul & naik meskipun sedang pause
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
    }

    // --- KONTROL PAUSE ---
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void jedaPermainan() {
        isPaused = true;
        Greenfoot.getKey(); // Bersihkan buffer keyboard

        addObject(new PauseOverlay(), getWidth() / 2, getHeight() / 2);
        addObject(new PauseDialogBox(), getWidth() / 2, getHeight() / 2);
        addObject(new BtnBackPause(), getWidth() / 2, 195);
        addObject(new BtnMainMenu(), getWidth() / 2, 265);
    }

    public void lanjutkanPermainan() {
        isPaused = false;
        Greenfoot.getKey(); // Bersihkan buffer keyboard

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

        // Pemicu Game Over saat nyawa habis
        // Pemicu Game Over saat nyawa habis
        if (nyawa <= 0) {
            isGameOver = true;
            SFX.playGameOver();

            // 1. Bersihkan sisa koin soal & peluru
            removeObjects(getObjects(CoinSoal.class));
            removeObjects(getObjects(Meteor.class));

            // 2. Redupkan layar belakang
            addObject(new PauseOverlay(), getWidth() / 2, getHeight() / 2);

            // 3. Munculkan Dialog Box Game Over
            addObject(new GameOverDB(skorSaatIni, bestScore), getWidth() / 2, getHeight() / 2);

            // 4. Munculkan tombol Main Menu (sama seperti di dialog Pause) di bawah teks skor
            addObject(new BtnMainMenu(), getWidth() / 2, 275);
        }
    }

    private void prosesTimerKembaliKeMenu() {
        timerKembaliKeMenu--;
        if (timerKembaliKeMenu <= 0) {
            Greenfoot.setWorld(new MenuWorld());
        }
    }

    private void periksaBonusNyawa() {
        comboBenarBerturut++;

        if (comboBenarBerturut >= 5) {
            comboBenarBerturut = 0;
            if (nyawa < 5) {
                daftarHati[nyawa] = new Heart();
                addObject(daftarHati[nyawa], 540 + (nyawa * 38), 45);
                daftarHati[nyawa].mulaiKedip();
                nyawa++;
                SFX.playBenar();
            }
        }
    }

    public void koinTerlewat() {
        if (!isGameOver) {
            pemicuSalah();
            kurangiNyawa();
        }
    }

    // --- SPAWNER KOIN ---
    private void munculkanKoinBertahap() {
        timerSpawn--;
        if (timerSpawn <= 0) {
            CoinSoal koinBaru = new CoinSoal(batasAngkaLevel, kecepatanKoinSaatIni);
            addObject(koinBaru, 750, Greenfoot.getRandomNumber(280) + 60);
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
                SFX.playBenar();

                if (playerKoi != null) {
                    playerKoi.tembak(koin.getX(), koin.getY());
                }

                tambahSkor();
                periksaBonusNyawa();
                periksaPeningkatanKecepatan();

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

    private void pemicuSalah() {
        SFX.playSalah();
        comboBenarBerturut = 0;
        timerGetar = 10;
        jawabanDiketik = "";
    }

    public void tambahSkor() {
        skorSaatIni += 1;
        if (skorSaatIni > bestScore) {
            bestScore = skorSaatIni;
        }
        if (tampilanSkor != null) {
            tampilanSkor.ubahTeks(String.valueOf(skorSaatIni), 28, "Press Start 2P");
        }
    }

    private void periksaPeningkatanKecepatan() {
        totalKoinHancur++;
        if (totalKoinHancur % 5 == 0) {
            if (jedaSpawnKoin > 50) {
                jedaSpawnKoin -= 20;
            }
            if (kecepatanKoinSaatIni < 5) {
                kecepatanKoinSaatIni += 1;
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
}
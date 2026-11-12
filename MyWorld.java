import greenfoot.*;
import java.util.List;

public class MyWorld extends World
{
    private int batasAngkaLevel; 
    private Koi playerKoi;
    
    private GreenfootImage bgA;
    private GreenfootImage bgB;
    
    private Heart hearts;

    private int scrollX = 0;
    private int speed = 1;
    
    private int spawnCounter = 0;
    
    // --- STATUS GAME OVER & TIMER 3 DETIK (180 FRAME) ---
    private boolean isGameOver = false;
    private int timerKembaliKeMenu = 180; // 3 detik * 60 FPS
    
    // --- VARIABEL ASLI SPAWNER KOIN (DARI TESTINGFISHBLAST) ---
    private int jedaSpawnKoin = 150;     // Jeda awal kemunculan koin (perlahan di awal)
    private int timerSpawn = 50;         // Waktu tunggu koin pertama keluar
    private int kecepatanKoinSaatIni = 2; // Kecepatan gerak awal koin melayang
    private int totalKoinHancur = 0;     // Penghitung koin yang berhasil dijawab[cite: 3]
    
    // --- SISTEM NYAWA (MAKSIMAL 5 HATI, AWAL 3) ---
    private int nyawa = 3; 
    private Heart[] daftarHati = new Heart[5]; // Slot kapasitas maksimal 5 hati
    private int comboBenarBerturut = 0;        // Counter untuk bonus hati
    
    // --- VARIABEL INPUT & UI ---
    private String jawabanDiketik = "";
    private TeksUI tampilanSkor;  
    
    // --- VARIABEL SKOR & BEST SCORE ---
    private int skorSaatIni = 0;
    private static int bestScore = 0; // Bersifat global selama game aktif, reset jika keluar game
    //private TeksUI tampilanSkor;
    
    // --- VARIABEL EFEK GETAR (SCREEN SHAKE) ---
    private int timerGetar = 0; // Durasi getaran objek
    


    public MyWorld(int batasAngka)
    {
        super(750, 420, 1);

        bgA = new GreenfootImage("background.png");
        bgB = new GreenfootImage("background1.png");
        bgA.scale(750, 420);
        bgB.scale(750, 420);

      
        // BATAS JML ANGKA (min.3) default
        this.batasAngkaLevel = (batasAngka <= 0) ? 3 : batasAngka;
    
        // INISIALISASI OBJECT KOI
        this.playerKoi = new Koi();
        addObject(this.playerKoi, 115, 230);

        // 3 Hati awal di pojok kanan atas
        // PERBAIKAN: Geser X awal dari 580 menjadi 490
        for (int i = 0; i < 3; i++) {
            daftarHati[i] = new Heart();
            addObject(daftarHati[i], 540 + (i * 38), 35);
        }
        
        // Teks Skor
        tampilanSkor = new TeksUI("Point: " + skorSaatIni, 20, "Press Start 2P");
        addObject(tampilanSkor, 200, 42);
    }

    public void act()
    {
        if (!isGameOver) {
        // Gameplay normal berjalan
        scrollBackground();
        munculkanKoinBertahap();
        sistemMengetikDanMenembak();
        efekGetarObjek();
        } else {
            // Hitung mundur 3 detik saat kondisi Game Over
            prosesTimerKembaliKeMenu();
        }
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
        if (nyawa <= 0) {
            isGameOver = true; // Aktifkan status Game Over

            // 1. Hentikan musik latar
            //if (bgm != null) bgm.stop();

            // 2. Putar suara efek Game Over
            putarSuaraAman("gameOver.mp3"); // Pastikan file audio tersedia di folder sounds

            // 3. Tampilkan teks Game Over di tengah layar
            TeksUI teksGameOver = new TeksUI("GAME OVER!", 30, "Press Start 2P");
            addObject(teksGameOver, getWidth() / 2, getHeight() / 2);

            // JANGAN gunakan Greenfoot.stop() agar method act() tetap memproses timer 3 detik!
        }
    }
    // --- PENGHITUNG MUNDUR 3 DETIK MENUJU HALAMAN AWAL ---
    private void prosesTimerKembaliKeMenu() {
        timerKembaliKeMenu--;

        if (timerKembaliKeMenu <= 0) {
            // Ganti "MainMenu" dengan nama class World halaman pertama Anda jika berbeda
            Greenfoot.setWorld(new MenuWorld());
        }
    }
    // Menambah hati berkedip jika berhasil 5 kali berturut-turut tanpa salah
    private void periksaBonusNyawa() {
        comboBenarBerturut++;

        if (comboBenarBerturut >= 5) {
            comboBenarBerturut = 0;
            if (nyawa < 5) {
                daftarHati[nyawa] = new Heart();
                addObject(daftarHati[nyawa], 580 + (nyawa * 38), 35);
                daftarHati[nyawa].mulaiKedip();
                nyawa++;
                // --- PANGGIL SFX BONUS NYAWA ---
                SFX.playBenar();
            }
        }
    }
        // Dipanggil otomatis oleh CoinSoal saat koin lolos melewati tubuh Koi
    public void koinTerlewat() {
        if (!isGameOver) {
            pemicuSalah();
            kurangiNyawa();
        }
    }
    // ==============================================================
    // 1. LOGIKA ASLI SPAWNER KOIN (BERTAHAP PERLAHAN DULU)[cite: 3]
    // ==============================================================
    private void munculkanKoinBertahap() {
        timerSpawn--;
        
        // Koin hanya keluar satu per satu saat hitungan mundur mencapai 0[cite: 3]
        if (timerSpawn <= 0) {
            CoinSoal koinBaru = new CoinSoal(batasAngkaLevel, kecepatanKoinSaatIni);
            // Spawn koin di tepi kanan layar (X = 750) dengan ketinggian Y acak[cite: 3]
            addObject(koinBaru, 750, Greenfoot.getRandomNumber(280) + 60);
            
            // Reset timer spawn kembali ke jeda yang sedang aktif[cite: 3]
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
                // --- PANGGIL SFX BENAR ---
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
        // Putar SFX salah yang sudah disetel volumenya
        //if (sfxSalah != null) {
            // Hentikan dulu jika sebelumnya sedang berbunyi agar suaranya instan terulang
        //    sfxSalah.stop();
        //    sfxSalah.play();
        //}
        
        // --- PANGGIL SFX SALAH (TETOT) ---
        SFX.playSalah();
        comboBenarBerturut = 0;
        timerGetar = 10;
        jawabanDiketik = "";
    }
    
    // Method pembantu agar game tidak crash jika file audio belum ada
    private void putarSuaraAman(String namaFile) {
        try {
            Greenfoot.playSound(namaFile);
        } catch (Exception e) {
            // Melindungi dari error FileNotFound / SoundException
        }
    }
    private void prosesCekJawabanOtomatis() {
        if (jawabanDiketik.isEmpty()) return; 

        int jawabanPemain = Integer.parseInt(jawabanDiketik);
        List<CoinSoal> daftarKoin = getObjects(CoinSoal.class); 

        for (CoinSoal koin : daftarKoin) {
            if (koin.getJawaban() == jawabanPemain) {
                Greenfoot.playSound("dingSFX.mp3"); // Nama file harus persis di folder sounds
                playerKoi.tembak(koin.getX(), koin.getY());
                tambahSkor();
                // Memicu kenaikan kecepatan game
                periksaPeningkatanKecepatan();

                // Reset teks input
                jawabanDiketik = ""; 
                //tampilanInput.ubahTeks("Jawaban: ", 30, "Press Start 2P"); 

                break; 
            }
        }
    }
    
    
    
    // --- FUNGSI TAMBAH SKOR & CEK BEST SCORE ---
    public void tambahSkor() {
        skorSaatIni += 1;
        if (skorSaatIni > bestScore) {
            bestScore = skorSaatIni;
        }
        if (tampilanSkor != null) {
            tampilanSkor.ubahTeks("Point: " + skorSaatIni, 20, "Press Start 2P");
        }
    }
    // ==============================================================
    // 2. LOGIKA ASLI: MAKIN BANYAK & CEPAT KETIKA USER BISA MENJAWAB[cite: 3]
    // ==============================================================
    private void periksaPeningkatanKecepatan() {
        totalKoinHancur++;
        
        // Setiap user berhasil menjawab kelipatan 5 soal benar:[cite: 3]
        if (totalKoinHancur % 5 == 0) {
            // Jeda kemunculan dikurangi 20 frame -> koin keluar lebih sering/rapat (makin banyak di layar)[cite: 3]
            if (jedaSpawnKoin > 50) {
                jedaSpawnKoin -= 20; // Batas minimal 50 frame agar tetap manusiawi untuk anak SD[cite: 3]
            }
            // Kecepatan laju horizontal koin ditambah -> meluncur lebih cepat[cite: 3]
            if (kecepatanKoinSaatIni < 5) {
                kecepatanKoinSaatIni += 1;
            }
        }
    }
    
    
    // --- LOGIKA GETAR KOI & KOIN (SHAKE EFFECT) ---
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
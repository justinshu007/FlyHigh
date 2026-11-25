    import greenfoot.*;
    import java.util.List;
    import java.util.ArrayList;
    
    public class MyWorld extends World
    {
        private int batasAngkaLevel; 
        private Koi playerKoi;
        private boolean isPaused = false;
        private GreenfootImage bgA;
        private GreenfootImage bgB;
    
        private int scrollX = 0;
        private int speed = 1;
        private double scoreMultiplier = 1.0;
        
        // --- PENYIMPAN KOIN SAAT PAUSE ---
            private List<CoinSoal> koinTersimpan = new ArrayList<>();
            private List<int[]> posisiKoinTersimpan = new ArrayList<>();
        // --- STATUS GAME OVER ---
        private boolean isGameOver = false;
        
        // --- VARIABEL SPAWNER KOIN (PSIKOLOGI FLOW & RECOVERY) ---
        private int jedaSpawnKoin = 160;      // Jeda awal santai (~3,6 detik) untuk orientasi
        private int timerSpawn = 45;          // Koin pertama segera menyapa agar tidak menunggu lama
        private int kecepatanKoinSaatIni = 2; // Laju gerak awal lembut (1 px/frame)
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
        
        public MyWorld()
        {
            this(3);
        }
    
        public MyWorld(int batasAngka)
        {
            super(750, 420, 1);
            
            setPaintOrder(
                TutorialDialog.class,        
            
                FloatingMessage.class,
            
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
                Bubble.class,
                TutorialOverlay.class
            );
    
            bgA = new GreenfootImage("background.png");
            bgB = new GreenfootImage("background1.png");
            bgA.scale(750, 420);
            bgB.scale(750, 420);
    
            this.batasAngkaLevel = (batasAngka <= 0) ? 3 : batasAngka;
            
            if (batasAngkaLevel <= 3)
            {
                scoreMultiplier = 1.0;
            }
            else if (batasAngkaLevel <= 5)
            {
                scoreMultiplier = 1.5;
            }
            else
            {
                scoreMultiplier = 3.0;
            }
            
            isPaused = true;
    
            addObject(
                new TutorialOverlay(),
                getWidth()/2,
                getHeight()/2
            );
            
            addObject(
                new TutorialDialog(),
                310,
                320
            );
            
            // 1. Koi Utama
            this.playerKoi = new Koi();
            addObject(this.playerKoi, 115, 230);
    
            // 2. Tombol Pause di Pojok Kiri Atas
            addObject(new BtnPause(), 55, 45);
    
            // 3. Skor Angka Saja di Tengah Atas
            tampilanSkor = new TeksUI(String.valueOf(skorSaatIni), 28, "Press Start 2P");
            addObject(tampilanSkor, 375, 45);
            
            // 4. 3 Hati Awal di Pojok Kanan Atas
            for (int i = 0; i < 3; i++) {
                daftarHati[i] = new Heart();
                addObject(daftarHati[i], 540 + (i * 42), 45);
            }
        }
    
        public void act()
        {    
            if (!isGameOver) {
                scrollBackground();   
                if (!isPaused) {
                           // Berhenti saat pause
                    munculkanKoinBertahap();     // Berhenti saat pause
                    sistemMengetikDanMenembak(); // Berhenti saat pause
                    efekGetarObjek();            // Berhenti saat pause
                }
    
                // Bubble tetap berjalan saat pause
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
        
        public void selesaiTutorial()
        {
            isPaused = false;
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
            jawabanDiketik = ""; // Reset ketikan setengah jalan agar tidak bocor
            Greenfoot.getKey();
    
            // 1. Simpan posisi & objek koin aktif, lalu hilangkan dari layar
            koinTersimpan.clear();
            posisiKoinTersimpan.clear();
            for (CoinSoal koin : getObjects(CoinSoal.class)) {
                koinTersimpan.add(koin);
                posisiKoinTersimpan.add(new int[]{ koin.getX(), koin.getY() });
            }
            removeObjects(koinTersimpan);
    
            // 2. Tampilkan overlay & dialog pause
            addObject(new PauseOverlay(), getWidth() / 2, getHeight() / 2);
            addObject(new PauseDialogBox(), getWidth() / 2, getHeight() / 2);
            addObject(new BtnBackPause(), 400, 195);
            addObject(new BtnMainMenu(), 400, 240);
        }
    
        public void lanjutkanPermainan() {
            isPaused = false;
            Greenfoot.getKey();
    
            // 1. Hapus komponen dialog pause
            removeObjects(getObjects(BtnBackPause.class));
            removeObjects(getObjects(BtnMainMenu.class));
            removeObjects(getObjects(PauseDialogBox.class));
            removeObjects(getObjects(PauseOverlay.class));
    
            // 2. Munculkan kembali koin-koin di posisi terakhirnya
            for (int i = 0; i < koinTersimpan.size(); i++) {
                CoinSoal koin = koinTersimpan.get(i);
                int[] pos = posisiKoinTersimpan.get(i);
                addObject(koin, pos[0], pos[1]);
            }
            koinTersimpan.clear();
            posisiKoinTersimpan.clear();
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

                addObject(
                    new GameOverDB(
                        skorSaatIni,
                        bestScore
                    ),
                    getWidth() / 2,
                    getHeight() / 2
                );
                addObject(new BtnMainMenu(), getWidth() / 2, 275);
            }
        }
    
        public void periksaBonusNyawa() {
            comboBenarBerturut++;
    
            if (comboBenarBerturut >= 5) {
                comboBenarBerturut = 0;
                if (nyawa < 5) {
                    daftarHati[nyawa] = new Heart();
                    addObject(daftarHati[nyawa], 540 + (nyawa * 42), 45);
                    daftarHati[nyawa].mulaiKedip();
                    nyawa++;
                    SFX.playLiveUp(); // <-- Memutar SFX khusus saat nyawa bertambah
                }
            }
        }
    
        public void koinTerlewat() {
            if (!isGameOver) {
                pemicuCoinTerlewat();
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
        
        public void pemicuCoinTerlewat()
        {
            SFX.playSalah();
        
            addObject(
                new FloatingMessage("COIN PASSED!"),
                500,
                100
            );
        
            timerGetar = 10;
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
    
                if (playerKoi != null) {
                    playerKoi.tembak(koin.getX(), koin.getY());
                }
    
                if (getObjects(CoinSoal.class).size() <= 1 && timerSpawn > 45) {
                    timerSpawn = 45;
                }
    
                jawabanDiketik = "";
                break;
            } else if (jawabanDiketik.length() == 1 && koin.getJawaban() >= 10) {
                // Hanya tahan validasi jika baru mengetik 1 digit dan memang ada koin bernilai >= 10
                if (String.valueOf(koin.getJawaban()).startsWith(jawabanDiketik)) {
                    adaPotensiBenar = true;
                }
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
                new FloatingMessage("WRONG ANSWER!"),
                500,
                100
            );
        
            comboBenarBerturut = 0;
            streakKecepatan = 0;
            timerGetar = 10;
            kurangiNyawa();
            jawabanDiketik = "";
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
    }
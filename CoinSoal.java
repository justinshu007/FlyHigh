import greenfoot.*;

public class CoinSoal extends Actor
{
    private int jawabanBenar;
    private int kecepatan = 2;
    private boolean sudahTerlewat = false; // Mencegah pemanggilan ganda
    
    public CoinSoal(int batasAngkaLevel, int kecepatanKoin) {
        this.kecepatan = kecepatanKoin; // Menyimpan kecepatan dinamis dari MyWorld

        // Pengaman batas level agar tidak bernilai <= 0
        int batas = (batasAngkaLevel <= 0) ? 3 : batasAngkaLevel;

        int angka1 = Greenfoot.getRandomNumber(batas) + 1;
        int angka2 = Greenfoot.getRandomNumber(batas) + 1;
        String teksSoal = "";

        // Logika Level Hard (batas == 10): Bisa Penjumlahan atau Pengurangan
        if (batas == 10 && Greenfoot.getRandomNumber(2) == 1) {
            if (angka1 < angka2) {
                int temp = angka1;
                angka1 = angka2;
                angka2 = temp;
            }
            
            jawabanBenar = angka1 - angka2;
            teksSoal = angka1 + " - " + angka2;
        } else {
            jawabanBenar = angka1 + angka2;
            teksSoal = angka1 + " + " + angka2;
        }

        // Buat gambar koin dan cetak soal di atasnya
        GreenfootImage gambarKoin = new GreenfootImage("icon_coin.png");
        gambarKoin.scale(150, 150);
        
        GreenfootImage teks = new GreenfootImage(teksSoal, 30, Color.BLACK, new Color(0,0,0,0));
        
        int posisiTengahX = (gambarKoin.getWidth() - teks.getWidth()) / 2;
        int posisiTengahY = (gambarKoin.getHeight() - teks.getHeight()) / 2;
        
        gambarKoin.drawImage(teks, posisiTengahX, posisiTengahY); 
        setImage(gambarKoin);
    }

    public void act()
    {
        // 1. REM DARURAT PERTAMA: Jika koin sudah hancur
        if (getWorld() == null) {
            return; 
        }

        // 2. REM PAUSE: Bekukan pergerakan koin saat dialog Game Paused terbuka
        MyWorld dunia = (MyWorld) getWorld();
        if (dunia.isPaused()) {
            return;
        }

        // Koin bergerak pelan dari kanan ke kiri
        setLocation(getX() - kecepatan, getY());
        
        // Jika koin mencapai ujung kiri layar
        if (getX() <= 0) {
            getWorld().removeObject(this);
            return; 
        }
        
        // Koin melewati posisi Ikan Koi (X = 115)
        if (!sudahTerlewat && getX() < 90) {
            sudahTerlewat = true;
            
            if (dunia != null) {
                dunia.koinTerlewat();
            }

            // Wajib cek getWorld() != null karena koin bisa saja sudah dihapus saat Game Over
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
            return;
        }
    }

    public int getJawaban() {
        return jawabanBenar;
    }
}
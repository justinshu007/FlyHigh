import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CoinSoal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CoinSoal extends Actor
{
    /**
     * Act - do whatever the CoinSoal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int jawabanBenar;
    private int kecepatan = 2;
    private boolean sudahTerlewat = false; // Mencegah pemanggilan ganda
    
    public CoinSoal(int batasAngkaLevel,int kecepatanKoin) {
        // Pengaman batas level agar tidak bernilai <= 0
        int batas = (batasAngkaLevel <= 0) ? 3 : batasAngkaLevel;

        int angka1 = Greenfoot.getRandomNumber(batas) + 1;
        int angka2 = Greenfoot.getRandomNumber(batas) + 1;
        String teksSoal = "";

        // Logika Level Hard (batas == 10): Bisa Penjumlahan atau Pengurangan
        if (batas == 10 && Greenfoot.getRandomNumber(2) == 1) {
            // OPERASI PENGURANGAN
            // Pastikan angka pertama selalu lebih besar atau sama dengan angka kedua
            if (angka1 < angka2) {
                int temp = angka1;
                angka1 = angka2;
                angka2 = temp;
            }
            
            jawabanBenar = angka1 - angka2;
            teksSoal = angka1 + " - " + angka2;
        } else {
            // OPERASI PENJUMLAHAN (Easy, Medium, atau sebagian soal Hard)
            jawabanBenar = angka1 + angka2;
            teksSoal = angka1 + " + " + angka2;
        }
        // 2. Buat gambar koin dan cetak soal di atasnya
        GreenfootImage gambarKoin = new GreenfootImage("icon_coin.png"); // Ganti nama file sesuai di foldermu
        gambarKoin.scale(150, 150); // Ukuran koinmu[cite: 5]
        
        // Membuat teks soal dengan font ukuran 20
        GreenfootImage teks = new GreenfootImage(teksSoal, 30, Color.BLACK, new Color(0,0,0,0));
        
        // --- TRIK POSISI TENGAH OTOMATIS ---
        int posisiTengahX = (gambarKoin.getWidth() - teks.getWidth()) / 2;
        int posisiTengahY = (gambarKoin.getHeight() - teks.getHeight()) / 2;
        
        // Menempelkan teks menggunakan variabel yang sudah dihitung
        gambarKoin.drawImage(teks, posisiTengahX, posisiTengahY); 
        
        setImage(gambarKoin);
    }
    public void act()
    {
        // 1. REM DARURAT PERTAMA: 
        // Jika koin ini sudah hancur (misal baru saja ditembak Meteor), 
        // langsung hentikan semua aktivitas agar tidak error!
        if (getWorld() == null) {
            return; 
        }

        // Koin bergerak pelan dari kanan ke kiri
        setLocation(getX() - kecepatan, getY());
        
        // 2. Jika koin sudah mencapai ujung kiri layar (terlewat oleh pemain)
        if (getX() <= 0) {
            MyWorld dunia = (MyWorld) getWorld();
            //dunia.kurangiNyawa(); 
            
            // Hapus koin ini dari layar
            getWorld().removeObject(this);
            
            // 3. REM DARURAT KEDUA:
            // Wajib ditambahkan agar sistem tidak membaca kode apa-apa lagi di bawahnya
            // setelah benda ini dihapus.
            return; 
        }
        
        // KOIN MELEWATI IKAN KOI (Posisi Koi ada di X = 115)
        // Jika koin sudah melewati bagian belakang ikan (X < 90) dan belum dihitung terlewat:
        if (!sudahTerlewat && getX() < 90) {
            sudahTerlewat = true;
            
            MyWorld dunia = (MyWorld) getWorld();
            if (dunia != null) {
                // Panggil method pengurangan nyawa beserta sfx tetot di MyWorld
                dunia.koinTerlewat();
            }

            // Hapus koin dari layar setelah melapor
            getWorld().removeObject(this);
            return;
        }
    }
    public int getJawaban() {
        return jawabanBenar;
    }
}

import greenfoot.*;

public class CoinSoal extends Actor
{
    private int jawabanBenar;
    private int kecepatan = 2;
    private boolean sudahTerlewat = false;

    public CoinSoal(int batasAngkaLevel, int kecepatanKoin) {
        this(batasAngkaLevel, kecepatanKoin, 10);
    }

    public CoinSoal(int batasAngkaLevel, int kecepatanKoin, int skorSaatIni) {
        this.kecepatan = kecepatanKoin;

        int batas = (batasAngkaLevel <= 0) ? 3 : batasAngkaLevel;

        // PSIKOLOGI SCAFFOLDING: 4 soal pertama pakai rentang pemanasan agar anak langsung percaya diri
        int batasEfektif = batas;
        if (skorSaatIni < 4 && batas > 3) {
            batasEfektif = Math.max(3, batas / 2);
        }

        int angka1 = Greenfoot.getRandomNumber(batasEfektif) + 1;
        int angka2 = Greenfoot.getRandomNumber(batasEfektif) + 1;
        String teksSoal = "";

        // Logika Level Hard (batas == 10): Penjumlahan atau Pengurangan (setelah 3 soal pemanasan)
        if (batas == 10 && skorSaatIni >= 3 && Greenfoot.getRandomNumber(2) == 1) {
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

        GreenfootImage gambarKoin = new GreenfootImage("icon_coin.png");
        gambarKoin.scale(130, 130); // Proporsional untuk 3 jalur renang vertikal
        
        GreenfootImage teks = new GreenfootImage(teksSoal, 28, Color.BLACK, new Color(0, 0, 0, 0));
        
        int posisiTengahX = (gambarKoin.getWidth() - teks.getWidth()) / 2;
        int posisiTengahY = (gambarKoin.getHeight() - teks.getHeight()) / 2;
        
        gambarKoin.drawImage(teks, posisiTengahX, posisiTengahY); 
        setImage(gambarKoin);
    }

    public void act()
    {
        if (getWorld() == null) {
            return; 
        }

        MyWorld dunia = (MyWorld) getWorld();
        if (dunia.isPaused()) {
            return;
        }

        setLocation(getX() - kecepatan, getY());
        
        if (getX() <= 0) {
            getWorld().removeObject(this);
            return; 
        }
        
        if (!sudahTerlewat && getX() < 90) {
            sudahTerlewat = true;
            dunia.koinTerlewat();

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
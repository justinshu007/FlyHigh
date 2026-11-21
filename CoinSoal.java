import greenfoot.*;

public class CoinSoal extends Actor
{
    private int jawabanBenar;
    private int kecepatan = 2;
    private boolean sudahTerlewat = false;
    private GreenfootImage[] animasiHancur = new GreenfootImage[11];
    private boolean sedangHancur = false;
    private int frameHancur = 0;
    private int timerAnimasi = 0;
    private CoinType tipeCoin;
    

    public CoinSoal(int batasAngkaLevel, int kecepatanKoin) {
        this(batasAngkaLevel, kecepatanKoin, 10);
    }

    public CoinSoal(int batasAngkaLevel, int kecepatanKoin, int skorSaatIni) {
        this.kecepatan = kecepatanKoin;
        
        tipeCoin = CoinType.values() [Greenfoot.getRandomNumber(3)];
        
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

        GreenfootImage gambarKoin;

        if(tipeCoin == CoinType.GOLD)
        {
            gambarKoin = new GreenfootImage("coinGold.png");
        }
        else if(tipeCoin == CoinType.SILVER)
        {
            gambarKoin = new GreenfootImage("coinSilver.png");
        }
        else
        {
            gambarKoin = new GreenfootImage("coinBronze.png");
        }
        gambarKoin.scale(80,80);
        
        Font fontPixel = new Font("Pixelify Sans", true, false, 22);

        gambarKoin.setFont(fontPixel);
        
        // Outline biru tua
        Color outline = new Color(10, 20, 80);
        
        int textX = 20;
        int textY = 46;
        
        // OUTLINE
        gambarKoin.setColor(outline);
        
        gambarKoin.drawString(teksSoal, textX - 2, textY);
        gambarKoin.drawString(teksSoal, textX + 2, textY);
        gambarKoin.drawString(teksSoal, textX, textY - 2);
        gambarKoin.drawString(teksSoal, textX, textY + 2);
        
        gambarKoin.drawString(teksSoal, textX - 2, textY - 2);
        gambarKoin.drawString(teksSoal, textX + 2, textY - 2);
        gambarKoin.drawString(teksSoal, textX - 2, textY + 2);
        gambarKoin.drawString(teksSoal, textX + 2, textY + 2);
        
        // TEKS PUTIH
        gambarKoin.setColor(Color.WHITE);
        gambarKoin.drawString(teksSoal, textX, textY); 
        setImage(gambarKoin);
        
        //for animasi
        String prefix;
        
        if(tipeCoin == CoinType.GOLD)
        {
            prefix = "coinG_";
        }
        else if(tipeCoin == CoinType.SILVER)
        {
            prefix = "coinS_";
        }
        else
        {
            prefix = "coinB_";
        }
        
        for(int i = 0; i < 11; i++)
        {
            animasiHancur[i] =
                new GreenfootImage(prefix + (i + 1) + ".png");
        
            animasiHancur[i].scale(115, 115);
        }
    }

    public void act()
    {
        if (sedangHancur)
        {
            jalankanAnimasiHancur();
            return;
        }
        
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
    
    
    //Dipanggil saat meteor mengenai koin
    public void mulaiAnimasiHancur()
    {
        if (!sedangHancur)
        {
            sedangHancur = true;
            frameHancur = 0;
            timerAnimasi = 0;
    
            setImage(animasiHancur[0]);
        }
    }
    
    /**
     * Menjalankan animasi hancur
     */
    private void jalankanAnimasiHancur()
    {
        timerAnimasi++;
        
        // ganti frame setiap 3 act
        if (timerAnimasi >= 4)
        {
            timerAnimasi = 0;
    
            frameHancur++;
    
            if (frameHancur < animasiHancur.length)
            {
                setImage(animasiHancur[frameHancur]);
            }
            else
            {
                // animasi selesai
                if (getWorld() != null)
                {
                    getWorld().removeObject(this);
                }
            }
        }
    }
    
    public CoinType getTipeCoin()
    {
        return tipeCoin;
    }
}
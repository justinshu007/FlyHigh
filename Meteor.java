import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Meteor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Meteor extends Actor
{
    private int kecepatanPeluru = 10;
    private CoinType tipeMeteor;
    
    public Meteor(CoinType tipe)
    {
        tipeMeteor = tipe;
    
        GreenfootImage gambar;
    
        if(tipeMeteor == CoinType.GOLD)
        {
            gambar = new GreenfootImage("meteorG.png");
        }
        else if(tipeMeteor == CoinType.SILVER)
        {
            gambar = new GreenfootImage("meteorS.png");
        }
        else
        {
            gambar = new GreenfootImage("meteorB.png");
        }
    
        gambar.scale(20, 20);
        setImage(gambar);
    }
    /**
     * Act - do whatever the Meteor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        move(kecepatanPeluru); // Bergerak cepat mengejar target[cite: 1]
        
        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }
        
        // Jika peluru menabrak KoinSoal, hancurkan koinnya[cite: 1]
        CoinSoal koinTarget =
    (CoinSoal)getOneIntersectingObject(CoinSoal.class);

        if (koinTarget != null)
        {
            // WARNA SAMA = BOLEH HANCUR
            if(koinTarget.getTipeCoin() == tipeMeteor)
            {
                MyWorld dunia = (MyWorld)getWorld();
            
                SFX.playBenar();
            
                dunia.tambahSkor();
            
                dunia.periksaBonusNyawa();
            
                dunia.periksaPeningkatanKecepatan();
            
                koinTarget.mulaiAnimasiHancur();
            
                getWorld().removeObject(this);
            }
            // WARNA BERBEDA = TEMBAKAN GAGAL
            else
            {
                MyWorld dunia = (MyWorld)getWorld();
            
                dunia.efekSalahWarna();
            
                getWorld().removeObject(this);
            }

        
            return;
        
        }
    }
    
    public CoinType getTipeMeteor()
    {
        return tipeMeteor;
    }
}

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
    
    public Meteor()
    {
        GreenfootImage gambar;
    
        int variasi = Greenfoot.getRandomNumber(3);
    
        if(variasi == 0)
        {
            gambar = new GreenfootImage("meteorB.png");
        }
        else if(variasi == 1)
        {
            gambar = new GreenfootImage("meteorS.png");
        }
        else
        {
            gambar = new GreenfootImage("meteorG.png");
        }
    
        gambar.scale(20, 20);
    
        setImage(gambar);
    }

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

        if(koinTarget != null)
        {
            MyWorld dunia = (MyWorld)getWorld();
        
            SFX.playBenar();
        
            dunia.tambahSkor();
        
            dunia.periksaBonusNyawa();
        
            dunia.periksaPeningkatanKecepatan();
        
            koinTarget.mulaiAnimasiHancur();
        
            getWorld().removeObject(this);
        
            return;
        }
    }
}

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
    
    public Meteor(){
        // Atur ukuran peluru, ganti gambar jika punya asetnya[cite: 1]
        GreenfootImage gambar = getImage();
        gambar.scale(40, 30); 
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
        CoinSoal koinTarget = (CoinSoal) getOneIntersectingObject(CoinSoal.class);
        if (koinTarget != null) {
            getWorld().removeObject(koinTarget);
            getWorld().removeObject(this);
            // Kamu bisa menambahkan efek suara atau penambah skor di sini nantinya!
        }
    }
}

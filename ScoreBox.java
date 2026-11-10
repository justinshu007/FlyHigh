import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScoreBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScoreBox extends Actor
{
    /**
     * Act - do whatever the ScoreBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public ScoreBox(int skorAkhir, int bestScore)
    {
        // 1. Ambil gambar aset papan skor Anda
        GreenfootImage board = new GreenfootImage("papan_skor.png"); // Pastikan nama file sesuai di folder images
        
        // Sesuaikan ukuran papan skor jika terlalu besar/kecil (misal: 350x220)
        board.scale(350, 220);

        // 2. Render teks skor akhir dan rekor tertinggi
        GreenfootImage teksFinal = new GreenfootImage("Score: " + skorAkhir, 22, Color.WHITE, new Color(0, 0, 0, 0));
        GreenfootImage teksBest  = new GreenfootImage("Best : " + bestScore, 22, Color.YELLOW, new Color(0, 0, 0, 0));

        // 3. Trik posisi tengah otomatis di papan
        int xFinal = (board.getWidth() - teksFinal.getWidth()) / 2;
        int xBest  = (board.getWidth() - teksBest.getWidth()) / 2;

        // Gambar teks di atas papan
        board.drawImage(teksFinal, xFinal, (board.getHeight() / 2) - 25);
        board.drawImage(teksBest, xBest, (board.getHeight() / 2) + 15);

        setImage(board);
    }
    public void act()
    {
        // Add your action code here.
    }
}

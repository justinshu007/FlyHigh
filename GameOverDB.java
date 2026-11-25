import greenfoot.*;
import java.awt.GraphicsEnvironment;

public class GameOverDB extends Actor
{
    public GameOverDB(int skorMentah, int bestScore)
    {
        GreenfootImage board;

        try {
            board = new GreenfootImage("dbGameScore.png");
            board.scale(440, 340); 
        } catch (Exception e) {
            board = new GreenfootImage(440, 290);
            board.setColor(new Color(235, 232, 220));
            board.fillRect(0, 0, 420, 340);
            board.setColor(Color.BLACK);
            board.drawRect(2, 2, 436, 286);
        }

        //Tampilan font
        Font fontSkor = new Font("Pixelify Sans", true, false, 24);
        board.setFont(fontSkor);

        String teksSkor =
            "Your Score : " + skorMentah;
        
        String teksBest =
            "Best Score : " + bestScore;

        drawOutlinedText(board, teksSkor, 120, 160);
                
        drawOutlinedText(board, teksBest, 120, 190);

        setImage(board);
    }

    /**
     * Membuat outline sederhana untuk teks
     */
    private void drawOutlinedText(GreenfootImage img, String text, int x, int y)
    {
        Color outline = new Color(10, 20, 80);
    
        img.setColor(outline);
    
        img.drawString(text, x - 2, y);
        img.drawString(text, x + 2, y);
    
        img.drawString(text, x, y - 2);
        img.drawString(text, x, y + 2);
    
        img.drawString(text, x - 2, y - 2);
        img.drawString(text, x + 2, y - 2);
    
        img.drawString(text, x - 2, y + 2);
        img.drawString(text, x + 2, y + 2);
    
        // Teks utama putih
        img.setColor(Color.WHITE);
        img.drawString(text, x, y);
    }

    public void act()
    {
    }
}
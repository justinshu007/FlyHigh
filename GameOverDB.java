import greenfoot.*;
import java.awt.GraphicsEnvironment;

public class GameOverDB extends Actor
{
    public GameOverDB(int skorAkhir, int bestScore)
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

        String teksSkor = "Your Score : " + skorAkhir;
        String teksBest = "Best Score : " + bestScore;

        // Posisi tengah
        int posXSkor =
            (board.getWidth() - teksSkor.length() * 11) / 2;

        int posXBest =
            (board.getWidth() - teksBest.length() * 11) / 2;

        int posYSkor = 140;
        int posYBest = 180;

        // Outline Hitam
        board.setColor(Color.BLACK);

        drawOutlinedText(board, teksSkor, posXSkor, posYSkor);
        drawOutlinedText(board, teksBest, posXBest, posYBest);

        // Teks utama
        board.setColor(Color.WHITE);
        board.drawString(teksSkor, posXSkor, posYSkor);

        board.setColor(new Color(255, 220, 0));
        board.drawString(teksBest, posXBest, posYBest);

        setImage(board);
    }

    /**
     * Membuat outline sederhana untuk teks
     */
    private void drawOutlinedText(
        GreenfootImage img,
        String text,
        int x,
        int y)
    {
        img.drawString(text, x - 2, y);
        img.drawString(text, x + 2, y);
        img.drawString(text, x, y - 2);
        img.drawString(text, x, y + 2);

        img.drawString(text, x - 2, y - 2);
        img.drawString(text, x + 2, y - 2);
        img.drawString(text, x - 2, y + 2);
        img.drawString(text, x + 2, y + 2);
    }

    public void act()
    {
    }
}
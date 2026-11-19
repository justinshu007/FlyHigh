import greenfoot.*;

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

        //Tampilan FONT
        Font fontSkor = new Font("PixelifySans", true, false, 16);
        board.setFont(fontSkor);

        String teksSkor = "your score: " + skorAkhir;
        String teksBest = "best score: " + bestScore;

        // TEKS RATA TENGAH
        int posXSkor = Math.max(20, (board.getWidth() - (teksSkor.length() * 15)) / 2);
        int posXBest = Math.max(20, (board.getWidth() - (teksBest.length() * 15)) / 2);

        // teks: "Your score"
        board.setColor(Color.BLACK);
        board.drawString(teksSkor, posXSkor, 130);

        // teks: "Best score"
        board.setColor(new Color(180, 110, 0));
        board.drawString(teksBest, posXBest, 168);

        setImage(board);
    }

    public void act()
    {
    }
}
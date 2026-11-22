import greenfoot.*;

public class TutorialDialog extends Actor
{
    private int halaman = 0;

    private boolean tombolSudahDilepas = true;

    private String[] isiTutorial =
    {
        "Hi there! I'm Mr. Fih.\nYou can call me Fih.",

        "I'll Teach you how to\nplay.",

        "Type the right answer for\nthe math problem.",

        "Example: 1 + 1 is 2\nso just press 2 on your keyboard.",

        "Do not let the coin pass!",

        "Press Space to start!"
    };

    public TutorialDialog()
    {
        updateDialog();
    }

    public void act()
    {
        if(Greenfoot.isKeyDown("space"))
        {
            if(tombolSudahDilepas)
            {
                tombolSudahDilepas = false;

                halaman++;

                if(halaman >= isiTutorial.length)
                {
                    selesaiTutorial();
                }
                else
                {
                    updateDialog();
                }
            }
        }
        else
        {
            tombolSudahDilepas = true;
        }
    }

    private void updateDialog()
    {
        GreenfootImage dialog =
            new GreenfootImage("speech_box.png");

        dialog.scale(480, 160);

        Font font =
            new Font("Pixelify Sans", true, false, 20);

        dialog.setFont(font);

        String teks = isiTutorial[halaman];

        Color outline = new Color(10, 20, 80);

        dialog.setColor(outline);

        int x = 40;
        int y = 60;

        String[] baris = teks.split("\n");

        for(int i = 0; i < baris.length; i++)
        {
            int yBaris = y + (i * 28);

            drawLabel(
                dialog,
                baris[i],
                x,
                yBaris
            );
        }

        dialog.setColor(Color.WHITE);

        for(int i = 0; i < baris.length; i++)
        {
            dialog.drawString(
                baris[i],
                x,
                y + (i * 28)
            );
        }

        drawLabel(
            dialog,
            "Mr. Fih",
            372,
            24
        );
        
        drawLabel(
            dialog,
            "SPACE >>",
            330,
            135
        );
        setImage(dialog);
    }
    
    private void drawLabel(GreenfootImage img,String text, int x, int y)
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
    
        img.setColor(Color.WHITE);
        img.drawString(text, x, y);
    }
    
    private void selesaiTutorial()
    {
        MyWorld dunia =
            (MyWorld)getWorld();

        dunia.selesaiTutorial();

        getWorld().removeObjects(
            getWorld().getObjects(TutorialOverlay.class)
        );

        getWorld().removeObject(this);
    }
}
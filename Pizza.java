import greenfoot.*;

public class Pizza extends Actor
{
    private int speed = 3;

    public Pizza()
    {
        setImage("pizza.png");
        getImage().scale(60, 60);
    }

    public void act()
    {
        setLocation(getX() - speed, getY());
    
        Koi fish = (Koi)getOneIntersectingObject(Koi.class);

        if (fish != null)
        {
            int dx = Math.abs(getX() - fish.getX());
            int dy = Math.abs(getY() - fish.getY());
        
            // Hitbox lebih kecil
            if (dx < 25 && dy < 20)
            {
                fish.damage();
        
                getWorld().removeObject(this);
                return;
            }
        }
    
        if(getX() <= 0)
        {
            getWorld().removeObject(this);
            return;
        }
    }
}
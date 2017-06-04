package oo.ep2;

public class Alien extends Sprite
{
    private static final int Y_MAX_SPEED = 1;

    private int speed_y;
    public Alien(int x, int y)
    {
        super(x, y);
        speed_y = Y_MAX_SPEED;
        visible = true;
    }

        public void showAlien()
        {
            loadImage("images/alien_EASY.png");
            y+=speed_y;
        }

}

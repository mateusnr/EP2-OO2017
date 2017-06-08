package oo.ep2;

public class Alien extends Sprite
{
    private static final int Y_MAX_SPEED = 1;

    private int speed_y;
    private String levelImage;

    public Alien(int x, int y)
    {
        super(x, y);
        this.levelImage = "images/alien_EASY.png";
        speed_y = Y_MAX_SPEED;
        visible = true;
    }

    public Alien(int x, int y, String level)
    {
        super(x, y);
        this.levelImage = level;
        speed_y = Y_MAX_SPEED;
        visible = true;
    }

        public void showAlien()
        {
            loadImage(levelImage);
            y+=speed_y;
        }

}

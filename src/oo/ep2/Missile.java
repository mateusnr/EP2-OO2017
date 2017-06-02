package oo.ep2;

public class Missile extends Sprite
{
    private static final int Y_MAX_SPEED = 2;

    private int speed_y;

    public Missile(int x, int y)
    {
        super(x + 6, y);  //É usado x+6 aqui para que a imagem do míssil apareça centralizada na imagem da nave espacial.
        speed_y = Y_MAX_SPEED;
    }


    public void showMissile()
    {
        y-=speed_y; //move o míssil na velocidade determinada
        loadImage("images/missile.png");
    }


}



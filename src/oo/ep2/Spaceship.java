package oo.ep2;

import java.awt.event.KeyEvent;

public class Spaceship extends Sprite
{
    private static final int X_MAX_SPEED = 2;
    private static final int Y_MAX_SPEED = 1;

    private int speed_x;
    private int speed_y;

    public Spaceship(int x, int y) {
        super(x, y);

        initSpaceShip();
    }

    private void initSpaceShip() {
        noThrust();
    }

    private void noThrust() {
        loadImage("images/spaceship.png");
    }

    private void thrust() {
        loadImage("images/spaceship_thrust.png");
    }

    public void move()
    {
        if ((speed_x < 0 && x <= 0) || (speed_x > 0 && x + width >= Game.getWidth())) {
            speed_x = 0;
        }

        x += speed_x;

        if ((speed_y < 0 && y <= 0) || (speed_y > 0 && y + height >= Game.getHeight())) {
            speed_y = 0;
        }

        y += speed_y;
    }

    public void keyPressed(KeyEvent event)
    {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            speed_x = -1 * X_MAX_SPEED;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            speed_x = X_MAX_SPEED;
        }

        if (key == KeyEvent.VK_UP)
        {
            speed_y = -1 * Y_MAX_SPEED;
            thrust();
        }

        if (key == KeyEvent.VK_DOWN)
        {
            speed_y = Y_MAX_SPEED;
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            speed_x = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            speed_y = 0;
            noThrust();
        }
    }
}

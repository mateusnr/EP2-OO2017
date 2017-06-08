package oo.ep2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Spaceship extends Sprite
{
    private static final int X_MAX_SPEED = 2;
    private static final int Y_MAX_SPEED = 2;

    private int speed_x;
    private int speed_y;

    private int score;
    private int lives;


    private ArrayList<Missile> missiles = new ArrayList<Missile>();

    public int getLives() {
        return lives;
    }

    public void setLives(int lives)
    {
        this.lives -= lives;
    }

    public Spaceship(int x, int y) {
        super(x, y);
        lives = 2;
        score = 0;

        initSpaceShip();
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public int getScore(){ return score; }

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

    public void moveMissile()
    {
        for(int i = 0; i < missiles.size(); ++i)
        {
            missiles.get(i).showMissile();
            if(missiles.get(i).getY() > Game.getHeight()) //Remove o míssil da array quando ele chegar numa posição maior que o quadro do jogo
            {
                missiles.remove(i);
            }
        }
    }


    public void keyPressed(KeyEvent event)
    {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
        {
            speed_x = -1 * X_MAX_SPEED;
        }

        else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
        {
            speed_x = X_MAX_SPEED;
        }

        else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
        {
            speed_y = -1 * Y_MAX_SPEED;
            thrust();
        }

        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
        {
            speed_y = Y_MAX_SPEED;
        }

        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER)
        {
            missiles.add(new Missile(this.x, this.y));
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            speed_x = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            speed_y = 0;
            noThrust();
        }

        if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER){}
    }

    public synchronized void removeUsedMissiles() {
        for (int i = 0; i < missiles.size(); i++) {
            if (!missiles.get(i).isVisible())
                missiles.remove(i);
        }


    }
}

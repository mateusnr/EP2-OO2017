package oo.ep2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Map extends JPanel implements ActionListener
{
    private final int SPACESHIP_X = 220;
    private final int SPACESHIP_Y = 430;

    private static final int EASY = 2;
    private static final int MEDIUM = 6;
    private static final int HARD = 11;
    private static final int MEDIUM_SCORE = 200;
    private static final int HARD_SCORE = 600;
    private static final String ALIEN_EASY = "images/alien_EASY.png";
    private static final String ALIEN_MEDIUM = "images/alien_MEDIUM.png";
    private static final String ALIEN_HARD = "images/alien_HARD.png";

    private final Timer timer_map;

    private final Image background;
    private final Spaceship spaceship;
    private final ArrayList<Alien> aliens;

    private int difficulty;
    private String difficultyImage;
    private int delay = 0;
    private boolean isAlive;
    private boolean isRunning;


    public Map()
    {
        addKeyListener(new KeyListerner());

        setFocusable(true);
        setDoubleBuffered(true);
        ImageIcon image = new ImageIcon("images/space.jpg");

        this.background = image.getImage();

        isAlive = true;
        isRunning = true;
        difficulty = EASY;
        difficultyImage = ALIEN_EASY;
        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
        aliens = new ArrayList<>();

        timer_map = new Timer(Game.getDelay(), this);
        timer_map.start();

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(this.background, 0, 0, null);

        if (isAlive) {
            drawScore(g);
            drawSpaceship(g);
            drawAliens(g);
            drawMissiles(g);
        }
        else
            drawGameOver(g);


        if (!isRunning)
        {
            timer_map.stop();
            drawPause(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawAliens(Graphics g)
    {
        for (Alien alien : aliens)
        {
            g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
        }
    }

    public void checkIfAlive(){
        if(!isAlive)
        {
            timer_map.stop();
        }

    }

    private void drawMissiles(Graphics g)
    {
        for (Missile missile : spaceship.getMissiles())
        {
            g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
        }
    }

    private void drawSpaceship(Graphics g)
    {
        g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
    }

    private void updateDifficulty()
    {
        if (spaceship.getScore() >= MEDIUM_SCORE)
        {
            difficulty = MEDIUM;
            difficultyImage = ALIEN_MEDIUM;
        }

        if (spaceship.getScore() >= HARD_SCORE)
        {
            difficulty = HARD;
            difficultyImage = ALIEN_HARD;
        }
    }

    private synchronized void generateAliens()
    {
        Random randomX = new Random();
        int prev = 0;
        for (int i = 0; i < difficulty; i++) {
            int randX = randomX.nextInt(Game.getWidth());

            if (randX == prev)
                randX = randomX.nextInt(Game.getWidth()) + 20;

            aliens.add(new Alien(randX, 0, difficultyImage));
            prev = randX;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        checkIfAlive();
        updateSpaceship();
        updateAlien();

        spaceshipAndAlienCollision();
        alienAndMissileCollision();

        updateDifficulty();

        if (delay == Game.getDelay())
        {
            generateAliens();
            delay = 0;
        }
        else
            ++delay;

        repaint();
    }

    private void drawScore(Graphics g)
    {

        String message = "Score: " + spaceship.getScore();

        String message2 = "Lives: " + spaceship.getLives();
        Font font = new Font("Cantarell", Font.BOLD, 14);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, 10, 15);
        g.drawString(message2, 10, 30);
    }

    private void drawPause(Graphics g)
    {

        String message = "PAUSED";

        Font font = new Font("Cantarell", Font.BOLD, 14);

        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }

    private void drawGameOver(Graphics g)
    {

        String message = "Game Over";
        String scoreMessage = "Final Score: " + spaceship.getScore();
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
        g.drawString(scoreMessage, (Game.getWidth() - metric.stringWidth(scoreMessage)) / 2, Game.getHeight() / 2 + 15);
    }

    private synchronized void spaceshipAndAlienCollision()
    {
        Rectangle spaceship_d = spaceship.getBounds();

        for (int i = 0; i < aliens.size(); i++)
        {
            Rectangle alien = aliens.get(i).getBounds();
            if (spaceship_d.intersects(alien)) {
                spaceship.setLives(1);
                if (spaceship.getLives() == 0) {
                    isAlive = false;
                }
                aliens.get(i).setVisible(false);
            }
        }
    }

    private synchronized void alienAndMissileCollision()
    {
        ArrayList<Missile> missiles = spaceship.getMissiles();

        for (int i = 0; i < aliens.size(); i++)
        {
            Rectangle alienBounds = aliens.get(i).getBounds();
            for (int j = 0; j < missiles.size(); j++)
            {
                Rectangle missleBounds = missiles.get(j).getBounds();
                if (alienBounds.intersects(missleBounds))
                {
                    spaceship.setScore(2);
                    missiles.get(j).setVisible(false);
                    aliens.get(i).setVisible(false);
                }
            }
        }

        spaceship.removeUsedMissiles();

    }

    private synchronized void updateSpaceship()
    {
        spaceship.move();
        spaceship.moveMissile();
    }

    private synchronized void updateAlien()
    {
        for (int i = 0; i < aliens.size(); i++)
        {
            if (aliens.get(i).getY() > Game.getHeight() || !aliens.get(i).isVisible())
                aliens.remove(i);
            else
                aliens.get(i).showAlien();
        }
    }

    private class KeyListerner extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            spaceship.keyPressed(e);
            int pressedKey = e.getKeyCode();

            if (pressedKey == KeyEvent.VK_ESCAPE)
            {
                if (timer_map.isRunning())
                    isRunning = false;
                else {
                    isRunning = true;
                    timer_map.start();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            spaceship.keyReleased(e);
        }
    }
}

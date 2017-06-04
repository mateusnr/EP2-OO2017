package oo.ep2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Map extends JPanel implements ActionListener
{
    private final int SPACESHIP_X = 220;
    private final int SPACESHIP_Y = 430;
    private final Timer timer_map;

    private final Image background;
    private final Spaceship spaceship;
    private final ArrayList<Alien> aliens;

    private int delay = 0;


    public Map()
    {
        addKeyListener(new KeyListerner());

        setFocusable(true);
        setDoubleBuffered(true);
        ImageIcon image = new ImageIcon("images/space.jpg");

        this.background = image.getImage();

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

        drawSpaceship(g);
        drawAliens(g);
        drawMissiles(g);

        Toolkit.getDefaultToolkit().sync();
    }

    public void drawAliens(Graphics g)
    {
        for (Alien alien : aliens)
        {
            g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
        }
    }

    public void drawMissiles(Graphics g)
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

    public synchronized void generateAliens()
    {
        Random randomX = new Random();

        for (int i = 0; i < 5; i++) {
            int randX = randomX.nextInt(Game.getWidth());
            aliens.add(new Alien(randX, 0));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        updateSpaceship();

        updateAlien();

        if (delay == Game.getDelay())
        {
            generateAliens();
            delay = 0;
        }
        else
            ++delay;

        repaint();
    }

    private void drawMissionAccomplished(Graphics g)
    {

        String message = "MISSION ACCOMPLISHED";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }

    private void drawGameOver(Graphics g)
    {

        String message = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
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
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            spaceship.keyReleased(e);
        }
    }
}

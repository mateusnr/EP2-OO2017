package oo.ep2;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame
{

    public Application()
    {
        add(new Map());

        setSize(Game.getWidth(), Game.getHeight());

        setTitle("EP2");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /*public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                Application game = new Application();
                game.setVisible(true);
            }
        });
    }*/
}

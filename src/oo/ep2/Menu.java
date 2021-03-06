package oo.ep2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Menu {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    public Menu() {
        gui();
        panel();
    }

    private void gui() {
        mainFrame = new JFrame("EP2 - Space War");
        mainFrame.setSize(400, 350);
        mainFrame.setLocation(500, 200);
        mainFrame.setLayout(new GridLayout(3, 1));

        /*mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });*/
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        headerLabel = new JLabel("Header Label", JLabel.CENTER);
        statusLabel = new JLabel("2017", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);

        mainFrame.setVisible(true);

    }

    private void panel() {
        headerLabel.setText("Use as setas do teclado para mover e espaço para atirar");
        JButton jogarButton = new JButton("Jogar");
        JButton sairButton = new JButton("Sair");

        jogarButton.setActionCommand("Jogar");
        sairButton.setActionCommand("Sair");

        jogarButton.addActionListener(new ButtonClickListener());
        sairButton.addActionListener(new ButtonClickListener());

        controlPanel.add(jogarButton);
        controlPanel.add(sairButton);

        mainFrame.setVisible(true);
    }


    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String comando = arg0.getActionCommand();
            if (comando.equals("Jogar")) {
                Application game = new Application();
                game.setVisible(true);
                mainFrame.setVisible(false);

            } else {
                System.exit(0);
            }


        }

    }

    public static void main(String[] args) {
         EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                Menu exemplo = new Menu();
            }
        });

    }
}

package game;

import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
// court dimensions

public abstract class Map extends Application {

    int MAPWIDTH = 500;
    int MAPHEIGHT = 500;

    int VELOCITY = 3;

    Player[] players;

    public Map(JLabel sco1, JLabel sco2, int p) {
        setBackground(Color.WHITE);
        if (p > 8) { p = 8; }
        this.players = new Player[p];

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);


        // player one controls
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!player.getAlive()) {
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setXVelocity(-VELOCITY);
                    player.setYVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setXVelocity(VELOCITY);
                    player.setYVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player.setYVelocity(-VELOCITY);
                    player.setXVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.setYVelocity(VELOCITY);
                    player.setXVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    player.jump();
                } else if (e.getKeyCode() == KeyEvent.VK_B) {
                    player.startBoost();
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void setBackground(Color background) {
        this.background = background;
    }
}

package server.main.room;

import server.main.Player;
import server.main.powerUp.PowerUp;
import server.main.powerUp.PowerUpKind;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Franek on 2018-04-22.
 */
public class PowerUpSpawner implements Runnable {
    private Timer timer;
    private Board board;
    private ArrayList<PowerUp> powerUps;

    public PowerUpSpawner(Board board) {
        this.timer = new Timer();
        this.board = board;
        this.powerUps = new ArrayList<>();
    }

    public String parsePowerUpList() {
        StringBuilder powerUpState = new StringBuilder();
        powerUpState.append("$");
        for(PowerUp powerUp : powerUps) {

            powerUpState.append(powerUp.getPosition().getX());
            powerUpState.append(".");
            powerUpState.append(powerUp.getPosition().getY());
            powerUpState.append(".");
            powerUpState.append(powerUp.getPowerUpName());
            powerUpState.append(";");

        }
        return powerUpState.toString();
    }

    @Override
    public void run() {
        timer.schedule(new spawnerTask(this), 0, 5000);
    }

    private class spawnerTask extends TimerTask {

        PowerUpSpawner spawner;

        spawnerTask(PowerUpSpawner spawner){
            this.spawner = spawner;
        }

        @Override
        public void run() {
            int x, y, p;
            PowerUpKind powerUpKind = PowerUpKind.IMMORTALITY;

            Random r = new Random();
            do{

                x = r.nextInt((board.getWidth() - 15) + 1) + 8;
                y = r.nextInt((board.getHeight() - 15) + 1) + 8;
                p = r.nextInt(3);
                switch(p) {
                    case 0:
                        powerUpKind = PowerUpKind.IMMORTALITY;
                        break;
                    case 1:
                        powerUpKind = PowerUpKind.SPEEDDOWN;
                        break;
                    case 2:
                        powerUpKind = PowerUpKind.SPEEDUP;
                        break;
                }

            } while(powerUps.contains(new PowerUp(x,y,powerUpKind)));

            PowerUp powerUp = new PowerUp(x,y,powerUpKind);
            powerUps.add(powerUp);
            board.addPowerUp(powerUp);
        }
    }

}

package server.main.powerUp;

import server.main.Player;

/**
 * Created by Franek on 2018-04-22.
 */
public class PowerUpEffect implements Runnable {

    private static int normalSpeed = 2;
    private static int maxSpeed = normalSpeed*2;
    private static int minSpeed = normalSpeed/2;

    private int time = 4;

    private Player player;

    PowerUpKind powerUpKind;

    public void setPlayer(Player player){
        this.player = player;
    }

    public PowerUpEffect(PowerUpKind powerUpKind) {
        this.powerUpKind = powerUpKind;
    }

    @Override
    public void run() {
        switch(powerUpKind) {
            case SPEEDUP:
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player.setSpeed(normalSpeed);
                break;
            case SPEEDDOWN:
                player.setSpeed(minSpeed);
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player.setSpeed(normalSpeed);
                break;
            case IMMORTALITY:
                player.setImmortal(true);
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player.setImmortal(false);
                break;
            case POINTS:
                player.addPowerUpPoints();
                break;
        }

    }
}

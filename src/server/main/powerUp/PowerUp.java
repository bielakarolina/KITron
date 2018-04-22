package server.main.powerUp;

import server.main.Player;
import server.main.room.Board;
import server.main.room.Point;

public class PowerUp implements Runnable{

    public static int normalSpeed = 2;
    public static int maxSpeed = normalSpeed*2;
    public static int minSpeed = normalSpeed/2;

    private int time = 4;
    private Point point;
    private PowerUpKind powerUpKind;
    private int size = 4;
    private Player player;

    public PowerUp(int x, int y, PowerUpKind powerUpKind){
        this.point = new Point(x, y, "powerUp");
        this.powerUpKind = powerUpKind;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void clean(Board board){
        board.cleanPowerUp(point, size);
    }

    public Point getPosition() {
        return this.point;
    }

    public String getPowerUpName() {
        switch(powerUpKind) {
            case SPEEDUP:
                return "speedup";
            case SPEEDDOWN:
                return "speeddown";
            case IMMORTALITY:
                return "immortality";
        }
        return null;
    }


    @Override
    public void run() {
        if(powerUpKind == PowerUpKind.SPEEDUP){
            player.setSpeed(maxSpeed);
            try {
                Thread.sleep(time*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.setSpeed(normalSpeed);
        }
        else if(powerUpKind == PowerUpKind.SPEEDDOWN){
            player.setSpeed(minSpeed);
            try {
                Thread.sleep(time*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.setSpeed(normalSpeed);
        }
        else if(powerUpKind == PowerUpKind.IMMORTALITY) {
            player.setImmortal(true);
            try {
                Thread.sleep(time*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.setImmortal(false);
        }

    }

    public PowerUpKind getPowerUpKind(){
        return this.powerUpKind;
    }

    public int getSize() {
        return size;
    }
}

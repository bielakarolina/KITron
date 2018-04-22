package server.main.powerUp;

import server.main.Player;
import server.main.room.Board;
import server.main.room.Point;

public class PowerUp {

    private Point point;
    private PowerUpKind powerUpKind;
    private int id;
    private int size = 4;

    public PowerUp(int x, int y, PowerUpKind powerUpKind){
        this.point = new Point(x, y, "powerUp");
        this.powerUpKind = powerUpKind;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void clean(Board board){
        board.clearPowerUp(this);
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
            case POINTS:
                return "points";
        }
        return null;
    }

    public PowerUpKind getPowerUpKind(){
        return this.powerUpKind;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }
}

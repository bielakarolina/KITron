package server.main;

import server.main.room.Path;
import server.main.room.Room;

public class Player {

    private int id;
    private String color;
    private String name;
    private Direction direction;
    private boolean initialized = false;
    private Path path;
    private PlayerState playerState;

    Player(int id){
        this.id = id;
        this.path = new Path();
        this.playerState = PlayerState.IDLE;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(String color, String name){
        if(!initialized){
            this.color = color;
            this.name = name;
            initialized = true;
            System.out.println("Initialized player: " + name);
        }

    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public String getParsedPath() {
        return path.toString();
    }
}

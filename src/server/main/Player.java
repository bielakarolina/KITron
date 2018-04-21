package server.main;

import server.main.room.Path;
import server.main.room.Point;
import server.main.room.Room;

public class Player {

    private int id;
    private String color;
    private String name;
    private Direction direction;
    private Direction newDirection;
    private boolean initialized = false;
    private Path path;
    private PlayerState playerState;
    private Point position;
    private int speed = 2;
    private int size = 3;
    private boolean alive = true;

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

    public void clearPath(){
        this.path = new Path();
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void addToPath(Point position){
        path.addPoint(position);
    }

    public String getParsedPath() {
        return path.toString()+";"+position.getX()+";"+position.getY();
    }

    public Point getPosition() {
        return position;
    }

    public Point findNewPosition() {

        int oldX = position.getX();
        int oldY = position.getY();

        Point point = null;

        switch(direction) {
            case DOWN:
                point = new Point(oldX,oldY+speed,"end");
                break;
            case UP:
                point = new Point(oldX,oldY-speed,"end");
                break;
            case LEFT:
                point = new Point(oldX-speed,oldY,"end");
                break;
            case RIGHT:
                point = new Point(oldX+speed,oldY,"end");
                break;
        }

        return point;
    }

    public Direction getNewDirection() {
        return newDirection;
    }

    public void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        System.out.println(this.alive);
    }
}

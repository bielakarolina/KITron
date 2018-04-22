package server.main;

import server.main.room.Path;
import server.main.room.Point;
import server.main.room.Room;

import java.net.Socket;

public class Player {

    private final Socket socket;
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
    private boolean immortal = false;

    Player(int id, Socket socket){
        this.id = id;
        this.path = new Path();
        this.playerState = PlayerState.IDLE;
        this.socket = socket;

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

    public void setColor(String color){ this.color = color; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(String name){
        if(!initialized){
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
        return path.toString()+","+position.getX()+ "." + position.getY() + ".end";
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

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        System.out.println(this.alive);
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public Socket getSocket() {
        return socket;
    }
}

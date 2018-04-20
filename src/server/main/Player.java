package server.main;

public class Player {

    private int id;
    private String color;
    private String name;
    private Direction direction;
    private boolean initialized = false;

    Player(int id){
        this.id = id;
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

    public void setColor(String color) {
        this.color = color;
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
}

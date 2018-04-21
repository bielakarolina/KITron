package server.main.room;

public class Point {

    private int x;
    private int y;
    private String state;

    public Point(int x, int y, String state){
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        return (x == ((Point) o).x && y == ((Point) o).y);
    }

    public String getState() {
        return state;
    }
}

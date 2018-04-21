package server.main.room;

public class Point {

    private int x;
    private int y;

    Point(int x, int y){
        this.x = x;
        this.y = y;
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
}

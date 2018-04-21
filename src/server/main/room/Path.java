package server.main.room;

import server.main.Player;

import java.util.LinkedList;
import java.util.List;

public class Path {

    private List<Point> points = new LinkedList<>();

    public void addPoint(Point point){
        points.add(point);
    }

    public String toString() {
        String s = "";
        for(Point p : points){
            s += ";" + p.getX() + ";" + p.getY();
        }
        return s;
    }
}

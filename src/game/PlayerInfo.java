package game;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PlayerInfo {
    private String id;
    private String player;
    private Color color;
    private ArrayList<PointPlayer> points;
    private ArrayList<Stage> stages;


    public PlayerInfo() {
        this.points = new ArrayList<>();
        this.stages = new ArrayList<>();
    }

    public PlayerInfo(String id, String player, Color color, ArrayList<PointPlayer> points, ArrayList<Stage> stages) {
        this.id = id;
        this.player = player;
        this.color = color;
        this.points = points;
        this.stages = stages;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPoints(ArrayList<PointPlayer> points) {
        this.points = points;
    }

    public void setStages(ArrayList<Stage> stages) {
        this.stages = stages;
    }

    public String getId() {
        return id;
    }

    public String getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<PointPlayer> getPoints() {
        return points;
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }
}

package game;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;

public class Bonus {
    private PointPlayer point; // = new ArrayList<>();

    private String bonus;


    public Bonus(){

    }

    public PointPlayer getPoint() {
        return point;
    }


    public String getBonus() {
        return bonus;
    }


    public void setPoint(PointPlayer points) {
        this.point = points;
    }


    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}

package game;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Spliter {



    public ArrayList<PlayerInfo> parse(String msg){

        String [] colonSplittedString = msg.split(":");

        //~~~~~~~~~~~~~~~~~~~~~~~~do : mamy colonSplittedString[0]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        String [] semicolonSplittedString = colonSplittedString[0].split(";");

        ArrayList<PlayerInfo> playerInfoArrayList = new ArrayList<>();
        for (int i = 0; i < semicolonSplittedString.length; i++){
            playerInfoArrayList.add(new PlayerInfo());
        }

        for (int i = 0; i < semicolonSplittedString.length; i++){
            String [] commaSplittedString = semicolonSplittedString[i].split(",");

            playerInfoArrayList.get(i).setId(commaSplittedString[0]);
            playerInfoArrayList.get(i).setPlayer(commaSplittedString[1]);
            String color = commaSplittedString[2];
            Color parsedColor = Color.web(color);
            playerInfoArrayList.get(i).setColor(parsedColor);

            for (int j = 3; j < commaSplittedString.length; j++) {
                String[] pointSplittedString = commaSplittedString[j].split("_");

                PointPlayer point = new PointPlayer(Integer.parseInt(pointSplittedString[0]), Integer.parseInt(pointSplittedString[1]));
                Stage stage = Stage.valueOf(pointSplittedString[2]);

                playerInfoArrayList.get(i).getPoints().add(point);
                playerInfoArrayList.get(i).getStages().add(stage);
            }


        }


//        for (int i = 0; i < playerInfoArrayList.size(); i++) {
//            System.out.println("ID: " + playerInfoArrayList.get(i).getId());
//            System.out.println("Player: " + playerInfoArrayList.get(i).getPlayer());
//            System.out.println("color: " + playerInfoArrayList.get(i).getColor());
//            for (int j = 0; j < playerInfoArrayList.get(i).getStages().size(); j++) {
//                System.out.println("stage: " + playerInfoArrayList.get(i).getStages().get(j));
//                System.out.println("point x: " + playerInfoArrayList.get(i).getPoints().get(j).getX());
//                System.out.println("point y: " + playerInfoArrayList.get(i).getPoints().get(j).getY());
//            }
//        }

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        return playerInfoArrayList;
    }

    public ArrayList<Bonus> parseBonus(String msg) {
        String [] colonSplittedString = msg.split(":");

        ArrayList<Bonus> bonusInfoArrayList = new ArrayList<>();
        String [] semicolonSplittedString = colonSplittedString[1].split(";");

        for (int i = 0; i < semicolonSplittedString.length; i++){
            bonusInfoArrayList.add(new Bonus());
        }

        for (int i = 0; i < semicolonSplittedString.length; i++) {
            String[] commaSplittedString = semicolonSplittedString[i].split("_");

            PointPlayer point = new PointPlayer(Integer.parseInt(commaSplittedString[0]), Integer.parseInt(commaSplittedString[1]));

            bonusInfoArrayList.get(i).setPoint(point);
            bonusInfoArrayList.get(i).setBonus(commaSplittedString[2]);


        }

//        for (int i = 0; i < bonusInfoArrayList.size(); i++){
//            System.out.println("X: " + bonusInfoArrayList.get(i).getPoint().getX());
//            System.out.println("Y: " + bonusInfoArrayList.get(i).getPoint().getY());
//            System.out.println("Bonus: " + bonusInfoArrayList.get(i).getBonus());
//        }


        return bonusInfoArrayList;
    }

    public static void main(String[] args) {
        //String tmp = "id,gracz,#9D00FF,1.2.stage1,3.4.stage2;id2,gracz2,#9D00F0,5.6.stage3,7.8.stage4;";
        //String tmp = "id,gracz,#9D00FF,1|2|stage1,3|4|stage2;id2,gracz2,#9D00F0,5|6|stage3,7|8|stage4;";
        String tmp = "id,gracz,#9D00FF,1_2_curve,3_4_curve;id2,gracz2,#9D00F0,5_6_curve,7_8_curve:9_9_bonus1;10_10_bonus2";

        Spliter spliter = new Spliter();
        spliter.parse(tmp);
        spliter.parseBonus(tmp);


    }

}

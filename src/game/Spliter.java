package game;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Spliter {



    public ArrayList<PlayerInfo> parse(String msg){
        String tmp = msg;
        int count = tmp.length() - tmp.replace(";", "").length();

        String [] semicolonSplittedString = msg.split(";");
//        for (int i = 0; i < semicolonSplittedString.length; i++){
//            System.out.println(semicolonSplittedString[i]);
//        }

        ArrayList<PlayerInfo> playerInfoArrayList = new ArrayList<>();
        for (int i = 0; i < semicolonSplittedString.length; i++){
            playerInfoArrayList.add(new PlayerInfo());
        }

        //System.out.println("Size: " + playerInfoArrayList.size());

        for (int i = 0; i < semicolonSplittedString.length; i++){
            String [] commaSplittedString = semicolonSplittedString[i].split(",");

//            for (int j = 0; j < commaSplittedString.length; j++)
//                System.out.println(commaSplittedString[j]);


            playerInfoArrayList.get(i).setId(commaSplittedString[0]);
            playerInfoArrayList.get(i).setPlayer(commaSplittedString[1]);
            String color = commaSplittedString[2];
            Color parsedColor = Color.web(color);
            playerInfoArrayList.get(i).setColor(parsedColor);

            for (int j = 3; j < commaSplittedString.length; j++) {
                String[] pointSplittedString = commaSplittedString[j].split("_");

//                for (int l = 0; l < pointSplittedString.length; l++)
////                     System.out.println(pointSplittedString[l]);

                PointPlayer point = new PointPlayer(Integer.parseInt(pointSplittedString[0]), Integer.parseInt(pointSplittedString[1]));
                Stage stage = Stage.valueOf(pointSplittedString[2]);
               // System.out.println(point.getX() + " " + point.getY() + " " + stage);

                playerInfoArrayList.get(i).getPoints().add(point);
                playerInfoArrayList.get(i).getStages().add(stage);
            }


        }


        for (int i = 0; i < playerInfoArrayList.size(); i++) {
            playerInfoArrayList.get(i).getId();
            playerInfoArrayList.get(i).getPlayer();
            playerInfoArrayList.get(i).getColor();
            for (int j = 0; j < playerInfoArrayList.get(i).getStages().size(); j++) {
                playerInfoArrayList.get(i).getStages().get(j);
                playerInfoArrayList.get(i).getPoints().get(j).getX();
                playerInfoArrayList.get(i).getPoints().get(j).getY();
            }
        }
        return playerInfoArrayList;
    }

    public static void main(String[] args) {
        //String tmp = "id,gracz,#9D00FF,1.2.stage1,3.4.stage2;id2,gracz2,#9D00F0,5.6.stage3,7.8.stage4;";
        //String tmp = "id,gracz,#9D00FF,1|2|stage1,3|4|stage2;id2,gracz2,#9D00F0,5|6|stage3,7|8|stage4;";
        String tmp = "id,gracz,#9D00FF,1_2_curve,3_4_curve;id2,gracz2,#9D00F0,5_6_curve,7_8_curve;";

        Spliter spliter = new Spliter();
        spliter.parse(tmp);
    }

}

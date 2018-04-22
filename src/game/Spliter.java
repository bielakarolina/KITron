import com.sun.xml.internal.ws.util.StringUtils;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Spliter {



    public void parse(String msg){
        String tmp = msg;
        int count = tmp.length() - tmp.replace(";", "").length();

        String [] semicolonSplittedString = msg.split(";");
        for (int i = 0; i < semicolonSplittedString.length; i++){
            System.out.println(semicolonSplittedString[i]);
        }

        ArrayList<PlayerInfo> playerInfoArrayList = new ArrayList<>();
        for (int i = 0; i < semicolonSplittedString.length; i++){
            playerInfoArrayList.add(new PlayerInfo());
        }

        System.out.println("Size: " + playerInfoArrayList.size());

        for (int i = 0; i < semicolonSplittedString.length; i++){
            String [] commaSplittedString = semicolonSplittedString[i].split(",");

            for (int j = 0; j < commaSplittedString.length; j++)
                System.out.println(commaSplittedString[j]);


            playerInfoArrayList.get(i).setId(commaSplittedString[0]);
            playerInfoArrayList.get(i).setPlayer(commaSplittedString[1]);
            String color = commaSplittedString[2];
            Color parsedColor = Color.web(color);
            playerInfoArrayList.get(i).setColor(parsedColor);

            for (int j = 3; j < commaSplittedString.length; j++) {
                String[] pointSplittedString = commaSplittedString[j].split(".");
                System.out.println("gowno" + commaSplittedString[j]);
//                for (int l = 0; l < pointSplittedString.length; l++)
//                     System.out.println(pointSplittedString[l]);

//                Point point = new Point(Integer.parseInt(pointSplittedString[0]), Integer.parseInt(pointSplittedString[1]));
//                Stage stage = Stage.valueOf(pointSplittedString[2]);
//                System.out.println(j + " " + point + " " + stage);
            }


        }

    }

    public static void main(String[] args) {
        String tmp = "id,gracz,#9D00FF,1.2.stage1,3.4.stage2;id2,gracz2,#9D00F0,5.6.stage3,7.8;";
        Spliter spliter = new Spliter();
        spliter.parse(tmp);
    }

}

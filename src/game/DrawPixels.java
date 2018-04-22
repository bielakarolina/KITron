package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrawPixels {



    final static double lineWith =3.0;


    public Canvas setRoad(String datagram,Canvas canvas){
        Map map = new Map();
        Spliter spliter = new Spliter();
        ArrayList <PlayerInfo> Data = spliter.parse(datagram);
        GraphicsContext gc1 = canvas.getGraphicsContext2D();
        gc1.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.drawD17(gc1);
        drawLines(gc1,Data);
      //  drawShapes(gc1);
        return  canvas;
    }

    private void drawLines(GraphicsContext gc,ArrayList<PlayerInfo> Data) {

        for(int i=0;i<Data.size();i++) {
            Color pColor=Data.get(i).getColor();
            gc.setStroke(pColor);

            for (int j = 0; j < Data.get(i).getStages().size()-1; j++) {

                game.Stage stage1= Data.get(i).getStages().get(j);
                double x1 =  Data.get(i).getPoints().get(j).getX();
                double y1= Data.get(i).getPoints().get(j).getY();

                double x2 =  Data.get(i).getPoints().get(j+1).getX();
                double y2 = Data.get(i).getPoints().get(j+1).getY();

                if (stage1== game.Stage.sizeup) gc.setLineWidth(lineWith+2.0);
                else if(stage1==game.Stage.sizedown) gc.setLineWidth(lineWith-2.0);
                else gc.setLineWidth(lineWith);

                gc.beginPath();
                gc.moveTo(x1,y1);
                gc.lineTo(x2,y2);
                gc.stroke();
            }




        }
    }

    //rysowanie shapów, przyda się przy bonusach
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }


}

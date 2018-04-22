package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class DrawPixels {

    Map map = new Map();
    final Canvas canvas = map.setCanvas();
    int n;
    double[][] points =new double [2][n];
    String [] stage = new String[n];
    double lineWith =3.0;

    public Canvas setRoad(){

        GraphicsContext gc1 = canvas.getGraphicsContext2D();
    //     drawLines(gc1,Color.YELLOW);
       // drawShapes(gc1);
        return  canvas;
    }

    private void drawLines(GraphicsContext gc,int id, String playerName, Color pColor,double points, Stage stage) {
        gc.setStroke(pColor);
        for(int i=0;i<n-1;i++) {
            if (stage==Stage.sizeup) gc.setLineWidth(lineWith+2.0);
            else if(stage==Stage.sizedown) gc.setLineWidth(lineWith-2.0);
            else gc.setLineWidth(lineWith);
                gc.beginPath();
           // gc.moveTo(points[0][i], points[1][i]);
           // gc.lineTo(points[0][i], points[1][i]);
            gc.stroke();
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

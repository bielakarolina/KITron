package game;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Map {

    public Map(){}

    public Canvas setCanvas(){
        Canvas canvas = new Canvas(600, 440);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawLines(gc);


        return canvas;
    }

    private void drawLines(GraphicsContext gc) {

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5.0);
        gc.beginPath();
        gc.lineTo(260, 310);
        gc.lineTo(60, 250);
        gc.lineTo(60,40);
        gc.lineTo(260,60);
        gc.lineTo(260,200);
        gc.moveTo(300, 210);
        gc.lineTo(300, 90);
        gc.lineTo(530, 110);
        gc.lineTo(550, 380);
        gc.lineTo(300, 315);
        //gc.lineTo();
        gc.stroke();
        gc.applyEffect(new DropShadow(20, 0, -20, Color.WHITE));
        //gc.setFill(Color.BLUE);

    }


}

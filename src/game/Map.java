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
        drawD17(gc);
        System.out.println("gowno");
        return canvas;
    }

    private void drawD17(GraphicsContext gc) {

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5.0);
        gc.beginPath();
        gc.lineTo(260, 310);
        gc.lineTo(50, 250);
        gc.lineTo(50,200);
        gc.lineTo(50,200);
        gc.lineTo(260,220);
        gc.lineTo(260,240);
        gc.moveTo(300, 240);
        gc.lineTo(300, 230);
        gc.lineTo(400, 230);
        gc.lineTo(450, 350);
        gc.lineTo(300, 315);
        //gc.lineTo();
        gc.stroke();
        gc.applyEffect(new DropShadow(20, 0, -20, Color.WHITE));
        //gc.setFill(Color.BLUE);
        System.out.println("gowno2");
        System.out.println("gowno1");
    }


}

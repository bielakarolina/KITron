package game;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Map {

    public Map(){}

    public Canvas setCanvas(){
        Canvas canvas = new Canvas(600, 440);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawD17(gc);
        return canvas;
    }

    public void drawD17(GraphicsContext gc) {

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5.0);
        gc.beginPath();
        gc.lineTo(260, 305);
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

    }
    public int[][] readingPixels(Canvas canvas, int[][] board) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.BLACK);

        PixelReader reader = canvas.snapshot(params, null).getPixelReader();

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        System.out.println(canvasWidth + " " + canvasHeight);
        for (int x = 0; x < canvasWidth; x++)
            for (int y = 0; y < canvasHeight; y++) {
                Color col=reader.getColor(x, y);
                Color D17=Color.WHITE;
                if ( D17.equals(col)) board[x][y] =88;
                else board[x][y]=0;
            }

        return board;
    }

}

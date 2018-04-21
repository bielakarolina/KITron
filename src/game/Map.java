package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Map extends Application {


    //    public static void main(String[] args) {
//        launch(args);
//    }
    public Map(){

    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//
//    }


    public Canvas setCanvas(){
        Canvas blurryCanvas = createCanvasGrid(600, 300);
        VBox vbox = new VBox(5, blurryCanvas);
           return vbox;
    }

    private Canvas createCanvasGrid(int width, int height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D() ;
        gc.setLineWidth(1.0);
        for (int x = 0; x < width; x+=10) {
            double x1 ;
            x1 = x + 0.5 ;
            gc.moveTo(x1, 0);
            gc.lineTo(x1, height);
            gc.stroke();
        }

        for (int y = 0; y < height; y+=10) {
            double y1 ;
            y1 = y + 0.5 ;
            gc.moveTo(0, y1);
            gc.lineTo(width, y1);
            gc.stroke();
        }
        return canvas ;
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas blurryCanvas = createCanvasGrid(600, 300);
        VBox root = new VBox(5, blurryCanvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

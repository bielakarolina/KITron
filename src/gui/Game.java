package gui;

import game.DrawPixels;
import game.Map;
import game.MapReceiver;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game {
    private Stage owner;
    private int widthScene=650;
    private int heightScene=850;
    private int widthStage=650;
    private int heightStage=850;
    private String title = "KI TRON";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

    Socket socket = null;
    public BufferedReader in;
    public PrintWriter out;

    public Canvas canvas;
  //  public String data;
    public Canvas canvas1;
   // public String data = "id,gracz,#9D0FFF,180_200_sizeup,30_200_curve,30_300_curve,150_300_curve;id2,gracz2,#9D00FF,100_260_sizedown,100_100_curve;";

    public Game(Socket socket) throws IOException {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/gameView.css").toExternalForm());
        setStageProperty();
        setHBoxProperty();

        // create socket
        this.socket = socket;

        // in & out streams
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setStageProperty(){
        owner.setScene(scene);
        owner.setTitle(title);
        owner.setWidth(widthStage);
        owner.setHeight(heightStage);
        owner.toBack();
        owner.show();
    }

    public void setHBoxProperty() {
        //root.setStyle(rootStyle);
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showActualGame(){
       canvas= initCanvas();
       Button endGame = new Button("End Game");
        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                GameOver gameOver = null;
                try {
                    gameOver = new GameOver(socket);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                gameOver.showGameOver(getOwner());
            }
        });
        root.getChildren().addAll(canvas, endGame);
        System.out.println("lalal");

        MapReceiver mapReceiver = new MapReceiver(this);
        Thread thread = new Thread(mapReceiver);
        thread.start();
       // actualGame();
    }

//    public void actualGame(String data){
//        MapReceiver mapReceiver = new MapReceiver(this);
//        Thread thread = new Thread(mapReceiver);
//        thread.start();
//
//        canvas = getCanvas(data);
//    }




//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//
//                   canvas = canvas1;
//            }
//        };


    public Canvas initCanvas(){
        Map map =new Map();
       final Canvas canvas = map.setCanvas();
        return  canvas;
    }

    public Canvas getCanvas(String data){

        DrawPixels drawPixels = new DrawPixels();
        canvas = drawPixels.setRoad(data,canvas);

        return  canvas;
    }
    public Stage getOwner() {
        return owner;
    }
}

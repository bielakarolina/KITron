package gui;


import game.DrawPixels;
import game.Map;
import game.MapReceiver;
import game.TCPHandler;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.*;
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
        owner.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
        TCPHandler tcpHandler = new TCPHandler(scene, socket);
        Thread tcpThread = new Thread(tcpHandler);
        tcpThread.start();

        final ImageView imv = new ImageView();
        final Image image2 = new Image(Main.class.getResourceAsStream("stylesheets/images/logo.png"));
        imv.setImage(image2);


       canvas= initCanvas();

        HBox hbox = setButtonHbox();
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().addAll(imv,canvas, hbox);

        MapReceiver mapReceiver = new MapReceiver(this);
        Thread thread = new Thread(mapReceiver);
        thread.start();
        // actualGame();
    }


    public HBox setButtonHbox(){

        HBox hbox = new HBox();

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

        Button returnButton = new Button("Return");
        returnButton.setId("back");
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                out.println("leaveRoom");
                try {
                    String response = in.readLine();
                    if(response.contains("success")){
                        Menu menu = new Menu();
                        menu.showMenu();
                        owner.close();
                    }
                    else{
                        AlertView alert = new AlertView(owner, "Upsss. Try Again");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(returnButton, endGame);
        return hbox;
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


    public Scene getScene(){
        return this.scene;
    }

}

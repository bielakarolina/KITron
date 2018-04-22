package gui;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;


public class GameOver {
    private Stage owner;
    private int widthScene=600;
    private int heightScene=250;
    private int widthStage=600;
    private int heightStage=250;

    private Scene scene;
    private VBox root;
    private int topMarg = 30;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 25;

    Socket socket = null;
    public BufferedReader in;
    public PrintWriter out;
    public String line= null;

    public GameOver(Socket socket) throws IOException {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add
                (GameOver.class.getResource("stylesheets/gameOver.css").toExternalForm());
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

        owner.setWidth(widthStage);
        owner.setHeight(heightStage);
        owner.initModality(Modality.WINDOW_MODAL);
        owner.toBack();
        owner.show();
    }

    public void setHBoxProperty() {
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
        root.setAlignment(Pos.CENTER);
    }

    public void showGameOver(Stage ownerFromGame){
        Label lost = new Label("GAME OVER");
        Label won = new Label("YOU WON!");

        HBox hbox = setHBox(ownerFromGame);

        root.getChildren().addAll(lost, hbox);
    }

    public HBox setHBox(Stage ownerFromGame){
        HBox hbox = new HBox();

        Button playAgain = new Button("Menu");
        playAgain.setId("playAgain");
        playAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Menu menu = new Menu();
                menu.showMenu();
                owner.close();
            }
        });

        Button backToRooms = new Button("Change room");
        backToRooms.setId("back");
        backToRooms.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                    String line = null;
                    line = setRooms();
                    RoomsView pokoje = null;
                    try {
                        pokoje = new RoomsView(line, socket);
                        pokoje.showRoomsView();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                ownerFromGame.close();
                owner.close();
            }
        });

        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().addAll(playAgain, backToRooms);
        return hbox;
    }

    public String setRooms(){
        out.println("roomList");
        String line = null;
        while(line == null) {
            try {
                line = in.readLine();
            } catch (IOException e1) {

            }
        }
        System.out.println(line);
        return line;
    }
}

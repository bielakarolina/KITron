package gui;

import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class NewRoom {
    private Stage owner;
    private int widthScene=400;
    private int heightScene=400;
    private int widthStage=400;
    private int heightStage=400;
    private String title = "New Room";
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
    public TextField nameField;
    public ChoiceBox cb;
    public String response;

    public NewRoom(Socket socket) throws IOException {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add
                (NewRoom.class.getResource("stylesheets/newRoom.css").toExternalForm());
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
        owner.initModality(Modality.WINDOW_MODAL);
        owner.show();
    }

    public void setHBoxProperty() {
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showNewRoom(Stage rooms){
        HBox nameHBox = setNameHBox();
        HBox gamerHBOX = setGamerHBox();
        HBox bttnHBox = setBttnHBox(rooms);

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(nameHBox, gamerHBOX, bttnHBox);
    }

    public HBox setNameHBox(){
        HBox nameHBox = new HBox();

        Label name = new Label("Room name  ");

        nameField = new TextField();
        nameField.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.getChildren().addAll(name, nameField);
        return nameHBox;
    }

    public HBox setGamerHBox(){
        HBox gamerHBox = new HBox();

        Label gamer = new Label("Players  ");

        ChoiceBox cb = setChoice();

        gamerHBox.setAlignment(Pos.CENTER);
        gamerHBox.getChildren().addAll(gamer, cb);

        return gamerHBox;
    }

    public ChoiceBox setChoice(){
        cb = new ChoiceBox();
        cb.getItems().addAll("2", "3", "4");
        cb.getSelectionModel().selectFirst();
        return cb;
    }

    public HBox setBttnHBox(Stage rooms){
        HBox bttnHBox = new HBox();

        Button create = new Button("Create room");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                out.println("hostRoom "+ nameField.getText() + " "+ cb.getSelectionModel().getSelectedItem());
                String msg = null;
                try {
                    msg = in.readLine();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if(msg.contains("success")) {
                    ProgressMaking(rooms);
                }
                else{
                    AlertView alert = new AlertView(owner, "Something Broken. Try Again.");
                }
                owner.close();
            }
        });

        bttnHBox.setAlignment(Pos.CENTER_RIGHT);
        bttnHBox.getChildren().addAll(create);
        return bttnHBox;
    }

    public void ProgressMaking(Stage rooms){
        Waiting pForm = new Waiting();
        pForm.Waiting();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws IOException {
                while(response.equals(null)) {
                    String response = in.readLine();
                    if (response.contains("startGame")) {
                        return null;
                    }
                }
                return null;
            /*   for(int i = 0; i < 10000; i++){
                    System.out.println(i);
                }
                */
            }
        };
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
            rooms.close();
            Game actualGame = null;
            try {
                actualGame = new Game(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            actualGame.showActualGame();
        });
        pForm.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }


}

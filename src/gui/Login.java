package gui;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;


public class Login {
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
    private String rootStyle ="-fx-background-color: #FFFFFF;";
    String hostName = "localhost";
    int portNumber = 12345;
    Socket socket = null;

    public Login(){
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/gameView.css").toExternalForm());
        setStageProperty();
        setHBoxProperty();
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
        root.setStyle(rootStyle);
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showActualGame() throws IOException {
        VBox login = setLogin();

        root.getChildren().addAll(login);

    }

    public VBox setLogin() throws IOException {
        // create socket
        socket = new Socket(hostName, portNumber);

        // in & out streams
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //VBox
        VBox vbox = new VBox(8);
        Label tytul = new Label("Enter your name:");
        tytul.setId("tytul");
        TextField text = new TextField();
        text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        Button submit = new Button("Submit");


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                RoomsView pokoje = new RoomsView();
                try {
                    String imie = text.getText();
                    out.println(imie);
                    pokoje.showRoomsView();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                owner.close();
            }
        });

        vbox.getChildren().addAll(tytul,text, submit);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}

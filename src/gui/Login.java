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
    private int widthScene=450;
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
    public PrintWriter out;
    public BufferedReader in;

    public Login() throws IOException {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/gameView.css").toExternalForm());
        setStageProperty();
        setHBoxProperty();

        // create socket
        socket = new Socket(hostName, portNumber);

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
        root.setStyle(rootStyle);
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showLogin() throws IOException {
        VBox login = setLogin();

        root.getChildren().addAll(login);

    }

    public VBox setLogin() throws IOException {
        //VBox
        VBox vbox = new VBox(8);
        Label tytul = new Label("Enter your name:");
        tytul.setId("tytul");
        TextField text = new TextField();
        text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        Button submit = new Button("Submit");


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String imie = text.getText();
                out.println(imie);
                String line = null;
                line = setRooms();
                RoomsView pokoje = null;
                try {
                    pokoje = new RoomsView(line, socket);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    pokoje.showRoomsView();
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

    public String setRooms(){
        out.println("Giv");
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

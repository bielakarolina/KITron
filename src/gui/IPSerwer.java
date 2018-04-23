package gui;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;


public class IPSerwer {
    private Stage owner;
    private int widthScene=450;
    private int heightScene=350;
    private int widthStage=650;
    private int heightStage=350;
    private String title = "SERVER'S IP";
    private Scene scene;
    private VBox root;
    private int topMarg = 50;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

/*    String hostName = "localhost";
    int portNumber = 12345;
//    Socket socket = null;
    public PrintWriter out;
    public BufferedReader in;
*/
    public IPSerwer() {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add
                (Login.class.getResource("stylesheets/login.css").toExternalForm());
        setStageProperty();
        setHBoxProperty();
/*
        // create socket
        socket = new Socket(hostName, portNumber);

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
*/
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
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showIPSerwer() throws IOException {
        VBox IP = setIPSerwer();

        root.getChildren().addAll(IP);
    }

    public VBox setIPSerwer() throws IOException {
        //VBox
        VBox vbox = new VBox(8);

        Label tytul = new Label("Enter Server's IP:");
        tytul.setId("tytul");

        TextField text = new TextField();
        text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        text.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    sendName(text);
                }
            }
        });

        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                sendName(text);
            }
        });

        vbox.getChildren().addAll(tytul,text, submit);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public void sendName(TextField text){
        String IP = text.getText();

        //
        if (IP.equals("")) {
            AlertView alert = new AlertView(owner, "Please enter Server's IP!");

        } else {
            Login login = null;
            try {
                login = new Login(IP);
                login.showLogin();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            owner.close();


        }

    }

}

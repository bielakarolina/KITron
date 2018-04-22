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

import java.io.*;
import java.net.Socket;


public class Login {
    private Stage owner;
    private int widthScene=450;
    private int heightScene=850;
    private int widthStage=650;
    private int heightStage=850;
    private String title = "LOGIN";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

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
                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add
                (Login.class.getResource("stylesheets/login.css").toExternalForm());
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
        try {
            String imie = text.getText();
            String msg = "initPlayer ".concat("BLUE".concat(" ".concat(imie)));



            //
                if (imie.equals("")) {
                    AlertView alert = new AlertView(owner, "Please enter your name!");
                 /*
                   Login login = new Login();
                    login.showLogin();
                    owner.close();
               */
                } else {
                    out.println(msg);

                    String response = null;
                    response = in.readLine();
                    System.out.println(response);

                    if(response.contains("init OK")) {
                        owner.close();
                        String rooms = null;
                        rooms = setRooms();
                        RoomsView pokoje = new RoomsView(rooms, socket);
                        pokoje.showRoomsView();
                    }
                }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
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

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
import java.net.UnknownHostException;


public class Login {
    private Stage owner;
    private int widthScene=450;
    private int heightScene=600;
    private int widthStage=650;
    private int heightStage=500;
    private String title = "LOGIN";
    private Scene scene;
    private VBox root;
    private int topMarg = 50;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

    private String hostName = null;
    private int portNumber = 12345;
    private Socket socket = null;
    public PrintWriter out;
    public BufferedReader in;
    private TextField text1;
    public TextField text;

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
       // this.hostName = hostName;


        owner.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    if(hostName != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setStageProperty(){
        owner.setScene(scene);
        owner.setTitle(title);
        owner.setWidth(widthStage);
        owner.setHeight(heightStage);
        owner.toBack();
        owner.show();
    }

    private void setHBoxProperty() {
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
    }

    public void showLogin() throws IOException {
        VBox login = setLogin();

        root.getChildren().addAll(login);

    }

    private VBox setLogin() throws IOException {
        //VBox
        VBox vbox = new VBox(8);

        Label tytul1 = new Label("Enter Server's IP:");
        tytul1.setId("tytul");

        text1 = new TextField();
        text1.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        text1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                sendName();
                }
            }
        });
 /*       text1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    String IP = text1.getText();

                    //
                    if (IP.equals("")) {
                        AlertView alert = new AlertView(owner, "Please enter Server's IP!");

                    } else {
                        hostName = IP;
                        // create socket
                        try {
                            socket = new Socket(hostName, portNumber);


                        // in & out streams
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Button submit1 = new Button("Submit");
        submit1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String IP = text1.getText();

                //
                if (IP.equals("")) {
                    AlertView alert = new AlertView(owner, "Please enter Server's IP!");

                } else {
                    hostName = IP;
                    // create socket
                    try {
                        socket = new Socket(hostName, portNumber);


                        // in & out streams
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
*/
        Label tytul = new Label("Enter your name:");
        tytul.setId("tytul");

        text = new TextField();
        text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        text.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    sendName();
                }
            }
        });

        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                sendName();
            }
        });

        vbox.getChildren().addAll(tytul1, text1, tytul,text, submit);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private void sendName() {
        String IP = text1.getText();

        if (IP.equals("")) {
            AlertView alert = new AlertView(owner, "Please enter Server's IP!");

        } else {
            hostName = IP;

            try {
                String imie = text.getText();
                String msg = "initPlayer ".concat(" ".concat(imie));

                if (imie.equals("")) {
                    AlertView alert = new AlertView(owner, "Please enter your name!");
                    //System.out.println(IP);

                } else {
                    socket = new Socket(hostName, portNumber);

                    // in & out streams
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    out.println(msg);
                    //System.out.println(msg);

                    String response = null;
                    response = in.readLine();
                    System.out.println(response);

                    if (response.contains("init OK")) {
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
/*
        if (hostName != null) {
            try {
                String imie = text.getText();
                String msg = "initPlayer ".concat(" ".concat(imie));

                //
                if (imie.equals("")) {
                    AlertView alert = new AlertView(owner, "Please enter your name!");

                } else {
                    out.println(msg);

                    String response = null;
                    response = in.readLine();
                    System.out.println(response);

                    if (response.contains("init OK")) {
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
        } else {
            AlertView alert = new AlertView(owner, "Please enter Server's IP!");

        }
*/
    }

    private String setRooms(){
        out.println("roomList");
        String line = null;
        while(line == null) {
            try {
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(line);
        return line;
    }

}

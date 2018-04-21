package gui;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
    private Alert.AlertType type = Alert.AlertType.INFORMATION;
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
        Button submit = new Button("Submit");


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {


                try {
                    String imie = "initPlayer " + text.getText();
                    out.println(imie);
                    
                  if (imie.equals("")) {

                        Alert alert = showAlert();
                        owner.close();
                        Login login = new Login();
                        login.showLogin();
                    } else {
                        owner.close();
                        String line = null;
                        line = setRooms();
                        RoomsView pokoje = new RoomsView(line, socket);
                        pokoje.showRoomsView();
                    }
               } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        vbox.getChildren().addAll(tytul,text, submit);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }


    public Alert showAlert(){
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.getDialogPane().setContentText("Please enter your name!");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("The alert was approved"));
        return alert;
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

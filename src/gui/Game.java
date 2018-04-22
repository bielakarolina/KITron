package gui;

import game.DrawPixels;
import game.Map;
import game.MapReceiver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

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
    String hostName = "localhost";
    int portNumber = 12345;
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
        Image logo = new Image("file:stylesheets/images/logo.png");
        ImageView logoView = new ImageView(logo);

        canvas= initCanvas();

        TableView<String[]> highscore = setTable();

        HBox hbox = setButtonHbox();
        hbox.setAlignment(Pos.BOTTOM_CENTER);

        root.getChildren().addAll(logoView, canvas, highscore,  hbox);

        MapReceiver mapReceiver = new MapReceiver(this);
        Thread thread = new Thread(mapReceiver);
        thread.start();
    }

    public TableView<String[]> setTable() throws IOException {
        TableView<String[]> table = new TableView<>();

        ObservableList<String[]> tItems = FXCollections.observableArrayList();

        tItems = createlist(tItems);

        TableColumn<String[],Integer> number = new TableColumn<>("No.");
        number.setPrefWidth(100);

        TableColumn<String[],String[]> name = new TableColumn<>("Username");
        name.setPrefWidth(350);

        TableColumn<String[],String[]> score = new TableColumn<>("Score");
        score.setPrefWidth(150);

        number.setCellValueFactory(new PropertyValueFactory<>("0"));
        name.setCellValueFactory(new PropertyValueFactory<>("1"));
        score.setCellValueFactory(new PropertyValueFactory<>("2"));

        table.getColumns().addAll(number, name, score);
        table.setItems(tItems);

        return table;
    }

    public ObservableList<String[]> createlist(ObservableList<String[]> list) throws IOException{
        String line = "Mikolaj, 100; Basia, 200";//in.readLine();
        String[] tmp = line.split("; ");
        int m = 0;
        String[][] persons = new String[tmp.length][3];
        for(String x: tmp ){
            String tmp1[] = x.split(", ");
            persons[m][0] = String.valueOf(m+1);
            persons[m][1] = tmp1[0];
            persons[m][2] = tmp1[1];
            m++;
        }
        for(String[] y: persons) {
            list.add(y);
        }
        return list;
    }


    public HBox setButtonHbox(){

        HBox hbox = new HBox();

        Button endGame = new Button("End Game");
        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                out.println("leaveRoom");
                try {
                    String response = in.readLine();
                    if(response.contains("success")){
                        GameOver gameOver = null;
                        try {
                            gameOver = new GameOver(socket);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        gameOver.showGameOver(getOwner());
                    }
                    else{
                        AlertView alert = new AlertView(owner, "Upsss. Try Again");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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

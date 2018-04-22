package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;

public class RoomsView {
    private Stage owner;
    private int widthScene=600;
    private int heightScene=700;
    private int widthStage=600;
    private int heightStage=700;
    private String title = "Rooms";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

    public Socket socket = null;
    public PrintWriter out;
    public BufferedReader in;

    public Label nazwa;
    public Label ilosc;
    public String line;
    public ObservableList<String[]> items;
    public ListView<String[]> list;
    public String response = null;

    public RoomsView(String line, Socket socket) throws IOException {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add
                (RoomsView.class.getResource("stylesheets/roomsView.css").toExternalForm());
        setStageProperty();
        setVBoxProperty();

        // create socket
        this.socket = socket;

        // in & out streams
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //out.println("Give me rooms");
        this.line = line;
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

    public void setVBoxProperty() {
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
        root.setAlignment(Pos.CENTER);
    }

    public void showRoomsView() throws IOException {

        Label napis = new Label("Choose room: ");
        napis.setId("tytul");

        HBox hbox = setHbox();
        HBox hboxret = setButtonHbox();

        list = setList();

        hbox.setAlignment(Pos.CENTER);
        hboxret.setAlignment(Pos.BOTTOM_LEFT);
        root.getChildren().addAll(napis, hbox, list, hboxret);
    }

    public HBox setHbox() throws FileNotFoundException {
        HBox hbox = new HBox();

        Button refresh = new Button("refresh");
        refresh.setAlignment(Pos.CENTER_LEFT);


        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                line = setRooms();
                System.out.println(line);
                try {
                    items = FXCollections.observableArrayList();
                    items = createList(items);
                    //setList();
                    list.setItems(items);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        Button newRoomBttn = setNewButton();

        VBox grupa = setGroup();


        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(refresh, newRoomBttn,grupa);
        return hbox;
    }

    public Button setNewButton(){

        Button newRoomBttn = new Button("+");
        newRoomBttn.setId("newRoom");
        newRoomBttn.setAlignment(Pos.CENTER_LEFT);

        newRoomBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                NewRoom newRoom = null;
                try {
                    newRoom = new NewRoom(socket);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                newRoom.showNewRoom(getOwner());

            }
        });
        return newRoomBttn;
    }

    public HBox setButtonHbox(){

        HBox hbox = new HBox();

        Button returnButton = new Button("Return");
        returnButton.setId("back");
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                Menu menu = new Menu();
                menu.showMenu();
                owner.close();
            }
        });

        Button acceptBttn= new Button("Choose");
        acceptBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String[] room = null;
                room = list.getSelectionModel().getSelectedItem();
                if(room != null) {
                    out.println("joinRoom " + room[0]);
                    String msg = null;
                    try {
                        msg = in.readLine();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if (msg.contains("success")) {
                        ProgressMaking();
                    } else {
                        AlertView alert = new AlertView(owner, "Sorry. Room full.");
                    }
                }
                else{
                    AlertView alert = new AlertView(owner, "Choose Room");
                }
            }
        });

        acceptBttn.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(returnButton, acceptBttn);
        return hbox;
    }

    public VBox setGroup(){
        VBox grupa = new VBox();
        grupa.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));

        nazwa = new Label("Name: ");
        ilosc = new Label("Players: ");

        nazwa.setTextFill(Color.WHITE);
        nazwa.setStyle("-fx-font-size: 24;");

        ilosc.setTextFill(Color.WHITE);
        ilosc.setStyle("-fx-font-size: 24;");

        grupa.getChildren().addAll(nazwa, ilosc);
        return grupa;
    }

    public ListView<String[]> setList() throws IOException {
        ListView<String[]> list = new ListView<>();

        items = FXCollections.observableArrayList();
        items = createList(items);
        list.setItems(items);

        list.setCellFactory(param -> new ListCell<String[]>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item[0] == null) {
                    setText(null);

                } else {
                    setText(item[0]);

                }
            }
        });

        list.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String[]>() {
                    @Override
                    public void changed(ObservableValue<? extends String[]> observable,
                                        String[] oldValue, String[] newValue) {
                        printRow(newValue);
                    }
                });

        return list;
    }

    public void printRow(String[] row){
        nazwa.setText("Name: "+ row[0]);
        nazwa.setTextFill(Color.WHITE);
        nazwa.setStyle("-fx-font-size: 24;");
        ilosc.setText("Players: " + row[1] + "/" + row[2] );
        ilosc.setTextFill(Color.WHITE);
        ilosc.setStyle("-fx-font-size: 24;");


    }

    public ObservableList<String[]> createList(ObservableList<String[]> items) throws IOException {

        String[] rooms = line.split(";");
        int i = rooms.length, m = 0;
        String[][] roomsInfo = new String[i][3];
        for(String x: rooms){
            String[] tmp = x.split(",");
            roomsInfo[m] = tmp;
            m++;
        }

        for(String[] room: roomsInfo){
            items.add(room);
        }

        return items;
    }

    public void ProgressMaking(){
        Waiting pForm = new Waiting();
        pForm.Waiting(socket);
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws IOException {
                //while(response.equals(null)) {
                    String response = in.readLine();
                    if (response.contains("startGame")) {
                        return null;
                    }
                //}
            /*   for(int i = 0; i < 10000; i++){
                    System.out.println(i);
                }
                */
                return null ;
            }
        };
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
            owner.close();
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

    public Stage getOwner() {
        return owner;
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
        //System.out.println(line);
        return line;
    }



}

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.*;
import java.net.Socket;

public class RoomsView {
    private Stage owner;
    private int widthScene=500;
    private int heightScene=700;
    private int widthStage=500;
    private int heightStage=700;
    private String title = "Rooms";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

    public String hostName = "localhost";
    public int portNumber = 12345;
    public Socket socket = null;
    public PrintWriter out;
    public BufferedReader in;

    public Text nazwa;
    public Text ilosc;
    public String line;

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

        ListView<String[]> list = setList();

        Button acceptBttn= new Button("Choose");
        acceptBttn.setId("accept");
        acceptBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String[] room = list.getSelectionModel().getSelectedItem();
                out.println(room[0] + "  "+ room[1]+"   "+ room[2]+"   "+room[3]);
                ProgressMaking();
            }
        });

        root.getChildren().addAll(napis, hbox, list, acceptBttn);
    }

    public HBox setHbox() throws FileNotFoundException {
        HBox hbox = new HBox();

        Button newRoomBttn = setNewButton();
        VBox grupa = setGroup();

        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(newRoomBttn, grupa);
        return hbox;
    }

    public Button setNewButton() throws FileNotFoundException {

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

    public VBox setGroup(){
        VBox grupa = new VBox();
        grupa.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));

        nazwa = new Text("Nazwa: ");
        ilosc = new Text("Ilośc gości w pokoju: ");


        grupa.getChildren().addAll(nazwa, ilosc);
        return grupa;
    }

    public ListView<String[]> setList() throws IOException {
        ListView<String[]> list = new ListView<String[]>();

        ObservableList<String[]> items = FXCollections.observableArrayList ();
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
        nazwa.setText("Nazwa: "+ row[0]);
        ilosc.setText("Ilośc gości w pokoju: " + row[2] + "/" + row[3] );


    }

    public ObservableList<String[]> createList(ObservableList<String[]> items) throws IOException {
        //out.println("Give me rooms");
        //String line = in.readLine();
        //String line = "lala, 123, 3, 4; mama, 134, 1, 2";
        String[] rooms = line.split("; ");
        int i = rooms.length, m = 0;
        String[][] roomsInfo = new String[i][4];
        for(String x: rooms){
            String[] tmp = x.split(", ");
            roomsInfo[m] = tmp;
            m++;
        }


        for(String[] room: roomsInfo){
            System.out.println(room[0] + "   " + room[1]+ "    "+ room[2]+ "    "+ room[3]+"\n");
            items.add(room);
        }
        return items;
    }

    public void ProgressMaking(){
        Waiting pForm = new Waiting();
        pForm.Waiting();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws IOException {
 /*               while(response.equals(null)) {
                    String response = in.readLine();
                    if (response.equals("Game started")) {
                        return null;
                    }
                }
   */             for(int i = 0; i < 100000; i++){
                    System.out.println(i);
                }
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


}

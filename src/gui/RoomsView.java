package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RoomsView {
    private Stage owner;
    private int widthScene=500;
    private int heightScene=700;
    private int widthStage=500;
    private int heightStage=700;
    private String title = "Wybierz pokój";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;
    private String rootStyle ="-fx-background-color: #FFFFFF;";
    private String address = "plus.png";

    public RoomsView(){
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        setStageProperty();
        setVBoxProperty();
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
        root.setStyle(rootStyle);
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
        root.setAlignment(Pos.CENTER);
    }

    public void showRoomsView() throws FileNotFoundException {
        Label napis = new Label("Wybierz pokój: ");
        napis.setFont(new Font("Arial", 30));

        HBox hbox = setHbox();

        ListView<String> list = setList();

        Button acceptBttn= new Button("Wybierz ten pokój");
        acceptBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String room = list.getSelectionModel().getSelectedItem();
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
        Image imageDecline = new Image(getClass().getResourceAsStream(address));
        Button newRoomBttn = new Button();
        newRoomBttn.setGraphic(new ImageView(imageDecline));
        newRoomBttn.setAlignment(Pos.CENTER_LEFT);

        newRoomBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                NewRoom newRoom = new NewRoom();
                newRoom.showNewRoom();
                owner.close();
                ProgressMaking();

            }
        });
        return newRoomBttn;
    }

    public VBox setGroup(){
        VBox grupa = new VBox();
        grupa.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));

        Text nazwa = new Text("Nazwa: ");
        Text ilosc = new Text("Ilośc gości w pokoju: ");
        Text maxIlosc = new Text("Oczekiwana ilość gości w pokoju: ");
        maxIlosc.setWrappingWidth(300);

        grupa.getChildren().addAll(nazwa, ilosc, maxIlosc);
        return grupa;
    }

    public ListView<String> setList(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        list.setItems(items);
        return list;
    }

    public void ProgressMaking(){
        Waiting pForm = new Waiting();
        pForm.Waiting();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException, IOException {
                for(int i = 0; i < 1000000; i++){
                    System.out.println(i);
                }
                return null ;
            }
        };
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
            owner.close();
            Game actualGame = new Game();
            actualGame.showActualGame();
        });
        pForm.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }


}

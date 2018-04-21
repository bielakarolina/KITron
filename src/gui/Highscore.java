package gui;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import game.Map;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
=======
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;



public class Highscore {
    private Stage owner;
    private int widthScene = 650;
    private int heightScene = 850;
    private int widthStage = 650;
    private int heightStage = 850;


    private String title = "HIGHSCORE";

    private String title = "";

    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;
   public BufferedReader in;
    public PrintWriter out;


    public Highscore() {
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add

                (Game.class.getResource("stylesheets/default.css").toExternalForm());
        scene.getStylesheets().add

                (Highscore.class.getResource("stylesheets/highscore.css").toExternalForm());
        setStageProperty();
        setHBoxProperty();
    }

    public void setStageProperty() {
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


    public void showHighscore()throws IOException {

        Label tytul = new Label("HIGHSCORE ");
        tytul.setId("tytul");

        HBox hbox = setHbox();
        HBox hbuttnbox = setButtonHbox();

        TableView<String[]> table = new TableView<>();
        ObservableList<String[]> tItems = FXCollections.observableArrayList();

        tItems = createlist(tItems);

        TableColumn<String[],Integer> number = new TableColumn<>("No.");
        number.setPrefWidth(100);

        TableColumn<String[],String[]> name = new TableColumn<>("Username");
        name.setPrefWidth(350);

        TableColumn<String[],String[]> score = new TableColumn<>("Score");
        score.setPrefWidth(150);

        /*number.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<String[], Integer> integerCellDataFeatures) {
                return Integer(integerCellDataFeatures.getValue();
            }
        });*/
        number.setCellValueFactory(new PropertyValueFactory<>("0"));
        name.setCellValueFactory(new PropertyValueFactory<>("1"));
        score.setCellValueFactory(new PropertyValueFactory<>("2"));

        table.getColumns().addAll(number, name, score);
        table.setItems(tItems);
        root.getChildren().addAll(tytul,hbox,table,hbuttnbox);
    }


    public HBox setHbox()  {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        return hbox;
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

        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(returnButton);
        return hbox;
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
}

    public void showHighscore() {

        root.getChildren().addAll();

    }
}





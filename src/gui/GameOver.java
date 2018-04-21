package gui;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;


public class GameOver {
    private Stage owner;
    private int widthScene=200;
    private int heightScene=200;
    private int widthStage=400;
    private int heightStage=200;
    private String title = "Game Over";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;
    private String rootStyle ="-fx-background-color: #FFFFFF;";

    public GameOver(){
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        setStageProperty();
        setHBoxProperty();
    }

    public void setStageProperty(){
        owner.setScene(scene);
        owner.setTitle(title);
        owner.setWidth(widthStage);
        owner.setHeight(heightStage);
        owner.initModality(Modality.WINDOW_MODAL);
        owner.toBack();
        owner.show();
    }

    public void setHBoxProperty() {
        root.setStyle(rootStyle);
        root.setPadding(new Insets(topMarg, rightMarg, bottomMarg, leftMarg));
        root.setSpacing(rootSpacing);
        root.setAlignment(Pos.CENTER);
    }

    public void showGameOver(){
        Label tytul = new Label("Game Over");
        tytul.setFont(new Font("Arial", 30));

        HBox hbox = setHBox();

        root.getChildren().addAll(tytul, hbox);
    }

    public HBox setHBox(){
        HBox hbox = new HBox();

        Button playAgain = new Button("Play Again");
        playAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                owner.close();
            }
        });

        Button backToRooms = new Button("Change room");
        backToRooms.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                RoomsView pokoje = new RoomsView();
                try {
                    pokoje.showRoomsView();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                owner.close();
            }
        });

        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().addAll(playAgain, backToRooms);
        return hbox;
    }


}

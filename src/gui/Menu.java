package gui;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Menu {
    private Stage owner;
    private int widthScene=650;
    private int heightScene=550;
    private int widthStage=650;
    private int heightStage=550;
    private String title = "KI TRON";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;

    public Menu(){
        new JFXPanel();
        owner = new Stage(StageStyle.DECORATED);
        root = new VBox();
        scene = new Scene(root, widthScene, heightScene);
        scene.getStylesheets().add
                (Menu.class.getResource("stylesheets/menu.css").toExternalForm());
        setStageProperty();
        setHBoxProperty();
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

    public void showMenu(){
        Button startBttn = new Button("PLAY");
        startBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Login login = new Login();
                try {
                    login.showLogin();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                owner.close();
            }
        });

        Button instrBttn = new Button("Instruction");
        instrBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Instruction instr = new Instruction();
                try {
                    instr.showInstr();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                owner.close();
            }
        });

        Button highBttn = new Button("Highscore");
        highBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Highscore highscore = new Highscore();
                highscore.showHighscore();
                owner.close();
            }
        });

        Button endGame = new Button("Quit");
        endGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                owner.close();
            }
        });
        root.getChildren().addAll(startBttn, instrBttn, highBttn, endGame);
        root.setAlignment(Pos.CENTER);
    }


}
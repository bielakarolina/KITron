package gui;

import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RoomsView {
    private Stage owner;
    private int widthScene=200;
    private int heightScene=200;
    private int widthStage=400;
    private int heightStage=200;
    private String title = "Wybierz pokój";
    private Scene scene;
    private VBox root;
    private int topMarg = 15;
    private int rightMarg = 12;
    private int bottomMarg = 15;
    private int leftMarg = 12;
    private int rootSpacing = 10;
    private String rootStyle ="-fx-background-color: #336699;";

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

    public void showRoomsView(){
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ProgressMaking();
                owner.close();
            }
        });
        root.getChildren().add(submit);
    }

    public void ProgressMaking(){
        Waiting pForm = new Waiting();
        pForm.Waiting();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException, IOException {

                return null ;
            }
        };
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
            Game actualGame = new Game();
            actualGame.showActualGame();
        });
        pForm.getDialogStage().show();

        Thread thread = new Thread(task);
        thread.start();
    }
}

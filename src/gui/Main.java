package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox vbox = confVBox(primaryStage);
        primaryStage.setTitle("KI TRON");
        Scene scene = new Scene(vbox, 300, 275);
        primaryStage.setScene(scene);
        scene.getStylesheets().add
                (Main.class.getResource("stylesheets/main.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public VBox confVBox(Stage primaryStage) throws IOException {
        VBox vbox = new VBox(8);
        Label tytul = new Label("Podaj swoje imię:");
        tytul.setId("tytul");
        TextField text = new TextField("name");
        text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                RoomsView pokoje = new RoomsView();
                try {
                    String imie = text.getText();
                    pokoje.showRoomsView();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                primaryStage.close();
            }
        });

        vbox.getChildren().addAll(tytul,text, submit);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }


}

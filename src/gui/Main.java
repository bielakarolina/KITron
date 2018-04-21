package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Main extends Application {

    String hostName = "localhost";
    int portNumber = 12345;
    Socket socket = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Menu menu = new Menu();
        menu.showMenu();
    }

    public static void main(String[] args){
        launch(args);
    }

}

package gui;

import javafx.application.Application;
import javafx.stage.Stage;

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

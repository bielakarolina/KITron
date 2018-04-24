package gui;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    String fontName = "stylesheets/ARCADECLASSIC.TTF";
    
    @Override
    public void start(Stage primaryStage) throws Exception{


        InputStream fontStream = Main.class.getResourceAsStream(fontName);
        if (fontStream != null) {
            Font gameFont = Font.loadFont(fontStream, 36);
            fontStream.close();

            Menu menu = new Menu();
            menu.showMenu();
        }

    }

    public static void main(String[] args){
        launch(args);
    }


}

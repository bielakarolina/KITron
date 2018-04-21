package game;



import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static game.Direction.*;

public class ControlsThread extends Thread{

    private String hostName = "localhost";      //tu trza bedzie zmienic
    private int portNumber = 12345;                 //tu trza bedzie zmienic
    private Socket socket = null;


    public void run(Scene scene){
        System.out.println("Tutaj bedzie player sobie wiadomosci TCP wysylal jak zmieni kierunek");


        try {
            socket = new Socket(hostName, portNumber);


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DOWN) {
                sendMessage(DOWN);
            } else if (e.getCode() == KeyCode.UP) {
                sendMessage(UP);
            } else if (e.getCode() == KeyCode.RIGHT) {
                sendMessage(RIGHT);
            } else if (e.getCode() == KeyCode.LEFT) {
                sendMessage(LEFT);
            }
        });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(Direction moveDirection) {    //down/up, mozliwe ze pozycja
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String message = null; //dopisac wiadomosc w formacie jakims tam np,. up/pozycja obecna
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}

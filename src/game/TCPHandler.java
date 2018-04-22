package game;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPHandler extends Thread{
    private Scene scene = null;
    private Socket socket;
    PrintWriter out;

    public TCPHandler(Scene scene, Socket socket){
        this.scene = scene;
        this.socket = socket;
    }

    @Override
    public void run() {



        scene.setOnKeyPressed(e -> {
            String message = "";
            if (e.getCode() == KeyCode.DOWN) {
                message = "down";
                System.out.println("down arrow key was pressed");
            } else if (e.getCode() == KeyCode.UP) {
                message = "up";
                System.out.println("up arrow key was pressed");
            } else if (e.getCode() == KeyCode.RIGHT) {
                message = "right";
                System.out.println("right arrow key was pressed");
            } else if (e.getCode() == KeyCode.LEFT) {
                message = "left";
                System.out.println("left arrow key was pressed");
            }
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}

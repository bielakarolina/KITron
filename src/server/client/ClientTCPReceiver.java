package server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTCPReceiver implements Runnable {

    private Socket socket;

    public ClientTCPReceiver(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = in.readLine();
                System.out.println("received response: " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

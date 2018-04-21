package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer {

    public static void main(String[] args) throws IOException {

        int portNumber = 12345;
        ServerSocket serverSocket = null;

        try {
            // create socket
            serverSocket = new ServerSocket(portNumber);
            int i =0;
            while(true){

                // accept client
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");

                // in & out streams
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String msg = in.readLine();

                if(msg.equals("lalala")) {

                    System.out.println("Imie: " + msg);
                }
                if(msg.equals("Giv")){
                    out.println("lala, 123, 3, 4; mama, 134, 1, 2");
                }
                if(msg.contains("lala")) {
                    String room = in.readLine();
                    System.out.println("Pokój: " + room);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (serverSocket != null){
                serverSocket.close();
            }
        }
    }

}

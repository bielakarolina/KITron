package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MockServer {

    public static void main(String[] args) throws IOException {

        int portNumber = 12345;
        ServerSocket serverSocket = null;
        String rooms = "lala,3,4;mama,1,2";
        String rooms1 = ";bumbum,3,4;nora,1,2;dupa,2,3";

        try {
            // create socket
            serverSocket = new ServerSocket(portNumber);
            int i =0;
            System.out.println(i);
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected");
            i++;
            while(true){
                // accept client

                System.out.println(i);
                // in & out streams
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                String msg = in.readLine();

                if(msg.contains("initPlayer")) {
                    String[] tmp = msg.split(" ");

                    System.out.println("Imie: " + tmp[1]);
                    msg = in.readLine();

                    if (msg.contains("roomRequest")) {
                        System.out.println(msg);
                        System.out.println(rooms);
                        out.println(rooms);
                    }
                }


                if (msg.contains("joinRoom")) {
                    System.out.println(msg);
                    out.println("success");
                }

                if (msg.contains("roomList")) {
                    System.out.println(msg);
                    System.out.println(rooms);
                    out.println(rooms);
                }

                if (msg.contains("refresh")) {
                    System.out.println(msg);
                    rooms = rooms.concat(rooms1);
                    System.out.println(rooms);
                    out.println(rooms);
                }

                if(msg.contains("Zosta")){
                    System.out.println("Odebrano: "+ msg);
                }

                if(msg.contains("hostRoom")){
                    Random rand = new Random();
                    String[] tmp = msg.split(" ");
                    rooms = rooms.concat(";"+ tmp[1]+","+" 0," + tmp[2]);
                    System.out.println(rooms);
                }

                if(msg.contains("leaveRoom")){
                    System.out.println("Odebrano: "+ msg);
                }
                i++;

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

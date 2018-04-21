
package game;

import server.client.DummyClient;
import server.client.DummyClientMulticastReceiver;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class MapReceiver extends Thread {
        public static int multicastPort = 12345;
        public String host = "localhost";

        private static PrintStream tcpOutput = null;
        private static DataInputStream tcpInput = null;
        private static BufferedReader inputReader = null;
        private static int port = 12345;


        //private static DataInputStream tcpInput = null;

        @Override
        public void run() {
                Socket clientSock = null;
                try {
                        clientSock = new Socket(host, port);


                        tcpOutput = new PrintStream(clientSock.getOutputStream());
                        tcpInput = new DataInputStream(clientSock.getInputStream());
                        inputReader = new BufferedReader(new InputStreamReader(System.in));

                        System.out.println("gownooooooooo");
                        String receivedMessage;


                        while ((receivedMessage = tcpInput.readLine()) != null) {
                                if (receivedMessage.equals("Exit request."))
                                        break;

                                //wypisujemy odebrana wiadomosc (w wiadomosci wysylamy nazwe klienta
                                System.out.println(receivedMessage);

                                System.out.println("gownooooooooo odebrane");
                                //to tutaj jak odbierze gowno to powinno sie wywolac cos co narysuje plansze 

                        }


                } catch (IOException e) {
                        System.out.println("IOException.");
                }




        }
}
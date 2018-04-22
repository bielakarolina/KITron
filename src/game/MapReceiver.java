package game;

import gui.Game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MapReceiver implements Runnable {
        private int port = 4446;

        private Game game;

        public MapReceiver(Game game){
                this.game = game;
        }

        @Override
        public void run() {

                InetAddress group = null;
                MulticastSocket multicastSocket = null;
                byte[] buf = new byte[256];

                try {
                        multicastSocket = new MulticastSocket(port);
                        group = InetAddress.getByName("224.0.113.0");
                        multicastSocket.joinGroup(group);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                if(multicastSocket != null){
                        while(true){
                                try {
                                        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                                        multicastSocket.receive(datagramPacket);
                                        String response = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                                        game.getCanvas(response);
                                        System.out.println(response);

                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
}











//
//package game;
//
//import server.client.DummyClient;
//import server.client.DummyClientMulticastReceiver;
//
//import java.io.*;
//import java.net.*;
//import java.util.Arrays;
//import java.util.Scanner;
//
//public class MapReceiver extends Thread {
//        public static int multicastPort = 12345;
//        public String host = "localhost";
//
//        private static PrintStream tcpOutput = null;
//        private static DataInputStream tcpInput = null;
//        private static BufferedReader inputReader = null;
//        private static int port = 12345;
//
//
//        //private static DataInputStream tcpInput = null;
//
//        @Override
//        public void run() {
//                Socket clientSock = null;
//                try {
//                        clientSock = new Socket(host, port);
//
//
//                        tcpOutput = new PrintStream(clientSock.getOutputStream());
//                        tcpInput = new DataInputStream(clientSock.getInputStream());
//                        inputReader = new BufferedReader(new InputStreamReader(System.in));
//
//                        System.out.println("gownooooooooo");
//                        String receivedMessage;
//
//
//                        while ((receivedMessage = tcpInput.readLine()) != null) {
//                                if (receivedMessage.equals("Exit request."))
//                                        break;
//
//                                //wypisujemy odebrana wiadomosc (w wiadomosci wysylamy nazwe klienta
//                                System.out.println(receivedMessage);
//
//                                System.out.println("gownooooooooo odebrane");
//                                //to tutaj jak odbierze gowno to powinno sie wywolac cos co narysuje plansze
//
//                        }
//
//
//                } catch (IOException e) {
//                        System.out.println("IOException.");
//                }
//
//
//
//
//        }
//}
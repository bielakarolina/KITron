package server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Scanner;

public class DummyClient {

    public static int multicastPort = 4446;

    public static void main(String[] args) throws IOException {

        System.out.println("JAVA TCP UDP CLIENT");
        String hostName = "192.168.137.176";
        int portNumber = 12345;
        Socket socket = null;


        DatagramSocket datagramSocket = null;
        byte[] sendBuffer = new byte[1024];
        MulticastSocket multicastSocket = new MulticastSocket();
        InetAddress group = InetAddress.getByName("239.1.1.1");


        try {
            // create socket
            socket = new Socket(hostName, portNumber);

            new Thread(new DummyClientMulticastReceiver()).start();
            new Thread(new ClientTCPReceiver(socket)).start();

            while(true){
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();

                // send msg
                out.println(message);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                socket.close();
            }
        }
    }
}

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
        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;


        DatagramSocket datagramSocket = null;
        byte[] sendBuffer = new byte[1024];
        MulticastSocket multicastSocket = new MulticastSocket();
        InetAddress group = InetAddress.getByName("239.1.1.1");

        new Thread(new DummyClientMulticastReceiver()).start();

        try {
            // create socket
            socket = new Socket(hostName, portNumber);

            while(true){
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();

                // send msg, read response
                out.println(message);
                String response = in.readLine();
                System.out.println("received response: " + response);
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

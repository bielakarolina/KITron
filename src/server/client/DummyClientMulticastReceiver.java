package server.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DummyClientMulticastReceiver implements Runnable {


    @Override
    public void run() {

        InetAddress group = null;
        MulticastSocket multicastSocket = null;
        byte[] buf = new byte[256];

        try {
            multicastSocket = new MulticastSocket(DummyClient.multicastPort);
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
                    System.out.println(response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

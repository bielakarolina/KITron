package game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class MapReceiver extends Thread{
//        @Override
//        public void run() {
//            System.out.println("JAVA UDP CLIENT");
//            DatagramSocket socket = null;
//            int portNumber = 9008;
//
//            try {
//                socket = new DatagramSocket();
//                InetAddress address = InetAddress.getByName("localhost");
//                byte[] sendBuffer = "Ping Java Udp".getBytes();
//
//                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
//                socket.send(sendPacket);
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//            finally {
//                if (socket != null) {
//                    socket.close();
//                }
//            }



//            System.out.println("MapReceiver works");
//            DatagramSocket socket = null;
//            int portNumber = 9008;                  //do zmiany
//
//            try{
//                socket = new DatagramSocket(portNumber);
//                byte[] receiveBuffer = new byte[1024];
//
//                while(true) {
//                    Arrays.fill(receiveBuffer, (byte)0);
//                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
//                    socket.receive(receivePacket);
//                    String msg = new String(receivePacket.getData());
//                    System.out.println("received msg: " + msg);
//
//                    //dostaje wiadomosc i wywoluje mape przekazujac do niej liste
//                }
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//            finally {
//                if (socket != null) {
//                    socket.close();
//                }
//            }

        }


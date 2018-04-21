package server.main;

import server.main.room.Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {


    private static final int STANDARD_BOARD_WIDTH = 20;
    private static final int STANDARD_BOARD_HEIGHT = 20;
    private int portNumber = 12345;
    private ServerSocket serverSocket;

    private PrintWriter out;
    private Socket clientSocket;


    private AtomicInteger playerIdGiver = new AtomicInteger();

    List<Room> rooms = new ArrayList<>();
    List<Player> players = new ArrayList<>();


    Server(){

    }

    public void process(int poolSize) throws IOException {

        System.out.println("SERVER");
        System.out.println("Waiting for players...");
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        try {
            serverSocket = new ServerSocket(portNumber);

            while(true){

                clientSocket = serverSocket.accept();
                System.out.println("Client " + playerIdGiver.incrementAndGet() + " connected");
                Player player = new Player(playerIdGiver.get());

                players.add(player);

                Runnable clientListener = new ClientHandler(this, player, clientSocket);
                executorService.execute(clientListener);

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


    public synchronized int addRoom(String name, int maxPeople, Player player) {

        if(getRoomId(name) == -1){
            Room room = new Room(STANDARD_BOARD_WIDTH,STANDARD_BOARD_HEIGHT,maxPeople,name);
            rooms.add(room);
            new Thread(room).start();
            room.join(player);
            System.out.println("Player " + player.getName() + " added new Room " + name + " " + maxPeople);
            return 0;
        }
        return 1;
    }

    private Room getRoomByName(String name) {
        for(Room room : rooms) {
            if(room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    public int getRoomId(String name) {
        for(int i = 0; i < rooms.size(); i++){
            if(rooms.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }


    public synchronized int joinRoom(Player player, String roomName){

        Room room = getRoomByName(roomName);

        if(room != null && !room.isRoomActive())
        {
            room.join(player);
            System.out.println("Player " + player.getName() + " joined Room " + roomName);
            return 0;
        }
        return 1;
    }

    public synchronized int leaveRoom(Player player){
        for(Room room : rooms) {
            if(room.containsPlayer(player)){
                room.leave(player);
                System.out.println("Player " + player.getName() + " left the room");
                return 0;
            }
        }
        return 1;
    }
}

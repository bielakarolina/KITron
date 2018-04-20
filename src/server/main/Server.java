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


    public synchronized void addRoom(String name, int maxPeople, Player player) {
        //tutaj dodajemy pokoj i playera do pokoju wydaje mi sie ze pokoje powinny byc osobnymi watkami
    }

    public synchronized void joinRoom(Player player, int roomID){
        //tutaj dodajemy playera do pokoju trzeba napisac wyszukiwanie pokoju po ID
    }

    public synchronized void leaveRoom(Player player){
        //albo przeszukamy wszystkie pokoje i znajdziemy playera albo playerom bedziemy przypisywac pokoje
    }
}

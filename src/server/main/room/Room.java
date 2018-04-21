package server.main.room;

import server.main.Player;
import java.util.ArrayList;
import java.util.List;

public class Room implements Runnable{

    List<Player> players = new ArrayList<>();
    private Board board;
    private int maxPlayers;
    private boolean roomActive = false;
    private String name;

    Room(int height, int width, int maxPlayers, String name){
        this.board = new Board(height, width);
        this.maxPlayers = maxPlayers;
        this.name = name;

    }

    public synchronized void joinRoom(Player player){
        players.add(player);
    }

    public boolean isRoomActive() {
        return roomActive;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {

        while(players.size() != maxPlayers);

        //wyslanie wiadomosci do klienta ze gra sie zaczyna


    }
}

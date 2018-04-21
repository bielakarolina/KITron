package server.main.room;

import server.main.Player;
import server.main.PlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Room implements Runnable{

    List<Player> players = new ArrayList<>();
    private Board board;
    private int maxPlayers;
    private boolean roomActive = false;
    private String name;
    private Timer timer;

    public Room(int width, int height, int maxPlayers, String name){
        this.board = new Board(height, width);
        this.maxPlayers = maxPlayers;
        this.name = name;

    }

    public synchronized void join(Player player){
        players.add(player);
        player.setPlayerState(PlayerState.WAITING);
    }

    public synchronized void leave(Player player){
        players.remove(player);
        player.setPlayerState(PlayerState.IDLE);
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

        startGame();
        roomActive = true;

        //wyslanie wiadomosci do klienta ze gra sie zaczyna

        timer.schedule(new processTask(), 0, 33);


    }


    private void startGame() {
        for(Player player : players){
            player.setPlayerState(PlayerState.PLAYING);
        }
    }

    private class processTask extends TimerTask{


        @Override
        public void run() {
            //TODO
            //process one step //BOARD
            //check collision //BOARD
            //send update //dostaje
        }
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }
}

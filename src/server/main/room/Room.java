package server.main.room;

import server.main.Player;
import server.main.PlayerState;

import java.util.*;

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
        timer = new Timer();

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

        System.out.println("Room init");

        while(players.size() != maxPlayers){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Game started in: " + this.name);

        startGame();
        roomActive = true;

        //wyslanie wiadomosci do klienta ze gra sie zaczyna

        timer.schedule(new processTask(), 0, 1000);


    }


    private void startGame() {
        for(Player player : players){
            player.setPlayerState(PlayerState.PLAYING);
        }
        putPlayersOnBoard();
    }

    private void putPlayersOnBoard() {
        for(Player player: players){
            player.clearPath();

            List<Point> startPoints = new ArrayList<>();

            int x;
            int y;

            Random r = new Random();

            do{

                x = r.nextInt((550 - 50) + 1) + 50;
                y = r.nextInt((400 - 50) + 1) + 50;

            } while(startPoints.contains(new Point(x, y)));


            Point point = new Point(x, y);

            player.setPosition(point);
            player.addToPath(point);
        }
    }

    private class processTask extends TimerTask{


        @Override
        public void run() {
            System.out.println("Sending package");
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

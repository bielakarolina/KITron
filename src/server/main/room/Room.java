package server.main.room;

import server.main.Player;
import server.main.PlayerState;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
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
    private DatagramChannel channel;
    private NetworkInterface multicastInterface = null;
    private DatagramChannel multicastChannel = null;
    private InetSocketAddress serverAddress = new InetSocketAddress("239.1.1.1", 5000);

    public Room(int width, int height, int maxPlayers, String name){
        this.board = new Board(height, width);
        this.maxPlayers = maxPlayers;
        this.name = name;

    try {
        multicastInterface = NetworkInterface.getNetworkInterfaces().nextElement();
        multicastChannel = DatagramChannel.open(StandardProtocolFamily.INET)
                .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                .bind(new InetSocketAddress(5000))
                .setOption(StandardSocketOptions.IP_MULTICAST_IF, multicastInterface);
        multicastChannel.configureBlocking(false);
        MembershipKey groupKey = multicastChannel.join(Inet4Address.getByName("239.1.1.1"), multicastInterface);
    } catch (Exception e) {
        e.printStackTrace();
    }
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
            board.update();
            ByteBuffer buffer = ByteBuffer.wrap(parsePlayerList().getBytes());
            try {
                multicastChannel.send(buffer, serverAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //process one step //BOARD
            //check collision //BOARD
            //send update //dostaje
        }
    }

    public String parsePlayerList() {
        String s = "";
        for(Player p : players) {
            s = p.getId() + ";" + p.getName()+";" + p.getColor();
            s += ";" + p.getParsedPath();
        }
        return s;
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }
}

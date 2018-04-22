package server.main.room;

import server.main.Direction;
import server.main.Player;
import server.main.PlayerState;

import java.io.PrintWriter;
import java.util.*;
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

    //room properties
    private Board board;
    private int maxPlayers;
    private boolean roomActive = false;
    private String name;
    private int alive;
    private Timer timer;
    private List<Player> players = new ArrayList<>();
    private List<String> colors = Arrays.asList("#9D00FF", "#FF00FF","#00FFFF","#00FF00","#FF0000","#FFFF00","#ff0099","#6e0dd0");

    //sockets
    private static int multicastPort = 4446;
    private DatagramChannel channel;
    private NetworkInterface multicastInterface = null;
    private DatagramChannel multicastChannel = null;
    private InetSocketAddress serverAddress = new InetSocketAddress("239.1.1.1", 5000);
    MulticastSocket multicastSocket;
    InetAddress group;


    public Room(int width, int height, int maxPlayers, String name){
        this.board = new Board(width, height);
        this.maxPlayers = maxPlayers;
        this.name = name;

        try {
            multicastSocket = new MulticastSocket();
            group = InetAddress.getByName("224.0.113.0");
        } catch (IOException e) {
            e.printStackTrace();
        }


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

        player.setColor(colors.get(players.size()));
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

        try {
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress group = InetAddress.getByName("224.0.113.0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Room init");
        this.timer = new Timer();
        board.refreshBoard();

        while(players.size() != maxPlayers){
            try {
                System.out.println(players.size());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Game started in: " + this.name);

        startGame();
        roomActive = true;

        sendToAllStartMessage();

        //wyslanie wiadomosci do klientow ze gra sie zaczyna

        timer.schedule(new processTask(this), 0, 2000);

        System.out.println("koniec room");
    }

    private void sendToAllStartMessage() {

        PrintWriter out;
        Socket clientSocket;

        for(Player player: players){
            clientSocket = player.getSocket();
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("startGame");
                System.out.println(player.getName() + " was sent startGame message");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void startGame() {
        for(Player player : players){
            player.setPlayerState(PlayerState.PLAYING);
            player.setAlive(true);
        }
        alive = players.size();
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

                x = r.nextInt((board.getWidth() - 15) + 1) + 8;
                y = r.nextInt((board.getHeight() - 15) + 1) + 8;

            } while(startPoints.contains(new Point(x, y, "start")));




           Point point = new Point(x, y, "start");
           board.drawPlayer(point, player);
            //Point point = new Point(5, 5, "start");

            player.setPosition(point);
            player.addToPath(point);
            Random rand = new Random();
            int direction = rand.nextInt(4);



            switch (direction){
                case 0:
                    player.setDirection(Direction.UP);
                    break;
                case 1:
                    player.setDirection(Direction.DOWN);
                    break;
                case 2:
                    player.setDirection(Direction.LEFT);
                    break;
                case 3:
                    player.setDirection(Direction.RIGHT);
                    break;

            }

            //player.setDirection(Direction.LEFT);

            System.out.println(player.getName() + " x " + player.getPosition().getX() + " y " + player.getPosition().getY());
            System.out.println(player.getPosition());
        }
    }

    public void printPlayersPaths(){
        for(Player player: players){
            System.out.println(player.getName());
            System.out.println(player.getParsedPath());
        }
        System.out.println();
    }

    private class processTask extends TimerTask{

        Room room;

        processTask(Room room){
            this.room = room;
        }

        @Override
        public void run() {

            update();

            board.printBoard();
            System.out.println("Allive: " + room.alive);
            System.out.println();

            sendUpdate();

            if(alive <= 1){
                timer.cancel();
                timer.purge();
                if(findWinner() != null){
                    System.out.println("Player winner: " + findWinner().getName());
                }

                new Thread(room).start();
            }

        }
    }

    private void sendUpdate() {

        System.out.println("Sending Package UDP");
        DatagramPacket sendPacket;

        //buffer = ByteBuffer.wrap(parsePlayerList().getBytes();

        //ByteBuffer buffer = ByteBuffer.wrap(parsePlayerList().getBytes());
        byte[] buffer = parsePlayerList().getBytes();
        try {

            sendPacket = new DatagramPacket(buffer, buffer.length, group, Room.multicastPort);
            multicastSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String parsePlayerList() {

        StringBuilder gameState = new StringBuilder();

        for(Player player : players) {

            gameState.append(player.getId());
            gameState.append(",");
            gameState.append(player.getName());
            gameState.append(",");
            gameState.append(player.getColor());
            gameState.append(player.getParsedPath());
            gameState.append(";");

        }
        return gameState.toString();
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    private void update() {
        for(Player player : players) {

            if(player.isAlive()){

                Point newPosition;
                newPosition = player.findNewPosition();

                if(board.checkCollision(player.getPosition(), newPosition, player)){
                    player.setPosition(newPosition);
                }
                else{
                    player.setAlive(false);
                    alive--;
                }
            }
        }
    }

    public Player findWinner(){
        for(Player player: players){
            if(player.isAlive())
                return player;
        }
        return null;
    }

    public int getUserNumber(){
        return players.size();
    }

    public int getMaxPlayers(){
        return maxPlayers;
    }


}

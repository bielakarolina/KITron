package server.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;
    private Player player;

    ClientHandler(Server server, Player player, Socket clientSocket) throws IOException {
        this.server = server;
        this.player = player;
        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {

        String message;
        while(true){
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                message = in.readLine();

                //here we have message via tcp and we have to somehow handle it

                String[] messageList = message.split(" ");

                System.out.println(message);

                if(messageList.length == 1 && player.getPlayerState() == PlayerState.PLAYING) {
                    switch (messageList[0]) {
                        case "left":
                            player.setDirection(Direction.LEFT);
                            System.out.println("left");
                            break;
                        case "right":
                            player.setDirection(Direction.RIGHT);
                            System.out.println("right");
                            break;
                        case "down":
                            player.setDirection(Direction.DOWN);
                            System.out.println("down");
                            break;
                        case "up":
                            player.setDirection(Direction.UP);
                            System.out.println("up");
                            break;
                    }
                }
                else{
                    //wiadomosc bedize miec postac albo
                    // hostRoom nazwaPokoju maxIloscGraczy
                    // joinRoom idPokoju
                    // leaveRoom
                    // initPlayer kolorGracza imieGracza

                        switch (messageList[0]){
                            case "hostRoom":
                                server.addRoom(messageList[1], Integer.parseInt(messageList[2]), player);
                                break;
                            case "joinRoom":
                                server.joinRoom(player, Integer.parseInt(messageList[1]));
                                break;
                            case "leaveRoom":
                                server.leaveRoom(player);
                                break;
                            case "initPlayer":
                                player.init(messageList[1], messageList[2]);
                                break;
                    }
                }




                //and response for the player
                //need to think when and what we should response
                //TODO
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Response");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

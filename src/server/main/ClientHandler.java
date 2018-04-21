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
        String response;
        while(true){
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                message = in.readLine();

                //here we have message via tcp and we have to somehow handle it

                String[] messageList = message.split(" ");

                //System.out.println(message);

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

                    int i;
                    response = "wrong command";
                    //wiadomosc bedize miec postac albo
                    // hostRoom nazwaPokoju maxIloscGraczy
                    // joinRoom nazwa pokoju
                    // leaveRoom
                    // initPlayer kolorGracza imieGracza

                        switch (messageList[0]){
                            case "hostRoom":
                                i = server.addRoom(messageList[1], Integer.parseInt(messageList[2]), player);
                                if(i == 0) response = "hostRoom success";
                                else response = "hostRoom fail";
                                break;
                            case "joinRoom":
                                i = server.joinRoom(player, messageList[1]);
                                if(i == 0) response = "joinRoom success";
                                else response = "joinRoom fail";
                                break;
                            case "leaveRoom":
                                i = server.leaveRoom(player);
                                if(i == 0) response = "leaveRoom success";
                                else response = "leaveRoom fail";
                                break;
                            case "initPlayer":
                                //check if there is player with this name
                                if(messageList.length == 3){
                                    player.init(messageList[1], messageList[2]);
                                    response = "init OK";
                                }
                                break;
                    }

                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(response);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

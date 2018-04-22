package server.main;

import server.main.room.Room;

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

                System.out.println("Player playing: - " + (player.getPlayerState() == PlayerState.PLAYING));

                if(player.getPlayerState() == PlayerState.PLAYING) {
                    switch (messageList[0]) {
                        case "left":
                            if(player.getDirection() != Direction.LEFT && player.getDirection() != Direction.RIGHT){
                                player.setDirection(Direction.LEFT);
                                player.addToPath(player.getPosition());
                                System.out.println("left");
                            }
                            break;
                        case "right":
                            if(player.getDirection() != Direction.LEFT && player.getDirection() != Direction.RIGHT){
                                player.setDirection(Direction.RIGHT);
                                System.out.println("right");
                                player.addToPath(player.getPosition());
                            }
                            break;
                        case "down":
                            if(player.getDirection() != Direction.DOWN && player.getDirection() != Direction.UP){
                                player.setDirection(Direction.DOWN);
                                System.out.println("down");
                                player.addToPath(player.getPosition());
                            }
                            break;
                        case "up":
                            if(player.getDirection() != Direction.DOWN && player.getDirection() != Direction.UP){
                                player.setDirection(Direction.UP);
                                System.out.println("up");
                                player.addToPath(player.getPosition());
                            }
                            break;
                    }
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("moved");
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
                                    player.init(messageList[1]);
                                    response = "init OK";
                                }
                                break;
                            case "roomList":
                                response = createRoomList();
                    }

                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(response);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String createRoomList() {
        StringBuilder roomList = new StringBuilder();

        for(Room room: server.rooms){
            roomList.append(room.getName());
            roomList.append(",");
            roomList.append(room.getUserNumber());
            roomList.append(",");
            roomList.append(room.getMaxPlayers());
            roomList.append(';');
        }
        return roomList.toString();
    }
}

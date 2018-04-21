package server.main.room;

public class Board {

    private int[][] board;

    Board(int width, int height){
        this.board = new int[width][height];
    }


    public void setWall(Point position, int id) {
        this.board[position.getX()][position.getY()] = id;
    }
}

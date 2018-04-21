package server.main.room;

import server.main.Direction;
import server.main.Player;

public class Board {

    private int[][] board;
    private int width;
    private int height;

    Board(int width, int height){
        this.board = new int[width][height];
        this.width = width;
        this.height = height;

        refreshBoard();
    }

    public boolean checkCollision(Point oldPosition, Point newPosition, Player player){

        Direction direction = player.getDirection();

        Point leftTop = null;
        Point rightBottom = null;

        switch(direction){

            case UP:
                leftTop = new Point(newPosition.getX(), newPosition.getY(), "collision");
                rightBottom = new Point(oldPosition.getX()+player.getSize()-1, oldPosition.getY()-1, "collision");
                break;

            case DOWN:
                leftTop = new Point(oldPosition.getX()+player.getSize(), oldPosition.getY(), "collision");
                rightBottom = new Point(newPosition.getX()+player.getSize()-1, newPosition.getY()+player.getSize()-1, "collision");
                break;

            case LEFT:
                leftTop = new Point(newPosition.getX(), newPosition.getY(), "collision");
                rightBottom = new Point(oldPosition.getX() - 1, oldPosition.getY()+player.getSize()-1, "collision");
                break;

            case RIGHT:
                //dobrze i hope so
                leftTop = new Point(oldPosition.getX()+player.getSize(), oldPosition.getY(), "collision");
                rightBottom = new Point(newPosition.getX()+player.getSize()-1, oldPosition.getY()+player.getSize()-1 , "collision");
                break;

        }

        if (checkArrayBoundaries(leftTop, rightBottom) && checkArrayMarkedSections(leftTop, rightBottom)){
            markSection(leftTop, rightBottom, player);
            return true;
        }
        else return false;



    }

    private boolean checkArrayMarkedSections(Point leftTop, Point rightBottom) {

        for(int i=leftTop.getX(); i< rightBottom.getX(); i++){
            for(int j=leftTop.getY(); j< rightBottom.getY(); j++){
                if(board[i][j] != 0)
                    return false;
            }
        }
        return true;
    }

    private boolean checkArrayBoundaries(Point leftTop, Point rightBottom) {

        return leftTop.getX() < width && leftTop.getX() > 0 &&
                rightBottom.getX() < width && rightBottom.getX() > 0 &&
                leftTop.getY() < height && leftTop.getY() > 0 &&
                leftTop.getY() < height && leftTop.getY() > 0;

    }

    public void markSection(Point leftTop, Point rightBottom, Player player){
        for(int i=leftTop.getX(); i< rightBottom.getX(); i++){
            for(int j=leftTop.getY(); j< rightBottom.getY(); j++){
                board[i][j] = player.getId();
            }
        }
    }

    public void refreshBoard(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j< this.height; j++){
                board[i][j] = 0;
            }
        }
    }
}

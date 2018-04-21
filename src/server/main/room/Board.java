package server.main.room;

import server.main.Direction;
import server.main.Player;

public class Board {

    private int[][] board;
    private int width;
    private int height;
    private Point collisionPoint;

    Board(int width, int height){
        this.board = new int[width][height];
        this.width = width;
        this.height = height;
    }


    public int getWidth(){
        return  width;
    }

    public int getHeight(){
        return height;
    }

    public boolean checkCollision(Point oldPosition, Point newPosition, Player player){

        Direction direction = player.getDirection();

        Point leftTop = null;
        Point rightBottom = null;

        System.out.println("oldPosition: x " + oldPosition.getX() + " y: " + oldPosition.getY());
        System.out.println("newPosition: x " + newPosition.getX() + " y: " + newPosition.getY());

        switch(direction){

            case UP:
                leftTop = new Point(newPosition.getX(), newPosition.getY(), "collision");
                rightBottom = new Point(oldPosition.getX()+player.getSize()-1, oldPosition.getY()-1, "collision");
                if(leftTop.getY() < 0){
                    leftTop = new Point(newPosition.getX(), 0, "collision");
                    player.setAlive(false);
                    System.out.println("up");
                }
                break;

            case DOWN:
                leftTop = new Point(oldPosition.getX(), oldPosition.getY()+player.getSize(), "collision");
                rightBottom = new Point(newPosition.getX()+player.getSize()-1, newPosition.getY()+player.getSize()-1, "collision");
                if(rightBottom.getY() >= height){
                    rightBottom = new Point(newPosition.getX()+player.getSize()-1, height-1, "collision");
                    player.setAlive(false);
                    System.out.println("down");
                }
                break;

            case LEFT:
                leftTop = new Point(newPosition.getX(), newPosition.getY(), "collision");
                rightBottom = new Point(oldPosition.getX() - 1, oldPosition.getY()+player.getSize()-1, "collision");
                if(leftTop.getX() < 0){
                    leftTop = new Point(0, newPosition.getY(), "collision");
                    player.setAlive(false);
                    System.out.println("left");
                }
                break;

            case RIGHT:
                leftTop = new Point(oldPosition.getX()+player.getSize(), oldPosition.getY(), "collision");
                rightBottom = new Point(newPosition.getX()+player.getSize()-1, oldPosition.getY()+player.getSize()-1 , "collision");
                if(rightBottom.getX() >= width){
                    rightBottom = new Point(width-1, oldPosition.getY()+player.getSize()-1 , "collision");
                    player.setAlive(false);
                    System.out.println("right");
                }
                break;

        }




        if (player.isAlive() && checkArrayMarkedSections(leftTop, rightBottom)){
            markSection(leftTop, rightBottom, player);
            return true;
        }
        else {
            markSection(leftTop, rightBottom, player);
            return false;
        }



    }

    private boolean checkArrayMarkedSections(Point leftTop, Point rightBottom) {

        for(int i=leftTop.getX(); i< rightBottom.getX(); i++){
            for(int j=leftTop.getY(); j< rightBottom.getY(); j++){
                if(board[i][j] != 0){
                    collisionPoint = new Point(i, j, "collision");
                    System.out.println("collision");
                    return false;
                }

            }
        }
        return true;
    }

    private boolean checkArrayBoundaries(Point leftTop, Point rightBottom) {

        int x = 0;
        int y = 0;

        if(leftTop.getX() >= width){
            collisionPoint = new Point(width-1,leftTop.getY(),"collision");
            return false;
        }


        if(leftTop.getX() < width && leftTop.getX() > 0 &&
                rightBottom.getX() < width && rightBottom.getX() > 0 &&
                leftTop.getY() < height && leftTop.getY() > 0 &&
                rightBottom.getY() < height && rightBottom.getY() > 0){
            return true;
        }

        return false;

    }

    public void markSection(Point leftTop, Point rightBottom, Player player){
        for(int i=leftTop.getX(); i<= rightBottom.getX(); i++){
            for(int j=leftTop.getY(); j <= rightBottom.getY(); j++){
                board[i][j] = player.getId();
            }
        }
        System.out.println("LeftTop: x " + leftTop.getX() + " y: " + leftTop.getY());
        System.out.println("RightBottom: x " + rightBottom.getX() + " y: " + rightBottom.getY());
        System.out.println(player.getName());

    }

    public void refreshBoard(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j< this.height; j++){
                board[i][j] = 0;
            }
        }
    }

    public void printBoard() {

        for(int i = 0 ;i<width;i++){
            for(int j = 0; j<height; j++){
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }
    }
}

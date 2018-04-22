package server.main.room;

import server.main.Direction;
import server.main.Player;
import server.main.powerUp.PowerUp;
import server.main.powerUp.PowerUpEffect;
import server.main.powerUp.PowerUpSpawner;

public class Board {

    private int[][] board;
    private int width;
    private int height;
    private Point collisionPoint;
    private PowerUpSpawner powerUpSpawner;

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

        boolean collision;

        switch(direction){



            case UP:
                leftTop = new Point(newPosition.getX(), newPosition.getY(), "collision");
                rightBottom = new Point(oldPosition.getX()+player.getSize()-1, oldPosition.getY()-1, "collision");

                if(leftTop.getY() < 0){
                    leftTop = new Point(newPosition.getX(), 0, "collision");
                    player.setAlive(false);
                    System.out.println("up");
                }
                else {
                    collision = checkArrayMarkedSectionsUp(leftTop, rightBottom, player);
                    if(!collision){
                        int i = 1;

                        while(board[collisionPoint.getX()][collisionPoint.getY() + i] !=0){
                            i++;
                        }

                        int x = leftTop.getX();
                        int y = collisionPoint.getY() + i;

                        leftTop = new Point(x, y, "collision");

                        if(!player.isImmortal())
                            player.setAlive(false);

                    }
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
                else {

                    collision = checkArrayMarkedSectionsDown(leftTop, rightBottom, player);
                    if(!collision){
                        int i = 1;

                        while(board[collisionPoint.getX()][collisionPoint.getY() - i] !=0){
                            i++;
                        }

                        int x = rightBottom.getX();
                        int y = collisionPoint.getY() - i;

                        leftTop = new Point(x, y, "collision");

                        if(!player.isImmortal())
                            player.setAlive(false);
                    }
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
                else {
                    collision = checkArrayMarkedSectionsLeft(leftTop, rightBottom, player);
                    if(!collision){
                        int i = 1;

                        while(board[collisionPoint.getX()+ i ][collisionPoint.getY()] !=0){
                            i++;
                        }

                        int x = collisionPoint.getX()+ i;
                        int y = leftTop.getY();

                        leftTop = new Point(x, y, "collision");

                        if(!player.isImmortal())
                            player.setAlive(false);

                    }
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
                else{
                    collision = checkArrayMarkedSectionsRight(leftTop, rightBottom, player);
                    if(!collision){
                        int i = 1;

                        while(board[collisionPoint.getX()- i ][collisionPoint.getY()] !=0){
                            i++;
                        }

                        int x = collisionPoint.getX() - i;
                        int y = rightBottom.getY();

                        leftTop = new Point(x, y, "collision");
                        if(!player.isImmortal())
                            player.setAlive(false);
                    }
                }
                break;


        }

        markSection(leftTop, rightBottom, player);

        return player.isAlive() || player.isImmortal();

//        if (){
//
//            return true;
//        }
//        else {
//            markSection(leftTop, rightBottom, player);
//            return false;
//        }



    }

    //w gore
    private boolean checkArrayMarkedSectionsUp(Point leftTop, Point rightBottom, Player player) {

        for(int j=rightBottom.getY()-1 ; j >= leftTop.getY(); j--){
            for(int i=leftTop.getX(); i<=rightBottom.getX(); i++){
                if(board[i][j] > 0){
                    collisionPoint = new Point(i, j, "collision");
                    System.out.println("collision");
                    return false;
                } else {
                    handleGainingPowerUp(i,j,player);
                }
            }
        }
        return true;
    }

    private void handleGainingPowerUp(int i, int j, Player player) {
        if(board[i][j] < 0) {
            PowerUp powerUp = powerUpSpawner.getById(board[i][j]);
            clearPowerUp(powerUp);

            PowerUpEffect powerUpEffect = new PowerUpEffect(powerUp.getPowerUpKind());

            powerUpEffect.setPlayer(player);
            new Thread(powerUpEffect).start();
            //NOT SURE IF IT WORKS

            powerUpSpawner.remove(powerUp);
        }
    }


    //w doł jest spoko
    private boolean checkArrayMarkedSectionsDown(Point leftTop, Point rightBottom, Player player) {

        for(int j=leftTop.getY(); j<= rightBottom.getY(); j++){
            for(int i=leftTop.getX(); i<= rightBottom.getX(); i++){
                if(board[i][j] > 0){
                    collisionPoint = new Point(i, j, "collision");
                    System.out.println("collision");
                    return false;
                } else {
                    handleGainingPowerUp(i,j,player);
                }

            }
        }
        return true;
    }


    private boolean checkArrayMarkedSectionsLeft(Point leftTop, Point rightBottom, Player player) {

        for(int i=leftTop.getX(); i<=rightBottom.getX(); i++){
            for(int j=leftTop.getY(); j<= rightBottom.getY(); j++){
                if(board[i][j] > 0){
                    collisionPoint = new Point(i, j, "collision");
                    System.out.println("collision");
                    return false;
                } else {
                    handleGainingPowerUp(i,j,player);
                }
            }
        }
        return true;
    }

    //w prawo jest spoko
    private boolean checkArrayMarkedSectionsRight(Point leftTop, Point rightBottom, Player player) {

        for(int i=rightBottom.getX()- 1; i>= leftTop.getX(); i--){
            for(int j=leftTop.getY(); j<= rightBottom.getY(); j++){
                if(board[i][j] > 0){
                    collisionPoint = new Point(i, j, "collision");
                    System.out.println("collision");
                    return false;
                } else {
                    handleGainingPowerUp(i,j,player);
                }

            }
        }
        return true;
    }

//    private boolean checkArrayBoundaries(Point leftTop, Point rightBottom) {
//
//        int x = 0;
//        int y = 0;
//
//        if(leftTop.getX() >= width){
//            collisionPoint = new Point(width-1,leftTop.getY(),"collision");
//            return false;
//        }
//
//
//        if(leftTop.getX() < width && leftTop.getX() > 0 &&
//                rightBottom.getX() < width && rightBottom.getX() > 0 &&
//                leftTop.getY() < height && leftTop.getY() > 0 &&
//                rightBottom.getY() < height && rightBottom.getY() > 0){
//            return true;
//        }
//
//        return false;
//
//    }

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

    public void clearPowerUp(PowerUp powerUp) {
        Point point = powerUp.getPosition();
        int size = powerUp.getSize();
        for(int i = point.getX(); i< point.getX()+size; i++){
            for(int j = point.getY(); j< point.getY()+size; j++){
                board[i][j] = 0;
            }
        }
        System.out.println("cleared!!! :)");
    }

    public void drawPlayer(Point point, Player player){
        for(int i = point.getX(); i<point.getX()+player.getSize(); i++){
            for(int j = point.getY(); j<point.getY()+player.getSize(); j++){
                board[i][j] = player.getId();
            }
        }
    }

    public void addPowerUp(PowerUp powerUp) {
        int x = powerUp.getPosition().getX();
        int y = powerUp.getPosition().getY();
        int size = powerUp.getSize();
        drawRectangleOnBoard(x, y, size, powerUp.getId());
    }

    private void drawRectangleOnBoard(int x, int y, int size, int value) {
        for(int i = x; i < x+size; i++){
            for(int j = y; j < y+size; j++){
                board[i][j] = value;
            }
        }
    }

    public void setPowerUpSpawner(PowerUpSpawner powerUpSpawner) {
        this.powerUpSpawner = powerUpSpawner;
    }

    public boolean canPlacePowerUp(int x, int y, int size) {
        for(int i = x; i < x+size; i++)
            for(int j = y; j < y+size; j++)
                if(board[i][j] != 0)
                    return false;
        return true;
    }
}
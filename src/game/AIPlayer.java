//package game;
//
//import com.sun.javafx.geom.Shape;
//import server.main.Player;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class AIPlayer {
//
//
//    private void reactProximity() {
//        int velocity = Math.max(Math.abs(velocityX), Math.abs(velocityY));
//
//        // boosts randomly
//        int r = rand.nextInt(100);
//        if (r == 1) {
//            startBoost();
//        }
//
//        // adds all lines of all players to one list
//        ArrayList<Shape> lines = new ArrayList<Shape>();
//        lines.addAll(this.getPath());
//        Collections.reverse(lines);
//        for (Player p: players) {
//            if (p != this) {
//                lines.addAll(p.getPath());
//            }
//        }
//
//        for (int i = lines.size() - 1; i > 0; i--) {
//            Shape l = lines.get(i);
//            int maxX = Math.max(l.getStartX(), l.getEndX());
//            int minX = Math.min(l.getStartX(), l.getEndX());
//            int maxY = Math.max(l.getStartY(), l.getEndY());
//            int minY = Math.min(l.getStartY(), l.getEndY());
//
//            // if there is a line in the path, checks if there is one adjacent
//            if (velocityX > 0 && l.isVertical() && y >= minY && y <= maxY) {
//                if (l.getStartX() - x < 6 && l.getStartX() - x > 0) {
//                    boolean b = false;
//                    for (int j = lines.size() - 1; j > 0; j--) {
//                        Shape k = lines.get(j);
//                        if (!k.isVertical() && y - k.getEndY() < 6 &&
//                                y - k.getEndY() > 0) {
//                            b = true;
//                        }
//                    }
//
//                    // reacts appropriately
//                    if (b) {
//                        velocityY = velocity;
//                    } else {
//                        velocityY = -velocity;
//                    }
//                    velocityX = 0;
//                    time = 40;
//                    return;
//                }
//            }
//
//            // if there is a line in the path, checks if there is one adjacent
//            if (velocityX < 0 && l.isVertical() && y >= minY && y <= maxY) {
//                if (x - l.getStartX() < 6 && x - l.getStartX() > 0) {
//                    boolean b = false;
//                    for (int j = lines.size() - 1; j > 0; j--) {
//                        Shape k = lines.get(j);
//                        if (!k.isVertical() && y - k.getEndY() < 6 &&
//                                y - k.getEndY() > 0) {
//                            b = true;
//                        }
//                    }
//
//                    // reacts appropriately
//                    if (b) {
//                        velocityY = velocity;
//                    } else {
//                        velocityY = -velocity;
//                    }
//                    velocityX = 0;
//                    time = 40;
//                    return;
//                }
//            }
//
//            // if there is a line in the path, checks if there is one adjacent
//            if (velocityY > 0 && !l.isVertical() && x >= minX && x <= maxX) {
//                if (l.getStartY() - y < 6 && l.getStartY() - y > 0) {
//                    boolean b = false;
//                    for (int j = lines.size() - 1; j > 0; j--) {
//                        Shape k = lines.get(j);
//                        if (k.isVertical() && x - k.getEndX() < 6 &&
//                                x - k.getEndX() > 0) {
//                            b = true;
//                        }
//                    }
//
//                    // reacts appropriately
//                    if (b) {
//                        velocityX = velocity;
//                    } else {
//                        velocityX = -velocity;
//                    }
//                    velocityY = 0;
//                    time = 40;
//                    return;
//                }
//            }
//
//            // if there is a line in the path, checks if there is one adjacent
//            if (velocityY < 0 && !l.isVertical() && x >= minX && x <= maxX) {
//                if (y - l.getStartY() < 6 && y - l.getStartY() > 0) {
//                    boolean b = false;
//                    for (int j = lines.size() - 1; j > 0; j--) {
//                        Shape k = lines.get(j);
//                        if (k.isVertical() && x - k.getEndX() < 6 &&
//                                x - k.getEndX() > 0) {
//                            b = true;
//                        }
//                    }
//
//                    // reacts appropriately
//                    if (b) {
//                        velocityX = velocity;
//                    } else {
//                        velocityX = -velocity;
//                    }
//                    velocityY = 0;
//                    time = 40;
//                    return;
//                }
//            }
//        }
//
//        // checks if the Player is too close to the edge
//        if (x < 6 && velocityX != 0) {
//            if (y < 250) {
//                velocityY = velocity;
//            } else {
//                velocityY = -velocity;
//            }
//            velocityX = 0;
//            time = 40;
//            return;
//        }
//        if (rightBound - x < 6 && velocityX != 0) {
//            if (y < 250) {
//                velocityY = velocity;
//            } else {
//                velocityY = -velocity;
//            }
//            velocityX = 0;
//            time = 40;
//            return;
//        }
//        if (y < 6 && velocityY != 0) {
//            if (x < 250) {
//                velocityX = velocity;
//            } else {
//                velocityX = -velocity;
//            }
//            velocityY = 0;
//            time = 40;
//            return;
//        }
//        if (bottomBound - y < 6 && velocityY != 0) {
//            if (x < 250) {
//                velocityX = velocity;
//            } else {
//                velocityX = -velocity;
//            }
//            velocityY = 0;
//            time = 40;
//            return;
//        }
//
//        // moves randomly if all others do not
//        // cause the Player to change direction
//        if (time == 0) {
//            int rando = rand.nextInt(4);
//            if (rando == 0 && velocityX != velocity) {
//                if (x > 6) {
//                    velocityX = -velocity;
//                    velocityY = 0;
//                }
//            } else if (rando == 1 && velocityX != -velocity) {
//                if (rightBound - x > 6) {
//                    velocityX = velocity;
//                    velocityY = 0;
//                }
//            } else if (rando == 2 && velocityY != velocity) {
//                if (y > 6) {
//                    velocityX = 0;
//                    velocityY = -velocity;
//                }
//            } else if (rando == 3 && velocityY != -velocity) {
//                if (bottomBound - y > 6) {
//                    velocityX = 0;
//                    velocityY = velocity;
//                }
//            }
//            time = 40;
//        }
//        time--;
//    }
//
//}

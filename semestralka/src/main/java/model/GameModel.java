package model;

import javafx.scene.canvas.Canvas;

public class GameModel extends Canvas{
    //SCREEN
    private final int originalTile = 16;
    private final int scale = 3;
    private final int tileSize = originalTile * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    private final int screenHeight  = tileSize * maxScreenRow;// 576 pixels

    //WORLD
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    private final int worldWidth = tileSize * maxWorldRow;
    private final int worldHeight = tileSize * maxWorldCol;
    private int infoWindowCount = 0;

    public int getInfoWindowCount() {return infoWindowCount;}

    public void setInfoWindowCount(int infoWindowCount) {
        this.infoWindowCount = infoWindowCount;
    }

    public void increaseInfoWindowCount() {
        setInfoWindowCount(getInfoWindowCount() + 1);
    }
    private boolean gameOver = false;
    public boolean isGameOver() {return gameOver;}
    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}

    public int getScreenHeight(){return screenHeight;}
    public int getScreenWidth(){return screenWidth;}
    public int getTileSize(){return tileSize;}
    public int getWorldWidth(){return worldWidth;}
    public int getWorldHeight(){return worldHeight;}
    public int getMaxWorldCol(){return maxWorldCol;}
    public int getMaxWorldRow(){return maxWorldRow;}
    public int getMaxScreenCol(){return maxScreenCol;}
    public int getMaxScreenRow(){return maxWorldRow;}


}

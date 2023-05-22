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

//    public MyTimer myTimer = new MyTimer(this);
//    public KeyHandler keyHandler = new KeyHandler();
//    public TileManager tileManager = new TileManager(this);
//    public ItemManager itemManager = new ItemManager(this);
//    public Inventory inventory = new Inventory(this);
//    public Player player = new Player(this, keyHandler);
//    public EnemiesList enemiesList = new EnemiesList(this);
//    public CollisionCheck collisionCheck = new CollisionCheck(this);
//
//    public Model() throws IOException {
//        setWidth(screenWidth);
//        setHeight(screenHeight);
//        this.setOnKeyPressed(keyHandler);
//        this.setOnKeyReleased(keyHandler);
//        this.requestFocus();
//    }
//
//    public void startGameThread() {
//        myTimer.start();
//    }
//
//    public void saveGame() throws IOException {
//        itemManager.saveItems();
//        inventory.saveInventory();
//        player.savePlayer();
//        enemiesList.saveEnemies();
//    }

}

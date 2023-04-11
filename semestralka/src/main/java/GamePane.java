import javafx.scene.canvas.Canvas;


public class GamePane extends Canvas{
    //SCREEN
    final int originalTile = 16;
    final int scale = 3;
    public final int tileSize = originalTile * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight  = tileSize * maxScreenRow;// 576 pixels

    //WORLD
    final int maxWorldCol = 50;
    final int maxWorldRow = 50;
    final int worldWidth = tileSize * maxWorldRow;
    final int worldHeight = tileSize * maxWorldCol;
    MyTimer myTimer = new MyTimer(this);
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    ItemManager itemManager = new ItemManager(this);
    Inventory inventory = new Inventory(this);
    public Player player = new Player(this, keyHandler);
    public Enemy enemy = new Enemy(this);
    public CollisionCheck collisionCheck = new CollisionCheck(this);

    public GamePane() {
        setWidth(screenWidth);
        setHeight(screenHeight);
        this.setOnKeyPressed(keyHandler);
        this.setOnKeyReleased(keyHandler);
        this.requestFocus();
    }

    public void startGameThread() {
        myTimer.start();
    }

}

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GamePane extends Pane{

    Main main;
    public Canvas canvas ;

    //SCREEN
    final int originalTile = 16;
    final int scale = 3;
    public final int tileSize = originalTile * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    private final int screenHeight  = tileSize * maxScreenRow;// 576 pixels

    //WORLD
    final int maxWorldCol = 50;
    final int maxWorldRow = 50;
    final int worldWidth = tileSize * maxWorldRow;
    final int worldHeight = tileSize * maxWorldCol;

    public int getScreenWidth(){
        return screenWidth;
    }

    public int getScreenHeight(){
        return screenHeight;
    }

    Thread gameThread;
    MyTimer myTimer = new MyTimer(this);
    KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);
    ItemManager itemManager = new ItemManager(this);
    public CollisionCheck collisionCheck = new CollisionCheck(this);

    public GamePane(Main main) {
        this.setPrefSize(screenWidth, screenHeight);
        this.main = main;
        canvas = new Canvas(screenWidth, screenHeight);
        this.getChildren().add(canvas);
        this.setOnKeyPressed(keyHandler);
        this.setOnKeyReleased(keyHandler);
        canvas.requestFocus();

    }

    public void startGameThread() {
        myTimer.start();
    }

    public void renderMap(GraphicsContext gc){
        gc.clearRect(0, 0, worldWidth, worldHeight);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, screenWidth, screenHeight);
    }
}

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GamePane extends Pane implements Runnable{

    Main main;
    private Canvas canvas ;

    final int originalTile = 16;
    final int scale = 3;

    public final int tileSize = originalTile * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    private final int screenHeight  = tileSize * maxScreenRow;// 576 pixels

    public int getScreenWidth(){
        return screenWidth;
    }

    public int getScreenHeight(){
        return screenHeight;
    }

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);

    //set default
    int playerX = 100;
    int playerY = 100;
    int newPlayerX;
    int newPlayerY;
    int tempX = playerX;
    int tempY = playerY;
    int playerSpeed = 4;

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

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        while (gameThread != null) {
//            long currentTime = System.nanoTime();
            Platform.runLater(() -> {
                update();
                draw();
            });
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {
        player.update();

    }

    public void draw(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        renderMap(gc);
        player.draw(gc);

    }

    public void renderMap(GraphicsContext gc){
        gc.clearRect(0, 0, getScreenWidth(), getScreenHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getScreenWidth(), getScreenHeight());
    }
}

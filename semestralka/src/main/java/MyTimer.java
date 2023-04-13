import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyTimer extends AnimationTimer {
    GamePane gamePane;

    public MyTimer(GamePane gamePane) {
        this.gamePane = gamePane;
    }

    @Override
    public void handle(long l) {
        update();
        draw();
    }

    public void update() {
        gamePane.enemy.update();
        gamePane.player.update();
    }

    public void draw() {
        GraphicsContext gc = gamePane.getGraphicsContext2D();
        renderMap(gc);
        gamePane.tileManager.draw(gc);
        gamePane.itemManager.draw(gc);
        gamePane.enemy.draw(gc);
        gamePane.inventory.draw(gc);
        gamePane.player.draw(gc);
    }

    public void renderMap(GraphicsContext gc) {
        gc.clearRect(0, 0, gamePane.worldWidth, gamePane.worldHeight);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gamePane.screenWidth, gamePane.screenHeight);
    }
}

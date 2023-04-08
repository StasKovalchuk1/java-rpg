import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class MyTimer extends AnimationTimer {
    GamePane gamePane;

    public MyTimer(GamePane gamePane){
        this.gamePane = gamePane;
    }

    @Override
    public void handle(long l) {
        update();
        draw();
    }

    public void update(){
        gamePane.player.update();
    }
    public void draw(){
        GraphicsContext gc = gamePane.canvas.getGraphicsContext2D();
        gamePane.renderMap(gc);
        gamePane.tileManager.draw(gc);
        gamePane.itemManager.draw(gc);
        gamePane.player.draw(gc);
    }
}

package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyTimer extends AnimationTimer {
    GamePane gamePane;
//    private SoundManager soundManager = new SoundManager();


    public MyTimer(GamePane gamePane) {
        this.gamePane = gamePane;
    }

    @Override
    public void handle(long l) {
        update();
        draw();
//        soundManager.playTakeItemSound();
    }

    private void update() {
        gamePane.enemiesList.updateEnemies();
        gamePane.player.update();
    }

    private void draw() {
        GraphicsContext gc = gamePane.getGraphicsContext2D();
        renderMap(gc);
        gamePane.tileManager.draw(gc);
        gamePane.itemManager.draw(gc);
        gamePane.enemiesList.drawEnemies(gc);
        gamePane.inventory.draw(gc);
        gamePane.player.draw(gc);
    }

    private void renderMap(GraphicsContext gc) {
        gc.clearRect(0, 0, gamePane.getWorldWidth(), gamePane.getWorldHeight());
        gc.setFill(Color.rgb(111, 175, 242));
        gc.fillRect(0, 0, gamePane.getScreenWidth(), gamePane.getScreenHeight());
    }
}

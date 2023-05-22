package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import model.FilesModel;
import model.GameModel;

import java.util.Optional;

public class MyTimer extends AnimationTimer {
//    GameModel gameModel = new GameModel();
    Controller controller;
    public MyTimer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(long l) {
        update();
        draw();
    }

    private void update() {
        controller.enemiesList.updateEnemies();
        controller.player.update();
        controller.gameModel.setGameOver(controller.setGameOver());
        controller.checkGameOver();
    }

    private void draw() {
        GraphicsContext gc = controller.getGraphicsContext2D();
        renderMap(gc);
        controller.tileManager.draw(gc);
        controller.itemManager.draw(gc);
        controller.enemiesList.drawEnemies(gc);
        controller.inventory.draw(gc);
        controller.player.draw(gc);
    }

    private void renderMap(GraphicsContext gc) {
        gc.clearRect(0, 0, controller.gameModel.getWorldWidth(), controller.gameModel.getWorldHeight());
        gc.setFill(Color.rgb(111, 175, 242));
        gc.fillRect(0, 0, controller.gameModel.getScreenWidth(), controller.gameModel.getScreenHeight());
    }

}

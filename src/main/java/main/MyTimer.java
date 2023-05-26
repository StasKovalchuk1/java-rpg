package main;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import java.awt.*;

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
        controller.gameModel.setGameOver(controller.checkGameOver());
        controller.gameOver();
    }

    private void draw() {
        GraphicsContext gc = controller.getGraphicsContext2D();
        renderMap(gc);
        controller.tileManager.draw(gc);
        controller.itemManager.draw(gc);
        controller.enemiesList.drawEnemies(gc);
        controller.inventory.draw(gc);
        controller.player.draw(gc);
        gameInfo(gc);
    }

    private void renderMap(GraphicsContext gc) {
        gc.clearRect(0, 0, controller.gameModel.getWorldWidth(), controller.gameModel.getWorldHeight());
        gc.setFill(Color.rgb(111, 175, 242));
        gc.fillRect(0, 0, controller.gameModel.getScreenWidth(), controller.gameModel.getScreenHeight());
    }

    private void gameInfo(GraphicsContext gc){
        if (controller.gameModel.getInfoWindowCount() < 750){
            controller.gameModel.increaseInfoWindowCount();
            drawGameInfo(gc);
        }
        if (controller.keyHandler.spacePressed) controller.gameModel.setInfoWindowCount(750);
    }
    private void drawGameInfo(GraphicsContext gc) {
        gc.setFill(Color.rgb(20, 20, 20, 0.8));
        gc.setStroke(Color.WHITE);
        gc.fillRect(192, 168, controller.gameModel.getTileSize() * 9, controller.gameModel.getTileSize() * 6);
//        gc.strokeRect(5, 5, controller.gameModel.getTileSize() * 5, controller.gameModel.getTileSize() * 3);

        gc.setFont(new Font("Tiempos Text", 18));
        gc.setFill(Color.WHITE);
        gc.fillText("Game Info\n\n", 360, 195);
        gc.fillText("You will find items and fight with enemies\n", 200, 230);
        gc.fillText("To win you need to kill all enemies on the map\n \n", 200, 248);
        gc.fillText("Rules:\n" +
                "•\u200E to move your hero press W, S, A, D keys.\n" +
                "•\u200E to open your inventory press K key.\n" +
                "•\u200E to open a chest press L key.\n" +
                "•\u200E to attack press N key.\n" +
                "•\u200E to defend press M key.\n \n", 200, 284);
        gc.fillText("Press SPACE to close", 200, 430);
    }

}

package main;

import entities.EnemiesList;
import entities.Enemy;
import items.Inventory;
import entities.Player;
import items.ItemManager;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import data.FilesModel;
import data.GameModel;
import map.TileManager;

import java.io.IOException;
import java.util.Optional;

public class Controller extends Canvas {
    Stage stage;
    public GameModel gameModel = new GameModel();
    public MyTimer myTimer;
    public KeyHandler keyHandler;
    public TileManager tileManager;
    public ItemManager itemManager;
    public Inventory inventory;
    public Player player;
    public EnemiesList enemiesList;
    public CollisionCheck collisionCheck;

    public Controller(Stage stage) {
        try {
            myTimer = new MyTimer(this);
            keyHandler = new KeyHandler();
            tileManager = new TileManager(this);
            itemManager = new ItemManager(this);
            inventory = new Inventory(this);
            player = new Player(this, keyHandler);
            enemiesList = new EnemiesList(this);
            collisionCheck = new CollisionCheck(this);
            setWidth(gameModel.getScreenWidth());
            setHeight(gameModel.getScreenHeight());
            this.setOnKeyPressed(keyHandler);
            this.setOnKeyReleased(keyHandler);
            this.requestFocus();
            this.stage = stage;
        } catch (Exception e){
            MyLogger.getMyLogger().severe("Failed to create controller");
            e.printStackTrace();
        }
    }

    public Controller() {}

    public void startGameThread() {
        myTimer.start();
    }

    /**
     * Start all process to save the game
     * @throws IOException
     */
    public void saveGame() throws IOException {
        itemManager.saveItems();
        inventory.saveInventory();
        player.savePlayer();
        enemiesList.saveEnemies();
    }

    /**
     * Check if game is over
     * @return
     */
    public boolean checkGameOver() {
        if (!player.getAlive()) return true;
        for (Enemy enemy : enemiesList.getEnemiesList()) {
            if (enemy.getAlive()) return false;
        }
        return true;
    }

    /**
     * Shou popup window when game is over
     */
    public void gameOver() {
        if (gameModel.isGameOver()) {
            Platform.runLater(() -> {
                myTimer.stop();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over!");
                alert.setHeaderText(null);
                alert.setContentText("Game Over!");

                ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType playAgainButton = new ButtonType("Play again", ButtonBar.ButtonData.OK_DONE);

                alert.getButtonTypes().setAll(closeButton, playAgainButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == playAgainButton) {
                    try {
                        FilesModel.setDefaultData();
                        resetGame();
                        startGameThread();
                    } catch (Exception e) {
                        MyLogger.getMyLogger().severe("New game was not started");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FilesModel.setDefaultData();
                        stage.close();
                    } catch (Exception e) {
                        MyLogger.getMyLogger().severe("Default game settings were not saved");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Start processes to reset the game
     * @throws IOException
     */
    public void resetGame() throws IOException {
        itemManager.resetItems();
        inventory.resetInventory();
        player.resetPlayer();
        enemiesList.resetEnemies();
    }

}

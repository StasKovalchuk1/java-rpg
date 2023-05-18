package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;


public class Main extends Application{

    private GamePane gamePane;
    private Group root;

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MyLogger.init();
        gamePane = new GamePane();
        stage.setTitle("Nefor boy");

        root = new Group(gamePane);

        final Scene scene = new Scene(root);
        gamePane.requestFocus();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                gamePane.saveGame();
            } catch (IOException e) {
                MyLogger.getMyLogger().severe("Exeption! Game wasn't saved " + e);
            }
        });
        stage.show();

        gamePane.startGameThread();
    }

}

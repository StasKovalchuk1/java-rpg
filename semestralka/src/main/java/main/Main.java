package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application{

    private final GamePane gamePane = new GamePane();
    private Group root;

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Nefor boy");

        root = new Group(gamePane);

        final Scene scene = new Scene(root);
        gamePane.requestFocus();
        stage.setScene(scene);
        stage.show();

        gamePane.startGameThread();
    }

}

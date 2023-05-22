package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;
import model.FilesModel;

import java.io.IOException;
import java.util.Optional;


public class Main extends Application{

    private Controller controller;
    private Group root;

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MyLogger.init();
        controller = new Controller(stage);
        stage.setTitle("Nefor boy");

        root = new Group(controller);

        final Scene scene = new Scene(root);
        controller.requestFocus();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                controller.saveGame();
            } catch (IOException e) {
                MyLogger.getMyLogger().severe("Exeption! Game wasn't saved " + e);
                e.printStackTrace();
            }
        });
        stage.show();

        controller.startGameThread();


    }

}

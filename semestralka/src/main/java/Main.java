import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application{

    private final GamePane gamePane = new GamePane(this);
    private VBox root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Title");

        root = new VBox(gamePane);

        final Scene scene = new Scene(root);
        gamePane.requestFocus();
        stage.setScene(scene);
        stage.show();

        gamePane.startGameThread();
    }

}

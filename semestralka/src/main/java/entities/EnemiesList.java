package entities;

import javafx.scene.canvas.GraphicsContext;
import main.GamePane;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
;

public class EnemiesList {
    GamePane gamePane;
    private List<Enemy> enemiesList = new ArrayList();
    public List<Enemy> getEnemiesList() {
        return enemiesList;
    }
    private String enemiesFileName = "enemy/enemieslist.txt";
    private URL resourceToEnemies = getClass().getClassLoader().getResource(enemiesFileName);
    private File enemiesFile;
    {
        try {
            enemiesFile = new File(resourceToEnemies.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public EnemiesList(GamePane gamePane) throws IOException {
        this.gamePane = gamePane;
        setEnemiesList();
    }

    /**
     * Reads enemies from the file and add it to the list
     * @throws IOException
     */
    public void setEnemiesList() throws IOException {
        FileReader fileReader = new FileReader(enemiesFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            enemiesList.add(new Enemy(gamePane, Integer.parseInt(arrLine[0]), Integer.parseInt(arrLine[1])));
        }
    }

    /**
     * Draws enemies on the canvas
     * @param gc
     */
    public void drawEnemies(GraphicsContext gc) {
        for (Entity enemy : enemiesList){
            enemy.draw(gc);
        }
    }

    /**
     * Updates the values of each enemy
     */
    public void updateEnemies() {
        for (Entity enemy : enemiesList){
            enemy.update();
        }
    }
}

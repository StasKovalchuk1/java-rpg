package entities;

import javafx.scene.canvas.GraphicsContext;
import main.FilesModel;
import main.GamePane;
import main.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
;

public class EnemiesList {
    GamePane gamePane;
    private List<Enemy> enemiesList = new ArrayList();
    public List<Enemy> getEnemiesList() {
        return enemiesList;
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
        MyLogger.getMyLogger().info("Loading the enemies");
        FileReader fileReader = new FileReader(FilesModel.getEntitiesFile());
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            if (arrLine[0].equals("ork")){
                enemiesList.add(new Ork(gamePane, Integer.parseInt(arrLine[1]), Integer.parseInt(arrLine[2]), Integer.parseInt(arrLine[3])));
            } else if (arrLine[0].equals("boss")){
                enemiesList.add(new Boss(gamePane, Integer.parseInt(arrLine[1]), Integer.parseInt(arrLine[2]), Integer.parseInt(arrLine[3])));
//                System.out.println("boss has been created : " + enemiesList.get(2).getWorldX() + " " + enemiesList.get(2).getWorldY());
            }
        }
        reader.close();
    }

    /**
     * Draws enemies on the canvas
     * @param gc
     */
    public void drawEnemies(GraphicsContext gc) {
        for (Enemy enemy : enemiesList){
            enemy.draw(gc);
        }
    }

    /**
     * Updates the values of each enemy
     */
    public void updateEnemies() {
        for (Enemy enemy : enemiesList) {
            enemy.update();
        }
    }

    public void saveEnemies() throws IOException {
        FileWriter fileWriter = new FileWriter(FilesModel.getEntitiesFile(), true);
        for (Enemy enemy : enemiesList) {
            if (enemy.getAlive()){
                if (enemy instanceof Ork) {
                    fileWriter.write("ork " + enemy.getWorldY() / gamePane.getTileSize() + " " +
                            enemy.getWorldX() / gamePane.getTileSize() +  " " + enemy.getLives() + "\n");
                } else if (enemy instanceof Boss) {
                    fileWriter.write("boss " + enemy.getWorldY() / gamePane.getTileSize() + " " +
                            enemy.getWorldX() / gamePane.getTileSize() +  " " + enemy.getLives() + "\n");
                }
            }
        }
        fileWriter.close();
    }
}

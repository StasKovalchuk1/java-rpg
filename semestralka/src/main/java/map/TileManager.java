package map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Controller;
import model.FilesModel;
import model.GameModel;
import main.MyLogger;
import model.Tile;


import java.io.*;

/**
 * This class is for reading tiles,
 * filling them to the map
 * and drawing it on canvas
 */
public class TileManager {
    GameModel gameModel = new GameModel();
    private Controller controller;
    private Tile[] tiles;
    private int[][] mapTileNumbers;
    public int[][] getMapTileNumbers(){return mapTileNumbers;}
    public Tile[] getTilesList(){return tiles;}

    public TileManager(Controller controller) {
        this.controller = controller;
        try {
            getTiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mapTileNumbers = new int[gameModel.getMaxWorldRow()][gameModel.getMaxWorldCol()];
        fillMap(FilesModel.getMapFile());
    }

    /**
     * Gets tiles and adds them to the array
     * @throws IOException
     */
    private void getTiles() throws IOException {
        MyLogger.getMyLogger().info("Loading the tiles");
        FileReader fileReader = new FileReader(FilesModel.getTilesFile());
        BufferedReader reader = new BufferedReader(fileReader);
        try {
        String line = reader.readLine();
        int tileCount = Integer.parseInt(line);
        tiles = new Tile[tileCount];
        for (int i = 0; i < tileCount; i++) {
            line = reader.readLine();
            String[] arrLine = line.split(" ");
            tiles[i] = new Tile();
            tiles[i].image = new Image(arrLine[1]);
            if (arrLine[2].equals("solid")) {
                tiles[i].solid = true;
            }
        }
        reader.close();
        MyLogger.getMyLogger().info("Tiles are loaded");
        } catch (NumberFormatException e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
//            System.exit(1);
        }
    }

    /**
     * Fills a two-dimensional array of the map with tiles
     * @param file Your file with map
     */
    private void fillMap(File file) {
        try {
            MyLogger.getMyLogger().info("Loading the map");
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            for (int row = 0; row < mapTileNumbers.length; row++) {
                String line = reader.readLine();
                if (line != null) {
                    String[] arrStr = line.split(" ");
                    for (int col = 0; col < mapTileNumbers[0].length; col++) {
                        int value = Integer.parseInt(arrStr[col]);
                        mapTileNumbers[row][col] = value;
                    }
                }
            }
            MyLogger.getMyLogger().info("Map is loaded");
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    /**
     * Draws the map on canvas
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        for (int row = 0; row < gameModel.getMaxWorldRow(); row++) {
            for (int col = 0; col < gameModel.getMaxWorldCol(); col++) {
                int index = mapTileNumbers[row][col];
                int worldX = col * gameModel.getTileSize();
                int worldY = row * gameModel.getTileSize();
                // set where we should print title regarding the hero
                int screenX = worldX - controller.player.getWorldX() + controller.player.getScreenX();
                int screenY = worldY - controller.player.getWorldY() + controller.player.getScreenY();
                // print only tiles which are on the screen
                if ((screenX + gameModel.getTileSize() >= 0 && screenX - gameModel.getTileSize() <= gameModel.getScreenWidth()) &&
                        (screenY + gameModel.getTileSize() >= 0 && screenY - gameModel.getTileSize() <= gameModel.getScreenHeight())) {
                    gc.drawImage(tiles[index].image, screenX, screenY, gameModel.getTileSize(), gameModel.getTileSize());
                }
            }
        }
    }
}

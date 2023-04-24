package tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GamePane;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class TileManager {
    private GamePane gamePane;
    private Tile[] tiles;
    private int[][] mapTileNumbers;
    private String tileNumFile = "maps/tileNum.txt";
    private URL resourceToTiles = getClass().getClassLoader().getResource(tileNumFile);
    private File tilesFile;
    private File mapFile;
    private String mapFileName = "maps/map1.txt";
    private URL resourceToMap = getClass().getClassLoader().getResource(mapFileName);

    {
        try {
            tilesFile = new File(resourceToTiles.toURI());
            mapFile = new File(resourceToMap.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public int[][] getMapTileNumbers(){return mapTileNumbers;}
    public Tile[] getTilesList(){return tiles;}

    public TileManager(GamePane gamePane) {
        this.gamePane = gamePane;
        try {
            getTiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mapTileNumbers = new int[gamePane.getMaxWorldRow()][gamePane.getMaxWorldCol()];
        fillMap(mapFile);
    }
    private void getTiles() throws IOException {
        FileReader fileReader = new FileReader(tilesFile);
//        InputStreamReader fileReader = new InputStreamReader(tilesFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        int tileCount = Integer.parseInt(line);
        tiles = new Tile[tileCount];
        try {
            for (int i = 0; i < tileCount; i++) {
                line = reader.readLine();
                String[] arrLine = line.split(" ");
                tiles[i] = new Tile();
                tiles[i].image = new Image(arrLine[1]);
                if (arrLine[2].equals("solid")) {
                    tiles[i].solid = true;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void fillMap(File file) {
        try {
            FileReader fileReader = new FileReader(file);
//            InputStreamReader fileReader = new InputStreamReader(mapFile);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc) {
        for (int row = 0; row < gamePane.getMaxWorldRow(); row++) {
            for (int col = 0; col < gamePane.getMaxWorldCol(); col++) {
                int index = mapTileNumbers[row][col];
                int worldX = col * gamePane.getTileSize();
                int worldY = row * gamePane.getTileSize();
                // set where we should print title regarding the hero
                int screenX = worldX - gamePane.player.getWorldX() + gamePane.player.screenX;
                int screenY = worldY - gamePane.player.getWorldY() + gamePane.player.screenY;
                // print only tiles which are on the screen
                if ((screenX + gamePane.getTileSize() >= 0 && screenX - gamePane.getTileSize() <= gamePane.getScreenWidth()) &&
                        (screenY + gamePane.getTileSize() >= 0 && screenY - gamePane.getTileSize() <= gamePane.getScreenHeight())) {
                    gc.drawImage(tiles[index].image, screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize());
                }
            }
        }
    }
}

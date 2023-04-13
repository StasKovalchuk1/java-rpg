import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;

public class TileManager {
    GamePane gamePane;
    Tile[] tiles;
    int[][] mapTileNumbers;
    String tileNumFile = "maps/tileNum.txt";
    File tilesFile = new File(getClass().getResource(tileNumFile).getFile());
    String mapFileName = "maps/map1.txt";
    File mapFile = new File(getClass().getResource(mapFileName).getFile());

    public TileManager(GamePane gamePane) {
        this.gamePane = gamePane;
        try {
            getTiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mapTileNumbers = new int[gamePane.maxWorldRow][gamePane.maxWorldCol];
        fillMap(mapFile);
    }

    public void getTiles() throws IOException {
        FileReader fileReader = new FileReader(tilesFile);
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

    public void fillMap(File file) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc) {
        for (int row = 0; row < gamePane.maxWorldRow; row++) {
            for (int col = 0; col < gamePane.maxWorldCol; col++) {
                int index = mapTileNumbers[row][col];
                int worldX = col * gamePane.tileSize;
                int worldY = row * gamePane.tileSize;
                // set where we should print title regarding the hero
                int screenX = worldX - gamePane.player.worldX + gamePane.player.screenX;
                int screenY = worldY - gamePane.player.worldY + gamePane.player.screenY;
                // print only tiles which are on the screen
                if ((screenX + gamePane.tileSize >= 0 && screenX - gamePane.tileSize <= gamePane.screenWidth) &&
                        (screenY + gamePane.tileSize >= 0 && screenY - gamePane.tileSize <= gamePane.screenHeight)) {
                    gc.drawImage(tiles[index].image, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
                }
            }
        }
    }
}

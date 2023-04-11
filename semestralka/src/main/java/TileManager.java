import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;

public class TileManager {
    GamePane gamePane;
    Tile[] tiles;
    int[][] mapTileNumbers;
    String tileNumFile = "/Users/stanislavkovalcuk/kovalst1/semestralka/src/main/resources/maps/tileNum.txt";
    File tilesFile = new File(tileNumFile);
    public TileManager(GamePane gamePane){
        this.gamePane = gamePane;
        try {
            getTiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mapTileNumbers = new int[gamePane.maxWorldRow][gamePane.maxWorldCol];
        fillMap("/Users/stanislavkovalcuk/kovalst1/semestralka/src/main/resources/maps/map1.txt");
    }

    public void getTiles() throws IOException {
        FileReader fileReader = new FileReader(tilesFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        int tileCount = Integer.parseInt(line);
        tiles = new Tile[tileCount];
        try {
            for (int i = 0; i < tileCount; i++){
                line = reader.readLine();
                String[] arrLine = line.split(" ");
                tiles[Integer.parseInt(arrLine[0])] = new Tile();
                tiles[Integer.parseInt(arrLine[0])].image = new Image(arrLine[1]);
                if (arrLine.length == 3 && arrLine[2].equals("solid")){
                    tiles[Integer.parseInt(arrLine[0])].solid = true;
                }
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void fillMap(String fileName) {
        try{
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            for (int row = 0; row < mapTileNumbers.length; row++){
                String line = reader.readLine();
                if (line != null){
                    String[] arrStr = line.split(" ");
                    for (int col = 0; col < mapTileNumbers[0].length; col++){
                        int value = Integer.parseInt(arrStr[col]);
                        mapTileNumbers[row][col] = value;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc){
        for (int row = 0; row < gamePane.maxWorldRow; row++){
            for (int col = 0; col < gamePane.maxWorldCol; col++){
                int index = mapTileNumbers[row][col];
                int worldX = col * gamePane.tileSize;
                int worldY = row * gamePane.tileSize;
                // set where we should print title regarding the hero
                int screenX = worldX - gamePane.player.worldX + gamePane.player.screenX;
                int screenY = worldY - gamePane.player.worldY + gamePane.player.screenY;
                // print only tiles which are on the screen
                if ((screenX + gamePane.tileSize >= 0 && screenX - gamePane.tileSize <= gamePane.screenWidth) &&
                        (screenY + gamePane.tileSize >= 0 && screenY - gamePane.tileSize <= gamePane.screenHeight)){
                    gc.drawImage(tiles[index].image, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
                }
            }
        }
    }
}

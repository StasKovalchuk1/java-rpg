import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.util.HashMap;

public class ItemManager {
    GamePane gamePane;
    HashMap<String, Item> items = new HashMap<>();
    String itemsFileName = "/Users/stanislavkovalcuk/kovalst1/semestralka/src/main/resources/items/itemsInit.txt";
    File itemsFile = new File(itemsFileName);
    public ItemManager(GamePane gamePane){
        this.gamePane = gamePane;
        try {
            getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getItems() throws IOException {
        FileReader fileReader = new FileReader(itemsFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null){
            String[] arrLine = line.split(" ");
            switch (arrLine[1]){
                case "key":
                    items.put(arrLine[1], new Key(arrLine[1], new Image(arrLine[2]), Integer.parseInt(arrLine[3]),
                            Integer.parseInt(arrLine[4]), Boolean.parseBoolean(arrLine[5])));
                    break;
                case "sword":
                    items.put(arrLine[1], new Sword(arrLine[1], new Image(arrLine[2]), Integer.parseInt(arrLine[3]),
                            Integer.parseInt(arrLine[4]), Boolean.parseBoolean(arrLine[5])));
                    break;
                case "chest":
                    items.put(arrLine[1], new Chest(arrLine[1], new Image(arrLine[2]), Integer.parseInt(arrLine[3]),
                            Integer.parseInt(arrLine[4]), Boolean.parseBoolean(arrLine[5])));
                    break;
                case "shield":
                    items.put(arrLine[1], new Shield(arrLine[1], new Image(arrLine[2]), Integer.parseInt(arrLine[3]),
                            Integer.parseInt(arrLine[4]), Boolean.parseBoolean(arrLine[5])));
                    break;
            }
        }
        Chest chest = (Chest) items.get("chest");
        for (String i : items.keySet()){
            if (items.get(i).insideChest){
                chest.inside.add(items.get(i));
            }
        }
    }

    public void draw(GraphicsContext gc){
        for (String i : items.keySet()){
            if (items.get(i).worldX >= 0 && items.get(i).worldY >= 0){
                // set where we should print item regarding the hero
                int screenX = items.get(i).worldX - gamePane.player.worldX + gamePane.player.screenX;
                int screenY = items.get(i).worldY - gamePane.player.worldY + gamePane.player.screenY;
                // print only items which are on the screen
                if ((screenX + gamePane.tileSize >= 0 && screenX - gamePane.tileSize <= gamePane.getScreenWidth()) &&
                        (screenY + gamePane.tileSize >= 0 && screenY - gamePane.tileSize <= gamePane.getScreenHeight())) {
                    if (!items.get(i).isTaken){
                        gc.drawImage(items.get(i).image, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
                    }
                }
            }
        }
    }
}

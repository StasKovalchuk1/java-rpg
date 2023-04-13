import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

public class ItemManager {
    GamePane gamePane;
    HashMap<String, Item> items = new HashMap<>();
    String itemsFileName = "items/itemsInit.txt";
    File itemsFile = new File(getClass().getResource(itemsFileName).getFile());

    public ItemManager(GamePane gamePane) {
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
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            switch (arrLine[0]) {
                case "key":
                    items.put(arrLine[0], new Key(arrLine[0], new Image("items/key.png"), Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, Boolean.parseBoolean(arrLine[3])));
                    break;
                case "sword":
                    items.put(arrLine[0], new Sword(arrLine[0], new Image("items/sword.png"), Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, Boolean.parseBoolean(arrLine[3])));
                    break;
                case "chest":
                    items.put(arrLine[0], new Chest(arrLine[0], new Image("items/chest.png"), Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, Boolean.parseBoolean(arrLine[3])));
                    break;
                case "shield":
                    items.put(arrLine[0], new Shield(arrLine[0], new Image("items/shield.png"), Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, Boolean.parseBoolean(arrLine[3])));
                    break;
            }
        }
        Chest chest = (Chest) items.get("chest");
        for (String i : items.keySet()) {
            if (items.get(i).insideChest) {
                chest.inside.add(items.get(i));
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (String i : items.keySet()) {
            if (items.get(i).worldX >= 0 && items.get(i).worldY >= 0) {
                // set where we should print item regarding the hero
                int screenX = items.get(i).worldX - gamePane.player.worldX + gamePane.player.screenX;
                int screenY = items.get(i).worldY - gamePane.player.worldY + gamePane.player.screenY;
                // print only items which are on the screen
                if ((screenX + gamePane.tileSize >= 0 && screenX - gamePane.tileSize <= gamePane.screenWidth) &&
                        (screenY + gamePane.tileSize >= 0 && screenY - gamePane.tileSize <= gamePane.screenHeight)) {
                    if (!items.get(i).isTaken) {
                        if (i.equals("chest") && items.get(i).getIsOpened()) {
                            items.get(i).image = new Image("items/chest_opened.png");
                        }
                        gc.drawImage(items.get(i).image, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
                    }
                }
            }
        }
    }
}

package items;

import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {
    GamePane gamePane;
    private List<Item> items = new ArrayList<>();
    public List<Item> getAllItems(){return items;}
    private String itemsFileName = "items/itemsInit.txt";
    private URL resource = getClass().getClassLoader().getResource(itemsFileName);
    private File itemsFile;
    {
        try {
            itemsFile = new File(resource.toURI());
        } catch (URISyntaxException e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    public ItemManager(GamePane gamePane) {
        this.gamePane = gamePane;
        try {
            getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets items and adds them to the hashmap
     * @throws IOException
     */
    private void getItems() throws IOException {
        MyLogger.getMyLogger().info("Loading the items");
        FileReader fileReader = new FileReader(itemsFile);
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split(" ");
                switch (arrLine[0]) {
                    case "key":
                        items.add(new Key(arrLine[0], new Image("items/key.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                    case "sword":
                        items.add(new Sword(arrLine[0], new Image("items/sword.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                    case "chest":
                        items.add(new Chest(arrLine[0], new Image("items/chest.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                    case "shield":
                        items.add(new Shield(arrLine[0], new Image("items/shield.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                }
            }
            Chest chest = null;
            for (Item item : items) {
                if (item instanceof Chest) chest = (Chest) item;
            }
            for (Item item : items) {
                if (item.getInsideChest() && chest != null) {
                    chest.inside.add(item);
                }
            }
            MyLogger.getMyLogger().info("Tiles are loaded");
        } catch (Exception e){
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    /**
     * Draw all items on the canvas
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        for (Item item : items) {
            if (item.getWorldX() >= 0 && item.getWorldY() >= 0) {
                // set where we should print item regarding the hero
                int screenX = item.getWorldX() - gamePane.player.getWorldX() + gamePane.player.getScreenX();
                int screenY = item.getWorldY() - gamePane.player.getWorldY() + gamePane.player.getScreenY();
                // print only items which are on the screen
                if ((screenX + gamePane.getTileSize() >= 0 && screenX - gamePane.getTileSize() <= gamePane.getScreenWidth()) &&
                        (screenY + gamePane.getTileSize() >= 0 && screenY - gamePane.getTileSize() <= gamePane.getScreenHeight())) {
                    if (!item.getIsTaken()) {
                        if ((item instanceof Chest) && item.getIsOpened()) {
                            item.setImage(new Image("items/chest_opened.png"));
                        }
                        gc.drawImage(item.getImage(), screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize());
                    }
                }
            }
        }
    }
}

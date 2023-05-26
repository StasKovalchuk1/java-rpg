package items;

import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.FilesModel;
import model.Item;
import model.GameModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    GameModel gameModel = new GameModel();

    Controller controller;
    private List<Item> items = new ArrayList<>();
    public List<Item> getAllItems(){return items;}

    public ItemManager(Controller controller) {
        this.controller = controller;
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
    public void getItems() throws IOException {
        MyLogger.getMyLogger().info("Loading the items");
        FileReader fileReader = new FileReader(FilesModel.getItemsFile());
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split(" ");
                switch (arrLine[0]) {
                    case "key":
                        items.add(new Key(arrLine[0], new Image("items/key.png"), Integer.parseInt(arrLine[2]) * gameModel.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gameModel.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                    case "sword":
                        items.add(new Sword(arrLine[0], new Image("items/sword.png"), Integer.parseInt(arrLine[2]) * gameModel.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gameModel.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                    case "chest":
                        items.add(new Chest(arrLine[0], new Image("items/chest.png"), Integer.parseInt(arrLine[2]) * gameModel.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gameModel.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                    case "shield":
                        items.add(new Shield(arrLine[0], new Image("items/shield.png"), Integer.parseInt(arrLine[2]) * gameModel.getTileSize(),
                                Integer.parseInt(arrLine[1]) * gameModel.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                        break;
                }
            }
            reader.close();
            Chest chest = null;
            for (Item item : items) {
                if (item instanceof Chest) chest = (Chest) item;
            }
            for (Item item : items) {
                if (item.getInsideChest() && chest != null) {
                    chest.getItemsInside().add(item);
                }
            }
            MyLogger.getMyLogger().info("Items are loaded");
        } catch (Exception e){
            MyLogger.getMyLogger().severe("Loading items FAILED : " + e);
            e.printStackTrace();
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
                int screenX = item.getWorldX() - controller.player.getWorldX() + controller.player.getScreenX();
                int screenY = item.getWorldY() - controller.player.getWorldY() + controller.player.getScreenY();
                // print only items which are on the screen
                if ((screenX + gameModel.getTileSize() >= 0 && screenX - gameModel.getTileSize() <= gameModel.getScreenWidth()) &&
                        (screenY + gameModel.getTileSize() >= 0 && screenY - gameModel.getTileSize() <= gameModel.getScreenHeight())) {
                    if (!item.getIsTaken()) {
                        if ((item instanceof Chest) && item.getIsOpened()) {
                            item.setImage(new Image("items/chest_opened.png"));
                        }
                        gc.drawImage(item.getImage(), screenX, screenY, gameModel.getTileSize(), gameModel.getTileSize());
                    }
                }
            }
        }
    }

    /**
     * Save all not taken items on the map to the file
     * @throws IOException
     */
    public void saveItems() throws IOException {
        FileWriter fileWriter = new FileWriter(FilesModel.getItemsFile(), false);
        for (Item item : items) {
            if (!item.getIsTaken()) {
                if (item instanceof Key) {
                    fileWriter.write("key " + item.getWorldY() / gameModel.getTileSize() + " " + item.getWorldX() / gameModel.getTileSize() + " " + item.getInsideChest() + "\n");
                } else if (item instanceof Sword) {
                    fileWriter.write("sword " + item.getWorldY() / gameModel.getTileSize() + " " + item.getWorldX() / gameModel.getTileSize() + " " + item.getInsideChest() + "\n");
                } else if (item instanceof Chest) {
                    fileWriter.write("chest " + item.getWorldY() / gameModel.getTileSize() + " " + item.getWorldX() / gameModel.getTileSize() + " " + item.getInsideChest() + "\n");
                } else if (item instanceof Shield) {
                    fileWriter.write("shield " + item.getWorldY() / gameModel.getTileSize() + " " + item.getWorldX() / gameModel.getTileSize() + " " + item.getInsideChest() + "\n");
                }
            }
        }
        fileWriter.close();
    }

    /**
     * Delete all items from the items list
     * and set them again
     * @throws IOException
     */
    public void resetItems() throws IOException {
        items.clear();
        getItems();
    }
}

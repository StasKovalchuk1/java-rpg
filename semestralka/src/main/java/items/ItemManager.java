package items;

import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class ItemManager {
    GamePane gamePane;
    private HashMap<String, Item> items = new HashMap<>();
    public HashMap<String, Item> getAllItems(){return items;}

    private String itemsFileName = "items/itemsInit.txt";
    private URL resource = getClass().getClassLoader().getResource(itemsFileName);
    private File itemsFile;
    {
        try {
            itemsFile = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
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

    private void getItems() throws IOException {
        FileReader fileReader = new FileReader(itemsFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            switch (arrLine[0]) {
                case "key":
                    items.put(arrLine[0], new Key(arrLine[0], new Image("items/key.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                            Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                    break;
                case "sword":
                    items.put(arrLine[0], new Sword(arrLine[0], new Image("items/sword.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                            Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                    break;
                case "chest":
                    items.put(arrLine[0], new Chest(arrLine[0], new Image("items/chest.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                            Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                    break;
                case "shield":
                    items.put(arrLine[0], new Shield(arrLine[0], new Image("items/shield.png"), Integer.parseInt(arrLine[2]) * gamePane.getTileSize(),
                            Integer.parseInt(arrLine[1]) * gamePane.getTileSize(), Boolean.parseBoolean(arrLine[3])));
                    break;
            }
        }
        Chest chest = (Chest) items.get("chest");
        for (String i : items.keySet()) {
            if (items.get(i).getInsideChest()) {
                chest.inside.add(items.get(i));
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (String i : items.keySet()) {
            if (items.get(i).getWorldX() >= 0 && items.get(i).getWorldY() >= 0) {
                // set where we should print item regarding the hero
                int screenX = items.get(i).getWorldX() - gamePane.player.getWorldX() + gamePane.player.screenX;
                int screenY = items.get(i).getWorldY() - gamePane.player.getWorldY() + gamePane.player.screenY;
                // print only items which are on the screen
                if ((screenX + gamePane.getTileSize() >= 0 && screenX - gamePane.getTileSize() <= gamePane.getScreenWidth()) &&
                        (screenY + gamePane.getTileSize() >= 0 && screenY - gamePane.getTileSize() <= gamePane.getScreenHeight())) {
                    if (!items.get(i).getIsTaken()) {
                        if (i.equals("chest") && items.get(i).getIsOpened()) {
                            items.get(i).setImage(new Image("items/chest_opened.png"));
                        }
                        gc.drawImage(items.get(i).getImage(), screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize());
                    }
                }
            }
        }
    }
}

package items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Controller;
import data.FilesModel;
import data.GameModel;

import java.io.*;
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> list = new ArrayList<>();
    public ArrayList<Item> getInventory() {return list;}

    public void setInventory(ArrayList<Item> list) {
        this.list = list;
    }

    private int maxListSize = 15;
    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }

    GameModel gameModel = new GameModel();

    Controller controller;
    private Boolean inventoryIsFull = false;
    private int borderWidth = 5;
    private int fontSize = 18;
    private String title = "My Inventory";
    private Font titleFont = new Font("Tiempos Text", fontSize);

    public Inventory(Controller controller) {
        this.controller = controller;
    }

    /**
     * Set inventory from the file
     * @throws IOException
     */
    public void setInventory() throws IOException {
        FileReader fileReader = new FileReader(FilesModel.getInventoryFile());
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            switch (arrLine[0]) {
                case "key":
                    list.add(new Key(arrLine[0], new Image("items/key.png"), null, null, false));
                    break;
                case "sword":
                    list.add(new Sword(arrLine[0], new Image("items/sword.png"),null, null, false));
                    break;
                case "shield":
                    list.add(new Shield(arrLine[0], new Image("items/shield.png"),null, null, false));
                    break;
            }
        }
        reader.close();
    }

    /**
     * Draws inventory menu on the canvas
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        if (controller.keyHandler.inventoryPressed) {
            gc.setFill(Color.rgb(20, 20, 20, 0.7));
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(borderWidth);
            gc.fillRect(5, 5, gameModel.getTileSize() * 5 + borderWidth, gameModel.getTileSize() * 3 + borderWidth + fontSize);
            gc.strokeRect(5, 5, gameModel.getTileSize() * 5 + borderWidth, gameModel.getTileSize() * 3 + borderWidth + fontSize);

            gc.setFont(titleFont);
            gc.setFill(Color.WHITE);
            gc.fillText(title, 75, 23);
            int row = 0;
            int col = 0;
            int i = 0;
            while (i < list.size()){
                int inventoryScreenX = col * gameModel.getTileSize() + borderWidth * 2;
                int inventoryScreenY = row * gameModel.getTileSize() + borderWidth * 2 + fontSize;
                gc.drawImage(list.get(i).getImage(), inventoryScreenX, inventoryScreenY, gameModel.getTileSize(), gameModel.getTileSize());
                col++;
                i++;
                if (col == 5) {
                    row++;
                    col = 0;
                }
            }
        }
    }

    /**
     * Check if sword is in the inventory
     * @return
     */
    public Boolean checkSwordInList(){
        for (Item item : list){
            if (item instanceof Sword) return true;
        }
        return false;
    }

    /**
     * Check if key is in the inventory
     * @return
     */
    public Boolean checkKeyInList(){
        for (Item item : list){
            if (item instanceof Key) return true;
        }
        return false;
    }

    /**
     * Check if shield is in the inventory
     * @return
     */
    public Boolean checkShieldInList(){
        for (Item item : list){
            if (item instanceof Shield) return true;
        }
        return false;
    }

    /**
     * Save all items from inventory to the file
     * @throws IOException
     */
    public void saveInventory() throws IOException {
        FileWriter fileWriter = new FileWriter(FilesModel.getInventoryFile(), false);
        for (Item item : list) {
            if (item instanceof Key) {
                fileWriter.write("key \n");
            } else if (item instanceof Sword) {
                fileWriter.write("sword \n");
            } else if (item instanceof Shield) {
                fileWriter.write("shield \n");
            }
        }
        fileWriter.close();
    }

    /**
     * Delete all items from the inventory
     * and set them again
     * @throws IOException
     */
    public void resetInventory() throws IOException {
        list.clear();
        setInventory();
    }
}

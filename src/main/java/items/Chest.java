package items;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Item {
    private List<Item> itemsInside = new ArrayList<>();
    public boolean isOpened = false;

    public Chest(String name, Image image, int worldX, int worldY, boolean insideChest){
        setName(name);
        setImage(image);
        setWorldX(worldX);
        setWorldY(worldY);
        setInsideChest(insideChest);
    }

    public Chest() {}
    public boolean getIsOpened(){
        return isOpened;
    }

    public void setIsOpened(boolean value){
        isOpened = value;
    }

    public List<Item> getItemsInside() {
        return itemsInside;
    }

    public void setItemsInside(List<Item> itemsInside) {
        this.itemsInside = itemsInside;
    }
}

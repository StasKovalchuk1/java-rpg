import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Item{
    public List<Item> inside = new ArrayList<>();
    public boolean isOpened = false;

    public Chest(String name, Image image, int worldX, int worldY, boolean insideChest){
        this.name = name;
        this.image = image;
        this.worldX = worldX;
        this.worldY = worldY;
        this.insideChest = insideChest;
    }
    public boolean getIsOpened(){
        return isOpened;
    }

    public void setIsOpened(boolean value){
        isOpened = value;
    }

//    public List<Item> getList(){
//        return inside;
//    }
//    public void addItem(Item item){
//        inside.add(item);
//    }
//    public void removeItem(Item item){
//        inside.remove(item);
//    }

}

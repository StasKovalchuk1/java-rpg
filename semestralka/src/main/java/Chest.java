import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Item{
    public List<Item> inside = new ArrayList<>();

    public Chest(String name, Image image, int worldX, int worldY, boolean insideChest){
        this.name = name;
        this.image = image;
        this.worldX = worldX;
        this.worldY = worldY;
        this.insideChest = insideChest;
    }

}

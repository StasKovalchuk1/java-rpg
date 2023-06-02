package items;

import javafx.scene.image.Image;

public class Shield extends Item {
    public Shield(String name, Image image, Integer worldX, Integer worldY, boolean insideChest){
        setName(name);
        setImage(image);
        if (worldX != null) setWorldX(worldX);
        if (worldY != null) setWorldY(worldY);
        setInsideChest(insideChest);
    }
}

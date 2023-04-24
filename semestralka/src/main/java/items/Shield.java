package items;

import javafx.scene.image.Image;

public class Shield extends Item {
    public boolean block = false;
    public Shield(String name, Image image, int worldX, int worldY, boolean insideChest){
        setName(name);
        setImage(image);
        setWorldX(worldX);
        setWorldY(worldY);
        setInsideChest(insideChest);
    }
}

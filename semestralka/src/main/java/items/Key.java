package items;


import javafx.scene.image.Image;

public class Key extends Item {
    public Key(String name, Image image, int worldX, int worldY, boolean insideChest){
        setName(name);
        setImage(image);
        setWorldX(worldX);
        setWorldY(worldY);
        setInsideChest(insideChest);
    }
}

package items;

import javafx.scene.image.Image;

public class Sword extends Item {

    public int damage = 10;

    public Sword(String name, Image image, int worldX, int worldY, boolean insideChest){
        setName(name);
        setImage(image);
        setWorldX(worldX);
        setWorldY(worldY);
        setInsideChest(insideChest);
    }
}

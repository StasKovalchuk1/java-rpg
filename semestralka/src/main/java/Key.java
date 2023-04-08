import javafx.scene.image.Image;

public class Key extends Item{
    public Key(String name, Image image, int worldX, int worldY, boolean insideChest){
        this.name = name;
        this.image = image;
        this.worldX = worldX;
        this.worldY = worldY;
        this.insideChest = insideChest;
    }
}

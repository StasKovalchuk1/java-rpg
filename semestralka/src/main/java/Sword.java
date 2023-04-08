import javafx.scene.image.Image;

public class Sword extends Item{

    public int damage = 10;

    public Sword(String name, Image image, int worldX, int worldY, boolean insideChest){
        this.name = name;
        this.image = image;
        this.worldX = worldX;
        this.worldY = worldY;
        this.insideChest = insideChest;
    }
}

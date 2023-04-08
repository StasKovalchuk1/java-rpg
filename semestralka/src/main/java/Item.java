import javafx.scene.image.Image;

public abstract class Item {
    public Image image;
    public String name;
    public boolean isTaken = false;
    public boolean insideChest;
    public Item needFor;
    public int worldX, worldY;

}

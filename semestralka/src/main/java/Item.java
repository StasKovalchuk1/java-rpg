import javafx.scene.image.Image;

import java.util.List;

public abstract class Item {


    private boolean isOpened;

    public Image image;
    public String name;

    public boolean isTaken = false;
    public boolean insideChest;
    public int worldX, worldY;

    public boolean getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(boolean value) {
        isOpened = value;
    }


}

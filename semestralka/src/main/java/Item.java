import javafx.scene.image.Image;

import java.util.List;

public abstract class Item {
//    public abstract Image getImage();
//    public abstract void setImage(Image image);
//
//    public abstract String getName();
//    public abstract void setName(String name);
//
//    public abstract boolean getIsTaken();
//    public abstract void setIsTaken(boolean isTaken);
//    public abstract int getWorldX();
//    public abstract void setWorldX(int worldX);
//
//    public abstract int getWorldY();
//    public abstract void setWorldY(int worldY);

    private boolean isOpened;
//    private List<Item> inside;
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

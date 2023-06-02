package items;

import javafx.scene.image.Image;

public abstract class Item {
    private boolean isOpened;
    private Image image;
    private String name;
    private boolean isTaken = false;
    private boolean insideChest;
    private Integer worldX, worldY;
    public int getWorldY(){return worldY;}
    public void setWorldY(int value){worldY = value;}
    public int getWorldX(){return worldX;}
    public void setWorldX(int value){worldX = value;}
    public String getName(){return name;}
    public void setName(String value){name = value;}
    public Image getImage(){return image;}
    public void setImage(Image value){image = value;}
    public boolean getInsideChest(){return insideChest;}
    public void setInsideChest(boolean value){insideChest = value;}
    public boolean getIsTaken(){return isTaken;}
    public void setIsTaken(boolean value){isTaken = value;}
    public boolean getIsOpened() {
        return isOpened;
    }
    public void setIsOpened(boolean value) {
        isOpened = value;
    }

}

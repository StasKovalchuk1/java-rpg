package items;

import main.*;
import entities.*;
import tiles.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Item {
    public List<Item> inside = new ArrayList<>();
    public boolean isOpened = false;

    public Chest(String name, Image image, int worldX, int worldY, boolean insideChest){
        setName(name);
        setImage(image);
        setWorldX(worldX);
        setWorldY(worldY);
        setInsideChest(insideChest);
    }
    public boolean getIsOpened(){
        return isOpened;
    }

    public void setIsOpened(boolean value){
        isOpened = value;
    }

}

package data;

import main.MyLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilesModel {

    public FilesModel() {

    }

    private static final String itemsFileName = "gamedata/itemsInit.txt";
    private static final String tileNumFile = "gamedata/tileNum.txt";
    private static final String mapFileName = "gamedata/map.txt";
    private static final String entitiesFileName = "gamedata/entitieslist.txt";
    private static final String inventoryFileName = "gamedata/inventory.txt";
    private static File itemsFile;
    private static File tilesFile;
    private static File mapFile;
    private static File entitiesFile;
    private static File inventoryFile;

    private static final String defaultEntities = "hero 24 24 5\n" +
            "boss 5 45 5\n" +
            "ork 42 42 3\n" +
            "ork 42 3 3";

    private static final String defaultItems = "chest 42 40 false\n" +
            "shield -1 -24 true\n" +
            "key 8 8 false\n" +
            "sword 42 2 false";

    private static final String defaultInventory = "";

//    private static final String

    public static void setInventoryFile() {
        try {
            inventoryFile = new File(inventoryFileName);
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    public static void setEntitiesFile() {
        try {
            entitiesFile = new File(entitiesFileName);
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }


    public static void setItemsFile() {
        try {
            itemsFile = new File(itemsFileName);
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    public static void setTilesFile(){
        try {
            tilesFile = new File(tileNumFile);
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    public static void setMapFile(){
        try {
            mapFile = new File(mapFileName);
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception ::" + e);
        }
    }

    public static File getItemsFile() {
        setItemsFile();
        return itemsFile;
    }

    public static File getTilesFile() {
        setTilesFile();
        return tilesFile;
    }

    public static File getMapFile() {
        setMapFile();
        return mapFile;
    }

    public static File getEntitiesFile() {
        setEntitiesFile();
        return entitiesFile;
    }

    public static File getInventoryFile() {
        setInventoryFile();
        return inventoryFile;
    }

    public static void setDefaultData() throws IOException {
        FileWriter fileWriter1 = new FileWriter(FilesModel.getEntitiesFile(), false);
        FileWriter fileWriter2 = new FileWriter(FilesModel.getItemsFile(), false);
        FileWriter fileWriter3 = new FileWriter(FilesModel.getInventoryFile(), false);
        fileWriter1.write(defaultEntities);
        fileWriter2.write(defaultItems);
        fileWriter3.write(defaultInventory);
        fileWriter1.close();
        fileWriter2.close();
        fileWriter3.close();
    }
}

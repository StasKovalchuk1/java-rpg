package main;

import java.io.File;
import java.net.URL;

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
}

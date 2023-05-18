package main;

import org.json.JSONObject;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class JsonParser {

    private String name;
    private int row;
    private int column;
    private boolean insideChest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isInsideChest() {
        return insideChest;
    }

    public void setInsideChest(boolean insideChest) {
        this.insideChest = insideChest;
    }

    public static String getJSONFromFile(File file){
        String jsonText = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null){
                jsonText += line + "\n";
            }

            reader.close();
        } catch (Exception e) {
            MyLogger.getMyLogger().severe("Exception :: " + e);
        }
        return jsonText;
    }

    public void readJSONEntities(File file){
        String strJson = getJSONFromFile(file);

//        try {
//            JsonParser parser = new JsonParser();
//            JSONObject json = (JSONObject) parser.parse(strJson);
//        }
//        catch ()
    }

}

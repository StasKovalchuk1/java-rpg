import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory {

    public ArrayList<Item> list = new ArrayList<>();
    GamePane gamePane;

    int borderWidth = 5;
    int fontSize = 18;
    String inventoryFileName = "hero1/inventory.txt";
    File inventoryFile = new File(getClass().getResource(inventoryFileName).getFile());
    String title = "My Inventory";
    Font titleFont = new Font("Tiempos Text", fontSize);

    public Inventory(GamePane gamePane) {
        this.gamePane = gamePane;
    }

    public void setInventory() throws IOException {
        FileReader fileReader = new FileReader(inventoryFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            switch (arrLine[1]) {
                case "key":
                    list.add(new Key(arrLine[0], new Image("items/key.png"),
                            Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, false));
                    break;
                case "sword":
                    list.add(new Sword(arrLine[0], new Image("items/sword.png"),
                            Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, false));
                    break;
                case "shield":
                    list.add(new Shield(arrLine[0], new Image("items/shield.png"),
                            Integer.parseInt(arrLine[2]) * gamePane.tileSize,
                            Integer.parseInt(arrLine[1]) * gamePane.tileSize, false));
                    break;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        if (gamePane.keyHandler.inventoryPressed) {
            gc.setFill(Color.rgb(20, 20, 20, 0.7));
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(borderWidth);
            gc.fillRect(5, 5, gamePane.tileSize * 5 + borderWidth, gamePane.tileSize * 3 + borderWidth + fontSize);
            gc.strokeRect(5, 5, gamePane.tileSize * 5 + borderWidth, gamePane.tileSize * 3 + borderWidth + fontSize);

            gc.setFont(titleFont);
            gc.setFill(Color.WHITE);
            gc.fillText(title, 75, 23);
            int row = 0;
            int col = 0;
            for (int i = 0; i < list.size(); i++) {
                int inventoryScreenX = col * gamePane.tileSize + borderWidth * 2;
                int inventoryScreenY = row * gamePane.tileSize + borderWidth * 2 + fontSize;
                gc.drawImage(list.get(i).image, inventoryScreenX, inventoryScreenY, gamePane.tileSize, gamePane.tileSize);
                col++;
                if (col == 5) {
                    row++;
                    col = 0;
                }
            }
        }
    }
}

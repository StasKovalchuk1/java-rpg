import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Player extends Entity{
    GamePane gamePane;
    KeyHandler keyHandler;
    Image playerImage;
    public final int screenX;
    public final int screenY;
    public String inventoryFile = "/Users/stanislavkovalcuk/kovalst1/semestralka/src/main/resources/hero1/inventory.txt";
    public ArrayList<Item> inventory = new ArrayList<>();
    public Player(GamePane gamePane, KeyHandler keyHandler){
        this.gamePane = gamePane;
        this.keyHandler = keyHandler;
        screenX = gamePane.getScreenWidth() / 2 - (gamePane.tileSize / 2);
        screenY = gamePane.getScreenHeight() /2 - (gamePane.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
        rectangle = new Rectangle(worldX + 10, worldY + 8, 28, 32);
        try {
            setInventory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues(){
        worldX = gamePane.tileSize * 24;
        worldY = gamePane.tileSize * 24;
        speed = 8;
        direction = "DOWN";
    }

    public void getPlayerImage(){
        up1 = new Image("hero1/up1.png");
        up2 = new Image("hero1/up2.png");
        down1 = new Image("hero1/down1.png");
        down2 = new Image("hero1/down2.png");
        left1 = new Image("hero1/left1.png");
        left2 = new Image("hero1/left2.png");
        right1 = new Image("hero1/right1.png");
        right2 = new Image("hero1/right2.png");
    }

    public void setInventory() throws IOException {
        FileReader fileReader = new FileReader(inventoryFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            switch (arrLine[1]){
                case "key":
                    inventory.add(new Key(arrLine[1], new Image(arrLine[2]),
                            Integer.parseInt(arrLine[4]), Integer.parseInt(arrLine[5]), false));
                    break;
                case "sword":
                    inventory.add(new Sword(arrLine[1], new Image(arrLine[2]),
                            Integer.parseInt(arrLine[4]), Integer.parseInt(arrLine[5]), false));
                    break;
                case "shield":
                    inventory.add(new Shield(arrLine[1], new Image(arrLine[2]),
                            Integer.parseInt(arrLine[4]), Integer.parseInt(arrLine[5]), false));
                    break;
            }
        }
    }


    public void update(){
        prevX = worldX;
        prevY = worldY;
        rectangle.setX(worldX + 8);
        rectangle.setY(worldY + 8);
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed){
            collisionOn = false;
            gamePane.collisionCheck.check(this);
            gamePane.collisionCheck.checkItem(this);
                if (keyHandler.upPressed){
                    if (!collisionOn && worldY - speed >= 0){
                        worldY -= speed;
                    }
                    direction = "UP";
                } else if (keyHandler.downPressed){
                    if (!collisionOn && worldY + speed <= gamePane.worldHeight){
                        worldY += speed;
                    }
                    direction = "DOWN";
                } else if (keyHandler.leftPressed){
                    if (!collisionOn && worldX - speed >= 0){
                        worldX -= speed;
                    }
                    direction = "LEFT";
                } else if (keyHandler.rightPressed){
                    if (!collisionOn && worldX + speed <= gamePane.worldWidth){
                        worldX += speed;
                    }
                    direction = "RIGHT";
                }

                spriteCounter++;
                if (spriteCounter > 10) {
                    if (spriteNum == 1){
                        spriteNum = 2;
                    } else {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
    }

    public void draw(GraphicsContext gc){
        switch (direction) {
            case "UP":
                if (spriteNum == 1) {
                    playerImage = up1;
                }
                if (spriteNum == 2) {
                    playerImage = up2;
                }
                break;
            case "DOWN":
                if (spriteNum == 1) {
                    playerImage = down1;
                }
                if (spriteNum == 2) {
                    playerImage = down2;
                }
                break;
            case "LEFT":
                if (spriteNum == 1) {
                    playerImage = left1;
                }
                if (spriteNum == 2) {
                    playerImage = left2;
                }
                break;
            case "RIGHT":
                if (spriteNum == 1) {
                    playerImage = right1;
                }
                if (spriteNum == 2) {
                    playerImage = right2;
                }
                break;
        }

        drawInventory(gc);
        gc.drawImage(playerImage, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
    }

    public void drawInventory(GraphicsContext gc){
        if (keyHandler.inventoryPressed) {
            gc.setFill(Color.rgb(20,20, 20, 0.7));
            gc.fillRect(0, 0, gamePane.tileSize * 5, gamePane.tileSize * 3);
            int row = 0;
            int col = 0;
            for (int i = 0; i < inventory.size(); i++){
                int inventoryScreenX = col * gamePane.tileSize;
                int inventoryScreenY = row * gamePane.tileSize;
                gc.drawImage(inventory.get(i).image, inventoryScreenX, inventoryScreenY, gamePane.tileSize, gamePane.tileSize);
                col++;
                if (col == 5) {
                    row++;
                    col = 0;
                }
            }
        }
    }
}

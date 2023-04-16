import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Player extends Entity {
    GamePane gamePane;
    KeyHandler keyHandler;
    Image playerImage;
    public final int screenX;
    public final int screenY;

    public Player(GamePane gamePane, KeyHandler keyHandler) {
        this.gamePane = gamePane;
        this.keyHandler = keyHandler;
        screenX = gamePane.screenWidth / 2 - (gamePane.tileSize / 2);
        screenY = gamePane.screenHeight / 2 - (gamePane.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
        rectangle = new Rectangle(worldX + 8, worldY + 16, 32, 32);
        try {
            gamePane.inventory.setInventory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDefaultValues() {
        worldX = gamePane.tileSize * 24;
        worldY = gamePane.tileSize * 24;
        speed = 8;
        direction = "DOWN";
    }

    private void getPlayerImage() {
        up1 = new Image("hero1/up1.png");
        up2 = new Image("hero1/up2.png");
        down1 = new Image("hero1/down1.png");
        down2 = new Image("hero1/down2.png");
        left1 = new Image("hero1/left1.png");
        left2 = new Image("hero1/left2.png");
        right1 = new Image("hero1/right1.png");
        right2 = new Image("hero1/right2.png");
    }

    @Override
    public void update() {
        rectangle.setX(worldX + 8);
        rectangle.setY(worldY + 8);
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed
                || keyHandler.rightPressed) {
            collisionOn = false;
            gamePane.collisionCheck.check(this);
            gamePane.collisionCheck.checkItem(this);
            if (keyHandler.upPressed) {
                if (!collisionOn && worldY - speed >= 0) {
                    worldY -= speed;
                }
                direction = "UP";
            } else if (keyHandler.downPressed) {
                if (!collisionOn && worldY + speed <= gamePane.worldHeight) {
                    worldY += speed;
                }
                direction = "DOWN";
            } else if (keyHandler.leftPressed) {
                if (!collisionOn && worldX - speed >= 0) {
                    worldX -= speed;
                }
                direction = "LEFT";
            } else if (keyHandler.rightPressed) {
                if (!collisionOn && worldX + speed <= gamePane.worldWidth) {
                    worldX += speed;
                }
                direction = "RIGHT";
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (keyHandler.chestPressed) {
            gamePane.collisionCheck.checkChest(this);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
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
        gc.drawImage(playerImage, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
    }

    @Override
    public void attack() {
        
    }

    @Override
    public void defend() {

    }
}

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Player extends Entity{
    GamePane gamePane;
    KeyHandler keyHandler;
    Image playerImage;
    public final int screenX;
    public final int screenY;
    public Player(GamePane gamePane, KeyHandler keyHandler){
        this.gamePane = gamePane;
        this.keyHandler = keyHandler;
        screenX = gamePane.getScreenWidth() / 2 - (gamePane.tileSize / 2);
        screenY = gamePane.getScreenHeight() /2 - (gamePane.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
        rectangle = new Rectangle(worldX + 8, worldY + 16, 32, 32);// WTF???
    }

    public void setDefaultValues(){
        worldX = gamePane.tileSize * 24;
        worldY = gamePane.tileSize * 24;
        speed = 4;
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

    public void update(){
        prevX = worldX;
        prevY = worldY;
        rectangle.setX(worldX + 8);
        rectangle.setY(worldY + 16);
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed){
            collisionOn = false;
            gamePane.collisionCheck.check(this);
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

        gc.drawImage(playerImage, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
    }
}

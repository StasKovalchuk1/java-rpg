import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePane gamePane;
    KeyHandler keyHandler;

    Image playerImage;

    public Player(GamePane gamePane, KeyHandler keyHandler){
        this.gamePane = gamePane;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){

        x = 100;
        y = 100;
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
        prevX = x;
        prevY = y;
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed){
            if (keyHandler.upPressed == true){
                y -= speed;
                direction = "UP";
            } else if (keyHandler.downPressed == true){
                y += speed;
                direction = "DOWN";
            } else if (keyHandler.leftPressed == true){
                x -= speed;
                direction = "LEFT";
            } else if (keyHandler.rightPressed == true){
                x += speed;
                direction = "RIGHT";
            }
            spriteCounter++;
            if (spriteCounter > 10d) {
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

        gc.drawImage(playerImage, x, y, gamePane.tileSize, gamePane.tileSize);
    }
}

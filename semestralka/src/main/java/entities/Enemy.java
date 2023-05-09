package entities;

import items.Chest;
import javafx.scene.shape.Rectangle;
import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class Enemy extends Entity {
    GamePane gamePane;
    Image enemyImage;
    int radius;

    int walkingCounter = 0;

    public Enemy(GamePane gamePane, int row, int col) {
        this.gamePane = gamePane;
        setDefaultValues(row, col);
        getEnemyImage();
        rectangle = new Rectangle(worldX + 8, worldY + 16, 32, 32);
    }

    /**
     * Loads enemy images from resources
     */
    private void getEnemyImage() {
        up1 = new Image("enemy/up_1.png");
        up2 = new Image("enemy/up_2.png");
        down1 = new Image("enemy/down_1.png");
        down2 = new Image("enemy/down_2.png");
        left1 = new Image("enemy/left_1.png");
        left2 = new Image("enemy/left_2.png");
        right1 = new Image("enemy/right_1.png");
        right2 = new Image("enemy/right_2.png");
    }

    /**
     * Set the values of the enemy
     * @param row The row where enemy will be placed
     * @param col The column where enemy will be placed
     */
    private void setDefaultValues(int row, int col) {
        worldX = col * (gamePane.getTileSize());
        worldY = row * (gamePane.getTileSize());
        speed = 1;
        direction = "RIGHT";
        lives = 5;
        radius = 2 * gamePane.getTileSize();
    }

    @Override
    public void decreaseLives() {
        lives--;
        System.out.println(lives);
    }

    @Override
    public void increaseLives() {
        lives++;
    }

    /**
     * Update all enemy values
     */
    @Override
    public void update() {
        collisionOn = false;
        rectangle.setX(worldX);
        rectangle.setY(worldY);
        gamePane.collisionCheck.checkTile(this);
        gamePane.collisionCheck.checkEntity(this, gamePane.player);
            walking();

            switch (direction){
                case "UP":
                    if (!collisionOn){
                        worldY -= speed;
                        radius -= speed;
                    }
                    break;
                case "DOWN":
                    if (!collisionOn){
                        worldY += speed;
                        radius -= speed;
                    }
                    break;
                case "LEFT":
                    if (!collisionOn){
                    worldX -= speed;
                    radius -= speed;
                    }
                    break;
                case "RIGHT":
                    if (!collisionOn){
                        worldX += speed;
                        radius -= speed;
                    }
                    break;
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

    /**
     * Set enemy walking sprites
     */
    public void walking() {
        walkingCounter++;
        if (walkingCounter == 80){
            String[] dir = new String[] {"UP", "DOWN", "LEFT", "RIGHT"};
            Random random = new Random();
            direction = dir[random.nextInt(dir.length)];
            walkingCounter = 0;
        }
        switch (direction) {
            case "UP":
                if (spriteNum == 1) {
                    enemyImage = up1;
                } else enemyImage = up2;
                break;
            case "DOWN":
                if (spriteNum == 1) {
                    enemyImage = down1;
                } else enemyImage = down2;
                break;
            case "LEFT":
                if (spriteNum == 1) {
                    enemyImage = left1;
                } else enemyImage = left2;
                break;
            case "RIGHT":
                if (spriteNum == 1) {
                    enemyImage = right1;
                } else enemyImage = right2;
                break;
        }

    }

    /**
     * Draws enemy on the canvas
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc){
        if (isAlive){
            int screenX = worldX - gamePane.player.worldX + gamePane.player.screenX;
            int screenY = worldY - gamePane.player.worldY + gamePane.player.screenY;
            if ((screenX + gamePane.getTileSize() >= 0 && screenX - gamePane.getTileSize() <= gamePane.getScreenWidth()) &&
                    (screenY + gamePane.getTileSize() >= 0 && screenY - gamePane.getTileSize() <= gamePane.getScreenHeight())){
                gc.drawImage(enemyImage, screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize());
            }
        }
    }

    @Override
    public void attack(GraphicsContext gc) {

    }
    /**
     * Decrease lives when enemy gets hit
     */
    @Override
    public void getHitProcess() {
        increaseHitCounter();
        if (getHitCounter() > 8){
            decreaseLives();
            setHitCounter(0);
            if (lives <= 0) isAlive = false;
        }
    }

    @Override
    public void defend() {

    }
}

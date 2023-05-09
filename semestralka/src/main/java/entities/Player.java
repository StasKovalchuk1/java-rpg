package entities;

import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Player extends Entity {
    private GamePane gamePane;
    private KeyHandler keyHandler;
    private Image playerImage;
    public final int screenX;
    public final int screenY;
    private String heroFileName = "hero/hero.txt";
    private URL resourceToEnemies = getClass().getClassLoader().getResource(heroFileName);
    private File heroFile;
    {
        try {
            heroFile = new File(resourceToEnemies.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Player(GamePane gamePane, KeyHandler keyHandler) throws IOException {
        this.gamePane = gamePane;
        this.keyHandler = keyHandler;
        screenX = gamePane.getScreenWidth() / 2 - (gamePane.getTileSize() / 2);
        screenY = gamePane.getScreenHeight() / 2 - (gamePane.getTileSize() / 2);
        setDefaultValues();
        getPlayerImage();
        rectangle = new Rectangle(worldX + 8, worldY + 16, 32, 30);
        try {
            gamePane.inventory.setInventory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Set the values of the hero
     */
    private void setDefaultValues() throws IOException {
        FileReader fileReader = new FileReader(heroFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            worldX = gamePane.getTileSize() * Integer.parseInt(arrLine[0]);
            worldY = gamePane.getTileSize() * Integer.parseInt(arrLine[0]);
        }
        speed = 5;
        direction = "DOWN";
        lives = 3;
    }

    /**
     * Loads player images from resources
     */
    private void getPlayerImage() {
        up1 = new Image("hero/up1.png");
        up2 = new Image("hero/up2.png");
        down1 = new Image("hero/down1.png");
        down2 = new Image("hero/down2.png");
        left1 = new Image("hero/left1.png");
        left2 = new Image("hero/left2.png");
        right1 = new Image("hero/right1.png");
        right2 = new Image("hero/right2.png");
        attackUp1 = new Image("hero/attack_up_1.png");
        attackUp2 = new Image("hero/attack_up_2.png");
        attackDown1 = new Image("hero/attack_down_1.png");
        attackDown2 = new Image("hero/attack_down_2.png");
        attackLeft1 = new Image("hero/attack_left_1.png");
        attackLeft2 = new Image("hero/attack_left_2.png");
        attackRight1 = new Image("hero/attack_right_1.png");
        attackRight2 = new Image("hero/attack_right_2.png");
    }

    /**
     * Update all player values
     */
    @Override
    public void update() {
        rectangle.setX(worldX + 8);
        rectangle.setY(worldY + 16);

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed
                || keyHandler.rightPressed) {
            collisionOn = false;
            gamePane.collisionCheck.checkTile(this);
            gamePane.collisionCheck.checkItem(this);
            for (Enemy enemy : gamePane.enemiesList.getEnemiesList()){
                gamePane.collisionCheck.checkEntity(this, enemy);
            }
            if (keyHandler.upPressed) {
                if (!collisionOn && worldY - speed >= 0) {
                    worldY -= speed;
                }
                direction = "UP";
            } else if (keyHandler.downPressed) {
                if (!collisionOn && worldY + speed <= gamePane.getWorldHeight()) {
                    worldY += speed;
                }
                direction = "DOWN";
            } else if (keyHandler.leftPressed) {
                if (!collisionOn && worldX - speed >= 0) {
                    worldX -= speed;
                }
                direction = "LEFT";
            } else if (keyHandler.rightPressed) {
                if (!collisionOn && worldX + speed <= gamePane.getWorldWidth()) {
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

        if (keyHandler.attackPressed && gamePane.inventory.checkSwordInList()) {
            isAttacking = true;
            for (Enemy enemy : gamePane.enemiesList.getEnemiesList()) {
                if (enemy.getIsAlive()){
                    gamePane.collisionCheck.checkHit(gamePane.player, enemy);
                }
            }
        }
    }

    /**
     * Draws hero on the canvas
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (!isAttacking) {
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
            gc.drawImage(playerImage, screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize());
        } else {
            attack(gc);
        }
    }

    /**
     * Draws attack on the canvas
     * @param gc
     */
    @Override
    public void attack(GraphicsContext gc) {
            switch (direction){
                case "UP":
                    if (attackNum == 1) {
                        playerImage = attackUp1;
                    }
                    if (attackNum == 2) {
                        playerImage = attackUp2;
                    }
                    break;
                case "DOWN":
                    if (attackNum == 1) {
                        playerImage = attackDown1;
                    }
                    if (attackNum == 2) {
                        playerImage = attackDown2;
                    }
                    break;
                case "LEFT":
                    if (attackNum == 1) {
                        playerImage = attackLeft1;
                    }
                    if (attackNum == 2) {
                        playerImage = attackLeft2;
                    }
                    break;
                case "RIGHT":
                    if (attackNum == 1) {
                        playerImage = attackRight1;
                    }
                    if (attackNum == 2) {
                        playerImage = attackRight2;
                    }
                    break;
            }
            attackingProcess();
            drawImageAttack(gc);
    }

    /**
     * Draws attacking sprites
     * @param gc
     */
    private void drawImageAttack(GraphicsContext gc){
        switch (direction){
            case "UP":
                gc.drawImage(playerImage, screenX, screenY - gamePane.getTileSize(), gamePane.getTileSize(), gamePane.getTileSize() * 2);
                break;
            case "DOWN":
                gc.drawImage(playerImage, screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize() * 2);
                break;
            case "LEFT":
                gc.drawImage(playerImage, screenX - gamePane.getTileSize(), screenY, gamePane.getTileSize() * 2, gamePane.getTileSize());
                break;
            case "RIGHT":
                gc.drawImage(playerImage, screenX, screenY, gamePane.getTileSize() * 2, gamePane.getTileSize());
                break;
        }
    }

    /**
     * Control that each attack takes 25 frames
     */
    public void attackingProcess(){
        attackCounter++;
        if (attackCounter < 10){
            attackNum = 1;
        } else if (attackCounter >= 10 && attackCounter < 20){
            attackNum = 2;
        } else if (attackCounter >= 20 && attackCounter < 25){
            attackNum = 1;
        } else if (attackCounter >= 25) {
            attackCounter = 0;
            isAttacking = false;
        }
    }

    /**
     * Decrease lives when hero gets hit
     */
    @Override
    public void getHitProcess() {
        increaseHitCounter();
        if (getHitCounter() > 10){
            decreaseLives();
            setHitCounter(0);
        }
    }

    @Override
    public void defend() {

    }

    @Override
    public void decreaseLives() {
        lives--;
    }

    @Override
    public void increaseLives() {
        lives++;
    }
}

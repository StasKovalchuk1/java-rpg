package entities;

import javafx.scene.paint.Color;
import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import model.FilesModel;

import java.io.*;

public class Player extends Entity {
    private KeyHandler keyHandler;

    private Controller controller;

    public Player(KeyHandler keyHandler){
        this.keyHandler = keyHandler;
    }

    public Player() {}

    public Player(Controller controller, KeyHandler keyHandler) throws IOException {
        this.controller = controller;
        this.keyHandler = keyHandler;
        setScreenX(gameModel.getScreenWidth() / 2 - (gameModel.getTileSize() / 2));
        setScreenY(gameModel.getScreenHeight() / 2 - (gameModel.getTileSize() / 2));
        setDefaultValues();
        getPlayerImage();
        setHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        setAttackHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        try {
            controller.inventory.setInventory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Set the values of the hero
     */
    private void setDefaultValues() throws IOException {
        FileReader fileReader = new FileReader(FilesModel.getEntitiesFile());
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            if (arrLine[0].equals("hero")){
                setWorldX(gameModel.getTileSize() * Integer.parseInt(arrLine[2]));
                setWorldY(gameModel.getTileSize() * Integer.parseInt(arrLine[1]));
                setLives(Integer.parseInt(arrLine[3]));
            }
        }
        reader.close();
        setSpeed(4);
        setDirection("DOWN");
    }

    /**
     * Loads player images from resources
     */
    private void getPlayerImage() {
        up1 = new Image("entity/hero/up1.png");
        up2 = new Image("entity/hero/up2.png");
        down1 = new Image("entity/hero/down1.png");
        down2 = new Image("entity/hero/down2.png");
        left1 = new Image("entity/hero/left1.png");
        left2 = new Image("entity/hero/left2.png");
        right1 = new Image("entity/hero/right1.png");
        right2 = new Image("entity/hero/right2.png");
        attackUp1 = new Image("entity/hero/attack_up_1.png");
        attackUp2 = new Image("entity/hero/attack_up_2.png");
        attackDown1 = new Image("entity/hero/attack_down_1.png");
        attackDown2 = new Image("entity/hero/attack_down_2.png");
        attackLeft1 = new Image("entity/hero/attack_left_1.png");
        attackLeft2 = new Image("entity/hero/attack_left_2.png");
        attackRight1 = new Image("entity/hero/attack_right_1.png");
        attackRight2 = new Image("entity/hero/attack_right_2.png");
        guardDown = new Image("entity/hero/guard_down_1.png");
        guardUp = new Image("entity/hero/guard_up_1.png");
        guardLeft = new Image("entity/hero/guard_left_1.png");
        guardRight = new Image("entity/hero/guard_right_1.png");
    }

    @Override
    public void update() {
        handleMoving();
        getHitbox().setX(getWorldX() + 8);
        getHitbox().setY(getWorldY() + 8);
        interactionWithObjects();
    }

    /**
     *
     */
    public void handleMoving(){
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed
                || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                setDirection("UP");
            } else if (keyHandler.downPressed) {
                setDirection("DOWN");
            } else if (keyHandler.leftPressed) {
                setDirection("LEFT");
            } else {
                setDirection("RIGHT");
            }

            checkCollisions();
            changeCoordinates();
            setSpritesNum();
        }
    }

    @Override
    public void checkCollisions(){
        if (controller != null){
            setCollisionOn(false);
            controller.collisionCheck.checkTile(this);
            controller.collisionCheck.checkItem(this);
            for (Entity entity : controller.enemiesList.getEnemiesList()){
                controller.collisionCheck.checkEntity(this, entity);
            }
        }
    }

    /**
     * Handle interaction with a key, sword and shield
     */
    public void interactionWithObjects() {
        if (controller != null){
            if (keyHandler.chestPressed) {
                controller.collisionCheck.checkChest(this);
            }
            if ((keyHandler.attackPressed && controller.inventory.checkSwordInList()) || isAttacking()) {
                setAttacking(true);
                getAttackHitbox().setX(getWorldX() + 8);
                getAttackHitbox().setY(getWorldY() + 8);
                changeAttackHitboxCoord();
                for (Enemy enemy: controller.enemiesList.getEnemiesList()) {
                    if (enemy.getAlive()){
                        controller.collisionCheck.checkHit(this, enemy);
                    }
                }
            }
            if (keyHandler.defendPressed && controller.inventory.checkShieldInList()) {
                setDefending(true);
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (getAlive()) {
            gc.setFill(Color.WHITE);
            gc.fillText("Lives : " + getLives(), getScreenX(), getScreenY() - 5);
            if (isAttacking()) {
                attack(gc, getScreenX(), getScreenY());
//                int x = (int) (getAttackHitbox().getX() - getGamePane().player.getWorldX() + getGamePane().player.getScreenX());
//                int y = (int) (getAttackHitbox().getY() - getGamePane().player.getWorldY() + getGamePane().player.getScreenY());
//                gc.setStroke(Color.WHITE);
//                gc.setLineWidth(2);
//                gc.fillRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
//                gc.strokeRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
            } else if (isDefending()){
                defend(gc);
            } else {
                switch (getDirection()) {
                    case "UP":
                        if (getSpriteNum() == 1) {
                            setEntityImage(up1);
                        }
                        if (getSpriteNum() == 2) {
                            setEntityImage(up2);
                        }
                        break;
                    case "DOWN":
                        if (getSpriteNum() == 1) {
                            setEntityImage(down1);
                        }
                        if (getSpriteNum() == 2) {
                            setEntityImage(down2);
                        }
                        break;
                    case "LEFT":
                        if (getSpriteNum() == 1) {
                            setEntityImage(left1);
                        }
                        if (getSpriteNum() == 2) {
                            setEntityImage(left2);
                        }
                        break;
                    case "RIGHT":
                        if (getSpriteNum() == 1) {
                            setEntityImage(right1);
                        }
                        if (getSpriteNum() == 2) {
                            setEntityImage(right2);
                        }
                        break;
                }
                gc.drawImage(getEntityImage(), getScreenX(), getScreenY(), gameModel.getTileSize(), gameModel.getTileSize());
            }
        }
    }

    public void savePlayer() throws IOException {
        FileWriter fileWriter = new FileWriter(FilesModel.getEntitiesFile(), false);
        fileWriter.write("hero " + getWorldY() / gameModel.getTileSize() + " " +
                getWorldX() / gameModel.getTileSize() +  " " + getLives() + "\n");
        fileWriter.close();
    }

    public void resetPlayer() throws IOException {
        setAlive(true);
        setDefaultValues();
        getPlayerImage();
        setHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        setAttackHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        try {
            controller.inventory.setInventory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


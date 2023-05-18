package entities;

import javafx.scene.paint.Color;
import main.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Player extends Entity {
    private final KeyHandler keyHandler;

    public Player(GamePane gamePane, KeyHandler keyHandler) throws IOException {
        setGamePane(gamePane);
        this.keyHandler = keyHandler;
        setScreenX(gamePane.getScreenWidth() / 2 - (gamePane.getTileSize() / 2));
        setScreenY(gamePane.getScreenHeight() / 2 - (gamePane.getTileSize() / 2));
        setDefaultValues();
        getPlayerImage();
        setHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        setAttackHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
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
        FileReader fileReader = new FileReader(FilesModel.getEntitiesFile());
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            if (arrLine[0].equals("hero")){
                setWorldX(getGamePane().getTileSize() * Integer.parseInt(arrLine[2]));
                setWorldY(getGamePane().getTileSize() * Integer.parseInt(arrLine[1]));
            }
        }
        reader.close();
        setSpeed(4);
        setDirection("DOWN");
        setLives(15);
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
    private void handleMoving(){
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
        setCollisionOn(false);
        getGamePane().collisionCheck.checkTile(this);
        getGamePane().collisionCheck.checkItem(this);
        for (Entity entity : getGamePane().enemiesList.getEnemiesList()){
            getGamePane().collisionCheck.checkEntity(this, entity);
        }
    }

    /**
     * Handle interaction with a key, sword and shield
     */
    public void interactionWithObjects() {
        if (keyHandler.chestPressed) {
            getGamePane().collisionCheck.checkChest(this);
        }
        if ((keyHandler.attackPressed && getGamePane().inventory.checkSwordInList()) || isAttacking()) {
            setAttacking(true);
            getAttackHitbox().setX(getWorldX() + 8);
            getAttackHitbox().setY(getWorldY() + 8);
            changeAttackHitboxCoord();
            for (Enemy enemy: getGamePane().enemiesList.getEnemiesList()) {
                if (enemy.getAlive()){
                    getGamePane().collisionCheck.checkHit(this, enemy);
                }
            }
        }
        if (keyHandler.defendPressed && getGamePane().inventory.checkShieldInList()) {
            setDefending(true);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (getAlive()) {
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
                gc.drawImage(getEntityImage(), getScreenX(), getScreenY(), getGamePane().getTileSize(), getGamePane().getTileSize());
            }
        }
    }

    public void savePlayer() throws IOException {
        FileWriter fileWriter = new FileWriter(FilesModel.getEntitiesFile(), false);
        fileWriter.write("hero " + getWorldY() / getGamePane().getTileSize() + " " + getWorldX() / getGamePane().getTileSize() + "\n");
        fileWriter.close();
    }
}

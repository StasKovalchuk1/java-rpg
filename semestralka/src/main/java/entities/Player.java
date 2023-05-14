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
    public final int screenX;
    public final int screenY;
    private final String heroFileName = "hero/hero.txt";
    private final URL resourceToEnemies = getClass().getClassLoader().getResource(heroFileName);
    private final File heroFile;
    {
        try {
            heroFile = new File(resourceToEnemies.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Player(GamePane gamePane, KeyHandler keyHandler) throws IOException {
        setGamePane(gamePane);
        this.keyHandler = keyHandler;
        screenX = gamePane.getScreenWidth() / 2 - (gamePane.getTileSize() / 2);
        screenY = gamePane.getScreenHeight() / 2 - (gamePane.getTileSize() / 2);
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
        FileReader fileReader = new FileReader(heroFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            setWorldX(getGamePane().getTileSize() * Integer.parseInt(arrLine[0]));
            setWorldY(getGamePane().getTileSize() * Integer.parseInt(arrLine[0]));
        }
        setSpeed(4);
        setDirection("DOWN");
        setLives(5);
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

    @Override
    public void update() {
        handleMoving();
        getHitbox().setX(getWorldX() + 8);
        getHitbox().setY(getWorldY() + 8);
        interactionWithObjects();
//        System.out.println(getHitbox().getX());
//        System.out.println(getHitbox().getY());
//        System.out.println("-----------------");
    }

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
        if (keyHandler.attackPressed && getGamePane().inventory.checkSwordInList()) {
            setAttacking(true);
            getAttackHitbox().setX(getWorldX() + 8);
            getAttackHitbox().setY(getWorldY() + 8);
            changeAttackHitboxCoord();
            for (Entity entity: getGamePane().enemiesList.getEnemiesList()) {
                if (entity.getAlive()){
                    getGamePane().collisionCheck.checkHit(this, entity);
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (getAlive()) {
            if (!getAttacking()) {
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
                gc.drawImage(getEntityImage(), screenX, screenY, getGamePane().getTileSize(), getGamePane().getTileSize());
            } else {
                attack(gc, screenX, screenY);
                int x = (int) (getAttackHitbox().getX() - getGamePane().player.getWorldX() + getGamePane().player.screenX);
                int y = (int) (getAttackHitbox().getY() - getGamePane().player.getWorldY() + getGamePane().player.screenY);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(2);
                gc.fillRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
                gc.strokeRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
            }
        }
    }

    @Override
    public void defend() {

    }
}
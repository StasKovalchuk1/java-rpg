package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePane;

public abstract class Entity {
    private GamePane gamePane;
    private int lives;
    private int worldX, worldY, screenX, screenY;
    private int heightInTiles, widthInTiles;
    private int speed;
    private Image entityImage;
    protected Image up1, up2, down1, down2, left1, left2, right1, right2,
            attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2,
            guardDown, guardUp, guardLeft, guardRight;
    private String direction;
    private int hitCounter = 0; /* count of frames when an entity gets hit */
    private int spriteCounter = 0; /* count of frames when an entity moves */
    private int spriteNum = 1; /* for every move exist 2 sprites, spriteNum defines which one should use */
    private int attackCounter = 0; /* count of frames when an entity attacks */
    private int attackNum = 1; /* for every attack move exist 2 sprites, attackNum defines which one should use */
    private int defendCounter = 0;
    private Rectangle hitbox;
    private Rectangle attackHitbox;
    private boolean collisionOn = false;

    public int getHeightInTiles() {
        return heightInTiles;
    }

    public void setHeightInTiles(int heightInTiles) {
        this.heightInTiles = heightInTiles;
    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public void setWidthInTiles(int widthInTiles) {
        this.widthInTiles = widthInTiles;
    }

    private boolean attacking = false;
    private boolean defending = false;
    private boolean alive = true;

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(int hitCounter) {
        this.hitCounter = hitCounter;
    }

    public void increaseHitCounter() {
        hitCounter++;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decreaseLives() {
        lives--;
        System.out.println(this + " " + lives);
    }

    public void increaseLives() {
        lives++;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public boolean getCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean value) {
        collisionOn = value;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public Image getEntityImage() {
        return entityImage;
    }

    public void setEntityImage(Image entityImage) {
        this.entityImage = entityImage;
    }

    public GamePane getGamePane() {
        return gamePane;
    }

    public void setGamePane(GamePane gamePane) {
        this.gamePane = gamePane;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public int getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(int attackNum) {
        this.attackNum = attackNum;
    }

    public int getAttackCounter() {
        return attackCounter;
    }

    public void setAttackCounter(int attackCounter) {
        this.attackCounter = attackCounter;
    }

    public void increaseAttackCounter() {
        attackCounter++;
    }

    public Rectangle getAttackHitbox() {
        return attackHitbox;
    }

    public void setAttackHitbox(Rectangle attackHitbox) {
        this.attackHitbox = attackHitbox;
    }

    public void setWidthAndHeight() {
        switch (getDirection()) {
            case "UP", "DOWN":
                if (this instanceof Ork || this instanceof Player) {
                    setWidthInTiles(1);
                    setHeightInTiles(2);
                } else if (this instanceof Boss) {
                    setWidthInTiles(2);
                    setHeightInTiles(4);
                }
                break;
            case "LEFT", "RIGHT":
                if (this instanceof Ork || this instanceof Player) {
                    setWidthInTiles(2);
                    setHeightInTiles(1);
                } else if (this instanceof Boss) {
                    setWidthInTiles(4);
                    setHeightInTiles(2);
                }
                break;
        }
    }

    /**
     * Update entity values
     */
    public abstract void update();

    /**
     * Draws entity on the canvas
     * @param gc GraphicsContext
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * Method for defending that sets the value of the sprite image,
     * starts the attack process and renders it
     *
     * @param gc GraphicsContext
     */
    public void defend(GraphicsContext gc) {
        switch (getDirection()) {
            case "UP":
                setEntityImage(guardUp);
                break;
            case "DOWN":
                setEntityImage(guardDown);
                break;
            case "LEFT":
                setEntityImage(guardLeft);
                break;
            case "RIGHT":
                setEntityImage(guardRight);
                break;
        }
        defendingProcess();
        drawImageDefend(gc);
    }

    /**
     * Control that each defend takes 35 frames
     */
    public void defendingProcess() {
        defendCounter++;
        if (defendCounter > 35) {
            defendCounter = 0;
            setDefending(false);
        }
    }

    /**
     * Check if entities stands opposite each other
     *
     * @param entity first entity
     * @param target second entity
     * @return the result of the check
     */
    public boolean checkOppositeDirection(Entity entity, Entity target) {
        switch (entity.getDirection()) {
            case "UP":
                if (target.getDirection().equals("DOWN")) return true;
                break;
            case "DOWN":
                if (target.getDirection().equals("UP")) return true;
                break;
            case "LEFT":
                if (target.getDirection().equals("RIGHT")) return true;
                break;
            case "RIGHT":
                if (target.getDirection().equals("LEFT")) return true;
                break;
        }
        return false;
    }

    /**
     * Draws defending sprites
     *
     * @param gc
     */
    public void drawImageDefend(GraphicsContext gc) {
        gc.drawImage(getEntityImage(), getScreenX(), getScreenY(), getGamePane().getTileSize(), getGamePane().getTileSize());
    }

    /**
     * Check if entity has collision
     */
    public abstract void checkCollisions();

    /**
     * Method for the attack that sets the value of the sprite image,
     * starts the attack process and renders it
     *
     * @param gc GraphicsContext
     */
    public void attack(GraphicsContext gc, int screenX, int screenY) {
        switch (getDirection()) {
            case "UP":
                if (getAttackNum() == 1) {
                    setEntityImage(attackUp1);
                }
                if (getAttackNum() == 2) {
                    setEntityImage(attackUp2);
                }
                break;
            case "DOWN":
                if (getAttackNum() == 1) {
                    setEntityImage(attackDown1);
                }
                if (getAttackNum() == 2) {
                    setEntityImage(attackDown2);
                }
                break;
            case "LEFT":
                if (getAttackNum() == 1) {
                    setEntityImage(attackLeft1);
                }
                if (getAttackNum() == 2) {
                    setEntityImage(attackLeft2);
                }
                break;
            case "RIGHT":
                if (getAttackNum() == 1) {
                    setEntityImage(attackRight1);
                }
                if (getAttackNum() == 2) {
                    setEntityImage(attackRight2);
                }
                break;
        }
        attackingProcess();
        drawImageAttack(gc, screenX, screenY);
    }

    /**
     * Draws attacking sprites
     * It also multiplies by 0.5 screenX and screenY
     * because attacking part is a half of sprite size
     * @param gc GraphicsContext
     */
    public void drawImageAttack(GraphicsContext gc, int screenX, int screenY) {
        setWidthAndHeight();
        switch (getDirection()) {
            case "UP":
                gc.drawImage(getEntityImage(),
                        screenX,
                        screenY - ((getGamePane().getTileSize() * getHeightInTiles()) * 0.5),
                        getGamePane().getTileSize() * getWidthInTiles(),
                        getGamePane().getTileSize() * getHeightInTiles());
                break;
            case "DOWN", "RIGHT":
                gc.drawImage(getEntityImage(),
                        screenX,
                        screenY,
                        getGamePane().getTileSize() * getWidthInTiles(),
                        getGamePane().getTileSize() * getHeightInTiles());
                break;
            case "LEFT":
                gc.drawImage(getEntityImage(),
                        screenX - ((getGamePane().getTileSize() * getWidthInTiles()) * 0.5),
                        screenY,
                        getGamePane().getTileSize() * getWidthInTiles(),
                        getGamePane().getTileSize() * getHeightInTiles());
                break;
        }
    }


    /**
     * Control that each attack takes 25 frames
     */
    public void attackingProcess() {
        attackCounter++;
        if (attackCounter < 15) {
            attackNum = 1;
        } else if (attackCounter >= 15 && attackCounter < 25) {
            attackNum = 2;
        } else if (attackCounter >= 25 && attackCounter < 35) {
            attackNum = 1;
        } else if (attackCounter >= 35) {
            attackCounter = 0;
            setAttacking(false);
        }
    }

    /**
     * Control sprite number
     */
    public void setSpritesNum() {
        spriteCounter++;
        if (spriteCounter > 10) {
            if (getSpriteNum() == 1) {
                setSpriteNum(2);
            } else {
                setSpriteNum(1);
            }
            spriteCounter = 0;
        }
    }

    /**
     * Change entity coordinates
     */
    public void changeCoordinates() {
        if (!collisionOn) {
            switch (direction) {
                case "UP":
                    if (worldY - speed >= 0) worldY -= speed;
                    break;
                case "DOWN":
                    if (worldY + speed <= gamePane.getWorldHeight()) worldY += speed;
                    break;
                case "LEFT":
                    if (worldX - speed >= 0) worldX -= speed;
                    break;
                case "RIGHT":
                    if (worldX + speed <= gamePane.getWorldWidth()) worldX += speed;
                    break;
            }
        }
    }

    /**
     * Control hit counter and decrease lives
     */
    public void getHitProcess() {
        increaseHitCounter();
        if (getHitCounter() == 10) {
            decreaseLives();
            if (getLives() <= 0) setAlive(false);
        }
        if (getHitCounter() == 35) {
            setHitCounter(0);
        }
    }

    /**
     * Change hitbox coordinates for attack
     * depending on direction
     */
    public void changeAttackHitboxCoord() {
        switch (getDirection()) {
            case "UP":
                getAttackHitbox().setY(getAttackHitbox().getY() - getAttackHitbox().getHeight());
                break;
            case "DOWN":
                getAttackHitbox().setY(getAttackHitbox().getY() + getAttackHitbox().getHeight());
                break;
            case "LEFT":
                getAttackHitbox().setX(getAttackHitbox().getX() - getAttackHitbox().getWidth());
                break;
            case "RIGHT":
                getAttackHitbox().setX(getAttackHitbox().getX() + getAttackHitbox().getWidth());
                break;
        }
    }
}

package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePane;

public abstract class Entity {
    private GamePane gamePane;
    private int lives;
    private int worldX, worldY;
    private int speed;
    private Image entityImage;
    protected Image up1, up2, down1, down2, left1, left2, right1, right2,
            attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2;
    private String direction;
    private int hitCounter = 0; /* count of frames when an entity gets hit */
    private int spriteCounter = 0; /* count of frames when an entity moves */
    private int spriteNum = 1; /* for every move exist 2 sprites, spriteNum defines which one should use */
    private int attackCounter = 0; /* count of frames when an entity attacks */
    private int attackNum = 1; /* for every attack move exist 2 sprites, attackNum defines which one should use */
    private Rectangle hitbox;
    private Rectangle attackHitbox;
    private boolean collisionOn = false;
    private boolean attacking = false;
    private boolean alive = true;
    public boolean getAlive() {return alive;}
    public void setAlive(boolean alive){this.alive = alive;}
    public int getHitCounter() {return hitCounter;}
    public void setHitCounter(int hitCounter) {this.hitCounter = hitCounter;}
    public void increaseHitCounter() {hitCounter++;}
    public boolean getAttacking() {return attacking;}
    public void setAttacking(boolean attacking) {this.attacking = attacking;}
    public int getLives() {return lives;}
    public void setLives(int lives) {this.lives = lives;}
    public void decreaseLives(){lives--;
        System.out.println(this + " " + lives);}
    public void increaseLives(){lives++;}
    public int getSpeed(){return speed;}
    public void setSpeed(int speed) {this.speed = speed;}
    public String getDirection(){return direction;}
    public void setDirection(String direction) {this.direction = direction;}
    public Rectangle getHitbox(){return hitbox;}
    public void setHitbox(Rectangle hitbox) {this.hitbox = hitbox;}
    public boolean getCollisionOn(){return collisionOn;}
    public void setCollisionOn(boolean value){collisionOn = value;}
    public int getWorldX(){return worldX;}
    public void setWorldX(int worldX) {this.worldX = worldX;}
    public void setWorldY(int worldY) {this.worldY = worldY;}
    public int getWorldY(){return worldY;}
    public Image getEntityImage() {return entityImage;}
    public void setEntityImage(Image entityImage) {this.entityImage = entityImage;}
    public GamePane getGamePane() {return gamePane;}
    public void setGamePane(GamePane gamePane) {this.gamePane = gamePane;}
    public int getSpriteNum() {return spriteNum;}
    public void setSpriteNum(int spriteNum) {this.spriteNum = spriteNum;}
    public int getAttackNum() {return attackNum;}
    public void setAttackNum(int attackNum) {this.attackNum = attackNum;}
    public int getAttackCounter() {return attackCounter;}
    public void setAttackCounter(int attackCounter) {this.attackCounter = attackCounter;}
    public void increaseAttackCounter(){attackCounter++;}
    public Rectangle getAttackHitbox() {return attackHitbox;}
    public void setAttackHitbox(Rectangle attackHitbox) {this.attackHitbox = attackHitbox;}

    /**
     * Update entity values
     */
    public abstract void update();

    /**
     * Draws entity on the canvas
     * @param gc
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * Defend...
     */
    public abstract void defend();

    /**
     * Check if entity has collision
     */
    public abstract void checkCollisions();

    /**
     * Draws attacking entity on the canvas
     * @param gc
     */
    public void attack(GraphicsContext gc, int screenX, int screenY){
        switch (getDirection()){
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
     * @param gc
     */
    private void drawImageAttack(GraphicsContext gc, int screenX, int screenY){
        switch (getDirection()){
            case "UP":
                gc.drawImage(getEntityImage(), screenX, screenY - getGamePane().getTileSize(), getGamePane().getTileSize(), getGamePane().getTileSize() * 2);
                break;
            case "DOWN":
                gc.drawImage(getEntityImage(), screenX, screenY, getGamePane().getTileSize(), getGamePane().getTileSize() * 2);
                break;
            case "LEFT":
                gc.drawImage(getEntityImage(), screenX - getGamePane().getTileSize(), screenY, getGamePane().getTileSize() * 2, getGamePane().getTileSize());
                break;
            case "RIGHT":
                gc.drawImage(getEntityImage(), screenX, screenY, getGamePane().getTileSize() * 2, getGamePane().getTileSize());
                break;
        }
    }

    /**
     * Control that each attack takes 25 frames
     */
    public void attackingProcess(){
        attackCounter++;
        if (attackCounter < 15){
            attackNum = 1;
        } else if (attackCounter >= 15 && attackCounter < 25){
            attackNum = 2;
        } else if (attackCounter >= 25 && attackCounter < 35){
            attackNum = 1;
        } else if (attackCounter >= 35) {
            attackCounter = 0;
            setAttacking(false);
        }
    }

    /**
     * Control sprite number
     */
    public void setSpritesNum(){
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
     * Change entity coordinates
     */
    public void changeCoordinates() {
        if (!collisionOn){
            switch (direction){
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
//        System.out.println(hitCounter);
        increaseHitCounter();
        if (getHitCounter() == 5){
            decreaseLives();
            if (getLives() <= 0) setAlive(false);
        }
        if (getHitCounter() == 40){
            setHitCounter(0);
        }
    }

    public void changeAttackHitboxCoord(){
        switch (getDirection()){
            case "UP":
                getAttackHitbox().setY(getAttackHitbox().getY() - getGamePane().getTileSize());
                break;
            case "DOWN":
                getAttackHitbox().setY(getAttackHitbox().getY() + getHitbox().getHeight());
                break;
            case "LEFT":
                getAttackHitbox().setX(getAttackHitbox().getX() - getGamePane().getTileSize());
                break;
            case "RIGHT":
                getAttackHitbox().setX(getAttackHitbox().getX() + getGamePane().getTileSize());
                break;
        }
    }
}

package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class Entity {
    protected int lives;
    protected int worldX, worldY;
    protected int speed;
    protected Image up1, up2, down1, down2, left1, left2, right1, right2,
            attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2;
    protected String direction;
    protected int hitCounter = 0;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    protected int attackCounter = 0;
    protected int attackNum = 1;
    protected Rectangle rectangle;
    protected boolean collisionOn = false;
    protected boolean isAttacking = false;
    protected boolean isAlive = true;
    public boolean getIsAlive() {return isAlive;}
    public int getHitCounter() {return hitCounter;}
    public void setHitCounter(int hitCounter) {this.hitCounter = hitCounter;}
    public void increaseHitCounter() {hitCounter++;}
    public boolean getIsAttacking() {return isAttacking;}
    public int getLives() {return lives;}
    public abstract void decreaseLives();
    public abstract void increaseLives();
    public int getSpeed(){return speed;}
    public String getDirection(){return direction;}
    public Rectangle getRectangle(){return rectangle;}
    public boolean getCollisionOn(){return collisionOn;}
    public void setCollisionOn(boolean value){collisionOn = value;}
    public int getWorldX(){return worldX;}
    public int getWorldY(){return worldY;}
    public abstract void update();
    public abstract void draw(GraphicsContext gc);
    public abstract void attack(GraphicsContext gc);
    public abstract void getHitProcess();
//    public abstract void attackUpdate();
    public abstract void defend();
//    public abstract void defendUpdate();

}

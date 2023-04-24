package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Entity {

    protected int worldX, worldY;
    protected int speed;
    protected Image up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    protected Rectangle rectangle;
    protected boolean collisionOn = false;
    public int getSpeed(){return speed;}
    public String getDirection(){return direction;}
    public Rectangle getRectangle(){return rectangle;}
    public boolean getCollisionOn(){return collisionOn;}
    public void setCollisionOn(boolean value){collisionOn = value;}
    public int getWorldX(){return worldX;}
    public int getWorldY(){return worldY;}
    public abstract void update();
    public abstract void draw(GraphicsContext gc);
    public abstract void attack();
    public abstract void defend();
}

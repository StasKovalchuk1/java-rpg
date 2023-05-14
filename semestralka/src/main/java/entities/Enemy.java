package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.MyLogger;

import java.util.Random;

public class Enemy extends Entity{

    Image enemyImage;
    int walkingCounter = 0;
    private int range;
    public int getRange(){return range;}
    public void setRange(int range){this.range = range;}
    private Rectangle visualRange;
    public Rectangle getVisualRange(){return visualRange;}
    public void setVisualRange(int range){
        visualRange = new Rectangle(getWorldX() - range, getWorldY() - range, range * 2 + getGamePane().getTileSize(), range * 2 + getGamePane().getTileSize());
    }

    @Override
    public void update() {
        walking();
//        checkCollisions();
        getHitbox().setX(getWorldX() + 8);
        getHitbox().setY(getWorldY() + 16);
        getVisualRange().setX(getWorldX() - range);
        getVisualRange().setY(getWorldY() - range);
    }

    /**
     * Set enemy walking sprites
     */
    public void walking() {
        walkingCounter++;
        if (walkingCounter == 80){
            String[] dir = new String[] {"UP", "DOWN", "LEFT", "RIGHT"};
            Random random = new Random();
            setDirection(dir[random.nextInt(dir.length)]);
            walkingCounter = 0;
        }

        checkCollisions();
        changeCoordinates();
        setSpritesNum();

        switch (getDirection()) {
            case "UP":
                if (getSpriteNum() == 1) {
                    enemyImage = up1;
                } else enemyImage = up2;
                break;
            case "DOWN":
                if (getSpriteNum() == 1) {
                    enemyImage = down1;
                } else enemyImage = down2;
                break;
            case "LEFT":
                if (getSpriteNum() == 1) {
                    enemyImage = left1;
                } else enemyImage = left2;
                break;
            case "RIGHT":
                if (getSpriteNum() == 1) {
                    enemyImage = right1;
                } else enemyImage = right2;
                break;
        }

    }

    @Override
    public void checkCollisions() {
        setCollisionOn(false);
        getGamePane().collisionCheck.checkTile(this);
        getGamePane().collisionCheck.checkEntity(this, getGamePane().player);
        if (isInVisualRange(visualRange, getGamePane().player.getHitbox())) {
            getAttackHitbox().setX(getWorldX() + 8);
            getAttackHitbox().setY(getWorldY() + 8);
            changeAttackHitboxCoord();
            getGamePane().collisionCheck.checkHit(this, getGamePane().player);
        }
//        getHitbox().setX(getHitbox().getX() + getGamePane().getTileSize());
//        System.out.println(getHitbox().getX());
//        System.out.println(getHitbox().getY());
//        System.out.println("-----------------");
    }

    @Override
    public void draw(GraphicsContext gc){
        if (getAlive()){
            int screenX = getWorldX() - getGamePane().player.getWorldX() + getGamePane().player.screenX;
            int screenY = getWorldY() - getGamePane().player.getWorldY() + getGamePane().player.screenY;
            if (!isInVisualRange(visualRange, getGamePane().player.getHitbox())) {
                if ((screenX + getGamePane().getTileSize() >= 0 && screenX - getGamePane().getTileSize() <= getGamePane().getScreenWidth()) &&
                        (screenY + getGamePane().getTileSize() >= 0 && screenY - getGamePane().getTileSize() <= getGamePane().getScreenHeight())) {
                    gc.drawImage(enemyImage, screenX, screenY, getGamePane().getTileSize(), getGamePane().getTileSize());
                }
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

    private boolean isInVisualRange(Rectangle visualRange, Rectangle target){
        if (target.intersects(visualRange.getBoundsInLocal())) {
//            MyLogger.getMyLogger().info("Player is in visual range of th enemy : " );
            return true;
        }
        return false;
    }

    @Override
    public void defend() {

    }

}

package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Enemy extends Entity{

//    Image enemyImage;
    int walkingCounter = 0;
    private int range;
    public int getRange(){return range;}
    public void setRange(int range){this.range = range;}
    private Rectangle visualRange;
    public Rectangle getVisualRange(){return visualRange;}
    public void setVisualRange(int range){
        visualRange = new Rectangle(getWorldX() - range, getWorldY() - range, range * 2 + gameModel.getTileSize(), range * 2 + gameModel.getTileSize());
    }

    @Override
    public void update() {
        walking();
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
                    setEntityImage(up1);
                } else setEntityImage(up2);
                break;
            case "DOWN":
                if (getSpriteNum() == 1) {
                    setEntityImage(down1);
                } else setEntityImage(down2);
                break;
            case "LEFT":
                if (getSpriteNum() == 1) {
                    setEntityImage(left1);
                } else setEntityImage(left2);
                break;
            case "RIGHT":
                if (getSpriteNum() == 1) {
                    setEntityImage(right1);
                } else setEntityImage(right2);
                break;
        }

    }

    @Override
    public void checkCollisions() {
        setCollisionOn(false);
        controller().collisionCheck.checkTile(this);
        controller().collisionCheck.checkEntity(this, controller().player);
        if (isInVisualRange(visualRange, controller().player.getHitbox())) {
            getAttackHitbox().setX(getWorldX() + 8);
            getAttackHitbox().setY(getWorldY() + 8);
            changeAttackHitboxCoord();
            controller().collisionCheck.checkHit(this, controller().player);
        }
    }

    @Override
    public void draw(GraphicsContext gc){
        if (getAlive()){
            setScreenX(getWorldX() - controller().player.getWorldX() + controller().player.getScreenX());
            setScreenY(getWorldY() - controller().player.getWorldY() + controller().player.getScreenY());
            gc.setFill(Color.WHITE);
            gc.fillText("Lives : " + getLives(), getScreenX(), getScreenY() - 5);
            if (!isInVisualRange(visualRange, controller().player.getHitbox())) {
                if ((getScreenX() + gameModel.getTileSize() >= 0 && getScreenX() - gameModel.getTileSize() <= gameModel.getScreenWidth()) &&
                        (getScreenY() + gameModel.getTileSize() >= 0 && getScreenY() - gameModel.getTileSize() <= gameModel.getScreenHeight())) {
                    gc.drawImage(getEntityImage(), getScreenX(), getScreenY(), gameModel.getTileSize(), gameModel.getTileSize());
                }
            } else {
                attack(gc, getScreenX(), getScreenY());
//                int x = (int) (getAttackHitbox().getX() - getGamePane().player.getWorldX() + getGamePane().player.getScreenX());
//                int y = (int) (getAttackHitbox().getY() - getGamePane().player.getWorldY() + getGamePane().player.getScreenY());
//                gc.setStroke(Color.WHITE);
//                gc.setLineWidth(2);
//                gc.fillRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
//                gc.strokeRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
            }
        }
    }


    /**
     * Check if the target is in visual range
     * @param visualRange
     * @param target
     * @return
     */
    public boolean isInVisualRange(Rectangle visualRange, Rectangle target){
        if (target.intersects(visualRange.getBoundsInLocal())) {
//            MyLogger.getMyLogger().info("Player is in visual range of th enemy : " );
            return true;
        }
        return false;
    }
}


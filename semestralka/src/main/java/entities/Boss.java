package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GamePane;

public class Boss extends Enemy{

    public Boss(GamePane gamePane, int row, int col){
        setGamePane(gamePane);
        setDefaultValues(row, col);
        getEnemyImage();
        setHitbox(new Rectangle(getWorldX() + 16, getWorldY() + 32, 64, 64));
        setAttackHitbox(new Rectangle(getWorldX() + 16, getWorldY() + 32, 64, 64));
    }

    /**
     * Loads enemy images from resources
     */
    private void getEnemyImage() {
        up1 = new Image("enemy/boss/up_1.png");
        up2 = new Image("enemy/boss/up_2.png");
        down1 = new Image("enemy/boss/down_1.png");
        down2 = new Image("enemy/boss/down_2.png");
        left1 = new Image("enemy/boss/left_1.png");
        left2 = new Image("enemy/boss/left_2.png");
        right1 = new Image("enemy/boss/right_1.png");
        right2 = new Image("enemy/boss/right_2.png");
        attackUp1 = new Image("enemy/boss/attack_up_1.png");
        attackUp2 = new Image("enemy/boss/attack_up_2.png");
        attackDown1 = new Image("enemy/boss/attack_down_1.png");
        attackDown2 = new Image("enemy/boss/attack_down_2.png");
        attackLeft1 = new Image("enemy/boss/attack_left_1.png");
        attackLeft2 = new Image("enemy/boss/attack_left_2.png");
        attackRight1 = new Image("enemy/boss/attack_right_1.png");
        attackRight2 = new Image("enemy/boss/attack_right_2.png");
    }

    /**
     * Set the values of the enemy
     * @param row The row where enemy will be placed
     * @param col The column where enemy will be placed
     */
    private void setDefaultValues(int row, int col) {
        setWorldX(col * getGamePane().getTileSize());
        setWorldY(row * getGamePane().getTileSize());
        setSpeed(1);
        setDirection("UP");
        setLives(10);
        setRange(getGamePane().getTileSize());
        setVisualRange(getRange() * 2);
    }

    @Override
    public void attackingProcess(){
        increaseAttackCounter();
        if (getAttackCounter() < 25){
            setAttackNum(1);
        } else if (getAttackCounter() >= 25 && getAttackCounter() < 40){
            setAttackNum(2);
        } else if (getAttackCounter() >= 40 && getAttackCounter() < 50){
            setAttackNum(1);
        } else if (getAttackCounter() >= 50) {
            setAttackCounter(0);
            setAttacking(false);
        }
    }


    @Override
    public void draw(GraphicsContext gc){
        if (getAlive()){
            setScreenX(getWorldX() - getGamePane().player.getWorldX() + getGamePane().player.getScreenX());
            setScreenY(getWorldY() - getGamePane().player.getWorldY() + getGamePane().player.getScreenY());
            if (!isInVisualRange(getVisualRange(), getGamePane().player.getHitbox())) {
                if ((getScreenX() + getGamePane().getTileSize() >= 0 && getScreenX() - getGamePane().getTileSize() <= getGamePane().getScreenWidth()) &&
                        (getScreenY() + getGamePane().getTileSize() >= 0 && getScreenY() - getGamePane().getTileSize() <= getGamePane().getScreenHeight())) {
                    gc.drawImage(getEntityImage(), getScreenX(), getScreenY(), getGamePane().getTileSize() * 2, getGamePane().getTileSize() * 2);
                    int x = (int) (getAttackHitbox().getX() - getGamePane().player.getWorldX() + getGamePane().player.getScreenX());
                    int y = (int) (getAttackHitbox().getY() - getGamePane().player.getWorldY() + getGamePane().player.getScreenY());
                    gc.setStroke(Color.WHITE);
                    gc.setLineWidth(2);
                    gc.fillRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
                    gc.strokeRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
                }
            } else {
                attack(gc, getScreenX(), getScreenY());
                int x = (int) (getAttackHitbox().getX() - getGamePane().player.getWorldX() + getGamePane().player.getScreenX());
                int y = (int) (getAttackHitbox().getY() - getGamePane().player.getWorldY() + getGamePane().player.getScreenY());
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(2);
                gc.fillRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());
                gc.strokeRect(x, y, getAttackHitbox().getWidth(), getAttackHitbox().getHeight());

            }
        }
    }

//    @Override
//    public void drawImageAttack(GraphicsContext gc, int screenX, int screenY){
//        switch (getDirection()){
//            case "UP":
//                gc.drawImage(getEntityImage(), screenX, screenY - getGamePane().getTileSize() * 2, getGamePane().getTileSize() * 2, getGamePane().getTileSize() * 2 * 2);
//                break;
//            case "DOWN":
//                gc.drawImage(getEntityImage(), screenX, screenY, getGamePane().getTileSize() * 2, getGamePane().getTileSize() * 2 * 2);
//                break;
//            case "LEFT":
//                gc.drawImage(getEntityImage(), screenX - getGamePane().getTileSize() * 2, screenY, getGamePane().getTileSize() * 2 * 2, getGamePane().getTileSize()* 2);
//                break;
//            case "RIGHT":
//                gc.drawImage(getEntityImage(), screenX, screenY, getGamePane().getTileSize() * 2 * 2, getGamePane().getTileSize() * 2);
//                break;
//        }
//    }
}

package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Controller;

public class Boss extends Enemy{

    public Boss(Controller controller, int row, int col, int lives){
        setController(controller);
        setDefaultValues(row, col, lives);
        getEnemyImage();
        setHitbox(new Rectangle(getWorldX() + 16, getWorldY() + 32, 64, 64));
        setAttackHitbox(new Rectangle(getWorldX() + 16, getWorldY() + 32, 64, 64));
    }

    /**
     * Loads enemy images from resources
     */
    private void getEnemyImage() {
        up1 = new Image("entity/boss/up_1.png");
        up2 = new Image("entity/boss/up_2.png");
        down1 = new Image("entity/boss/down_1.png");
        down2 = new Image("entity/boss/down_2.png");
        left1 = new Image("entity/boss/left_1.png");
        left2 = new Image("entity/boss/left_2.png");
        right1 = new Image("entity/boss/right_1.png");
        right2 = new Image("entity/boss/right_2.png");
        attackUp1 = new Image("entity/boss/attack_up_1.png");
        attackUp2 = new Image("entity/boss/attack_up_2.png");
        attackDown1 = new Image("entity/boss/attack_down_1.png");
        attackDown2 = new Image("entity/boss/attack_down_2.png");
        attackLeft1 = new Image("entity/boss/attack_left_1.png");
        attackLeft2 = new Image("entity/boss/attack_left_2.png");
        attackRight1 = new Image("entity/boss/attack_right_1.png");
        attackRight2 = new Image("entity/boss/attack_right_2.png");
    }

    /**
     * Set the values of the enemy
     * @param row The row where enemy will be placed
     * @param col The column where enemy will be placed
     */
    private void setDefaultValues(int row, int col, int lives) {
        setWorldX(col * gameModel.getTileSize());
        setWorldY(row * gameModel.getTileSize());
        setLives(lives);
        setSpeed(1);
        setDirection("UP");
        setRange(gameModel.getTileSize());
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
            setScreenX(getWorldX() - controller().player.getWorldX() + controller().player.getScreenX());
            setScreenY(getWorldY() - controller().player.getWorldY() + controller().player.getScreenY());
            gc.setFill(Color.WHITE);
            gc.fillText("Lives : " + getLives(), getScreenX() + 20, getScreenY() - 5);
            if (!isInVisualRange(getVisualRange(), controller().player.getHitbox())) {
                if ((getScreenX() + gameModel.getTileSize() >= 0 && getScreenX() - gameModel.getTileSize() <= gameModel.getScreenWidth()) &&
                        (getScreenY() + gameModel.getTileSize() >= 0 && getScreenY() - gameModel.getTileSize() <= gameModel.getScreenHeight())) {
                    gc.drawImage(getEntityImage(), getScreenX(), getScreenY(), gameModel.getTileSize() * 2, gameModel.getTileSize() * 2);
                }
            } else {
                attack(gc, getScreenX(), getScreenY());
            }
        }
    }
}

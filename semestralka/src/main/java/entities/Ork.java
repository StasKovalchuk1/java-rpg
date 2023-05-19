package entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePane;


public class Ork extends Enemy{
    public Ork(GamePane gamePane, int row, int col, int lives) {
        setGamePane(gamePane);
        setDefaultValues(row, col, lives);
        getEnemyImage();
        setHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        setAttackHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
    }

    /**
     * Loads enemy images from resources
     */
    private void getEnemyImage() {
        up1 = new Image("entity/ork/up_1.png");
        up2 = new Image("entity/ork/up_2.png");
        down1 = new Image("entity/ork/down_1.png");
        down2 = new Image("entity/ork/down_2.png");
        left1 = new Image("entity/ork/left_1.png");
        left2 = new Image("entity/ork/left_2.png");
        right1 = new Image("entity/ork/right_1.png");
        right2 = new Image("entity/ork/right_2.png");
        attackUp1 = new Image("entity/ork/attack_up_1.png");
        attackUp2 = new Image("entity/ork/attack_up_2.png");
        attackDown1 = new Image("entity/ork/attack_down_1.png");
        attackDown2 = new Image("entity/ork/attack_down_2.png");
        attackLeft1 = new Image("entity/ork/attack_left_1.png");
        attackLeft2 = new Image("entity/ork/attack_left_2.png");
        attackRight1 = new Image("entity/ork/attack_right_1.png");
        attackRight2 = new Image("entity/ork/attack_right_2.png");
    }

    /**
     * Set the values of the enemy
     * @param row The row where enemy will be placed
     * @param col The column where enemy will be placed
     */
    private void setDefaultValues(int row, int col, int lives) {
        setWorldX(col * getGamePane().getTileSize());
        setWorldY(row * getGamePane().getTileSize());
        setSpeed(1);
        setDirection("UP");
        setLives(lives);
        setRange(getGamePane().getTileSize());
        setVisualRange(getRange());
    }

    @Override
    public void attackingProcess(){
        increaseAttackCounter();
        if (getAttackCounter() < 18){
            setAttackNum(1);
        } else if (getAttackCounter() >= 18 && getAttackCounter() < 32){
            setAttackNum(2);
        } else if (getAttackCounter() >= 32 && getAttackCounter() < 40){
            setAttackNum(1);
        } else if (getAttackCounter() >= 40) {
            setAttackCounter(0);
            setAttacking(false);
        }
    }
}

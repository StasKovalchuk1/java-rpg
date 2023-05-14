package entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePane;


public class Ork extends Enemy{
    public Ork(GamePane gamePane, int row, int col) {
        setGamePane(gamePane);
        setDefaultValues(row, col);
        getEnemyImage();
        setHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
        setAttackHitbox(new Rectangle(getWorldX() + 8, getWorldY() + 16, 32, 32));
    }

    /**
     * Loads enemy images from resources
     */
    private void getEnemyImage() {
        up1 = new Image("enemy/ork/up_1.png");
        up2 = new Image("enemy/ork/up_2.png");
        down1 = new Image("enemy/ork/down_1.png");
        down2 = new Image("enemy/ork/down_2.png");
        left1 = new Image("enemy/ork/left_1.png");
        left2 = new Image("enemy/ork/left_2.png");
        right1 = new Image("enemy/ork/right_1.png");
        right2 = new Image("enemy/ork/right_2.png");
        attackUp1 = new Image("enemy/ork/attack_up_1.png");
        attackUp2 = new Image("enemy/ork/attack_up_2.png");
        attackDown1 = new Image("enemy/ork/attack_down_1.png");
        attackDown2 = new Image("enemy/ork/attack_down_2.png");
        attackLeft1 = new Image("enemy/ork/attack_left_1.png");
        attackLeft2 = new Image("enemy/ork/attack_left_2.png");
        attackRight1 = new Image("enemy/ork/attack_right_1.png");
        attackRight2 = new Image("enemy/ork/attack_right_2.png");
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
        setLives(3);
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

//
//    @Override
//    public void update() {
//        walking();
//        hitbox.setX(worldX + 8);
//        hitbox.setY(worldY + 16);
//    }
//
//    /**
//     * Set enemy walking sprites
//     */
//    public void walking() {
//        walkingCounter++;
//        if (walkingCounter == 80){
//            String[] dir = new String[] {"UP", "DOWN", "LEFT", "RIGHT"};
//            Random random = new Random();
//            direction = dir[random.nextInt(dir.length)];
//            walkingCounter = 0;
//        }
//        switch (direction) {
//            case "UP":
//                if (spriteNum == 1) {
//                    enemyImage = up1;
//                } else enemyImage = up2;
//                break;
//            case "DOWN":
//                if (spriteNum == 1) {
//                    enemyImage = down1;
//                } else enemyImage = down2;
//                break;
//            case "LEFT":
//                if (spriteNum == 1) {
//                    enemyImage = left1;
//                } else enemyImage = left2;
//                break;
//            case "RIGHT":
//                if (spriteNum == 1) {
//                    enemyImage = right1;
//                } else enemyImage = right2;
//                break;
//        }
//
//        checkCollisions();
//        changeCoordinates();
//        setSpritesNum();
//    }
//
//    @Override
//    public void checkCollisions() {
//        collisionOn = false;
//        gamePane.collisionCheck.checkTile(this);
//        gamePane.collisionCheck.checkEntity(this, gamePane.player);
//    }
//
//    @Override
//    public void interactionWithObjects() {
//
//    }
//
//    @Override
//    public void draw(GraphicsContext gc){
//        if (isAlive){
//            int screenX = worldX - gamePane.player.worldX + gamePane.player.screenX;
//            int screenY = worldY - gamePane.player.worldY + gamePane.player.screenY;
//            if ((screenX + gamePane.getTileSize() >= 0 && screenX - gamePane.getTileSize() <= gamePane.getScreenWidth()) &&
//                    (screenY + gamePane.getTileSize() >= 0 && screenY - gamePane.getTileSize() <= gamePane.getScreenHeight())){
//                gc.drawImage(enemyImage, screenX, screenY, gamePane.getTileSize(), gamePane.getTileSize());
//            }
//        }
//    }
//
//    @Override
//    public void attack(GraphicsContext gc) {
//
//    }
//
//    @Override
//    public void getHitProcess() {
//        increaseHitCounter();
//        if (getHitCounter() > 8){
//            decreaseLives();
//            setHitCounter(0);
//            if (lives <= 0) isAlive = false;
//        }
//    }
//
//    @Override
//    public void defend() {
//
//    }
//
//    @Override
//    public void decreaseLives() {
//        lives--;
//        System.out.println(lives);
//    }
//
//    @Override
//    public void increaseLives() {
//        lives++;
//    }
}

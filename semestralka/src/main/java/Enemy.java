
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy extends Entity {
    GamePane gamePane;
    Image enemyImage;
    int radius;

    public Enemy(GamePane gamePane) {
        this.gamePane = gamePane;
        setDefaultValues();
        getEnemyImage();
    }

    private void getEnemyImage() {
        up1 = new javafx.scene.image.Image("enemy/up_1.png");
        up2 = new javafx.scene.image.Image("enemy/up_2.png");
        down1 = new javafx.scene.image.Image("enemy/down_1.png");
        down2 = new javafx.scene.image.Image("enemy/down_2.png");
        left1 = new javafx.scene.image.Image("enemy/left_1.png");
        left2 = new javafx.scene.image.Image("enemy/left_2.png");
        right1 = new javafx.scene.image.Image("enemy/right_1.png");
        right2 = new javafx.scene.image.Image("enemy/right_2.png");
    }

    private void setDefaultValues() {
        worldX = 1872;
        worldY = 1872;
        speed = 1;
        direction = "RIGHT";
        radius = 2 * gamePane.tileSize;
    }

    @Override
    public void update() {
        walking();
        switch (direction){
            case "UP":
                worldY -= speed;
                radius -= speed;
                if (radius <= 0){
                    direction = "RIGHT";
                    radius = 2 * gamePane.tileSize;
                }
                break;
            case "DOWN":
                worldY += speed;
                radius -= speed;
                if (radius <= 0){
                    direction = "LEFT";
                    radius = 2 * gamePane.tileSize;
                }
                break;
            case "LEFT":
                worldX -= speed;
                radius -= speed;
                if (radius <= 0){
                    direction = "UP";
                    radius = 2 * gamePane.tileSize;
                }
                break;
            case "RIGHT":
                worldX += speed;
                radius -= speed;
                if (radius <= 0){
                    direction = "DOWN";
                    radius = 2 * gamePane.tileSize;
                }
                break;
        }
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

    public void walking() {
        switch (direction) {
            case "UP":
                if (spriteNum == 1) {
                    enemyImage = up1;
                } else enemyImage = up2;
                break;
            case "DOWN":
                if (spriteNum == 1) {
                    enemyImage = down1;
                } else enemyImage = down2;
                break;
            case "LEFT":
                if (spriteNum == 1) {
                    enemyImage = left1;
                } else enemyImage = left2;
                break;
            case "RIGHT":
                if (spriteNum == 1) {
                    enemyImage = right1;
                } else enemyImage = right2;
                break;
        }
    }

    @Override
    public void draw(GraphicsContext gc){
        int screenX = worldX - gamePane.player.worldX + gamePane.player.screenX;
        int screenY = worldY - gamePane.player.worldY + gamePane.player.screenY;
        if ((screenX + gamePane.tileSize >= 0 && screenX - gamePane.tileSize <= gamePane.screenWidth) &&
                (screenY + gamePane.tileSize >= 0 && screenY - gamePane.tileSize <= gamePane.screenHeight)){
            gc.drawImage(enemyImage, screenX, screenY, gamePane.tileSize, gamePane.tileSize);
        }
    }

    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }
}

public class CollisionCheck {
    GamePane gamePane;
    public CollisionCheck(GamePane gamePane){
        this.gamePane = gamePane;
    }


    public void check(Entity entity){
        int rectLeftX = (int) entity.rectangle.getX();
        int rectRightX = (int) (entity.rectangle.getX() + entity.rectangle.getWidth());
        int rectTopY = (int) entity.rectangle.getY();
        int rectBottomY = (int) (entity.rectangle.getY() + entity.rectangle.getHeight());

        int rowOfTop;
        int colOfLeft;
        int colOfRight;
        int rowOfBottom;

        int value;
        switch (entity.direction) {
            case "UP" -> {
                rowOfTop = (rectTopY - entity.speed) / gamePane.tileSize;;
                colOfLeft = rectLeftX / gamePane.tileSize;
                colOfRight = rectRightX / gamePane.tileSize;
                value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfLeft];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                    System.out.println("suka");
                }
                value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfRight];
                if (gamePane.tileManager.tiles[value].solid) {
                    System.out.println("suka");
                    entity.collisionOn = true;
                }
            }
            case "DOWN" -> {
                rowOfBottom = (rectBottomY + entity.speed) / gamePane.tileSize;;
                colOfLeft = rectLeftX / gamePane.tileSize;
                colOfRight = rectRightX / gamePane.tileSize;
                value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfLeft];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                }
                value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfRight];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                }
            }
            case "LEFT" -> {
                colOfLeft = (rectLeftX - entity.speed) / gamePane.tileSize;
                rowOfTop = rectTopY / gamePane.tileSize;
                rowOfBottom = rectBottomY / gamePane.tileSize;
                value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfLeft];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                }
                value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfLeft];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                }
            }
            case "RIGHT" -> {
                colOfRight = (rectRightX + entity.speed) / gamePane.tileSize;
                rowOfTop = rectTopY / gamePane.tileSize;
                rowOfBottom = rectBottomY / gamePane.tileSize;
                value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfRight];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                }
                value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfRight];
                if (gamePane.tileManager.tiles[value].solid) {
                    entity.collisionOn = true;
                }
            }
        }
    }

//    public int findTilesCol(int x) {
//        return gamePane != null ? (x - gamePane.player.speed)/ gamePane.tileSize + 1 : 0;
//    }
//
//    public int findTilesRow(int y){
//        return gamePane != null ? (y - gamePane.player.speed) / gamePane.tileSize + 1 : 0;
//    }

}

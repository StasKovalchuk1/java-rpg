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

        int rowOfTop = rectTopY / gamePane.tileSize;
        int colOfLeft = rectLeftX / gamePane.tileSize;
        int colOfRight = rectRightX / gamePane.tileSize;
        int rowOfBottom = rectBottomY / gamePane.tileSize;

        // check collision with solid tiles
        int value;
        try {
            switch (entity.direction) {
                case "UP" -> {
                    rowOfTop = (rectTopY - entity.speed) / gamePane.tileSize;
                    value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfLeft];
                    if (gamePane.tileManager.tiles[value].solid) {
                        entity.collisionOn = true;
                    }
                    value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfRight];
                    if (gamePane.tileManager.tiles[value].solid) {
                        entity.collisionOn = true;
                    }
                }
                case "DOWN" -> {
                    rowOfBottom = (rectBottomY + entity.speed) / gamePane.tileSize;
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
        } catch (ArrayIndexOutOfBoundsException e){
            entity.collisionOn = true;
        }
    }

    public void checkItem(Entity entity){
        for (String i : gamePane.itemManager.items.keySet()){
            if (gamePane.itemManager.items.get(i).worldX <= entity.rectangle.getX() &&
                    gamePane.itemManager.items.get(i).worldX + gamePane.tileSize >= entity.rectangle.getX() &&
                        gamePane.itemManager.items.get(i).worldY <= entity.rectangle.getY() &&
                            gamePane.itemManager.items.get(i).worldY + gamePane.tileSize >= entity.rectangle.getY() &&
                                !gamePane.itemManager.items.get(i).isTaken &&
                                    !gamePane.itemManager.items.get(i).name.equals("chest")){
                gamePane.itemManager.items.get(i).isTaken = true;
                gamePane.player.inventory.add(gamePane.itemManager.items.get(i));
            }
        }
    }

}

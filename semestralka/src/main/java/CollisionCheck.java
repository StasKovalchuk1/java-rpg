public class CollisionCheck {
    GamePane gamePane;

    public CollisionCheck(GamePane gamePane) {
        this.gamePane = gamePane;
    }

    public void check(Entity entity) {
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
                    if (gamePane.tileManager.tiles[value].solid
                            || (rowOfTop * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                            && colOfLeft * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                        entity.collisionOn = true;
                    } else {
                        value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfRight];
                        if (gamePane.tileManager.tiles[value].solid
                                || (rowOfTop * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                                && colOfRight * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                            entity.collisionOn = true;
                        }
                    }
                }
                case "DOWN" -> {
                    rowOfBottom = (rectBottomY + entity.speed) / gamePane.tileSize;
                    value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfLeft];
                    if (gamePane.tileManager.tiles[value].solid
                            || (rowOfBottom * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                            && colOfLeft * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                        entity.collisionOn = true;
                    } else {
                        value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfRight];
                        if (gamePane.tileManager.tiles[value].solid
                                || (rowOfBottom * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                                && colOfRight * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                            entity.collisionOn = true;
                        }
                    }
                }
                case "LEFT" -> {
                    colOfLeft = (rectLeftX - entity.speed) / gamePane.tileSize;
                    value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfLeft];
                    if (gamePane.tileManager.tiles[value].solid
                            || (rowOfTop * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                            && colOfLeft * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                        entity.collisionOn = true;
                    } else {
                        value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfLeft];
                        if (gamePane.tileManager.tiles[value].solid
                                || (rowOfBottom * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                                && colOfLeft * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                            entity.collisionOn = true;
                        }
                    }
                }
                case "RIGHT" -> {
                    colOfRight = (rectRightX + entity.speed) / gamePane.tileSize;
                    value = gamePane.tileManager.mapTileNumbers[rowOfTop][colOfRight];
                    if (gamePane.tileManager.tiles[value].solid
                            || (rowOfTop * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                            && colOfRight * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                        entity.collisionOn = true;
                    } else {
                        value = gamePane.tileManager.mapTileNumbers[rowOfBottom][colOfRight];
                        if (gamePane.tileManager.tiles[value].solid
                                || (rowOfBottom * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldY
                                && colOfRight * gamePane.tileSize == gamePane.itemManager.items.get("chest").worldX)) {
                            entity.collisionOn = true;
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.collisionOn = true;
        }
    }

    public void checkItem(Entity entity) {
        for (String i : gamePane.itemManager.items.keySet()) {
            Item item = gamePane.itemManager.items.get(i);
            // if we have collision with an item then we take it
            if (item.worldX <= entity.rectangle.getX() && item.worldX + gamePane.tileSize >= entity.rectangle.getX()
                    && item.worldY <= entity.rectangle.getY()
                    && item.worldY + gamePane.tileSize >= entity.rectangle.getY()
                    && !item.isTaken) {
                if (!item.name.equals("chest")) {
                    item.isTaken = true;
                    gamePane.inventory.list.add(item);
                }
            }
        }
    }

    public void checkChest(Entity entity) {
        Chest chest = (Chest) gamePane.itemManager.items.get("chest");
        if ((chest.worldX - gamePane.tileSize * 2) <= entity.rectangle.getX() && (chest.worldX + gamePane.tileSize * 3) >= entity.rectangle.getX()
                && (chest.worldY - gamePane.tileSize * 2) <= entity.rectangle.getY() && (chest.worldY + gamePane.tileSize * 3) >= entity.rectangle.getY()
                && !chest.getIsOpened() && gamePane.keyHandler.chestPressed) {
            for (Item item : gamePane.inventory.list) {
                if (item.name.equals("key")) {
                    chest.setIsOpened(true);
                    gamePane.inventory.list.remove(gamePane.itemManager.items.get("key"));
                    gamePane.inventory.list.addAll(chest.inside);
                    break;
                }
            }
        }
    }

}

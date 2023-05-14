package main;

import entities.*;
import items.*;

/**
 * This class is for checking collisions
 */
public class CollisionCheck {
    GamePane gamePane;
    Chest chest = null;

    public CollisionCheck(GamePane gamePane) {
        this.gamePane = gamePane;
        for (Item item : gamePane.itemManager.getAllItems()){
            if (item instanceof Chest) chest = (Chest) item;
        }
    }

    /**
     * Check collision entity with solid tiles
     * @param entity Your entity that you check on collision
     */
    public void checkTile(Entity entity) {
        int rectLeftX = (int) entity.getHitbox().getX();
        int rectRightX = (int) (entity.getHitbox().getX() + entity.getHitbox().getWidth());
        int rectTopY = (int) entity.getHitbox().getY();
        int rectBottomY = (int) (entity.getHitbox().getY() + entity.getHitbox().getHeight());

        int rowOfTop = rectTopY / gamePane.getTileSize();
        int colOfLeft = rectLeftX / gamePane.getTileSize();
        int colOfRight = rectRightX / gamePane.getTileSize();
        int rowOfBottom = rectBottomY/ gamePane.getTileSize();

        try {
            switch (entity.getDirection()) {
                case "UP" -> {
                    rowOfTop = (rectTopY - entity.getSpeed()) / gamePane.getTileSize();
                    if (gamePane.tileManager.getTilesList()[getValue(rowOfTop, colOfLeft)].solid
                            || gamePane.tileManager.getTilesList()[getValue(rowOfTop, colOfRight)].solid
                                    || (rowOfTop == chest.getWorldY() / gamePane.getTileSize() &&
                                        (colOfRight == chest.getWorldX() / gamePane.getTileSize() || colOfLeft == chest.getWorldX() / gamePane.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
                case "DOWN" -> {
                    rowOfBottom = (rectBottomY + entity.getSpeed()) / gamePane.getTileSize();
                    if (gamePane.tileManager.getTilesList()[getValue(rowOfBottom, colOfLeft)].solid
                            || gamePane.tileManager.getTilesList()[getValue(rowOfBottom, colOfRight)].solid
                                || (rowOfBottom == chest.getWorldY() / gamePane.getTileSize()
                                    && (colOfRight == chest.getWorldX() / gamePane.getTileSize() || colOfLeft == chest.getWorldX() / gamePane.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
                case "LEFT" -> {
                    colOfLeft = (rectLeftX - entity.getSpeed()) / gamePane.getTileSize();
                    if (gamePane.tileManager.getTilesList()[getValue(rowOfTop, colOfLeft)].solid
                            || gamePane.tileManager.getTilesList()[getValue(rowOfBottom, colOfLeft)].solid
                                || (colOfLeft == chest.getWorldX() / gamePane.getTileSize()
                                    && (rowOfBottom == chest.getWorldY() / gamePane.getTileSize() || rowOfTop == chest.getWorldY() / gamePane.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
                case "RIGHT" -> {
                    colOfRight = (rectRightX + entity.getSpeed()) / gamePane.getTileSize();
                    if (gamePane.tileManager.getTilesList()[getValue(rowOfTop, colOfRight)].solid
                            || gamePane.tileManager.getTilesList()[getValue(rowOfBottom, colOfRight)].solid
                                || (colOfRight == chest.getWorldX() / gamePane.getTileSize()
                                    && (rowOfBottom == chest.getWorldY() / gamePane.getTileSize() || rowOfTop == chest.getWorldY() / gamePane.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.setCollisionOn(true);
        }
    }

    /**
     * Gets the tile value in a specific row and column
     * @param row Your row
     * @param col Your column
     * @return Tile's value
     */
    private int getValue(int row, int col) {
        return gamePane.tileManager.getMapTileNumbers()[row][col];
    }

    /**
     * Check collision entity with items
     * @param entity Your entity that you check on collision
     */
    public void checkItem(Entity entity) {
        for (Item item : gamePane.itemManager.getAllItems()) {
            // if we have collision with an item then we take it
            if ((entity.getHitbox().intersects(item.getWorldX(), item.getWorldY(), gamePane.getTileSize(), gamePane.getTileSize()))
                && !item.getIsTaken() && (gamePane.inventory.getInventory().size() < gamePane.inventory.getMaxListSize())){
                    if (!item.getName().equals("chest")){
                        item.setIsTaken(true);
                        gamePane.inventory.getInventory().add(item);
                    }
            }
        }
    }

    /**
     * Checks if the entity is near the chest and tries to open it
     * @param entity Your entity that you check
     */
    public void checkChest(Entity entity) {
        if (chest != null) {
            if (entity.getHitbox().intersects(chest.getWorldX() - gamePane.getTileSize() * 0.5, chest.getWorldY() - gamePane.getTileSize() * 0.5, gamePane.getTileSize() * 2, gamePane.getTileSize() * 2)
                    && !chest.getIsOpened() && gamePane.keyHandler.chestPressed) {
                for (Item item : gamePane.inventory.getInventory()) {
                    if (item.getName().equals("key")) {
                        chest.setIsOpened(true);
                        gamePane.inventory.getInventory().remove(item);
                        for (Item itemInside : chest.inside) {
                            if (gamePane.inventory.getInventory().size() < gamePane.inventory.getMaxListSize())  gamePane.inventory.getInventory().add(itemInside);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if the attacking entity has made a hit on another
     * @param entityAttack The entity that attacks
     * @param entityGetHit The entity that will be attacked
     */
    public void checkHit(Entity entityAttack, Entity entityGetHit) {
        if (entityGetHit.getAlive() && entityAttack.getAlive()) {
            if (entityAttack.getAttackHitbox().intersects(entityGetHit.getHitbox().getBoundsInLocal())) {
                entityGetHit.getHitProcess();
            } else entityGetHit.setHitCounter(0);
        }
    }

    /**
     * Check collision between two entities
     * @param entity Your first entity
     * @param target Your second entity
     */
    public void checkEntity(Entity entity, Entity target) {
        if (target.getAlive()){
            switch (entity.getDirection()){
                case "UP":
                    entity.getHitbox().setY(entity.getHitbox().getY() - entity.getSpeed());
                    if (entity.getHitbox().intersects(target.getHitbox().getBoundsInLocal())){
                        entity.setCollisionOn(true);
                    }
                    break;
                case "DOWN":
                    entity.getHitbox().setY(entity.getHitbox().getY() + entity.getSpeed());
                    if (entity.getHitbox().intersects(target.getHitbox().getBoundsInLocal())){
                        entity.setCollisionOn(true);
                    }
                    break;
                case "LEFT":
                    entity.getHitbox().setX(entity.getHitbox().getX() - entity.getSpeed());
                    if (entity.getHitbox().intersects(target.getHitbox().getBoundsInLocal())){
                        entity.setCollisionOn(true);
                    }
                    break;
                case "RIGHT":
                    entity.getHitbox().setX(entity.getHitbox().getX() + entity.getSpeed());
                    if (entity.getHitbox().intersects(target.getHitbox().getBoundsInLocal())){
                        entity.setCollisionOn(true);
                    }
                    break;
            }
        }
    }

}

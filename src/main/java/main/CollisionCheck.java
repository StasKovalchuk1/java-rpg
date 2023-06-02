package main;

import entities.*;
import items.*;
import items.Item;
import data.GameModel;
import data.SoundManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is for checking collisions
 */
public class CollisionCheck {
    GameModel gameModel =new GameModel();
    Controller controller;
    private Chest chest = null;

    public Chest getChest() {
        return chest;
    }

    public void setChest(Chest chest) {
        this.chest = chest;
    }

    public CollisionCheck(Controller controller) {
        this.controller = controller;
        getItemsFromItemManager();
    }

    private void getItemsFromItemManager() {
        if (controller.itemManager != null){
            for (Item item : controller.itemManager.getAllItems()){
                if (item instanceof Chest) chest = (Chest) item;
            }
        }
    }

    public CollisionCheck() {}

    /**
     * Check collision entity with solid tiles
     * @param entity Your entity that you check on collision
     */
    public void checkTile(Entity entity) {
        int rectLeftX = (int) entity.getHitbox().getX();
        int rectRightX = (int) (entity.getHitbox().getX() + entity.getHitbox().getWidth());
        int rectTopY = (int) entity.getHitbox().getY();
        int rectBottomY = (int) (entity.getHitbox().getY() + entity.getHitbox().getHeight());

        int rowOfTop = rectTopY / gameModel.getTileSize();
        int colOfLeft = rectLeftX / gameModel.getTileSize();
        int colOfRight = rectRightX / gameModel.getTileSize();
        int rowOfBottom = rectBottomY/ gameModel.getTileSize();

        try {
            switch (entity.getDirection()) {
                case "UP" -> {
                    rowOfTop = (rectTopY - entity.getSpeed()) / gameModel.getTileSize();
                    if (controller.tileManager.getTilesList()[getValue(rowOfTop, colOfLeft)].solid
                            || controller.tileManager.getTilesList()[getValue(rowOfTop, colOfRight)].solid
                                    || (rowOfTop == chest.getWorldY() / gameModel.getTileSize() &&
                                        (colOfRight == chest.getWorldX() / gameModel.getTileSize() || colOfLeft == chest.getWorldX() / gameModel.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
                case "DOWN" -> {
                    rowOfBottom = (rectBottomY + entity.getSpeed()) / gameModel.getTileSize();
                    if (controller.tileManager.getTilesList()[getValue(rowOfBottom, colOfLeft)].solid
                            || controller.tileManager.getTilesList()[getValue(rowOfBottom, colOfRight)].solid
                                || (rowOfBottom == chest.getWorldY() / gameModel.getTileSize()
                                    && (colOfRight == chest.getWorldX() / gameModel.getTileSize() || colOfLeft == chest.getWorldX() / gameModel.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
                case "LEFT" -> {
                    colOfLeft = (rectLeftX - entity.getSpeed()) / gameModel.getTileSize();
                    if (controller.tileManager.getTilesList()[getValue(rowOfTop, colOfLeft)].solid
                            || controller.tileManager.getTilesList()[getValue(rowOfBottom, colOfLeft)].solid
                                || (colOfLeft == chest.getWorldX() / gameModel.getTileSize()
                                    && (rowOfBottom == chest.getWorldY() / gameModel.getTileSize() || rowOfTop == chest.getWorldY() / gameModel.getTileSize()))) {
                        entity.setCollisionOn(true);
                    }
                }
                case "RIGHT" -> {
                    colOfRight = (rectRightX + entity.getSpeed()) / gameModel.getTileSize();
                    if (controller.tileManager.getTilesList()[getValue(rowOfTop, colOfRight)].solid
                            || controller.tileManager.getTilesList()[getValue(rowOfBottom, colOfRight)].solid
                                || (colOfRight == chest.getWorldX() / gameModel.getTileSize()
                                    && (rowOfBottom == chest.getWorldY() / gameModel.getTileSize() || rowOfTop == chest.getWorldY() / gameModel.getTileSize()))) {
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
        return controller.tileManager.getMapTileNumbers()[row][col];
    }

    /**
     * Check collision entity with items
     * @param entity Your entity that you check on collision
     */
    public void checkItem(Entity entity) {
        for (Item item : controller.itemManager.getAllItems()) {
            // if we have collision with an item then we take it
            if ((entity.getHitbox().intersects(item.getWorldX(), item.getWorldY(), gameModel.getTileSize(), gameModel.getTileSize()))
                && !item.getIsTaken() && (controller.inventory.getInventory().size() < controller.inventory.getMaxListSize())){
                    if (!item.getName().equals("chest")){
                        item.setIsTaken(true);
                        controller.inventory.getInventory().add(item);
                        SoundManager.playTakeItemSound();
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
            if (entity.getHitbox().intersects(chest.getWorldX() - gameModel.getTileSize() * 0.5, chest.getWorldY() - gameModel.getTileSize() * 0.5, gameModel.getTileSize() * 2, gameModel.getTileSize() * 2)
                    && !chest.getIsOpened() && controller.keyHandler.chestPressed) {
                ArrayList<Item> inventory = controller.inventory.getInventory();
                Iterator<Item> iterator = controller.inventory.getInventory().iterator();
                while (iterator.hasNext()) {
                    Item item = iterator.next();
                    if (item.getName().equals("key")) {
                        chest.setIsOpened(true);
                        iterator.remove();
                        for (Item itemInside : chest.getItemsInside()) {
                            if (inventory.size() < controller.inventory.getMaxListSize()) {
                                inventory.add(itemInside);
                            }
                        }
                        break;
                    }
                }
                controller.inventory.setInventory(inventory);
                chest.getItemsInside().clear();
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
                if (!(entityAttack.checkOppositeDirection(entityAttack, entityGetHit) && entityGetHit.isDefending())){
                    entityGetHit.getHitProcess();
                }
                else entityGetHit.blockHitProcess();
            }
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

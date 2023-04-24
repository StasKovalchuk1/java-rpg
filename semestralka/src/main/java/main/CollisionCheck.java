package main;

import entities.*;
import items.*;

public class CollisionCheck {
    GamePane gamePane;

    public CollisionCheck(GamePane gamePane) {
        this.gamePane = gamePane;
    }

    public void check(Entity entity) {
        int rectLeftX = (int) entity.getRectangle().getX();
        int rectRightX = (int) (entity.getRectangle().getX() + entity.getRectangle().getWidth());
        int rectTopY = (int) entity.getRectangle().getY();
        int rectBottomY = (int) (entity.getRectangle().getY() + entity.getRectangle().getHeight());

        int rowOfTop = rectTopY / gamePane.getTileSize();
        int colOfLeft = rectLeftX / gamePane.getTileSize();
        int colOfRight = rectRightX / gamePane.getTileSize();
        int rowOfBottom = rectBottomY / gamePane.getTileSize();

        // check collision with solid tiles
        int value;
        try {
            switch (entity.getDirection()) {
                case "UP" -> {
                    rowOfTop = (rectTopY - entity.getSpeed()) / gamePane.getTileSize();
                    value = gamePane.tileManager.getMapTileNumbers()[rowOfTop][colOfLeft];
                    if (gamePane.tileManager.getTilesList()[value].solid
                            || (rowOfTop * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                            && colOfLeft * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                        entity.setCollisionOn(true);
                    } else {
                        value = gamePane.tileManager.getMapTileNumbers()[rowOfTop][colOfRight];
                        if (gamePane.tileManager.getTilesList()[value].solid
                                || (rowOfTop * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                                && colOfRight * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                            entity.setCollisionOn(true);
                        }
                    }
                }
                case "DOWN" -> {
                    rowOfBottom = (rectBottomY + entity.getSpeed()) / gamePane.getTileSize();
                    value = gamePane.tileManager.getMapTileNumbers()[rowOfBottom][colOfLeft];
                    if (gamePane.tileManager.getTilesList()[value].solid
                            || (rowOfBottom * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                            && colOfLeft * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                        entity.setCollisionOn(true);
                    } else {
                        value = gamePane.tileManager.getMapTileNumbers()[rowOfBottom][colOfRight];
                        if (gamePane.tileManager.getTilesList()[value].solid
                                || (rowOfBottom * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                                && colOfRight * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                            entity.setCollisionOn(true);
                        }
                    }
                }
                case "LEFT" -> {
                    colOfLeft = (rectLeftX - entity.getSpeed()) / gamePane.getTileSize();
                    value = gamePane.tileManager.getMapTileNumbers()[rowOfTop][colOfLeft];
                    if (gamePane.tileManager.getTilesList()[value].solid
                            || (rowOfTop * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                            && colOfLeft * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                        entity.setCollisionOn(true);
                    } else {
                        value = gamePane.tileManager.getMapTileNumbers()[rowOfBottom][colOfLeft];
                        if (gamePane.tileManager.getTilesList()[value].solid
                                || (rowOfBottom * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                                && colOfLeft * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                            entity.setCollisionOn(true);
                        }
                    }
                }
                case "RIGHT" -> {
                    colOfRight = (rectRightX + entity.getSpeed()) / gamePane.getTileSize();
                    value = gamePane.tileManager.getMapTileNumbers()[rowOfTop][colOfRight];
                    if (gamePane.tileManager.getTilesList()[value].solid
                            || (rowOfTop * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                            && colOfRight * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                        entity.setCollisionOn(true);
                    } else {
                        value = gamePane.tileManager.getMapTileNumbers()[rowOfBottom][colOfRight];
                        if (gamePane.tileManager.getTilesList()[value].solid
                                || (rowOfBottom * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldY()
                                && colOfRight * gamePane.getTileSize() == gamePane.itemManager.getAllItems().get("chest").getWorldX())) {
                            entity.setCollisionOn(true);
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.setCollisionOn(true);
        }
    }

    public void checkItem(Entity entity) {
        for (String i : gamePane.itemManager.getAllItems().keySet()) {
            Item item = gamePane.itemManager.getAllItems().get(i);
            // if we have collision with an item then we take it
            if (item.getWorldX() <= entity.getRectangle().getX() && item.getWorldX() + gamePane.getTileSize() >= entity.getRectangle().getX()
                    && item.getWorldY() <= entity.getRectangle().getY()
                    && item.getWorldY() + gamePane.getTileSize() >= entity.getRectangle().getY()
                    && !item.getIsTaken()) {
                if (!item.getName().equals("chest")) {
                    item.setIsTaken(true);
                    gamePane.inventory.list.add(item);
                }
            }
        }
    }

    public void checkChest(Entity entity) {
        Chest chest = (Chest) gamePane.itemManager.getAllItems().get("chest");
        if ((chest.getWorldX() - gamePane.getTileSize() * 2) <= entity.getRectangle().getX() && (chest.getWorldX() + gamePane.getTileSize() * 3) >= entity.getRectangle().getX()
                && (chest.getWorldY() - gamePane.getTileSize() * 2) <= entity.getRectangle().getY() && (chest.getWorldY() + gamePane.getTileSize() * 3) >= entity.getRectangle().getY()
                && !chest.getIsOpened() && gamePane.keyHandler.chestPressed) {
            for (Item item : gamePane.inventory.list) {
                if (item.getName().equals("key")) {
                    chest.setIsOpened(true);
                    gamePane.inventory.list.remove(gamePane.itemManager.getAllItems().get("key"));
                    gamePane.inventory.list.addAll(chest.inside);
                    break;
                }
            }
        }
    }

}

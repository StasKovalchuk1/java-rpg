import entities.EnemiesList;
import entities.Enemy;
import entities.Ork;
import entities.Player;
import items.*;
import javafx.scene.shape.Rectangle;
import main.CollisionCheck;
import main.Controller;
import main.KeyHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegrationTests {

    @Test
    public void testChestOpeningWithKey() {
            Controller controller = new Controller();
            KeyHandler keyHandler = new KeyHandler();
            controller.keyHandler = keyHandler;

            CollisionCheck collisionCheck = new CollisionCheck(controller);

            Inventory inventory = new Inventory(controller);
            controller.inventory = inventory;

            Player player = new Player(keyHandler);
            controller.player = player;

            controller.player.setHitbox(new Rectangle(100, 100, 32, 32));

            // SET CHEST
            Chest chest = new Chest();
            chest.setWorldX(100);
            chest.setWorldY(50);

            // SET ITEMS
            Key key = new Key();
            key.setName("key");
            Sword sword = new Sword();
            chest.getItemsInside().add(sword);
            collisionCheck.setChest(chest);
            inventory.getInventory().add(key);

            // OPEN CHEST WITH KEY
            controller.keyHandler.chestPressed = true;
            collisionCheck.checkChest(controller.player);
            Assertions.assertTrue(chest.getIsOpened());
            Assertions.assertTrue(controller.inventory.getInventory().contains(sword));
            Assertions.assertFalse(controller.inventory.getInventory().contains(key));
    }

    @Test
    public void testChestOpeningWithoutKey() {
            Controller controller = new Controller();
            KeyHandler keyHandler = new KeyHandler();
            controller.keyHandler = keyHandler;

            CollisionCheck collisionCheck = new CollisionCheck(controller);

            Inventory inventory = new Inventory(controller);
            controller.inventory = inventory;

            Player player = new Player(keyHandler);
            controller.player = player;

            ItemManager itemManager = new ItemManager();
            controller.itemManager = itemManager;

            player.setHitbox(new Rectangle(100, 100, 32, 32));

            // SET CHEST
            Chest chest = new Chest();
            chest.setWorldX(100);
            chest.setWorldY(50);

            // SET ITEMS
            Key key = new Key();
            key.setName("key");
            key.setWorldX(150);
            key.setWorldY(150);
            Sword sword = new Sword();
            collisionCheck.setChest(chest);
            itemManager.getAllItems().add(key);

            // OPEN CHEST WITHOUT KEY
            controller.keyHandler.chestPressed = true;
            collisionCheck.checkChest(player);

            Assertions.assertFalse(chest.getIsOpened());
            Assertions.assertFalse(inventory.getInventory().contains(sword));

            chest.getItemsInside().add(sword);

            // PICK UP KEY
            controller.player.setHitbox(new Rectangle(150, 150, 32, 32));
            collisionCheck.checkItem(player);

            // MOVE TO THE CHEST
            controller.player.getHitbox().setX(100);
            controller.player.getHitbox().setY(50);

            collisionCheck.checkChest(controller.player);

            // OPEN CHEST WITH KEY
            Assertions.assertTrue(chest.getIsOpened());
            Assertions.assertTrue(inventory.getInventory().contains(sword));
            Assertions.assertFalse(inventory.getInventory().contains(key));
    }

    @Test
    public void testChestOpeningNoSpaceForKey() {
            Controller controller = new Controller();
            KeyHandler keyHandler = new KeyHandler();
            controller.keyHandler = keyHandler;

            CollisionCheck collisionCheck = new CollisionCheck(controller);

            Inventory inventory = new Inventory(controller);
            inventory.setMaxListSize(0);
            controller.inventory = inventory;

            Player player = new Player(keyHandler);
            controller.player = player;

            ItemManager itemManager = new ItemManager();
            controller.itemManager = itemManager;

            player.setHitbox(new Rectangle(100, 100, 32, 32));

            // SET CHEST
            Chest chest = new Chest();
            chest.setWorldX(100);
            chest.setWorldY(50);

            // SET ITEMS
            Key key = new Key();
            key.setName("key");
            key.setWorldX(150);
            key.setWorldY(150);
            Sword sword = new Sword();
            collisionCheck.setChest(chest);
            itemManager.getAllItems().add(key);

            controller.keyHandler.chestPressed = true;
            collisionCheck.checkChest(player);

            // OPEN CHEST WITHOUT KEY
            Assertions.assertFalse(chest.getIsOpened());
            Assertions.assertFalse(inventory.getInventory().contains(sword));

            chest.getItemsInside().add(sword);

            // TRYING TO PICK UP KEY
            controller.player.setHitbox(new Rectangle(150, 150, 32, 32));
            collisionCheck.checkItem(player);

            // NO SPACE FOR KEY IN INVENTORY
            Assertions.assertFalse(inventory.getInventory().contains(key));
    }

    @Test
    public void testGameOverWhenHeroDie() {
            Controller controller = new Controller();
            KeyHandler keyHandler = new KeyHandler();
            controller.keyHandler = keyHandler;

            Player player = new Player(keyHandler);
            controller.player = player;
            player.setAlive(false);

            EnemiesList enemiesList = new EnemiesList();
            Enemy enemy = new Ork();
            enemiesList.getEnemiesList().add(enemy);
            controller.enemiesList = enemiesList;

            controller.gameModel.setGameOver(controller.checkGameOver());

            Assertions.assertEquals(true, controller.gameModel.isGameOver());
    }
}

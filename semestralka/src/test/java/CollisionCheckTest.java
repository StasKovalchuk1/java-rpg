import entities.Entity;
import items.Chest;
import items.Inventory;
import javafx.scene.shape.Rectangle;
import main.CollisionCheck;
import main.Controller;
import items.ItemManager;
import main.KeyHandler;
import model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class CollisionCheckTest {
    Controller controller = mock(Controller.class);

    CollisionCheck collisionCheck;

    @ParameterizedTest
    @MethodSource("testDataForCheckHit")
    public void checkHitTest(boolean oppositeDirection, boolean defending, Rectangle attackHitbox, Rectangle getHitbox,
                             boolean expectedGetHit, boolean expectedBlockHit) {
        collisionCheck = new CollisionCheck();
        Entity entityAttack = mock(Entity.class);
        Entity entityGetHit = mock(Entity.class);

        when(entityAttack.getAlive()).thenReturn(true);
        when(entityGetHit.getAlive()).thenReturn(true);

        when(entityAttack.getAttackHitbox()).thenReturn(attackHitbox);
        when(entityGetHit.getHitbox()).thenReturn(getHitbox);

        when(entityAttack.checkOppositeDirection(entityAttack, entityGetHit)).thenReturn(oppositeDirection);
        when(entityGetHit.isDefending()).thenReturn(defending);

        collisionCheck.checkHit(entityAttack, entityGetHit);

        verify(entityGetHit, times(expectedGetHit ? 1 : 0)).getHitProcess();
        verify(entityGetHit, times(expectedBlockHit ? 1 : 0)).blockHitProcess();
    }

    private static Stream<Arguments> testDataForCheckHit() {
        return Stream.of(
                // data for GetHitTest
                Arguments.of(false, false, new Rectangle(100, 100, 32, 32),
                        new Rectangle(100, 68, 32, 32), true, false),
                // data for MissHitTest
                Arguments.of(false, false, new Rectangle(100, 100, 32, 32),
                        new Rectangle(100, 67, 32, 32), false, false),
                // data for BlockHitTest
                Arguments.of(true, true, new Rectangle(100, 100, 32, 32),
                        new Rectangle(100, 68, 32, 32), false, true),
                // data for NotBlockHitTest
                Arguments.of(false, true, new Rectangle(100, 100, 32, 32),
                        new Rectangle(100, 68, 32, 32), true, false)
        );
    }
//
//    @Test
//    public void checkGetHitTest() {
//        collisionCheck = new CollisionCheck();
//
//        Entity entityAttack = mock(Entity.class);
//        Entity entityGetHit = mock(Entity.class);
//
//        when(entityAttack.getAlive()).thenReturn(true);
//        when(entityGetHit.getAlive()).thenReturn(true);
//
//        when(entityAttack.getAttackHitbox()).thenReturn(new Rectangle(100, 100, 32, 32));
//        when(entityGetHit.getHitbox()).thenReturn(new Rectangle(100, 68, 32, 32));
//
//        when(entityAttack.checkOppositeDirection(entityAttack, entityGetHit)).thenReturn(false);
//        when(entityGetHit.isDefending()).thenReturn(false);
//
//        collisionCheck.checkHit(entityAttack, entityGetHit);
//
//        verify(entityGetHit, times(1)).getHitProcess();
//        verify(entityGetHit, times(0)).blockHitProcess();
//    }
//
//    @Test
//    public void checkMissHitTest() {
//        collisionCheck = new CollisionCheck();
//        Entity entityAttack = mock(Entity.class);
//        Entity entityGetHit = mock(Entity.class);
//
//        when(entityAttack.getAlive()).thenReturn(true);
//        when(entityGetHit.getAlive()).thenReturn(true);
//
//        when(entityAttack.getAttackHitbox()).thenReturn(new Rectangle(100, 100, 32, 32));
//        when(entityGetHit.getHitbox()).thenReturn(new Rectangle(100, 67, 32, 32));
//
//        when(entityAttack.checkOppositeDirection(entityAttack, entityGetHit)).thenReturn(false);
//        when(entityGetHit.isDefending()).thenReturn(false);
//
//        collisionCheck.checkHit(entityAttack, entityGetHit);
//
//        verify(entityGetHit, times(0)).getHitProcess();
//        verify(entityGetHit, times(0)).blockHitProcess();
//    }
//
//    @Test
//    public void checkBlockHitTest() {
//        collisionCheck = new CollisionCheck();
//        Entity entityAttack = mock(Entity.class);
//        Entity entityGetHit = mock(Entity.class);
//
//        when(entityAttack.getAlive()).thenReturn(true);
//        when(entityGetHit.getAlive()).thenReturn(true);
//
//        when(entityAttack.getAttackHitbox()).thenReturn(new Rectangle(100, 100, 32, 32));
//        when(entityGetHit.getHitbox()).thenReturn(new Rectangle(100, 68, 32, 32));
//
//        when(entityAttack.checkOppositeDirection(entityAttack, entityGetHit)).thenReturn(true);
//        when(entityGetHit.isDefending()).thenReturn(true);
//
//        collisionCheck.checkHit(entityAttack, entityGetHit);
//
//        verify(entityGetHit, times(0)).getHitProcess();
//        verify(entityGetHit, times(1)).blockHitProcess();
//    }
//
//    @Test
//    public void checkNotBlockHitTest() {
//        collisionCheck = new CollisionCheck();
//        Entity entityAttack = mock(Entity.class);
//        Entity entityGetHit = mock(Entity.class);
//
//        when(entityAttack.getAlive()).thenReturn(true);
//        when(entityGetHit.getAlive()).thenReturn(true);
//
//        when(entityAttack.getAttackHitbox()).thenReturn(new Rectangle(100, 100, 32, 32));
//        when(entityGetHit.getHitbox()).thenReturn(new Rectangle(100, 68, 32, 32));
//
//        when(entityAttack.checkOppositeDirection(entityAttack, entityGetHit)).thenReturn(false);
//        when(entityGetHit.isDefending()).thenReturn(true);
//
//        collisionCheck.checkHit(entityAttack, entityGetHit);
//
//        verify(entityGetHit, times(1)).getHitProcess();
//        verify(entityGetHit, times(0)).blockHitProcess();
//    }

    @Test
    public void checkItemTest() throws Exception {
        Entity entity = mock(Entity.class);
        List<Item> items = new ArrayList<>();
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);
        Item item4 = mock(Item.class);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);

        // change itemManger field in Controller to the mock one
        ItemManager itemManager = mock(ItemManager.class);
        doNothing().when(itemManager).getItems();
        when(itemManager.getAllItems()).thenReturn(items);

        Field itemManagerField = Controller.class.getDeclaredField("itemManager");
        itemManagerField.setAccessible(true);
        itemManagerField.set(controller, itemManager);

        collisionCheck = new CollisionCheck(controller);

        when(item1.getWorldX()).thenReturn(100);
        when(item1.getWorldY()).thenReturn(100);
        when(item1.getName()).thenReturn("key");
        when(item1.getIsTaken()).thenReturn(false);

        when(item2.getWorldX()).thenReturn(50);
        when(item2.getWorldY()).thenReturn(50);
        when(item2.getName()).thenReturn("sword");
        when(item2.getIsTaken()).thenReturn(false);

        when(item3.getWorldX()).thenReturn(75);
        when(item3.getWorldY()).thenReturn(75);
        when(item3.getName()).thenReturn("chest");
        when(item3.getIsTaken()).thenReturn(false);

        when(item4.getWorldX()).thenReturn(75);
        when(item4.getWorldY()).thenReturn(75);
        when(item4.getName()).thenReturn("chest");
        when(item4.getIsTaken()).thenReturn(true);


        when(entity.getHitbox()).thenReturn(new Rectangle(100, 100, 32, 32));

        // change inventory field in Controller to the mock
        Inventory inventory = mock(Inventory.class);
        ArrayList<Item> inventoryList = new ArrayList<>();
        inventoryList.add(item2);
        when(inventory.getInventory()).thenReturn(inventoryList);

        Field inventoryField = Controller.class.getDeclaredField("inventory");
        inventoryField.setAccessible(true);
        inventoryField.set(controller, inventory);

        when(controller.inventory.getInventory()).thenReturn(inventoryList);
        when(controller.inventory.getMaxListSize()).thenReturn(15);

        collisionCheck.checkItem(entity);

        verify(item1, times(1)).getName();
        verify(item2, times(0)).getName();
        verify(item3, times(1)).getName();
        verify(item4, times(0)).getName();

        verify(item1, times(1)).setIsTaken(true);
        verify(item2, times(0)).setIsTaken(true);
        verify(item3, times(0)).setIsTaken(true);
        verify(item4, times(0)).setIsTaken(true);
    }

    @Test
    public void checkChestTest() throws Exception {
        // change itemManager field in Controller to the mock one
        ItemManager itemManager = mock(ItemManager.class);
        doNothing().when(itemManager).getItems();

        Field itemManagerField = Controller.class.getDeclaredField("itemManager");
        itemManagerField.setAccessible(true);
        itemManagerField.set(controller, itemManager);

        collisionCheck = new CollisionCheck(controller);
        Entity entity = mock(Entity.class);
        when(entity.getHitbox()).thenReturn(new Rectangle(100, 100, 32, 32));

        // change keyHandler field in Controller to the mock one
        KeyHandler keyHandler = mock(KeyHandler.class);
        keyHandler.chestPressed = true;

        Field keyHandlerField = Controller.class.getDeclaredField("keyHandler");
        keyHandlerField.setAccessible(true);
        keyHandlerField.set(controller, keyHandler);

        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        when(item1.getName()).thenReturn("sword");
        when(item2.getName()).thenReturn("shield");
        List<Item> insideChest = new ArrayList<>();
        insideChest.add(item1);
        insideChest.add(item2);

        // change chest field in Controller
        Chest chest = new Chest();
        chest.setItemsInside(insideChest);
        chest.setWorldX(100);
        chest.setWorldY(50);

        Field chestField = CollisionCheck.class.getDeclaredField("chest");
        chestField.setAccessible(true);
        chestField.set(collisionCheck, chest);

        Item item3 = mock(Item.class);
        when(item3.getName()).thenReturn("key");

        // change inventory field in Controller
        Inventory inventory = new Inventory(controller);

        Field inventoryField = Controller.class.getDeclaredField("inventory");
        inventoryField.setAccessible(true);
        inventoryField.set(controller, inventory);

        ArrayList<Item> inventoryList = new ArrayList<>();
        inventoryList.add(item3);
        inventory.setInventory(inventoryList);
        inventory.setMaxListSize(15);

        collisionCheck.checkChest(entity);

        Assertions.assertEquals(2, inventory.getInventory().size());
        Assertions.assertEquals(0, chest.getItemsInside().size());
    }

}

import entities.Player;
import items.ItemManager;
import items.Key;
import main.CollisionCheck;
import main.Controller;
import main.KeyHandler;
import map.TileManager;
import model.GameModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

/**
 * Test if player can move
 */
public class PlayerTest {

    @Test
    public void testHandleMoving() throws Exception {
        KeyHandler keyHandler = new KeyHandler();
        Player player = new Player(keyHandler);

        Field keyHandlerField = Player.class.getDeclaredField("keyHandler");
        keyHandlerField.setAccessible(true);
        keyHandlerField.set(player, keyHandler);

        player.setSpeed(5);

        // move UP
        player.setWorldX(20);
        player.setWorldY(20);

        keyHandler.upPressed = true;
        keyHandler.downPressed = false;
        keyHandler.rightPressed = false;
        keyHandler.leftPressed = false;

        player.handleMoving();

        Assertions.assertEquals(15, player.getWorldY());
        Assertions.assertEquals(20, player.getWorldX());

        // move DOWN
        player.setWorldX(20);
        player.setWorldY(20);

        keyHandler.upPressed = false;
        keyHandler.downPressed = true;
        keyHandler.rightPressed = false;
        keyHandler.leftPressed = false;

        player.handleMoving();

        Assertions.assertEquals(25, player.getWorldY());
        Assertions.assertEquals(20, player.getWorldX());

        // move RIGHT
        player.setWorldX(20);
        player.setWorldY(20);

        keyHandler.upPressed = false;
        keyHandler.downPressed = false;
        keyHandler.rightPressed = true;
        keyHandler.leftPressed = false;

        player.handleMoving();

        Assertions.assertEquals(20, player.getWorldY());
        Assertions.assertEquals(25, player.getWorldX());

        // move LEFT
        player.setWorldX(20);
        player.setWorldY(20);

        keyHandler.upPressed = false;
        keyHandler.downPressed = false;
        keyHandler.rightPressed = false;
        keyHandler.leftPressed = true;

        player.handleMoving();

        Assertions.assertEquals(20, player.getWorldY());
        Assertions.assertEquals(15, player.getWorldX());
    }

}

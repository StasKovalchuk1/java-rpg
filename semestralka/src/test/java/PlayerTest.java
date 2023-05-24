import entities.Player;
import items.Key;
import main.CollisionCheck;
import main.Controller;
import main.KeyHandler;
import model.GameModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Test if player can move
 */
public class PlayerTest {
    private static Player player;
    private static final KeyHandler keyHandler = new KeyHandler();
    private GameModel gameModel = new GameModel();
    private static CollisionCheck collisionCheck;
    private static Controller controller;

    @Test
    public void testHandleMoving() {
        collisionCheck = mock(CollisionCheck.class);
        controller = mock(Controller.class);
        player = new Player(keyHandler);

        doNothing().when(collisionCheck).checkTile(player);
        doNothing().when(collisionCheck).checkEntity(player, player);
        doNothing().when(collisionCheck).checkItem(player);
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

import entities.Player;
import main.KeyHandler;
import main.MyLogger;
import data.FilesModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("testDataForSavePlayer")
    public void savePlayerTest(int worldX, int worldY, int lives, String expectedResult) throws IOException {
        Player player = new Player();
        player.setWorldX(worldX);
        player.setWorldY(worldY);
        player.setLives(lives);
        player.savePlayer();

        String result = null;
        FileReader fileReader = new FileReader(FilesModel.getEntitiesFile());
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] arrLine = line.split(" ");
            if (arrLine[0].equals("hero")){
                result = line;
            }
        }
        Assertions.assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> testDataForSavePlayer() {
        return Stream.of(
                Arguments.of(48, 96, 1, "hero 2 1 1"),
                Arguments.of(72, 120, 2, "hero 2 1 2"),
                Arguments.of(73, 121, 3, "hero 3 2 3")
        );
    }

}

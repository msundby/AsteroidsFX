import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
public class PlayerTest {

    @Test
    public void testMovement() {
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        GameData gameData = new GameData();
        World world = new World();
        Player player = new Player("Player");
        world.addEntity(player);

        gameData.getKeys().setKey(GameKeys.UP, true);
        playerControlSystem.process(gameData, world);

        assertNotEquals(0, player.getX());
        assertNotEquals(0, player.getY());
    }
}

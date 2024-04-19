import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.collision.CollisionPostProcessingSystem;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CollisionTest {
    @Test
    public void testCollsion() {
        CollisionPostProcessingSystem collisionPostProcessingSystem = new CollisionPostProcessingSystem();
        GameData gameData = new GameData();
        World world = new World();
        Player player = new Player("Player");
        Entity enemyBullet = new Entity("Enemy Bullet");
        world.addEntity(player);
        world.addEntity(enemyBullet);

        player.setX(5);
        player.setY(5);
        enemyBullet.setX(5);
        enemyBullet.setY(5);

        collisionPostProcessingSystem.process(gameData, world);

        assertFalse(world.getEntities().contains(player));
        assertFalse(world.getEntities().contains(enemyBullet));
    }
}

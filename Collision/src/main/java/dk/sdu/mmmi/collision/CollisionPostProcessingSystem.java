package dk.sdu.mmmi.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionPostProcessingSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        System.out.println(world.getEntities().size());
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.equals(e2)) continue;
//                if ((e1.getName().equals("Player") && e2.getName().equals("Player Bullet")) ||
//                        e2.getName().equals("Player") && e1.getName().equals("Player Bullet")) {
//                    continue;
//                }
//
//                if ((e1.getName().equals("Asteroid") && e2.getName().equals("Asteroid")) ||
//                        e2.getName().equals("Asteroid") && e1.getName().equals("Asteroid")) {
//                    continue;
//                }
//
//                if ((e1.getName().equals("Enemy") && e2.getName().equals("Enemy Bullet")) ||
//                        e2.getName().equals("Enemy") && e1.getName().equals("Enemy Bullet")) {
//                    continue;
//                }
//
//                if ((e1.getName().equals("Enemy Bullet") && e2.getName().equals("Enemy Bullet")) ||
//                        e2.getName().equals("Enemy Bullet") && e1.getName().equals("Enemy Bullet")) {
//                    continue;
//                }

                double distanceX = e1.getX() - e2.getX();
                double distanceY = e1.getY() - e2.getY();
                double distanceBetween = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

                if (distanceBetween < 10) {
                    if ((e1.getName().equals("Enemy Bullet") && e2.getName().equals("Player")) ||
                            e2.getName().equals("Player") && e1.getName().equals("Enemy Bullet")) {
                        world.removeEntity(e1);
                        world.removeEntity(e2);
                    }

                }
            }
        }
    }
}

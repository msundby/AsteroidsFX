package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.Timer;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private Entity enemy;

    @Override
    public void process(GameData gameData, World world) {
        System.out.println(gameData.getTime());
        gameData.setTime(gameData.getTime() + 1);

        if(gameData.getTime() == 250){
            enemy = createEnemyShipFromLeft(gameData);
            world.addEntity(enemy);
            gameData.setTime(0);
        }

        for(Entity enemy : world.getEntities(Enemy.class)){
            enemy.setX(enemy.getX() + 1);
        }

    }

    private Entity createEnemyShipFromLeft(GameData gameData) {
        double randomHeight = 1 + (Math.random() * gameData.getDisplayHeight());
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-15, 0, -7, 7, 7, 7, 15, 0, 7, -7, -7, -7);
        enemyShip.setX(-1);
        enemyShip.setY(randomHeight);
        return enemyShip;
    }

    private Entity createEnemyShipFromRight(GameData gameData) {
        double randomHeight = 1 + (Math.random() * gameData.getDisplayHeight());
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-15, 0, -7, 7, 7, 7, 15, 0, 7, -7, -7, -7);
        enemyShip.setX(gameData.getDisplayWidth()-1);
        enemyShip.setY(randomHeight);
        return enemyShip;
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Timer;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    public EnemyPlugin(){
    }

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShipFromLeft(gameData);
        world.addEntity(enemy);

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

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}

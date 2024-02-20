package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    public EnemyPlugin(){
    }
    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData){
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-15, 0, -7, 7, 7, 7, 15, 0, 7, -7, -7, -7);
        enemyShip.setX(-1);
        enemyShip.setY(gameData.getDisplayHeight() /3);
        return enemyShip;
    }



    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}

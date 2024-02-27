package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    volatile double playerX = 0;
    volatile double playerY = 0;

    @Override
    public void process(GameData gameData, World world) {


        for (Entity player : world.getEntities()) {
            if (player.getName().equals("Player")) {
                playerX = player.getX();
                playerY = player.getY();
            }
        }

        for (Entity bullet : world.getEntities(Bullet.class)) {
                double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
                double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
                bullet.setX(bullet.getX() + changeX * 3);
                bullet.setY(bullet.getY() + changeY * 3);
        }
    }

        @Override
        public Entity createBullet (Entity shooter, GameData gameData){

            Entity bullet;

            if (shooter.getName().equals("Player")) {
                bullet = new Bullet("Player Bullet");
                bullet.setPolygonCoordinates(2, -2, 2, 2, -2, 2, -2, -2);
                bullet.setX(shooter.getX());
                bullet.setY(shooter.getY());
                bullet.setRotation(shooter.getRotation());
            } else {
                bullet = new Bullet("Enemy Bullet");
                bullet.setPolygonCoordinates(2, -2, 2, 2, -2, 2, -2, -2);
                bullet.setX(shooter.getX());
                bullet.setY(shooter.getY());

                double dx = playerX - shooter.getX();
                double dy = playerY - shooter.getY();
                double angleToCenter = Math.toDegrees(Math.atan2(dy, dx));

                if(angleToCenter < 0){
                    angleToCenter += 360;
                }

                bullet.setRotation(angleToCenter);

                System.out.println("X = " + playerX);
                System.out.println("Y = " + playerY);
                System.out.println("Angle to center = " + angleToCenter);


            }
            return bullet;
        }



    }

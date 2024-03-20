import dk.sdu.mmmi.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Bullet {
    uses dk.sdu.mmmi.cbse.common.services.TargetSPI;
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with BulletPlugin;
    provides BulletSPI with BulletControlSystem;
    provides IEntityProcessingService with BulletControlSystem;
}
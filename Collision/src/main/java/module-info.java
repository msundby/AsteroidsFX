import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.collision.CollisionPostProcessingSystem;

module Collision {
    requires Common;
    requires CommonBullet;
    provides IPostEntityProcessingService with CollisionPostProcessingSystem;
}

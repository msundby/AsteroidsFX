import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.collision.CollisionPostProcessingSystem;

module Collision {
    requires Common;
    requires CommonBullet;
    requires java.net.http;
    provides IPostEntityProcessingService with dk.sdu.mmmi.collision.CollisionPostProcessingSystem;
    exports dk.sdu.mmmi.collision;

}

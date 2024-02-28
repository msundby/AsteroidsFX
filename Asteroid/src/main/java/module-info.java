
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.TargetSPI;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    provides IGamePluginService with dk.sdu.mmmi.cbse.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.AsteroidControlSystem;
    
}

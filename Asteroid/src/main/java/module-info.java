module Asteroid {
  requires Common;
  requires CommonAsteroid;
  provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with asteroidsystem.AsteroidPlugin;
  provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService with asteroidsystem.AsteroidControlSystem;
}
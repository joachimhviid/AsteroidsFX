package asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
  @Override
  public void start(GameData gameData, World world) {
    // Create asteroids on a set timer
    // Only 10 asteroids present at a time
    // Destroy asteroids when they leave the screen
    // Asteroids move in a random direction
    Entity asteroid = createAsteroid(gameData);
    world.addEntity(asteroid);
  }

  public Entity createAsteroid(GameData gameData) {
    Entity asteroid = new Asteroid();
    asteroid.setPolygonCoordinates(-5, -5, 5, -5, 5, 5, -5, 5);
    // Asteroids spawn at a random edge location
    int edge = (int) (Math.random() * 4);
    switch (edge) {
      case 0:
        asteroid.setX(0);
        asteroid.setY(Math.random() * gameData.getDisplayHeight());
        // Rotation is +- 90 degrees
        asteroid.setRotation(Math.random() * 180 - 90);
        break;
      case 1:
        asteroid.setX(gameData.getDisplayWidth());
        asteroid.setY(Math.random() * gameData.getDisplayHeight());
        // Rotation is between 90 and 270 degrees
        asteroid.setRotation(Math.random() * 180 + 90);
        break;
      case 2:
        asteroid.setX(Math.random() * gameData.getDisplayWidth());
        asteroid.setY(0);
        // Rotation is between 0 and 180 degrees
        asteroid.setRotation(Math.random() * 180);
        break;
      case 3:
        asteroid.setX(Math.random() * gameData.getDisplayWidth());
        asteroid.setY(gameData.getDisplayHeight());
        // Rotation is between 180 and 360 degrees
        asteroid.setRotation(Math.random() * 180 + 180);
        break;
    }
    asteroid.setColor("WHITE");
    return asteroid;
  }

  @Override
  public void stop(GameData gameData, World world) {
    for (Entity e : world.getEntities()) {
      if (e.getClass() == Asteroid.class) {
        if (e.getX() < 0 || e.getX() > gameData.getDisplayWidth() || e.getY() < 0 || e.getY() > gameData.getDisplayHeight()) {
          world.removeEntity(e);
        }
      }
    }
  }
}

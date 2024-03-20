package asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {
  @Override
  public void process(GameData gameData, World world) {
    for (Entity asteroid : world.getEntities(Asteroid.class)) {
      double dX = Math.cos(Math.toRadians(asteroid.getRotation())) * ((Asteroid) asteroid).getSpeed();
      double dY = Math.sin(Math.toRadians(asteroid.getRotation())) * ((Asteroid) asteroid).getSpeed();
      asteroid.setX(asteroid.getX() + dX);
      asteroid.setY(asteroid.getY() + dY);
    }
  }
}

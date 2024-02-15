package asteroidsystem;

import asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {
  @Override
  public void process(GameData gameData, World world) {

  }

  @Override
  public Entity createAsteroid(Entity e, GameData gameData) {
    return null;
  }
}

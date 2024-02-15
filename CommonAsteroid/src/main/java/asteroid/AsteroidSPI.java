package asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface AsteroidSPI {
  Entity createAsteroid(Entity e, GameData gameData);
}

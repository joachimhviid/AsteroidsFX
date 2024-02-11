package enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
  private Entity enemy;

  public EnemyPlugin() {
  }

  @Override
  public void start(GameData gameData, World world) {
    enemy = createEnemyShip(gameData);
    world.addEntity(enemy);
  }

  private Entity createEnemyShip(GameData gameData) {
    Entity enemyShip = new Enemy();
    enemyShip.setPolygonCoordinates(-8, -11, 13, 0, -8, 11);
    enemyShip.setX(gameData.getDisplayHeight() / (Math.random() * 5));
    enemyShip.setY(gameData.getDisplayWidth() / (Math.random() * 5));
    enemyShip.setColor("TOMATO");
    return enemyShip;
  }

  @Override
  public void stop(GameData gameData, World world) {
    world.removeEntity(enemy);
  }
}

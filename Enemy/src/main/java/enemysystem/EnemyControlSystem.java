package enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

  @Override
  public void process(GameData gameData, World world) {
    for (Entity enemy : world.getEntities(Enemy.class)) {
      if (Math.random() > 0.5) {
        enemy.setRotation(enemy.getRotation() + 1);
      } else {
        enemy.setRotation(enemy.getRotation() - 1);
      }

      if (Math.random() > 0.9) {
        Collection<? extends BulletSPI> bulletSPIs = getBulletSPIs();
        for (BulletSPI bulletSPI : bulletSPIs) {
          Entity bullet = bulletSPI.createBullet(enemy, gameData);
          world.addEntity(bullet);
        }
      }

      // TODO: Implement enemy movement
      if (enemy.getX() < 0) {
        enemy.setX(1);
      }

      if (enemy.getX() > gameData.getDisplayWidth()) {
        enemy.setX(gameData.getDisplayWidth() - 1);
      }

      if (enemy.getY() < 0) {
        enemy.setY(1);
      }

      if (enemy.getY() > gameData.getDisplayHeight()) {
        enemy.setY(gameData.getDisplayHeight() - 1);
      }

    }
  }

  private Collection<? extends BulletSPI> getBulletSPIs() {
    return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
  }
}

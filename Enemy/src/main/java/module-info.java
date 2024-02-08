import enemysystem.EnemyControlSystem;
import enemysystem.EnemyPlugin;

module Enemy {
    requires Common;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with EnemyPlugin;
    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService with EnemyControlSystem;
}
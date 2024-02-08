package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private double speed = 5.0;
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double dX = Math.cos(Math.toRadians(bullet.getRotation())) * this.speed;
            double dY = Math.sin(Math.toRadians(bullet.getRotation())) * this.speed;
            bullet.setX(bullet.getX() + dX);
            bullet.setY(bullet.getY() + dY);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(-2, -2, 2, -2, 2, 2, -2, 2);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation());

        return bullet;
    }

    private void setShape(Entity entity) {
    }

}

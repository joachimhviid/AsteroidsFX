package enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {
  private long lastBulletTime = 0;
  private final long bulletCooldown = 500000000; // 0.5 second in nanoseconds

  public long getLastBulletTime() {
    return lastBulletTime;
  }

  public void setLastBulletTime(long lastBulletTime) {
    this.lastBulletTime = lastBulletTime;
  }

  public boolean canShoot(long currentTime) {
    return currentTime - lastBulletTime > bulletCooldown;
  }
}

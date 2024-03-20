package asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
  private final double speed;
  public Asteroid() {
    this.speed = Math.random() * 10;
  }

  public double getSpeed() {
    return speed;
  }
}

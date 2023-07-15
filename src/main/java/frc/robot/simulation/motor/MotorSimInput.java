package frc.robot.simulation.motor;

import frc.robot.simulation.framework.SimInputDoubleInterface;

/**
 * Helper class to implement SimInputDoubleInterface.
 */
public class MotorSimInput implements SimInputDoubleInterface {
  /**
   * Constructor.
   */
  MotorSimInput() {
  }

  @Override
  public Double getInput() {
    return 0.0;
  }
}

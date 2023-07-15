package frc.robot.simulation.motor;

import frc.robot.simulation.framework.SimInputInterface;

/**
 * Helper class to implement SimInputDoubleInterface.
 */
public class MotorSimInput implements SimInputInterface<Double> {
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

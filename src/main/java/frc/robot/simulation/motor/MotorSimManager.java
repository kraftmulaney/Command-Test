package frc.robot.simulation.motor;

import frc.robot.simulation.framework.SimInputInterface;
import frc.robot.simulation.framework.SimManagerInterface;
import frc.robot.simulation.framework.SimOutputInterface;

/**
 * Simulation manager for a simple motor.
 */
public class MotorSimManager implements SimManagerInterface<Double, Double> {
  /**
   * Constructor.
   */
  public MotorSimManager() {
  }

  @Override
  public void setInputHandler(SimInputInterface<Double> inputHandler) {
  }

  @Override
  public void setOutputHandler(SimOutputInterface<Double> outputHandler) {
  }

  @Override
  public void simulationPeriodic() {
  }
}

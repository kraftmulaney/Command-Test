package frc.robot.simulation.motor;

import frc.robot.simulation.framework.SimManagerBase;

/**
 * Simulation manager for a simple motor.
 */
public class MotorSimManager extends SimManagerBase<Double, Double> {
  private final MotorSimModel m_model;

  /**
   * Constructor.
   */
  public MotorSimManager() {
    m_model = new MotorSimModel();
  }

  @Override
  protected void doSimulation() {
    // No need to call super, since it's abstract class and doesn't
    // implement doSimulation()

    // Step 1: Get the input from the input handler
    double motorPowerPercentage = m_inputHandler.getInput();

    // Step 2: Run the simulation for 20ms
    double newEncoderPosition = m_model.updateMotorPosition(motorPowerPercentage);

    // Step 3: Write the output to the output handler
    m_outputHandler.setOutput(newEncoderPosition);
  }
}

package frc.robot.simulation.motor;

import frc.robot.simulation.framework.SimInputInterface;
import frc.robot.simulation.framework.SimManagerInterface;
import frc.robot.simulation.framework.SimOutputInterface;

/**
 * Simulation manager for a simple motor.
 */
public class MotorSimManager implements SimManagerInterface<Double, Double> {
  private SimInputInterface<Double> m_inputHandler = null;
  private SimOutputInterface<Double> m_outputHandler = null;
  private final MotorSimModel m_model;

  /**
   * Constructor.
   */
  public MotorSimManager() {
    m_model = new MotorSimModel();
  }

  @Override
  public void setInputHandler(SimInputInterface<Double> inputHandler) {
    m_inputHandler = inputHandler;
  }

  @Override
  public void setOutputHandler(SimOutputInterface<Double> outputHandler) {
    m_outputHandler = outputHandler;
  }

  @Override
  public void simulationPeriodic() {
    // When Robot is disabled, the entire simulation freezes
    if (isRobotEnabled() && m_inputHandler != null && m_outputHandler != null) {

      double motorPowerPercentage = m_inputHandler.getInput();
      double newEncoderPosition = m_model.updateMotorPosition(motorPowerPercentage);
      m_outputHandler.setOutput(newEncoderPosition);
    }
  }
}

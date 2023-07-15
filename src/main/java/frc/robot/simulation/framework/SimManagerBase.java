package frc.robot.simulation.framework;

import edu.wpi.first.wpilibj.RobotState;

/**
 * Partially implements SimManagerInterface.
 */
public abstract class SimManagerBase<InputT, OutputT>
    implements SimManagerInterface<InputT, OutputT> {
  protected SimInputInterface<InputT> m_inputHandler = null;
  protected SimOutputInterface<OutputT> m_outputHandler = null;

  /**
   * Constructor.
   */
  public SimManagerBase() {
  }

  @Override
  public void setInputHandler(SimInputInterface<InputT> inputHandler) {
    m_inputHandler = inputHandler;
  }

  @Override
  public void setOutputHandler(SimOutputInterface<OutputT> outputHandler) {
    m_outputHandler = outputHandler;
  }

  private boolean isRobotEnabled() {
    return RobotState.isEnabled();
  }

  // Must be implemented by derived class
  protected abstract void doSimulation();

  // The following method cannot be further overriden by derived class
  @Override
  public final void simulationPeriodic() {
    // When Robot is disabled, the entire simulation freezes
    if (isRobotEnabled() && m_inputHandler != null && m_outputHandler != null) {
      this.doSimulation();
    }
  }
}

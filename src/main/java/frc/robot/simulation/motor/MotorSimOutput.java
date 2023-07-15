package frc.robot.simulation.motor;

import com.revrobotics.RelativeEncoder;
import frc.robot.simulation.framework.SimOutputInterface;
import frc.robot.subsystems.RelativeEncoderSim;

/**
 * Helper class to implement OutputDoubleInterface.
 */
public class MotorSimOutput implements SimOutputInterface<Double> {
  private final RelativeEncoderSim m_encoderRealWrapper;

  /**
   * Constructor.
   */
  MotorSimOutput(RelativeEncoder encoderReal) {
    // Create a "sim" wrapper of the Robots real encoder. The sim wrapper
    // is later used to write to the real encoder
    m_encoderRealWrapper = new RelativeEncoderSim(encoderReal);
  }

  @Override
  public void setOutput(Double output) {
    // Sets the encoder position in rotations.
    m_encoderRealWrapper.setPosition(output);
  }
}

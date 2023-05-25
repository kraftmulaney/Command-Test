package frc.robot.Simulation;

import edu.wpi.first.wpilibj.simulation.EncoderSim;

public class RelEncoderWrapper {
  private EncoderSim m_encoderSim;
  private double m_offset;

  // Constructor
  public RelEncoderWrapper(EncoderSim encoderSim) {
    m_encoderSim = encoderSim;
    reset();
  }

  public void reset() {
    m_offset = m_encoderSim.getDistance();
  }

  public double getDistance() {
    // We return the distance that the SIMULATED bot thinks it moved on the field, in METERS
    return m_encoderSim.getDistance() - m_offset;
  }
}

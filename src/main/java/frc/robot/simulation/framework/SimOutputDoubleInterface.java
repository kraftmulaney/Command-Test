package frc.robot.simulation.framework;

/**
 * Output of device is a Double.
 */
public interface SimOutputDoubleInterface extends SimOutputInterface<Double> {
  // Override set output
  @Override
  void setOutput(Double output);
}

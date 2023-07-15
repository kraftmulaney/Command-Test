package frc.robot.simulation.framework;

/**
 * Interface to set the output of a device. SimManager will call this method to set the output.
 */
public interface OutputInterface<T> {
  void setOutput(T output);
}

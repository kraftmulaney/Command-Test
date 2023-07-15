package frc.robot.simulation.framework;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Manages simulation of a particular device. It calls an input Supplier to periodically
 * query inputs to device, and calls the output Consumer to set the new state of the device.
 * It leverages a Model class to do the actual real-world simulation.
 */
public interface SimManagerInterface<InputT, OutputT> {
  void setInputSupplier(Supplier<InputT> inputSupplier);

  void setOutputConsumer(Consumer<OutputT> outputConsumer);

  void simulationPeriodic();
}

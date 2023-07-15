package frc.robot.simulation.framework;

import java.util.function.Consumer;

/**
 * Helper class to implement OutputDoubleInterface. Takes a lambda in the constructor.
 */
public class SimOutputDouble implements SimOutputDoubleInterface {
  private final Consumer<Double> m_consumer;

  SimOutputDouble(Consumer<Double> consumer) {
    if (consumer == null) {
      throw new IllegalArgumentException("Consumer cannot be null");
    }

    m_consumer = consumer;
  }

  @Override
  public void setOutput(Double output) {
    m_consumer.accept(output);
  }
}

package frc.robot.simulation.motor;

import frc.robot.simulation.framework.SimManagerInterface;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Simulation manager for a simple motor.
 */
public class MotorSimManager implements SimManagerInterface<Double, Double> {
  private Supplier<Double> m_inputSupplier;
  private Consumer<Double> m_outputConsumer;

  @Override
  public void setInputSupplier(Supplier<Double> inputSupplier) {
    m_inputSupplier = inputSupplier;
  }

  @Override
  public void setOutputConsumer(Consumer<Double> outputConsumer) {
    m_outputConsumer = outputConsumer;
  }

  @Override
  public void simulationPeriodic() {
    double input = m_inputSupplier.get();

    double output = 0;
    m_outputConsumer.accept(output);
  }
}

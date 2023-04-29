package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import java.util.Map;

import frc.robot.Constants;
import frc.robot.Simulation.ArmSimulation;
import frc.robot.Simulation.WinchSimulation;
import frc.robot.Simulation.WinchSimulation.StringOrientation;

public class ArmSystemSim extends ArmSystem {
  private RelativeEncoderSim m_winchEncoderSim;
  private DutyCycleEncoderSim m_winchAbsoluteEncoderSim;
  private DCMotor m_motorModel;
  private DCMotorSim m_motorSim;
  private double m_winchMotorOutputPercentage = 0;
  private WinchSimulation m_WinchSimulation;
  private ArmSimulation m_ArmSimulation;

  public static ArmSystem CreateArmSystemInstance(int armWinchChannel, int armExtenderChannel, XboxController m_controller, double m_deadband, boolean squareInputs, double maxOutputWinch)
  {
    ArmSystem result;
  
    if (RobotBase.isSimulation()) {
      result = new ArmSystemSim(
        armWinchChannel,
        armExtenderChannel,
        m_controller,
        m_deadband,
        squareInputs,
        maxOutputWinch);
    
      System.out.println("ARMSYSTEM: **** Simulation ****");
  
    } else {
      result = new ArmSystem(
        armWinchChannel,
        armExtenderChannel,
        m_controller,
        m_deadband,
        squareInputs,
        maxOutputWinch);
  
      System.out.println("ARMSYSTEM: Physical Robot version");
    }
  
    return result;
  }

  // Constructor
  public ArmSystemSim(int armWinchChannel, int armExtenderChannel, XboxController m_controller, double m_deadband, boolean squareInputs, double maxOutputWinch)
  {
    // FIRST, we call superclass
    super(armWinchChannel,
        armExtenderChannel,
        m_controller,
        m_deadband,
        squareInputs,
        maxOutputWinch);

    // This entire class should only be instantiated when we're under simulation.
    // But just in-case someone tries to instantiate it otherwise, we do an extra check here.
    if (!RobotBase.isSimulation()) {
      return;
    }

    // Model a NEO motor (or any other motor)
    m_motorModel = DCMotor.getNEO(1); // 1 motor in the gearbox

    // Create the motor simulation with motor model, gear ratio, and moment of inertia
    double gearRatio = 20.0; // 20:1 gear reduction // $TODO Move into constants
    double motorMomentInertia = 0.0005; // $TODO - Move into constants
    m_motorSim = new DCMotorSim(m_motorModel, gearRatio, motorMomentInertia);

    // Create winch simulated encoder
    m_winchEncoderSim = new RelativeEncoderSim(m_winchEncoder);

    m_WinchSimulation = new WinchSimulation(
      m_winchEncoderSim,
      0.0254, // $TODO Spool diameter, move to constants (1 inch)
      2,    // $TODO Total string length, move to constants
      0.25,    // $TODO Initial length spooled
      StringOrientation.BackOfRobot,  // $TODO Initial string orientation
      false);

    // Create simulated absolute encoder
    m_winchAbsoluteEncoderSim = new DutyCycleEncoderSim(m_winchAbsoluteEncoder);
    // $TODO - Under simulation, we need to set our robot's arm to some initial position, otherwise it would be 0 and out of bounds!  It needs to be "read" from the winchsimulation string-length

    m_ArmSimulation = new ArmSimulation(
      m_WinchSimulation,
      m_winchAbsoluteEncoderSim,
      Constants.OperatorConstants.kWinchEncoderUpperLimit,
      Constants.OperatorConstants.kWinchEncoderLowerLimit,
      0.02, // $TODO Delta until broken, move to constants
      0.60); // $TODO Delta below which grabber cant be opened, move to constants
  
    AddShuffleboardWidgets();
  }

  private void AddShuffleboardWidgets() {
    Shuffleboard.getTab("Simulation")
      .addDouble("Winch Motor Power", () -> m_winchMotorOutputPercentage)
      .withWidget(BuiltInWidgets.kNumberBar)
      .withProperties(Map.of(
        "min", -1.0,
        "max", 1.0,
        "show text", false));
    
    Shuffleboard.getTab("Simulation")
      .addDouble("Winch String % Extended", () -> m_WinchSimulation.GetStringExtendedPercent())
      .withWidget(BuiltInWidgets.kNumberBar)
      .withProperties(Map.of(
        "min", 0.0,
        "max", 1.0,
        "show text", false));

    Shuffleboard.getTab("Simulation")
      .addString("Winch string location", () -> m_WinchSimulation.GetStringOrientationName())
      .withWidget(BuiltInWidgets.kTextView);

    Shuffleboard.getTab("Simulation")
      .addBoolean("Winch Functional", () -> !m_WinchSimulation.GetIsBroken())
      .withWidget(BuiltInWidgets.kBooleanBox)
      .withProperties(Map.of("colorWhenTrue", "#C0FBC0", "colorWhenFalse", "#8B0000"));
  }

  private boolean isRobotEnabled() {
    return RobotState.isEnabled();
  }

  @Override
  public void periodic() {
      super.periodic();

      // When Robot is disabled, the entire simulation freezes
      if (isRobotEnabled()) {      
        m_WinchSimulation.periodic();
        m_ArmSimulation.periodic();
      }
  }

  @Override
  public void simulationPeriodic() {
    super.simulationPeriodic();

    // When Robot is disabled, the entire simulation freezes
    if (isRobotEnabled()) {
      // Get the motor controller output percentage
      m_winchMotorOutputPercentage = m_armWinch.get();

      // Calculate the input voltage for the motor
      double inputVoltageVolts = m_winchMotorOutputPercentage * 12.0;
      
      // Update the motor simulation
      m_motorSim.setInput(inputVoltageVolts);
      m_motorSim.update(0.02);

      // Update the Encoder based on the simulation - the units are "number of rotations"
      double motorRotations = m_motorSim.getAngularPositionRotations();
      m_winchEncoderSim.setPosition(motorRotations);

      //System.out.println("Input volts:           " + inputVoltageVolts);
      //System.out.println("Winch motor rotations: " + motorRotations);

      m_WinchSimulation.simulationPeriodic();
      m_ArmSimulation.simulationPeriodic();
    }
  }
}


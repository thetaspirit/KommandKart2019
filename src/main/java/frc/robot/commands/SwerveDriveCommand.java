package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.PidConfig;

public class SwerveDriveCommand extends Command {

	private SwerveSubsystem m_subsystem;
	private DoubleSupplier m_gyro;
	private boolean m_dynamicGain;

	/**
	 * 
	 * @param subsystem   swerve drive subsystem
	 * @param gyro        gyroscope for absolute driving
	 * @param dynamicGain drive with dynamic translation/rotation gain or static
	 *                    translation/rotation gain. defaults to true. <br>
	 *                    *note that dynamic gain is used everywhere else in the
	 *                    code
	 * 
	 */
	public SwerveDriveCommand(SwerveSubsystem subsystem, DoubleSupplier gyro, boolean dynamicGain) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);

		this.m_gyro = gyro;
		this.m_dynamicGain = true;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double angle = this.m_gyro.getAsDouble();
		double x = OI.transX.getAsDouble();
		double y = OI.transY.getAsDouble();
		double tempX = 0;
		double rotation = OI.rotation.getAsDouble();
		String mode = "robot relative";
		if (OI.absoluteDrive.getAsBoolean()) {
			tempX = (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
			y = (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
			x = tempX;
			mode = "field relative";
		}

		SmartDashboard.putString("Mode", mode);

		if (m_dynamicGain) {
			m_subsystem.dynamicGainDrive(x, y, rotation);

		} else {
			m_subsystem.staticGainDrive(x, y, rotation);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

}
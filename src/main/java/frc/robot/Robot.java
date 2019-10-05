/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI m_oi;
	public static RobotMap m_robotMap;
	public static SubsystemMap m_subsystemMap;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		m_robotMap = new RobotMap();
		m_subsystemMap = new SubsystemMap();
		m_oi = new OI();

		RobotMap.gyro.calibrate();
		RobotMap.gyro.reset();
		// chooser.addOption("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);

	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for
	 * items like diagnostics that you want ran during disabled, autonomous,
	 * teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("right front ", RobotMap.rightFrontEncoder.getRotation());
		SmartDashboard.putNumber("left front ", RobotMap.leftFrontEncoder.getRotation());
		SmartDashboard.putNumber("left back ", RobotMap.leftBackEncoder.getRotation());
		SmartDashboard.putNumber("right back ", RobotMap.rightBackEncoder.getRotation());

		// // SmartDashboard.putNumber("right front error ",
		// // RobotMap.rightFrontWheel.getPidController().getError());
		// // SmartDashboard.putNumber("left front error ",
		// // RobotMap.leftFrontWheel.getPidController().getError());
		// // SmartDashboard.putNumber("left back error ",
		// // RobotMap.leftBackWheel.getPidController().getError());
		// // SmartDashboard.putNumber("right back error ",
		// // RobotMap.rightBackWheel.getPidController().getError());
		// SmartDashboard.putNumber("right front encoder",
		// RobotMap.rightFrontEncoder.getRotation());
		// SmartDashboard.putNumber("left front encoder",
		// RobotMap.leftFrontEncoder.getRotation());
		// SmartDashboard.putNumber("left back encoder",
		// RobotMap.leftBackEncoder.getRotation());
		// SmartDashboard.putNumber("right back encoder",
		// RobotMap.rightBackEncoder.getRotation());
		SmartDashboard.putNumber("transX input", OI.transX.getAsDouble());
		SmartDashboard.putNumber("transY input", OI.transY.getAsDouble());
		SmartDashboard.putNumber("rotation input", OI.rotation.getAsDouble());
		SmartDashboard.putNumber("arm speed ", RobotMap.arm.get());
		SmartDashboard.putNumber("gyro value", RobotMap.gyro.getAngle());
		SmartDashboard.putNumber("arm position", RobotMap.armEncoder.getPosition());
		SmartDashboard.putNumber("lift position", RobotMap.liftEncoder.getDistance());
		SmartDashboard.putBoolean("lift limit switch is on", RobotMap.lowerLiftLimit.get());
		SmartDashboard.putNumber("lift input", OI.liftDrive.getAsDouble());
		SmartDashboard.putNumber("lit maintaining pid error", SubsystemMap.lift.getPidController().getError());

	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

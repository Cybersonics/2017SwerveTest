package org.usfirst.frc103.Swerve2017Test.commands;

import org.usfirst.frc103.Swerve2017Test.Robot;
import org.usfirst.frc103.Swerve2017Test.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootAuto extends Command {

	public static final double MAX_FLYWHEEL_SPEED = 3600.0;
	private double backupTime;
	private boolean isDone;
	private double speed;

	public ShootAuto(double speed) {
		requires(Robot.shooter);
		this.speed = speed;
	}
	
	@Override
	protected void initialize() {
		isDone = false;
		backupTime = Timer.getFPGATimestamp() + 0.5;
	}

	@Override
	protected void execute() {
		double flywheelSpeed = speed;
		double elevatorSpeed=0.4;
		double distanceFeet;
		double predictedFlywheelSpeed = flywheelSpeed;
		double SCALE_VALUE = 2.0;
		
		Robot.shooter.setFlywheelSpeed(flywheelSpeed);

		if (Timer.getFPGATimestamp() > backupTime) {
			//RobotMap.ultrasonic.setEnabled(true);
			//distanceFeet = RobotMap.ultrasonic.getDistance() / 304.8; // converting to feet from millimeters
			//predictedFlywheelSpeed = distanceFeet * 160 + 2000;
			
			Robot.shooter.setElevator(elevatorSpeed);
			Robot.shooter.setAgitator(-elevatorSpeed);
		}
		
		SmartDashboard.putNumber("ShooterTargetSpeed", flywheelSpeed);
		SmartDashboard.putNumber("Elevator", elevatorSpeed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shooter.setAgitator(0);
		Robot.shooter.setElevator(0);
		Robot.shooter.setFlywheelSpeed(0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}

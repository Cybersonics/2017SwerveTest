package org.usfirst.frc103.Swerve2017Test.commands;

import org.usfirst.frc103.Swerve2017Test.Robot;
import org.usfirst.frc103.Swerve2017Test.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shoot extends Command {

	public static final double MAX_FLYWHEEL_SPEED = 3600.0;

	public Shoot() {
		requires(Robot.shooter);
	}

	@Override
	protected void execute() {
		// double flywheelSpeed = Robot.oi.controller.getTriggerAxis(Hand.kRight) * MAX_FLYWHEEL_SPEED;
		// double elevatorSpeed = Robot.oi.controller.getTriggerAxis(Hand.kLeft) * 0.3;
		double flywheelSpeed;
		double elevatorSpeed;
		double agitatorSpeed;
		double distanceFeet;
		double rightStickY;
		double predictedFlywheelSpeed = 3550;
		double SCALE_VALUE = 2.0;
		double temp;

		if (Robot.oi.controller.getBumper(Hand.kLeft)) { // When Left Bumper Reads a Value
			//RobotMap.ultrasonic.setEnabled(true);
			//distanceFeet = RobotMap.ultrasonic.getDistance() / 304.8; // converting to feet from millimeters
			//predictedFlywheelSpeed = distanceFeet * 160 + 2000;

			if (Math.abs(Robot.oi.controller.getY(Hand.kRight)) > 0.05) {
				rightStickY = -Robot.oi.controller.getY(Hand.kRight);
				//flywheelSpeed = predictedFlywheelSpeed * (1 + rightStickY / SCALE_VALUE); // scale the amount of change
				temp = rightStickY * 3550;
				if (temp < 0){
					flywheelSpeed = predictedFlywheelSpeed + temp;
				} else {
					//flywheelSpeed = predictedFlywheelSpeed + (3550 - (rightStickY * 3550));	
					flywheelSpeed = predictedFlywheelSpeed + temp;	
				}
				

			} else {
				flywheelSpeed = predictedFlywheelSpeed;

			}
		}

		else {
			//RobotMap.ultrasonic.setEnabled(false);
			flywheelSpeed = 0;

		}

		if (Robot.oi.controller.getBumper(Hand.kRight)) {
			elevatorSpeed = 0.4;
			agitatorSpeed = 0.6; //0.6
		} else {
			elevatorSpeed = 0;
			agitatorSpeed = 0;
		}

		Robot.shooter.setFlywheelSpeed(flywheelSpeed);
		Robot.shooter.setElevator(elevatorSpeed);
		Robot.shooter.setAgitator(agitatorSpeed);
		SmartDashboard.putNumber("ShooterTargetSpeed", flywheelSpeed);
		SmartDashboard.putNumber("Elevator", elevatorSpeed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		/*
		 * double flywheelSpeed = 1000;
		 * 
		 * for(int spd=1; spd<100; spd++){
		 * 
		 * Robot.shooter.setFlywheelSpeed(flywheelSpeed / 2); }
		 */

		//RobotMap.ultrasonic.setEnabled(false);

	}

	@Override
	protected void interrupted() {
		end();
	}

}

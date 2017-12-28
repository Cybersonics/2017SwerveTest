package org.usfirst.frc103.Swerve2017Test.commands;

import org.usfirst.frc103.Swerve2017Test.Robot;
import org.usfirst.frc103.Swerve2017Test.RobotMap;
import org.usfirst.frc103.Swerve2017Test.subsystems.RangeFinder;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class VisionLeaveGear extends Command {
	
	private double backupTime;
	private static final double DISTANCE = 600;
	private boolean isDone;
	//private boolean doorClosed;
	
	public VisionLeaveGear() {
		requires(Robot.drive);
		requires(Robot.gearManipulator);
	}
	
	@Override
	protected void initialize() {
		Robot.drive.encoderReset();
		//Robot.gearManipulator.setGearDoor(1.0);
		Robot.gearManipulator.setDoors(true);
		//doorClosed = false;
		isDone = false;
		backupTime = Timer.getFPGATimestamp() + 0.5;
		
	}
	
	@Override
	protected void execute() {
		if (Timer.getFPGATimestamp() > backupTime) {
			/*if ((Math.abs(RobotMap.driveLeftFront.getPosition()) < DISTANCE) &&
					(Math.abs(RobotMap.driveRightFront.getPosition()) < DISTANCE) &&
					(Math.abs(RobotMap.driveLeftRear.getPosition()) < DISTANCE) &&
					(Math.abs(RobotMap.driveRightRear.getPosition()) < DISTANCE)) {*/
			if (RangeFinder.getDistance() < 60.0) {
				Robot.drive.swerveDrive(0.0, -0.25, 0.0);
			} else {
				Robot.drive.swerveDrive(0.0, 0.0, 0.0);
				
				/*double speed = 0.0;
				if (!RobotMap.gearManipulatorDoors.isRevLimitSwitchClosed() && !doorClosed) {
					speed = -1.0;
				} else if (RobotMap.gearManipulatorDoors.isRevLimitSwitchClosed()){
					doorClosed = true;
					isDone = true;
					speed = 0.0;
					
				}
				Robot.gearManipulator.setGearDoor(speed);*/
				Robot.gearManipulator.setDoors(false);
				isDone = true;
			}
		}
		

	}

	@Override
	protected boolean isFinished() {
		
		return isDone; 
		//return !Robot.oi.controller.getRawButton(7);
	}
	
	@Override
	protected void end() {
		Robot.gearManipulator.setDoors(false);
		RobotMap.relay0.set(Relay.Value.kOn);
		RobotMap.relay1.set(Relay.Value.kOn);
	}
	
	@Override
	protected void interrupted() {
		end();
	}

}

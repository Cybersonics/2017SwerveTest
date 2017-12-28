package org.usfirst.frc103.Swerve2017Test.commands;

import org.usfirst.frc103.Swerve2017Test.Robot;
import org.usfirst.frc103.Swerve2017Test.RobotMap;
import org.usfirst.frc103.Swerve2017Test.subsystems.RangeFinder;
import org.usfirst.frc103.Swerve2017Test.subsystems.Vision;
import org.usfirst.frc103.Swerve2017Test.subsystems.Vision.GearTarget;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class VisionPlaceGear extends Command {
	
	private boolean isPlacing, isBackingUp;
	private double retryTime;
	private double headingOffset;
	private double targetHeading;
	//private static final double PLACE_DISTANCE = 70.0 + 15.0, PEG_DISTANCE = 60.0 + 15.0, DONE_DISTANCE = 40.0 + 15.0;
	private static final double PLACE_DISTANCE = 60.0, PEG_DISTANCE = 45.0, DONE_DISTANCE = 30.0;
	private static final double PLACE_TIMEOUT = 2.0;
	
	public VisionPlaceGear(double angle) {
		requires(Robot.drive);
		this.headingOffset = angle;
	}
	
	@Override
	protected void initialize() {
		isPlacing = false;
		isBackingUp = false;
		targetHeading = (Robot.zeroHeading + headingOffset) % 360.0;
		if (targetHeading < 0.0) {
			targetHeading += 360.0;
		}
	}
	
	@Override
	protected void execute() {
		//double distance = RobotMap.lidar.getDistance();
		double distance = RangeFinder.getDistance();
		double angleError = targetHeading - RobotMap.navX.getFusedHeading();
		if (Math.abs(angleError) > 180.0) {
			angleError -= 360.0 * Math.signum(angleError);
    	}
		double forward = 0.0, strafe = 0.0, omega = 0.0;
		if (!isPlacing) {
			GearTarget target = Vision.getGearTarget();
			if (target != null) {
				forward = 0.35;
				strafe = (target.centerX - (0.3 * distance + 50.0)) / 200.0;
				omega = (Math.abs(angleError) > 2.0 ? angleError / 360.0 * 0.2 : 0.0);
			}
			if (distance < PLACE_DISTANCE) {
				System.out.println("Placing");
				isPlacing = true;
				retryTime = Timer.getFPGATimestamp() + PLACE_TIMEOUT;
			}
		} else {
			if (!isBackingUp) {
				forward = 0.35;
				strafe = Math.sin(15.0 * Math.PI * Timer.getFPGATimestamp()) * 0.3;
				omega = Math.sin(15.0 * Math.PI * Timer.getFPGATimestamp()) * 0.02;
				if (Timer.getFPGATimestamp() > retryTime) {
					System.out.println("Backing up");
					isBackingUp = true;
					retryTime = Timer.getFPGATimestamp() + PLACE_TIMEOUT;
				}
			} else {
				forward = -0.35;
				strafe = 0.0;
				omega = (Math.abs(angleError) > 2.0 ? angleError / 360.0 * 0.2 : 0.0);
				if (distance >= PEG_DISTANCE || Timer.getFPGATimestamp() > retryTime) {
					System.out.println("Retrying");
					isBackingUp = false;
					retryTime = Timer.getFPGATimestamp() + PLACE_TIMEOUT;
				}
			}
		}
		Robot.drive.swerveDrive(strafe, forward, omega);
	}

	@Override
	protected boolean isFinished() {
		//return RobotMap.lidar.getDistance() < DONE_DISTANCE;
		return RangeFinder.getDistance() < DONE_DISTANCE;
	}
	
	@Override
	protected void end() {
		System.out.println("Done");
		Robot.drive.swerveDrive(0, 0, 0);
		RobotMap.relay0.set(Relay.Value.kOff);
		RobotMap.relay1.set(Relay.Value.kOff);
	}
	
	@Override
	protected void interrupted() {
		end();
	}

}

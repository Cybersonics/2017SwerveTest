package org.usfirst.frc103.Swerve2017Test.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import static org.usfirst.frc103.Swerve2017Test.RobotMap.*;

import org.usfirst.frc103.Swerve2017Test.commands.Shoot;

public class Shooter extends Subsystem {
	
	public void setElevator(double speed) {
		shooterElevator.set(speed);
	}
	
	public double getFlywheelSpeed() {
		return shooterFlyWheel.getSpeed();
	}
	
	public void setFlywheelSpeed(double speed) {
		shooterFlyWheel.setSetpoint(speed);
		//shooterFlyWheel.set(speed);
	}
	
	public void setAgitator(double speed) {
		agitator.set(speed);
	}

	@Override
    public void initDefaultCommand() {
		setDefaultCommand(new Shoot());
    }
    
}


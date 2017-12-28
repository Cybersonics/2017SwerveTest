package org.usfirst.frc103.Swerve2017Test.commands;

import org.usfirst.frc103.Swerve2017Test.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterDriveForwardSpinSequence extends CommandGroup {
	
	public CenterDriveForwardSpinSequence() {
		requires(Robot.drive);
		requires(Robot.gearManipulator);
		requires(Robot.shooter);
		
		addSequential(new VisionPlaceGear(0.0));
		addSequential(new VisionLeaveGear());
		addSequential(new DriveForward(107.0, 0));
		addSequential(new ShootAuto(3550));
	}
	
	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

    @Override
	protected void end() {
    }

    @Override
	protected void interrupted() {
    	end();
    }
}

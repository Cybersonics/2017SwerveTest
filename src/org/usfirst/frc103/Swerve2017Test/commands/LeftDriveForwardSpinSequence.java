package org.usfirst.frc103.Swerve2017Test.commands;

import org.usfirst.frc103.Swerve2017Test.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftDriveForwardSpinSequence extends CommandGroup {
	
	public LeftDriveForwardSpinSequence() {
		requires(Robot.drive);
		requires(Robot.gearManipulator);
		requires(Robot.shooter);
		
		addSequential(new DriveForward(60.0, 4300));
		addSequential(new VisionPlaceGear(60.0));
		addSequential(new VisionLeaveGear());
		addSequential(new DriveForward(-144.0, 0));
		addSequential(new ShootAuto(3425));
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

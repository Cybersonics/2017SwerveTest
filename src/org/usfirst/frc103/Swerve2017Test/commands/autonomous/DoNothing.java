package org.usfirst.frc103.Swerve2017Test.commands.autonomous;

import org.usfirst.frc103.Swerve2017Test.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command {
	
	public DoNothing() {
		
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

package org.usfirst.frc103.Swerve2017Test;

import org.usfirst.frc103.Swerve2017Test.commands.VisionLeaveGear;
import org.usfirst.frc103.Swerve2017Test.commands.VisionPlaceGear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
 

	public Joystick leftJoy = new Joystick(0);
	public Joystick rightJoy = new Joystick(1);
	public XboxController controller = new XboxController(2);

	public JoystickButton leftBumper = new JoystickButton(controller, 5);
	public JoystickButton rightBumper = new JoystickButton(controller, 6);
	public JoystickButton gearDoorOpen = new JoystickButton(controller, 1);
	public JoystickButton gearDoorClose = new JoystickButton(controller, 2);
	public JoystickButton startButton = new JoystickButton(controller, 8);
	public JoystickButton selectButton = new JoystickButton(controller, 7);
	
    public OI() {
    	//startButton.whenPressed(new VisionPlaceGear(0.0));
    	//selectButton.whenPressed(new VisionLeaveGear());
    	startButton.whenPressed(new Command() {
			@Override
			protected void initialize() {
				System.out.println("limits disabled");
				RobotMap.gearManipulatorDoors.enableLimitSwitch(false, false);
			}
			@Override
			protected boolean isFinished() {
				return true;
			}
		});
    	startButton.whenReleased(new Command() {
			@Override
			protected void initialize() {
				System.out.println("limits enabled");
				RobotMap.gearManipulatorDoors.enableLimitSwitch(true, true);
			}
			@Override
			protected boolean isFinished() {
				return true;
			}
		});
    	/*startButton.whileHeld(new Command() {
			@Override
			protected void initialize() {
				RobotMap.gearManipulatorDoors.enableLimitSwitch(false, false);
			}
			@Override
			protected boolean isFinished() {
				return false;
			}
			@Override
			protected void end() {
				RobotMap.gearManipulatorDoors.enableLimitSwitch(true, true);
			}
			@Override
			protected void interrupted() {
				end();
			}
		});*/
    }

}


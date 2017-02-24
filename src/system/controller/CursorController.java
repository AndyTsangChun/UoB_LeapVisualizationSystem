package system.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import system.bugreport.ExceptionCatcher;
import system.general.SystemPreference;
import system.model.LVSObject;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

/** ************************************************************
 * This class is the controller which control the action perform
 * on cursor by java.awt.Robot. Functions allow system to transmit 
 * user motions or controls to native system input events.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class CursorController extends LVSObject{
	private Robot robot;
	private boolean LEFT_CLICK_DOWN_FLAG = false;
	private boolean SHIT_DOWN_FLAG = false;

	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system    
	 * @param	systemPreference	User's Preference of the system
	 */
	public CursorController(SystemController systemController, SystemPreference systemPreference) {
		super(systemController, systemPreference);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			ExceptionCatcher.logException(e);
		}
	}

	/**
	 * Order robot to move the cursor base on information provide from frame
	 * Stabilized Vector are use to obtain more stable movement from user 
	 * 
	 */
	public void moveMouse() {
		Hand hand = this.getSystemController().getLeapMotionFrameController().getStyleHand();
		int[] handPos = this.getSystemController().getLeapMotionFrameController().getNormalizedHandPosition(hand);
		float cursor_X = handPos[0];
		float cursor_Y = handPos[1];

		robot.mouseMove((int) cursor_X, (int) cursor_Y);
	}

	/**
	 * Order robot to hold left click
	 * 
	 */
	public void holdMouse() {
		if (!this.LEFT_CLICK_DOWN_FLAG) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			this.LEFT_CLICK_DOWN_FLAG = true;
		}
	}

	/**
	 * Order robot to do one left click
	 * 
	 */
	public void leftClick() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	/**
	 * Order robot to hold left click and shift
	 * 
	 */
	public void shiftHoldLeft() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.keyPress(KeyEvent.VK_SHIFT);
		this.LEFT_CLICK_DOWN_FLAG = true;
		this.SHIT_DOWN_FLAG = true;
	}

	/**
	 * Order robot to scroll base on the change in z-aixs
	 * 
	 * @param	frame	the current frame using
	 */
	public void scrollMouse(Frame frame) {
		HandList hands = frame.hands();
		int dz = (int) (hands.get(0).palmVelocity().getZ()) / 10;
		
		robot.mouseWheel(dz);
	}

	/**
	 * Order robot to release the Input base on FLAG
	 * 
	 */
	public void releaseInput() {
		if (this.LEFT_CLICK_DOWN_FLAG) {
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			this.LEFT_CLICK_DOWN_FLAG = false;
		}
		if (this.SHIT_DOWN_FLAG) {
			robot.keyRelease(KeyEvent.VK_SHIFT);
			this.SHIT_DOWN_FLAG = false;
		}
	}

	// Attributes Set and Get
	public boolean isLEFT_CLICK_DOWN_FLAG() {
		return LEFT_CLICK_DOWN_FLAG;
	}

	public void setLEFT_CLICK_DOWN_FLAG(boolean lEFT_CLICK_DOWN_FLAG) {
		LEFT_CLICK_DOWN_FLAG = lEFT_CLICK_DOWN_FLAG;
	}

	public boolean isSHIT_DOWN_FLAG() {
		return SHIT_DOWN_FLAG;
	}

	public void setSHIT_DOWN_FLAG(boolean sHIT_DOWN_FLAG) {
		SHIT_DOWN_FLAG = sHIT_DOWN_FLAG;
	}

}

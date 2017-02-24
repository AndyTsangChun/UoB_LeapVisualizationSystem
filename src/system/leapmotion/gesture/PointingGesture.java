package system.leapmotion.gesture;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.StaticGesture;
import system.res.SystemTextureManager;
import ui.awt.res.LVSStringInfo;

/** ************************************************************
 * This is a object class extend from custom gesture.
 * The flow gesture requires sub hand when SingleHand mode is 
 * disabled where only index finger is extended.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class PointingGesture extends StaticGesture {

	public PointingGesture(GestureController gestureController) {
		super(CustomGestureType.POINTING_GESTURE, gestureController, SystemTextureManager.POINTING_GESTURE_IMAGE, LVSStringInfo.OP_CURSOR);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is not extended
		check_hand = !extended[0] && extended[1] && !extended[2] && !extended[3] && !extended[4];
		
		return check_hand;
	}

	@Override
	public boolean checkPost() {
		return true;
	}

	@Override
	public void performAction() {
		if (super.printTest() && super.isEnable()) {
			System.out.println(super.getGestureName());
		}
		if (super.checkRecogniseThreshold() && super.isEnable()) {
			super.getGestureController().getSystemController().getCursorController().moveMouse();
		}
	}
}

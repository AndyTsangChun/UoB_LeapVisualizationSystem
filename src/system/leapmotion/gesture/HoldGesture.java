package system.leapmotion.gesture;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.StaticGesture;
import system.res.SystemTextureManager;

/** ************************************************************
 * This is a object class extend from custom gesture.
 * The hold gesture requires major hand only where only thumb 
 * and index finger is extended.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class HoldGesture extends StaticGesture {

	public HoldGesture(GestureController gestureController) {
		super(CustomGestureType.HOLD_GESTURE, gestureController, SystemTextureManager.HOLD_GESTURE_IMAGE, null);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is not extended
		check_hand = extended[0] && extended[1] && !extended[2] && !extended[3] && !extended[4];
		
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
		if (super.checkRecogniseThreshold() && super.isEnable()){
		}
	}
}

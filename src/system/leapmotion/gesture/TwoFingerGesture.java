package system.leapmotion.gesture;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.StaticGesture;
import system.res.SystemTextureManager;
import ui.awt.res.LVSStringInfo;

/** ************************************************************
 * This is a object class extend from custom gesture.
 * The two finger gesture requires major hand only where index
 * and middle finger is extended.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class TwoFingerGesture extends StaticGesture {

	public TwoFingerGesture(GestureController gestureController) {
		super(CustomGestureType.TWO_FINGER_GESTURE, gestureController, SystemTextureManager.TWO_FINGER_GESTURE_IMAGE, LVSStringInfo.OP_ZOOM);
		super.setRecogniseThreshold(10);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is not extended
		check_hand = !extended[0] && extended[1] && extended[2] && !extended[3] && !extended[4];
		
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
			//super.getGestureController().getSystemController().getCursorController().moveMouse(super.getFrame());
			//super.getGestureController().getSystemController().getCursorController().scrollMouse(super.getFrame());
			super.getGestureController().getSystemController().getVTKCameraController().zoom();
		}
	}
}

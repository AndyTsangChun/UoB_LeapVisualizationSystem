package system.leapmotion.gesture;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.StaticGesture;
import system.res.SystemTextureManager;
import ui.awt.res.LVSStringInfo;

/** ************************************************************
 * This is a object class extend from custom gesture.
 * The fist gesture requires major hand only where all fingers 
 * are not extended.   
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class FistGesture extends StaticGesture {

	public FistGesture(GestureController gestureController) {
		super(CustomGestureType.FIST_GESTURE, gestureController, SystemTextureManager.FIST_GESTURE_IMAGE, LVSStringInfo.OP_REPOSITION);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is not extended
		check_hand = !extended[0] && !extended[1] && !extended[2] && !extended[3] && !extended[4];
		
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

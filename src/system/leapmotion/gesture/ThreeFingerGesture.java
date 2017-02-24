package system.leapmotion.gesture;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.StaticGesture;
import system.res.SystemTextureManager;
import ui.awt.res.LVSStringInfo;

/** ************************************************************
 * This is a object class extend from custom gesture.
 * The three finger gesture requires major hand only where either
 * thumb, index and middle or index, middle and ring are extended. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ThreeFingerGesture extends StaticGesture {

	public ThreeFingerGesture(GestureController gestureController) {
		super(CustomGestureType.THREE_FINGER_GESTURE, gestureController, SystemTextureManager.THREE_FINGER_GESTURE_IMAGE, LVSStringInfo.OP_TRANSLATE);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is not extended
		check_hand = (extended[0] && extended[1] && extended[2] && !extended[3] && !extended[4]) || (!extended[0] && extended[1] && extended[2] && extended[3] && !extended[4]);
		
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
			if (super.getGestureController().getSystemController().getVTKController().isSliceTrans()){
				super.getGestureController().getSystemController().getVTKController().translateSliceActor();
			}
		}
	}
}

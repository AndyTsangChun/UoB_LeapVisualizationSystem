package system.leapmotion.gesture;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.StaticGesture;
import system.res.SystemTextureManager;
import ui.awt.res.LVSStringInfo;

import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

/** ************************************************************
 * This is a object class extend from custom gesture.
 * The stop gesture requires major hand only where the hand in
 * vertical pose (> 60 degree) and all finger is extended. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class StopGesture extends StaticGesture {

	public StopGesture(GestureController gestureController) {
		super(CustomGestureType.STOP_GESTURE, gestureController, SystemTextureManager.STOP_GESTURE_IMAGE, LVSStringInfo.OP_HELP);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is not extended
		check_hand = extended[0] && extended[1] && extended[2] && extended[3] && extended[4];
		
		return check_hand;
	}

	@Override
	public boolean checkPost() {
		Hand hand = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHand();

		Vector direction = hand.direction();
		if (Math.toDegrees(direction.pitch()) > 55)
			return true;
		else
			return false;
	}

	@Override
	public void performAction() {
		if (super.printTest() && super.isEnable()) {
			System.out.println(super.getGestureName());
		}
		if (super.checkRecogniseThreshold() && super.isEnable()) {
			super.getGestureController().getSystemController().getOverLayerController().setGestureGuideVisible(true);
		}
	}
}

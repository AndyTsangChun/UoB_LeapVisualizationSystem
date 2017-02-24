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
 * The flow gesture requires sub hand when SingleHand mode is 
 * disabled where the hand is in horizontal pose and all finger 
 * is extended.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class FlowGesture extends StaticGesture {

	public FlowGesture(GestureController gestureController) {
		super(CustomGestureType.FLOW_GESTURE, gestureController, SystemTextureManager.FLOW_GESTURE_IMAGE, LVSStringInfo.OP_ROTATE);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		boolean[] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
		boolean check_hand = false;
		
		// Check is all fingers is extended
		check_hand = extended[0] && extended[1] && extended[2] && extended[3] && extended[4];
		
		return check_hand;
	}

	@Override
	public boolean checkPost() {
		Hand hand = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHand();

		// Check the gesture post by direction
		Vector direction = hand.direction();
		if (Math.toDegrees(direction.pitch()) < 45 && Math.toDegrees(direction.pitch()) > -5){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void performAction() {
		if (super.printTest() && super.isEnable()) {
			System.out.println(super.getGestureName());
		}
		if (super.checkRecogniseThreshold() && super.isEnable()) {
			super.getGestureController().getSystemController().getVTKCameraController().rotate();
		}
	}
}

package system.leapmotion.gesture;
import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.DynamicGesture;
import system.res.SystemTextureManager;
import ui.awt.res.LVSStringInfo;

import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

/** ************************************************************
 * This is an object class extend from custom gesture.
 * The clap gesture requires both hands to be up straight and
 * all fingers are extended.   
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ClapGesture extends DynamicGesture{
	public ClapGesture(GestureController gestureController){
		super(CustomGestureType.CLAP_GESTURE, gestureController, SystemTextureManager.CLAP_GESTURE_IMAGE, LVSStringInfo.OP_CLOSE);
		super.setRecogniseThreshold(0);
	}

	@Override
	public boolean checkFinger() {
		HandList hands = super.getGestureController().getSystemController().getLeapMotionFrameController().getHands();
		// Check number of hand
		if (hands.count() < 2)
			return false;
		boolean[][] extended = super.getGestureController().getSystemController().getLeapMotionFrameController().getHandsIsExtended();
		boolean check_hand = false;
		// Check the grab strength of hand
		for (int i = 0 ; i < hands.count() ; i++){
			Hand hand = hands.get(i);
			if(hand.grabStrength() > 0.1){
				return false;
			}
		}
		// Check is all fingers extended
		check_hand = extended[0][0] && extended[0][1] && extended[0][2] && extended[0][3] && extended[0][4] && 
				extended[1][0] && extended[1][1] && extended[1][2] && extended[1][3] && extended[1][4];
		return check_hand;
	}

	@Override
	public boolean checkPost() {
		HandList hands = super.getGestureController().getSystemController().getLeapMotionFrameController().getHands();
		if (hands.count() < 2)
			return false;
		Hand hand = super.getGestureController().getSystemController().getLeapMotionFrameController().getStyleHand();
		Hand hand2 = super.getGestureController().getSystemController().getLeapMotionFrameController().getSubHand();
		float hand_x = 0;
		float hand2_x = 0;
		if (hand.isLeft() && hand2.isRight()){
			hand_x = hand.palmPosition().getX();
			hand2_x = hand2.palmPosition().getX();
		}else if (hand.isRight() && hand2.isLeft()){
			hand_x = hand2.palmPosition().getX();
			hand2_x = hand.palmPosition().getX();
		}
		float hand_distance = hand2_x - hand_x;
		if (hand_distance < 35 && hand_distance > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public void performAction() {
		if(super.printTest() && super.isEnable()){
			System.out.println(super.getGestureName());
		}
		if (super.checkRecogniseThreshold() && super.isEnable()){
			super.getGestureController().getSystemController().closeSystem();
		}
	}
}

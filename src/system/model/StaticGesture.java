package system.model;

import java.awt.Image;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;

/** ************************************************************
 * This abstract class of dynamic gesture extend from Leap Motion
 * Gesture. Each gesture has its own state and is Enable or not.
 * Each gesture has his own gesture threshold to maintain smoother
 * performance.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public abstract class StaticGesture extends CustomGesture {
	public StaticGesture(CustomGestureType cGestureType, GestureController gestureController, Image image, String operationName) {
		super(cGestureType, gestureController, image, operationName);
	}

}

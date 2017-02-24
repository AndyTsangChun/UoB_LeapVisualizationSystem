package system.model;

import java.awt.Image;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;

import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Vector;

/** ************************************************************
 * This abstract class of dynamic gesture extend from Leap Motion
 * Gesture. Each gesture has its own state and is Enable or not.
 * Each gesture has his own gesture threshold to maintain smoother
 * performance.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public abstract class DynamicGesture extends CustomGesture {
	private Vector startPosition;
	
	public DynamicGesture(CustomGestureType cGestureType, GestureController gestureController, Image image, String operationName) {
		super(cGestureType, gestureController, image, operationName);
	}
	
	/**
	 * Function to update the state of the gesture.
	 * 
	 * @param	end	if end is true the state will change from update to stop
	 */
	@Override
	public void updateState(boolean end){
		Gesture.State state = super.getState();
		switch(state){
		case STATE_INVALID:
			state = Gesture.State.STATE_START;
			break;
		case STATE_START:
			state = Gesture.State.STATE_UPDATE;
			break;
		case STATE_UPDATE:
			if (end)
				state = Gesture.State.STATE_STOP;
			break;
		case STATE_STOP:
			state = Gesture.State.STATE_INVALID;
			break;
		}
	}

	// Attributes Set and Get
	public Vector getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Vector startPosition) {
		this.startPosition = startPosition;
	}
}

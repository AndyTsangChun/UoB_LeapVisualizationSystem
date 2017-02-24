package system.leapmotion.gesture.util;

/** ************************************************************
 * This enum class is the custom gesture type store the info of
 * each gesture.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public enum CustomGestureType {
	UNKNOWN_GESTURE(0, "Gesture not match"), POINTING_GESTURE(1,
			"PointingGesture"), TWO_FINGER_GESTURE(2, "TwoFingerGesture"), THREE_FINGER_GESTURE(
			3, "ThreeFingerGesture"), FOUR_FINGER_GESTURE(4,
			"FourFingerGesture"), FLOW_GESTURE(5, "FlowGesture"), STOP_GESTURE(
			6, "Stop Gesture"), HOLD_GESTURE(7, "Hold Gesture"), FIST_GESTURE(8, "FistGesture"), CLAP_GESTURE(9, "ClapGesture");

	private final int gesture_ID;
	private final String gesture_Name;

	private CustomGestureType(int gesture_ID, String gesture_Name) {
		this.gesture_ID = gesture_ID;
		this.gesture_Name = gesture_Name;
	}

	// Attributes Set and Get
	public int getGestureID() {
		return gesture_ID;
	}

	public String getGestureName() {
		return this.gesture_Name;
	}
}

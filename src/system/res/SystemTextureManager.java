package system.res;

import java.awt.Image;
import java.awt.Toolkit;

/** ************************************************************
 * This the texture manager holding all LVS gesture
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class SystemTextureManager {
	public static final String POINTING_GESTURE_PATH = "rsc/Gesture/PointingGesture.png";
	public static final String TWO_FINGER_GESTURE_PATH = "rsc/Gesture/TwoFingerGesture.png";
	public static final String THREE_FINGER_GESTURE_PATH = "rsc/Gesture/ThreeFingerGesture.png";
	public static final String FOUR_FINGER_GESTURE_PATH = "rsc/Gesture/FourFingerGesture.png";
	public static final String FLOW_GESTURE_PATH = "rsc/Gesture/FlowGesture.png";
	public static final String STOP_GESTURE_PATH = "rsc/Gesture/StopGesture.png";
	public static final String HOLD_GESTURE_PATH = "rsc/Gesture/HoldGesture.png";
	public static final String FIST_GESTURE_PATH = "rsc/Gesture/FistGesture.png";
	public static final String CLAP_GESTURE_PATH = "rsc/Gesture/ClapGesture.png";
	
	public static Image POINTING_GESTURE_IMAGE = null;
	public static Image TWO_FINGER_GESTURE_IMAGE = null;
	public static Image THREE_FINGER_GESTURE_IMAGE = null;
	public static Image FOUR_FINGER_GESTURE_IMAGE = null;
	public static Image FLOW_GESTURE_IMAGE = null;
	public static Image STOP_GESTURE_IMAGE = null;
	public static Image HOLD_GESTURE_IMAGE = null;
	public static Image FIST_GESTURE_IMAGE = null;
	public static Image CLAP_GESTURE_IMAGE = null;
	
	/**
	 * Load all texture
	 */
	public static void loadTexture(){
		POINTING_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(POINTING_GESTURE_PATH);
		TWO_FINGER_GESTURE_IMAGE =Toolkit.getDefaultToolkit().getImage(TWO_FINGER_GESTURE_PATH);
		THREE_FINGER_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(THREE_FINGER_GESTURE_PATH);
		FOUR_FINGER_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(FOUR_FINGER_GESTURE_PATH);
		FLOW_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(FLOW_GESTURE_PATH);
		STOP_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(STOP_GESTURE_PATH);
		HOLD_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(HOLD_GESTURE_PATH);
		FIST_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(FIST_GESTURE_PATH);
		CLAP_GESTURE_IMAGE = Toolkit.getDefaultToolkit().getImage(CLAP_GESTURE_PATH);
	}
}

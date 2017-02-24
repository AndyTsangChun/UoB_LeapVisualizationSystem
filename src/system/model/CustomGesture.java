package system.model;
import java.awt.Image;

import system.controller.GestureController;
import system.leapmotion.gesture.util.CustomGestureType;

import com.leapmotion.leap.Gesture;

/** ************************************************************
 * This abstract class of custom gesture extend from Leap Motion
 * Gesture. Each gesture has its own state and is Enable or not.
 * Each gesture has his own gesture threshold to maintain smoother
 * performance.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public abstract class CustomGesture extends Gesture{
	private final String NO_OPERATION = "Not Mapped";
	private CustomGestureType cGestureType;
	private GestureController gestureController;
	private String operationName;
	private boolean ENABLED;
	private Gesture.State state;
	private int recogniseThreshold = 0;
	private int recogniseCounter;
	private Image image;
	// Testing
	private final int printTestThreshold = 10;
	private int printTestCounter = 0;
	
	/**
	 * Constructor
	 * 
	 * @param	cGestureType	gesture type of the object class
	 * @param	gestureController	the custom gesture controller this object controlled by  
	 */
	public CustomGesture(CustomGestureType cGestureType, GestureController gestureController, Image image, String operationName){
		this.cGestureType = cGestureType;
		this.gestureController = gestureController;
		this.ENABLED = false;
		this.setImage(image);
		if (operationName == null || operationName.equals("")){
			this.operationName = NO_OPERATION;
		}else{
			this.operationName = operationName;
		}
	}
	
	/**
	 * Method check custom gesture and return the gesture type. 
	 */
	public CustomGestureType check_Gesture(){
		if (!getGestureController().getSystemController().getLeapMotionFrameController().hasHand())
			return CustomGestureType.UNKNOWN_GESTURE;
		if (checkFinger() && checkPost()){
			return this.cGestureType;
		}else{
			return CustomGestureType.UNKNOWN_GESTURE;
		}
	}
	
	/**
	 * Abstract method check by finger extend or not.
	 * Condition change depends on gesture.
	 */
	public abstract boolean checkFinger();
	
	/**
	 * Abstract method check by the hand post.
	 * Condition change depends on gesture.
	 */
	public abstract boolean checkPost();
	
	/**
	 * Abstract method perform action mapped with this gesture.
	 * Action change depends on gesture.
	 */
	public abstract void performAction();
	
	/**
	 * Function in checking counter with threshold to stop printing every frame.
	 * 
	 * @return	boolean	if the counter = threshold true return to allow printing
	 */
	public boolean printTest(){
		if(this.getGestureController().getSystemController().getSystemPreference().isPrinting()){
			if (printTestCounter >= printTestThreshold){
				printTestCounter = 0;
				return true;
			}else{
				printTestCounter++;
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Function in checking counter with threshold to stop performing action every frame.
	 * Threshold might affect the smoothness of action
	 * 
	 * @return	boolean	if the counter = threshold true return to allow perform
	 */
	public boolean checkRecogniseThreshold(){
		if (recogniseCounter >= recogniseThreshold){
			recogniseCounter = 0;
			return true;
		}else{
			recogniseCounter++;
			return false;
		}
	}
	
	/**
	 * Function to update the state of the gesture.
	 * 
	 * @param	end	if end is true the state will change from update to stop
	 */
	public void updateState(boolean end){
		switch(state){
		case STATE_INVALID:
			break;
		case STATE_START:
			break;
		case STATE_UPDATE:
			break;
		case STATE_STOP:
			break;
		}
	}
	
	// Attributes Set and Get
	public void setState(Gesture.State state){
		this.state = state;
	}
	
	public void setEnable(boolean enabled){
		this.ENABLED = enabled;
	}
	
	public GestureController getGestureController(){
		return this.gestureController;
	}
	
	public CustomGestureType getCustomGestureType(){
		return this.cGestureType;
	}
	
	public int getGestureID(){
		return cGestureType.getGestureID();
	}
	
	public String getGestureName(){
		return cGestureType.getGestureName();
	}
	
	public Gesture.State getState(){
		return this.state;
	}
	
	public boolean isEnable(){
		return this.ENABLED;
	}
	
	public void setRecogniseThreshold(int recogniseThreshold){
		this.recogniseThreshold = recogniseThreshold;
	}
	
	public int getRecogniseThreshold(){
		return this.recogniseThreshold;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}

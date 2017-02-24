package system.controller;
import system.general.SystemPreference;
import system.leapmotion.gesture.ClapGesture;
import system.leapmotion.gesture.FistGesture;
import system.leapmotion.gesture.FlowGesture;
import system.leapmotion.gesture.FourFingerGesture;
import system.leapmotion.gesture.HoldGesture;
import system.leapmotion.gesture.PointingGesture;
import system.leapmotion.gesture.StopGesture;
import system.leapmotion.gesture.ThreeFingerGesture;
import system.leapmotion.gesture.TwoFingerGesture;
import system.leapmotion.gesture.util.CustomGestureList;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.CustomGesture;
import system.model.LVSObject;

import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.SwipeGesture;

/** ************************************************************
 * This class is the controller which controls the gesture recognition
 * process using data from Leap Motion Listener. Each frame will run a 
 * gesture checking and performing. If some previous gesture has held 
 * the input control it will be released base on the result of checking.
 * A gesture list is created to holds all the implemented gesture.
 * As to facilitate the development in short time, gestures are being 
 * recognized base on condition whilst not any models or Classifier.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class GestureController extends LVSObject{
	private Controller controller;
	private CustomGestureList customGestureList;
	private CustomGestureType previousGesture = null;
	
	private float start_x, start_y, start_z;
	
	/**
	 * Constructor
	 * 
	 * @param	controller	default controller of leap motion 
	 * @param	systemController	controller of system
	 * @param	systemPreference	User's Preference of the system
	 */
	public GestureController(Controller controller, SystemController systemController, SystemPreference systemPreference){
		super(systemController, systemPreference);
		this.controller = controller;
		customGestureList = new CustomGestureList(this);
		HoldGesture holdGesture = new HoldGesture(this);
		StopGesture stopGesture = new StopGesture(this);
		PointingGesture pointingGesture = new PointingGesture(this);
		TwoFingerGesture twoFingerGesture = new TwoFingerGesture(this);
		ThreeFingerGesture threeFingerGesture = new ThreeFingerGesture(this);
		FourFingerGesture fourFingerGesture = new FourFingerGesture(this);
		FlowGesture flowGesture = new FlowGesture(this);
		FistGesture fistGesture = new FistGesture(this);
		ClapGesture clapGesture = new ClapGesture(this);
		
		//EnableGesture
		holdGesture.setEnable(true);
		stopGesture.setEnable(true);
		pointingGesture.setEnable(true);
		twoFingerGesture.setEnable(true);
		threeFingerGesture.setEnable(true);
		fourFingerGesture.setEnable(true);
		flowGesture.setEnable(true);
		fistGesture.setEnable(true);
		clapGesture.setEnable(true);
		
		//Add Gesture to Gesture List for checking 
		customGestureList.addGesture(clapGesture);
		customGestureList.addGesture(holdGesture);
		customGestureList.addGesture(stopGesture);
		customGestureList.addGesture(pointingGesture);
		customGestureList.addGesture(twoFingerGesture);
		customGestureList.addGesture(flowGesture);
		customGestureList.addGesture(threeFingerGesture);
		customGestureList.addGesture(fourFingerGesture);
		customGestureList.addGesture(fistGesture);
	}
	
	/**
	 * Function to check both default gesture from leap motion and 
	 * custom gesture. Default gesture has a higher priority then
	 * custom gesture.
	 *
	 * @return	hasGesture	boolean of Gesture detected or not
	 */
	public boolean perform_Gesture(){
		Frame frame = controller.frame();
		GestureList gesture_List = frame.gestures();
		boolean hasGesture = false;

		//Default Gesture (Gesture Command)
		for (int i = 0 ; i < gesture_List.count() ; i++) {
    		Gesture gesture = gesture_List.get(i);
    		this.updateGestureStatus(null, gesture);
    		// Check the follow command enable only if index finger is extended
			boolean[] hand = super.getSystemController().getLeapMotionFrameController().getStyleHandIsExtended();
			boolean valid = (!hand[0] && hand[1] && !hand[2] && !hand[3] && !hand[4]);
			System.out.println(gesture.type());
    		switch (gesture.type()){
    		case TYPE_CIRCLE:
    			CircleGesture circle = new CircleGesture(gesture);

    			String clockwiseness;
    			if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
    				clockwiseness = "clockwise";
    			} else {
    				clockwiseness = "anti-clockwise";
    			}
    			
    			double sweptAngle = 0;
    			/*if (circle.state() != State.STATE_START){
    				CircleGesture previous = new CircleGesture(controller.frame(1).gesture(circle.id()));
    				sweptAngle = (circle.progress() - previous.progress()) * 2 * Math.PI;
    			}*/
    			if (circle.state() == State.STATE_STOP && valid){
    				System.out.println(clockwiseness);
    			}
    			
    			//System.out.println("Circle ID: " + circle.id() + " State: " + circle.state() + " Progress: " + circle.progress() + " Radius: " + circle.radius() + " Angle: " + Math.toDegrees(sweptAngle) + " " + clockwiseness);
    			break;
    		case TYPE_KEY_TAP:
    			if (getSystemController().getCurrentFile() != null && getSystemController().getCurrentFile().getCurrentActor().getSlice().isVisiable()){
    				//System.out.println("OverLayer Click");
    				getSystemController().getCursorController().leftClick();
    				//getSystemController().getOverLayerController().checkComponent(true);
    			}else{
    				//System.out.println("Mouse Click");
    				getSystemController().getCursorController().leftClick();
    			}
    			break;
    		case TYPE_SWIPE:
    			SwipeGesture swipe = new SwipeGesture(gesture);
    			//System.out.println("Swipe ID: " + swipe.id() + " State: " + swipe.state() + " Swipe Position: " + swipe.position() + " Direction: " + swipe.direction() + " Speed" + swipe.speed());
    			if(swipe.state().equals(State.STATE_START)){
    				start_x = swipe.direction().getX();
    				start_y = swipe.direction().getY();
    			}
    			if(swipe.state().equals(State.STATE_STOP)){
    				if(swipe.direction().getX() < -0.5 && valid){
                		super.getSystemController().changeActor(-1);
                    	//System.out.println("left : " + "start = " + start_x + ", stop = " +swipe.direction().getX());
                    }
                    else if(swipe.direction().getX() > 0.5 && valid){	
                    	super.getSystemController().changeActor(1);
                        //System.out.println("right : " + "start = " + start_x + ", stop = " +swipe.direction().getX()); 
                    }
                    if(swipe.direction().getY() < -0.5){
                    	//System.out.println("down: " + "start = " + start_y + ", stop = " +swipe.direction().getY());
                    }
                    else if(swipe.direction().getY() > 0.5){
                    	//System.out.println("up: " + "start = " + start_y + ", stop = " +swipe.direction().getY());
                    }
                }
    			break;
    		default:
    			break;
    		}
		}

		// Check if any default gesture recognized if not try to detect custom gesture
		if (gesture_List.count() == 0){
			CustomGestureType gestureType = check_custom_Gesture();
			this.checkPerviousGesture(gestureType);
			if (gestureType != null && gestureType != CustomGestureType.UNKNOWN_GESTURE)
				hasGesture = true;
		} else if (gesture_List.count() > 0 ){
			hasGesture = true;
		}
		return hasGesture;
	}
	
	/**
	 * Function call the update the gesture status panel
	 */
	public void updateGestureStatus(CustomGesture c_gesture, Gesture gesture){
		super.getSystemController().getOverLayerController().updateGestureStatus(c_gesture,gesture);
	}
	
	/**
	 * Function to check custom gesture and return type of gesture
	 * detected.
	 *
	 * @param	frame	the current frame using
	 * @return	CustomGestureType	the custom gesture detected
	 */
	public CustomGestureType check_custom_Gesture(){
		CustomGestureType result = null;
		//System.out.println("GestureListSize : " + this.customGestureList.getCustomGestureList().size());
		for (int i = 0 ; i < this.customGestureList.getCustomGestureList().size() ; i++){
			CustomGesture gesture = customGestureList.getCustomGestureList().get(i);
			//System.out.println(customGestureList.getCustomGestureList().size() + " : " + i + customGestureList.getCustomGestureList().get(i).getGestureName());
			result = customGestureList.getCustomGestureList().get(i).check_Gesture();
			if ( result != null && result != CustomGestureType.UNKNOWN_GESTURE){
				gesture.performAction();
				this.updateGestureStatus(gesture, null);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Function to check current gesture with previous gesture. If
	 * the gesture has changed, different function will be called.
	 *
	 * @param	currentGesture	the current custom gesture detected
	 */
	public void checkPerviousGesture(CustomGestureType currentGesture){
		if (previousGesture != null && previousGesture != CustomGestureType.UNKNOWN_GESTURE){
			switch(previousGesture){
			case THREE_FINGER_GESTURE:
				if (currentGesture != previousGesture){
					this.getSystemController().getCursorController().releaseInput();
				}
				break;
			case STOP_GESTURE:
				if (currentGesture != previousGesture){
					this.getSystemController().getOverLayerController().setGestureGuideVisible(false);
				}
				break;
			}
		}
		previousGesture = currentGesture;
	}

	// Attributes Set and Get
	public CustomGestureList getCustomGestureList(){
		return this.customGestureList;
	}
	
	public CustomGestureType getPreviousGesture() {
		return previousGesture;
	}

	public void setPreviousGesture(CustomGestureType previousGesture) {
		this.previousGesture = previousGesture;
	}
}

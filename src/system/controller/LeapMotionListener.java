package system.controller;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

/** ************************************************************
 * This class is the Custom Listener extend from the Leap Motion 
 * Listener. Different function is called at different connection
 * state.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LeapMotionListener extends Listener {
	SystemController systemController;
	
	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system
	 */
	public LeapMotionListener(SystemController systemController){
		super();
		this.systemController = systemController;
	}
	
	/**
	 * Function called when controller is connected
	 * 
	 * @param	controller	default controller of Leap Motion
	 */
	public void onConnect(Controller controller) {		
		System.out.println("Connected");
		//controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		// controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		if (systemController.getSwingController() != null)
			systemController.getSwingController().changeLeapMotionStatus(true);
	}

	/**
	 * Function called for every frame 
	 * If frame has hand draw overlay's component
	 * Else if the last frame was longer than threshold, clean the screen
	 * 
	 * @param	controller	default controller of leap motion
	 */
	public void onFrame(Controller controller) {
		this.getSystemController().getLeapMotionFrameController().updateFrame(controller.frame());
		if(this.getSystemController().getLeapMotionFrameController().hasHand()){
			boolean hasGesture = this.getSystemController().getGestureController().perform_Gesture();
			this.getSystemController().getOverLayerController().updateHasGesture(hasGesture);
		}
		this.getSystemController().getOverLayerController().updateFrameToOL(controller.frame());
		this.getSystemController().getOverLayerController().updateComponentPos();
		this.getSystemController().getOverLayerController().repaintOverLayer();
	}

	/**
	 * Function called when Leap Motion is disconnected
	 * 
	 * @param	controller	default controller of leap motion
	 */
	public void onDisconnect(Controller controller) {
		String message = "Leap Motion Disconnected";
		this.getSystemController().updateSystemStatus(message);
		System.out.println("Leap Motion Disconnected");
		if (systemController.getSwingController() != null)
			systemController.getSwingController().changeLeapMotionStatus(false);
		this.getSystemController().getOverLayerController().updateGestureStatus(null, null);
		this.getSystemController().getOverLayerController().updateHasGesture(false);
		this.getSystemController().getOverLayerController().updateFrameToOL(controller.frame());
		this.getSystemController().getOverLayerController().repaintOverLayer();
		this.getSystemController().checkLeapMotionConnection();
	}

	/**
	 * Function called when the system exit
	 * 
	 * @param	controller	default controller of leap motion
	 */
	public void onExit(Controller controller) {
		System.out.println("Exited");
		if (systemController.getSwingController() != null)
			systemController.getSwingController().changeLeapMotionStatus(false);
	}

	// Attributes Set and Get
	public SystemController getSystemController(){
		return this.systemController;
	}
}

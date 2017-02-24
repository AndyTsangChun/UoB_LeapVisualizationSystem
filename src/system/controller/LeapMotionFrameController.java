package system.controller;
import system.general.SystemPreference;
import system.model.LVSObject;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Vector;

/** ************************************************************
 * This class is the controller of com.leapmotion.leap.Frame 
 * Advanced feature such as stabilized hands and fingers 
 * information are being calculated from each frame the listener
 * provided. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LeapMotionFrameController extends LVSObject{
	private final float clickThreshold = (float) 0.9;
	private final int clickDelayThreshold = 20;
	private final long clearScreenThreshold = 2000;
	private Frame frame;
	
	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system
	 * @param	systemPreference	User's Preference of the system
	 */
	public LeapMotionFrameController(SystemController systemController, SystemPreference systemPreference){
		super(systemController, systemPreference);
	}
	
	/**
	 * Return the Left Hand of user
	 * @return Left Hand
	 */
	public Hand getLeftHand(){
		HandList hands = frame.hands();
		for (int i = 0 ; i < hands.count() ; i++){
			if (hands.get(i).isLeft())
				return hands.get(i);
		}
		return null;
	}
	
	/**
	 * Return the Right Hand of user
	 * @return Right Hand
	 */
	public Hand getRightHand(){
		HandList hands = frame.hands();
		for (int i = 0 ; i < hands.count() ; i++){
			if (hands.get(i).isRight())
				return hands.get(i);
		}
		return null;
	}
	
	/**
	 * Return the normalized leapmotion.Vector of input hand by calculating the the hand
	 * position with the com.leapmotion.leap.InteractionBox.
	 * 
	 * @param hand Input Hand
	 * @return normalizedPoint Normalized leapmotion.Vector of the input hand
	 */
	public Vector getNormalizedHand(Hand hand){
		InteractionBox iBox = frame.interactionBox();
		Vector leapPoint = hand.stabilizedPalmPosition();

		Vector normalizedPoint = iBox.normalizePoint(leapPoint, true);
		
		return normalizedPoint; 
	}
	
	/**
	 * Return the normalized hand position of input hand.
	 * The return object is an integer array with extracted information (x,y,z), for further
	 * system use.
	 * 
	 * @param hand Input hand
	 * @return position Integer array of the hand position (x,y,z)
	 */
	public int[] getNormalizedHandPosition(Hand hand){
		Vector vector = this.getNormalizedHand(hand);
		int[] position = handToPosition(vector);
		
		return position;
	}
	
	/**
	 * Return the unstabilized Hand leapmotion.Vector of input hand by calculating the the hand
	 * position with the com.leapmotion.leap.InteractionBox. This function mainly use for speed 
	 * tracking or dynamic hand.
	 * 
	 * @param hand Input Hand
	 * @return normalizedPoint Normalized leapmotion.Vector of the input hand
	 */
	public Vector getUnstabilizedHand(Hand hand){
		InteractionBox iBox = frame.interactionBox();
		Vector unstabilized_normalizedPoint = iBox.normalizePoint(hand.palmPosition(), true);
		
		return unstabilized_normalizedPoint;
	}
	
	/**
	 * Return the unstabilized hand position of input hand.
	 * The return object is an integer array with extracted information (x,y,z), for further
	 * system use.
	 * 
	 * @param hand Input hand
	 * @return position Integer array of the hand position (x,y,z)
	 */
	public int[] getUnstabilizedHandPosition(Hand hand){
		Vector vector = this.getUnstabilizedHand(hand);
		int[] position = handToPosition(vector);
		
		return position;
	}
	
	/**
	 * Extracting the X,Y,Z position from hand vector into an integer array
	 * @param hand Input hand vector
	 * @return position Integer array of the hand position (x,y,z)
	 */
	public int[] handToPosition(Vector hand){
		int[] position = new int[3]; 
		position[0] = (int)(hand.getX() * super.getSystemPreference().getScreenWidth());
		position[1] = (int)((1 - hand.getY()) * super.getSystemPreference().getScreenHeight());
		position[2] = (int)(hand.getZ() * 100);
		
		return position;
	}
	
	/**
	 * Return the fingers extended information from frame
	 * @return status 2D Boolean array [0] for style-hand [1] for sub-hand 
	 */
	public boolean[][] getHandsIsExtended(){
		boolean[][] status = new boolean[2][5];
		status[0][0] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_THUMB);
		status[0][1] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_INDEX);
		status[0][2] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_MIDDLE);
		status[0][3] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_RING);
		status[0][4] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_PINKY);
		
		status[1][0] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_THUMB);
		status[1][1] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_INDEX);
		status[1][2] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_MIDDLE);
		status[1][3] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_RING);
		status[1][4] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_PINKY);
		
		return status;
	}
	
	/**
	 * Return the fingers extended information of style-hand form frame
	 * @return status Boolean array of fingers extended info.
	 */
	public boolean[] getStyleHandIsExtended(){
		boolean[] status = new boolean[5];
		status[0] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_THUMB);
		status[1] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_INDEX);
		status[2] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_MIDDLE);
		status[3] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_RING);
		status[4] = getFingerState(getStyleHand().fingers(), Finger.Type.TYPE_PINKY);
		
		return status;
	}
	
	/**
	 * Return the fingers extended information of sub-hand form frame
	 * @return status Boolean array of fingers extended info.
	 */
	public boolean[] getSubHandIsExtended(){
		boolean[] status = new boolean[5];
		status[0] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_THUMB);
		status[1] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_INDEX);
		status[2] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_MIDDLE);
		status[3] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_RING);
		status[4] = getFingerState(getSubHand().fingers(), Finger.Type.TYPE_PINKY);
		
		return status;
	}
	
	/**
	 * Return the finger extended state of targeted finger
	 * @param fingers Target Hand
	 * @param finger_Type Target Finger Type
	 * @return boolean extended status
	 */
	public boolean getFingerState(FingerList fingers, Finger.Type finger_Type){
		for(int i = 0 ; i < fingers.count() ; i++){
			Finger finger = fingers.get(i);
			if(finger.type() == finger_Type)
				return finger.isExtended();
		}
		return false;
	}
	
	/**
	 * Return the hand detection status, true for >= 1 , else return false. 
	 * @return boolean hand detection statis
	 */
	public boolean hasHand(){
		if(frame.hands() == null || frame.hands().count() <= 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Return hand-list from frame
	 * @return 
	 */
	public HandList getHands(){
		return frame.hands();
	}
	
	/**
	 * Return finger-list from frame
	 * @return
	 */
	public FingerList getFingers(){
		return frame.fingers();
	}
	
	/**
	 * Return numbers of hand detected
	 * @return int No. of hand detected
	 */
	public int noOfHand(){
		return frame.hands().count();
	}
	
	/**
	 * Previous function require to check singlehand and zclick
	 * Function to check and perform z-aixs clicking
	 *
	 * @return	click	boolean of Z < threshold if true click
	 */
	public boolean checkZClick(){
		boolean click = false;
		
		Hand hand = this.getStyleHand();
		if (hand != null){
			InteractionBox iBox = frame.interactionBox();
			Vector unstabilized_normalizedPoint = iBox.normalizePoint(hand.palmPosition(), true);
			float cursor_Z = unstabilized_normalizedPoint.getZ();
			//System.out.println("Z: " + cursor_Z);
			if (cursor_Z < clickThreshold){
				click = true;
			}
		}
		return click;
	}
	
	/**
	 * Function to return the major hand of user base on systemPreferece Setting
	 *
	 * @return	hand	the major hand of user    
	 */
	public Hand getStyleHand(){
		HandList hands = frame.hands();
		Hand return_hand = null;
		if ((super.getSystemPreference().isLeftStyle() && super.getSystemPreference().isSingleHand()) || (!super.getSystemPreference().isLeftStyle() && !super.getSystemPreference().isSingleHand())){
			for (int i = 0 ; i < hands.count() ; i++){
				if (hands.get(i).isLeft()){
					return_hand = hands.get(i);
					break;
				}	
			}
		}else{
			for (int i = 0 ; i < hands.count() ; i++){
				if (hands.get(i).isRight()){
					return_hand = hands.get(i);
					break;
				}	
			}
		}
		return return_hand;
	}
	
	/**
	 * Function to return another hand of user base on systemPreferece Setting
	 *
	 * @return	hand	another hand of user    
	 */
	public Hand getSubHand(){
		HandList hands = frame.hands();
		Hand return_hand = null;
		if (super.getSystemPreference().isLeftStyle()){
			for (int i = 0 ; i < hands.count() ; i++){
				if (hands.get(i).isRight()){
					return_hand = hands.get(i);
					break;
				}	
			}
		}else{
			for (int i = 0 ; i < hands.count() ; i++){
				if (hands.get(i).isLeft()){
					return_hand = hands.get(i);
					break;
				}	
			}
		}
		return return_hand;
	}
	
	// Attributes Set and Get
	/**
	 * Update the current frame for extracting data
	 * @param frame Current Frame
	 */
	public void updateFrame(Frame frame){
		this.frame = frame;
	}
}

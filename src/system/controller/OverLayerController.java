package system.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import system.general.SystemPreference;
import system.model.CustomGesture;
import system.model.LVSObject;
import ui.awt.panel.LVSOverLayerPanel;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;

/** ************************************************************
 * This class is the controller of the over layer which used to
 * display additional HCI support for VTKPanel. This layer exist
 * due to no only the compatibility problem of VTK and MacOS, but
 * also the API used for draw java UI and VTKPanel. Since AWT was 
 * a heavy-weight component, and its drawing sequence was after 
 * Swing. Such that, it is not implementable to draw this layer 
 * in the origin JFrame. If OpenGL Canvas is used for the 
 * implementation this layer could be reduced.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class OverLayerController extends LVSObject {
	private LVSOverLayerPanel overLayerPanel;

	private JFrame frame;
	private boolean isTrackingVisible = true;
	private boolean isGestureStatusVisible = true;
	private boolean isGestureGuideVisible = false;
	private Point defaultLocation = new Point(0,0);

	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system
	 * @param	systemPreference	User's Preference of the system
	 */
	public OverLayerController(SystemController systemController,
			SystemPreference systemPreference) {
		super(systemController, systemPreference);
		frame = new JFrame();
		overLayerPanel = new LVSOverLayerPanel(this);
	}

	/**
	 * Step up the frame to be transparent and not focusable such to it will not block the all other functions.
	 */
	public void setup() {
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		//frame.setEnabled(false);
		frame.setFocusable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.addComponentListener(new ComponentListener(){
			@Override
			public void componentResized(ComponentEvent e) {
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				frame.setLocation(defaultLocation);
			}
			@Override
			public void componentShown(ComponentEvent e) {	
			}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		// Set the frame invisible
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setContentPane(overLayerPanel);
		frame.setVisible(true);
		defaultLocation = frame.getLocationOnScreen();
		overLayerPanel.setup();
	}
	
	/**
	 * Check the overlapping status of user's hand and over-layer components
	 * @param isClick
	 */
	public void checkComponent(boolean isClick) {
		Hand hand = this.getSystemController().getLeapMotionFrameController().getStyleHand();
		int[] handPos = this.getSystemController().getLeapMotionFrameController().getNormalizedHandPosition(hand);
		float cursor_X = handPos[0];
		float cursor_Y = handPos[1] - SystemPreference.MENU_BAR_OFFSET;

		overLayerPanel.checkComponent((int) cursor_X, (int) cursor_Y, isClick);
	}

	/**
	 * Function called to update gesture status
	 * 
	 * @param c_gesture Customer Gesture currently recognized
	 * @param gesture Default Gesture offer by Leap Motion
	 */
	public void updateGestureStatus(CustomGesture c_gesture, Gesture gesture){
		if(gesture != null){
			overLayerPanel.setGestureString(gesture.type().name());
		}
		if (c_gesture != null){
			overLayerPanel.setGestureImage(c_gesture.getImage());
			overLayerPanel.setGestureString(c_gesture.getOperationName());
		}
	}
	
	/**
	 * Function call to update other info used by over layer
	 * @param frame The current frame
	 * @param hasGesture Boolean state is there any gesture recognized in the current frame
	 */
	public void updateFrameToOL(Frame frame) {
		overLayerPanel.updateFrame(frame);
	}
	
	/**
	 * Function call to update hasGesture Boolean
	 * @param hasGesture Boolean state is there any gesture recognized in the current frame
	 */
	public void updateHasGesture(boolean hasGesture){
		overLayerPanel.setHasGesture(hasGesture);
	}
	
	/**
	 * Function call to update every component position
	 */
	public void updateComponentPos(){
		overLayerPanel.updateComponentPos();
	}
	
	/**
	 * Function call to repaint overlayer
	 */
	public void repaintOverLayer(){
		overLayerPanel.repaint();
	}
	
	/**
	 * Set visibility of overLayer  
	 * @param isVisible
	 */
	public void setOverLayerVisibilty(boolean isVisible){
		frame.setVisible(isVisible);
	}
	
	// Attributes Set and Get
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void setTrackingVisibility(boolean isTrackingVisible){
		this.isTrackingVisible = isTrackingVisible;
	}
	
	public boolean isTrackingVisible(){
		return this.isTrackingVisible;
	}

	public boolean isGestureStatusVisible() {
		return isGestureStatusVisible;
	}

	public void setGestureStatusVisible(boolean isGestureStatusVisible) {
		this.isGestureStatusVisible = isGestureStatusVisible;
	}
	
	public LVSOverLayerPanel getOverLayerPanel() {
		return overLayerPanel;
	}

	public void setOverLayerPanel(LVSOverLayerPanel overLayerPanel) {
		this.overLayerPanel = overLayerPanel;
	}

	public boolean isGestureGuideVisible() {
		return isGestureGuideVisible;
	}

	public void setGestureGuideVisible(boolean isGestureGuideVisible) {
		this.isGestureGuideVisible = isGestureGuideVisible;
	}
}

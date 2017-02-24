package ui.awt.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import system.controller.LeapMotionFrameController;
import system.controller.OverLayerController;
import system.general.SystemPreference;
import system.model.OverLayerButton;
import ui.awt.dialog.edit.tracking.TrackingModel;
import ui.awt.overlayer.SliceTranslateButton;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Vector;

/** ************************************************************
 * This class extends LVSPanel, an over layer display finger
 * tracking ,gesture status and all other addition Graphics. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSOverLayerPanel extends JPanel {
	private static final int DOT_SIZE = 20;
	private static final int GESTURE_OFFSET_X = 50;
	private static final int GESTURE_OFFSET_Y = 100;
	public static final int GESTURE_IMAGE_W = 75;
	public static final int GESTURE_IMAGE_H = 100;
	private Frame frame;
	private InteractionBox iBox;
	private boolean hasGesture;
	private OverLayerController overLayerController;
	private LeapMotionFrameController frameController;
	private Image gestureImage;
	private String gestureString;
	private List<OverLayerButton> componentList;
	
	/**
	 * Constructor
	 * @param overLayerController Controller of this over layer panel
	 */
	public LVSOverLayerPanel(OverLayerController overLayerController) {
		super();
		this.overLayerController = overLayerController;
		this.frameController = getOverLayerController().getSystemController().getLeapMotionFrameController();
		componentList = new ArrayList<OverLayerButton>();
		
		OverLayerButton moveSlice = new SliceTranslateButton(this, 0, SwingTextureManager.DEFAULT_SLICE_TRANSLATE_ICON_IMAGE);
		moveSlice.setEnableIcon(SwingTextureManager.ENABLE_SLICE_TRANSLATE_ICON_IMAGE);
		componentList.add(moveSlice);
	}
	
	/**
	 * Set up the panel
	 */
	public void setup(){
		this.setLayout(null);
	}
	
	/**
	 * Update Leap Motion Frame Information
	 * @param frame	Leap Motion Frame
	 */
	public void updateFrame(Frame frame){
		this.frame = frame;
	}
	
	/**
	 * Function call to update every component position
	 */
	public void updateComponentPos(){
		for (int i = 0 ; i < componentList.size() ; i++){
			componentList.get(i).updatePosition();
		}
	}

	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		// clear all old item before drawing
		g2d.clearRect(0, 0, overLayerController.getSystemPreference().getScreenWidth(), overLayerController.getSystemPreference().getScreenHeight());
		// draw swing component
		for (int i = 0 ; i < componentList.size() ; i++){
			OverLayerButton button = componentList.get(i);
			if (button.isButtonVisible()){
				button.setVisible(true);
			}else{
				button.setVisible(false);
			}
		}
		this.paintComponents(g);
		
		// draw gesture guide
		if (overLayerController.isGestureGuideVisible()){
			int width = this.overLayerController.getSystemPreference().getScreenWidth();
			int height = this.overLayerController.getSystemPreference().getScreenHeight();
			int w = (int)(width * 0.8);
			int h = (int)(height * 0.9);
			int x = (width - w) / 2;
			int y = (height - h) / 2;
			g2d.drawImage(SwingTextureManager.TUT_GESTURE_IMAGE, x, y, w, h, null);
		}
		
		// draw other component
		if (frame != null && frame.hands().count() > 0){
			if (overLayerController.getSystemController().getLeapMotionController().isConnected()){
				if(overLayerController.isGestureStatusVisible())
					drawGestureStatus(g2d);
				if(overLayerController.isTrackingVisible())
					drawFingersTracking(g2d);
			}
		}
	}
	
	/**
	 * Draw gesture status
	 * @param g2d Java Graphics
	 */
	public void drawGestureStatus(Graphics2D g2d){
		g2d.setColor(Color.WHITE);
		double w = this.getSize().getWidth();
		double h = this.getSize().getHeight();
		if (hasGesture){
			if (gestureImage != null && !gestureImage.equals("")){
				g2d.drawImage(gestureImage, (int)(w - GESTURE_IMAGE_W - GESTURE_OFFSET_X), GESTURE_OFFSET_Y , GESTURE_IMAGE_W, GESTURE_IMAGE_H, null);
			}
			if (gestureString != null && !gestureString.equals("")){
				g2d.drawString(gestureString, (int)(w - GESTURE_IMAGE_W - GESTURE_OFFSET_X), GESTURE_OFFSET_Y + GESTURE_IMAGE_H + 20);
			}
		}
	}
	
	/**
	 * Draw fingers
	 * @param g2d Java Graphics
	 */
	public void drawFingersTracking(Graphics2D g2d){
		TrackingModel trackingModel = this.getOverLayerController().getSystemController().getSwingController().getTrackingModel();
		iBox = frame.interactionBox();
		if (frameController.hasHand()){
			Hand hand = frameController.getLeftHand();
			if (hand !=null){
				if (trackingModel.isShowLeftPalm()){
					drawHand(hand, g2d);
				}
				FingerList fingerList = hand.fingers();
				if (fingerList != null && fingerList.count() > 0){
					if (overLayerController.getSystemPreference().getFinger_color() != null)
						g2d.setColor(overLayerController.getSystemPreference().getFinger_color());
					else
						g2d.setColor(Color.GREEN);
					for (int i = 0; i < fingerList.count(); i++) {
						Finger finger = fingerList.get(i);
						if (finger.type() == Finger.Type.TYPE_THUMB && trackingModel.isShowLeftThumb())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_INDEX && trackingModel.isShowLeftIndex())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_MIDDLE && trackingModel.isShowLeftMiddle())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_RING && trackingModel.isShowLeftRing())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_PINKY && trackingModel.isShowLeftPinky())
							drawFinger(finger, g2d);
					}
				}
			}
			hand = frameController.getRightHand();
			if (hand !=null){
				if (trackingModel.isShowRightPalm()){
					drawHand(hand, g2d);
				}
				FingerList fingerList = hand.fingers();
				if (fingerList != null && fingerList.count() > 0){
					if (overLayerController.getSystemPreference().getFinger_color() != null)
						g2d.setColor(overLayerController.getSystemPreference().getFinger_color());
					else
						g2d.setColor(Color.GREEN);
					for (int i = 0; i < fingerList.count(); i++) {
						Finger finger = fingerList.get(i);
						if (finger.type() == Finger.Type.TYPE_THUMB && trackingModel.isShowRightThumb())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_INDEX && trackingModel.isShowRightIndex())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_MIDDLE && trackingModel.isShowRightMiddle())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_RING && trackingModel.isShowRightRing())
							drawFinger(finger, g2d);
						if (finger.type() == Finger.Type.TYPE_PINKY && trackingModel.isShowRightPinky())
							drawFinger(finger, g2d);
					}
				}
			}
		}
	}

	public void drawHand (Hand hand, Graphics g2d){
		if (overLayerController.getSystemPreference().getPalm_color() != null)
			g2d.setColor(overLayerController.getSystemPreference().getPalm_color());
		else
			g2d.setColor(Color.BLUE);
		int[] plamPos = frameController.getNormalizedHandPosition(hand);
		g2d.fillOval(plamPos[0], plamPos[1] - SystemPreference.MENU_BAR_OFFSET, DOT_SIZE, DOT_SIZE);
	}
	
	public void drawFinger(Finger finger, Graphics g2d){
		Vector leapPoint = finger.stabilizedTipPosition();
		Vector normalizedPoint = iBox.normalizePoint(leapPoint, true);
		float cursor_X = normalizedPoint.getX() * overLayerController.getSystemPreference().getScreenWidth();
		float cursor_Y = (1 - normalizedPoint.getY()) * overLayerController.getSystemPreference().getScreenHeight();
		g2d.fillOval((int) cursor_X, (int) cursor_Y, DOT_SIZE, DOT_SIZE);
	}
	
	/**
	 * Perform button function
	 * 
	 * @param rect
	 */
	public void checkComponent(int x1, int y1, boolean isDown) {
		int x2 = x1 + 1, y2 = y1 + 1;
		Rectangle2D rect = new Rectangle2D.Double((x1 < x2) ? x1 : x2, (y1 < y2) ? y1
				: y2, Math.abs(x2 - x1) + 1, Math.abs(y2 - y1) + 1);
		double posX,posY;
		int width, height;
		Iterator<OverLayerButton> buttonIterator = componentList.iterator();
		while (buttonIterator.hasNext()) {
			OverLayerButton object = buttonIterator.next();
			posX = object.getPosX();
			posY = object.getPosY();
			width = object.getWidth();
			height = object.getHeight();
			// compare their rectangular area
			if (rect.intersects(new Rectangle2D.Double(posX, posY, width, height))){
				if(isDown){
					object.performAction();
				}else{
					System.out.println("2");
				}
			}else{
				if(!isDown){
				}
			}
		}
	}
	
	// Attributes Set and Get
	public Image getGestureImage() {
		return gestureImage;
	}

	public void setGestureImage(Image gestureImage) {
		this.gestureImage = gestureImage;
	}

	public String getGestureString() {
		return gestureString;
	}

	public void setGestureString(String gestureString) {
		this.gestureString = gestureString;
	}
	
	public OverLayerController getOverLayerController() {
		return overLayerController;
	}

	public void setOverLayerController(OverLayerController overLayerController) {
		this.overLayerController = overLayerController;
	}

	public boolean hasGesture() {
		return hasGesture;
	}

	public void setHasGesture(boolean hasGesture) {
		this.hasGesture = hasGesture;
	}

	public List<OverLayerButton> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<OverLayerButton> componentList) {
		this.componentList = componentList;
	}
}

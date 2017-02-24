package ui.awt.dialog.edit.tracking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import layout.TableLayout;
import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.listener.TrackingEditDialogButtonListener;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

/** ************************************************************
 * This class extends LVSPanel, a movement tracking edit panel 
 * which allows user to edit tracking information.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSTrackingEditDialog extends LVSPanel{
	private static final Color[] colorSet = {new Color(255,0,0),new Color(255,155,28),new Color(255,255,57),new Color(0,255,0),new Color(28,255,232),new Color(0,0,255),new Color(200,0,255),new Color(0,0,0),new Color(100,100,100),new Color(180,180,180),new Color(255,255,255)};
	private static final String[] colorName = {"Red","Orange","Yellow","Green","Cyan","Blue","Purple","Black","Dark Grey","Light Grey","White"};
	public static final String COMMAND_ALL = "All";
	public static final String COMMAND_ALL_FINGERS = "AllFingers";
	public static final String COMMAND_PALMS = "Palms";
	public static final String COMMAND_LFINGERS = "LeftFingers";
	public static final String COMMAND_RFINGERS = "RightFingers";
	public static final String COMMAND_NONE = "None";
	public static final String COMMAND_FINGER_COLOR = "fingerColor";
	public static final String COMMAND_PALM_COLOR = "palmColor";
	private int frame_w, frame_h, panel_w, panel_h;
	private LVSTrackingEditPanel trackingEditPanel;
	private TrackingEditDialogButtonListener listener;
	private JPanel rightPanel;
	private JLabel fingerColorLabel, palmColorLabel;
	private JButton all, all_fingers, palms, lfingers, rfingers, none;
	private JComboBox fingerColor, palmColor;

	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSTrackingEditDialog(SwingController swingController, JFrame mainFrame, TrackingModel trackingModel) {
		super(swingController, mainFrame);
		
		trackingEditPanel = new LVSTrackingEditPanel(super.getSwingController(), this, super.getMainFrame(), trackingModel);
		rightPanel = new JPanel();
		listener = new TrackingEditDialogButtonListener(this);
		
		all = new JButton("All");
		all_fingers = new JButton("All Fingers");
		palms = new JButton("Palms");
		lfingers = new JButton("Left Fingers");
		rfingers = new JButton("Right Fingers");
		none = new JButton("None");
		fingerColorLabel = new JLabel("Finger : ");
		fingerColor = new JComboBox();
		palmColorLabel = new JLabel("Palm : ");
		palmColor = new JComboBox();
	}

	@Override
	public void initPanel() {
		this.setLayout(new BorderLayout());
		frame_w = super.getSwingController().getSystemPreference().getScreenWidth();
		frame_h = super.getSwingController().getSystemPreference().getScreenHeight();
		panel_h = (int)(frame_h * LVSSwingInfo.EDIT_TRACKING_DIALOG_H_RATIO);
		panel_w = (int)(frame_w * LVSSwingInfo.EDIT_TRACKING_DIALOG_W_RATIO);
		this.setSize(new Dimension(panel_w, panel_h));
		this.setPreferredSize(new Dimension(panel_w, panel_h));
		
		trackingEditPanel.initPanel();
		double h = LVSSwingInfo.EDIT_TRACKING_CONTROL_BUTTON_H;
		double space = 15;
		double[][] size = {{TableLayout.FILL},{h,space,h,space,h,space,h,space,h,space,h,space,h,h,h,h}};
		rightPanel.setLayout(new TableLayout(size));
		
		formatButton(all);
		all.setActionCommand(LVSTrackingEditDialog.COMMAND_ALL);
		formatButton(all_fingers);
		all_fingers.setActionCommand(LVSTrackingEditDialog.COMMAND_ALL_FINGERS);
		formatButton(palms);
		palms.setActionCommand(LVSTrackingEditDialog.COMMAND_PALMS);
		formatButton(lfingers);
		lfingers.setActionCommand(LVSTrackingEditDialog.COMMAND_LFINGERS);
		formatButton(rfingers);
		rfingers.setActionCommand(LVSTrackingEditDialog.COMMAND_RFINGERS);
		formatButton(none);
		none.setActionCommand(LVSTrackingEditDialog.COMMAND_NONE);
		fingerColor = formatComboBox();
		fingerColor.setActionCommand(LVSTrackingEditDialog.COMMAND_FINGER_COLOR);
		int fcIndex = getColorIndex(super.getSwingController().getSystemPreference().getFinger_color());
		if (fcIndex != -1)
			fingerColor.setSelectedIndex(fcIndex);
		palmColor = formatComboBox();
		palmColor.setActionCommand(LVSTrackingEditDialog.COMMAND_PALM_COLOR);
		int pcIndex = getColorIndex(super.getSwingController().getSystemPreference().getPalm_color());
		if (pcIndex != -1)
			palmColor.setSelectedIndex(pcIndex);
		
		rightPanel.add(all,"0,0");
		rightPanel.add(all_fingers,"0,2");
		rightPanel.add(palms,"0,4");
		rightPanel.add(lfingers,"0,6");
		rightPanel.add(rfingers,"0,8");
		rightPanel.add(none,"0,10");
		rightPanel.add(fingerColorLabel,"0,12");
		rightPanel.add(fingerColor,"0,13");
		rightPanel.add(palmColorLabel,"0,14");
		rightPanel.add(palmColor,"0,15");
		
		this.add(trackingEditPanel, BorderLayout.CENTER);
		this.add(new JScrollPane(rightPanel), BorderLayout.EAST);
	}
	
	/**
	 * Format JComboBox
	 * @param comboBox
	 */
	public JComboBox formatComboBox(){
		JComboBox comboBox = new JComboBox(colorName);
		TrackingCBRenderer renderer = new TrackingCBRenderer(comboBox); 
		renderer.setColors(colorSet);
		renderer.setStrings(colorName);
		comboBox.setRenderer(renderer);
		comboBox.addActionListener(listener);
		
		return comboBox;
	}
	
	/**
	 * Format JButton
	 * @param button
	 */
	public void formatButton(JButton button){
		button.setSize(new Dimension(LVSSwingInfo.EDIT_TRACKING_CONTROL_BUTTON_W, LVSSwingInfo.EDIT_TRACKING_CONTROL_BUTTON_H));
		Image icon = SwingTextureManager.BUTTON_BG_2_IMAGE;
		icon = icon.getScaledInstance(LVSSwingInfo.EDIT_TRACKING_CONTROL_BUTTON_W, LVSSwingInfo.EDIT_TRACKING_CONTROL_BUTTON_H,  java.awt.Image.SCALE_SMOOTH );
		button.setIcon(new ImageIcon(icon));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addActionListener(listener);
	}
	
	/**
	 * Function call to get the chosen color from colorSet
	 * @param index Of target color
	 * @return Color Chosen Color
	 */
	public Color getChosenColor(int index){
		return this.colorSet[index];
	}
	
	public int getColorIndex(Color color){
		if (color == null)
			return -1;
		for (int i = 0 ; i < colorSet.length ; i++){
			if (colorSet[i].getRed() == color.getRed() && colorSet[i].getGreen() == color.getGreen() && colorSet[i].getBlue() == color.getBlue()){
				return i;
			}
		}
		return -1;
	}
	
	// Attribute Set and Get
	
	public TrackingEditDialogButtonListener getListener() {
		return listener;
	}

	public void setListener(TrackingEditDialogButtonListener listener) {
		this.listener = listener;
	}

	public LVSTrackingEditPanel getTrackingEditPanel() {
		return trackingEditPanel;
	}

	public void setTrackingEditPanel(LVSTrackingEditPanel trackingEditPanel) {
		this.trackingEditPanel = trackingEditPanel;
	}

}

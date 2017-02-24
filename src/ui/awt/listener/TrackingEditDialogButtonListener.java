package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import system.model.LVSPanel;
import ui.awt.dialog.edit.tracking.LVSTrackingEditDialog;
import ui.awt.dialog.edit.tracking.LVSTrackingEditPanel;

/** ************************************************************
 * This is a listener implementing awt.event.ActionListener
 * Handles all the action in LVSTrackingEditDialog
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class TrackingEditDialogButtonListener implements ActionListener{
	private LVSTrackingEditDialog trackingEditDialog;
	
	/**
	 * Constructor
	 * 
	 * @param trackingEditDialog
	 */
	public TrackingEditDialogButtonListener(LVSTrackingEditDialog trackingEditDialog) {
		this.trackingEditDialog = trackingEditDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			String command = button.getActionCommand();
			// Due to java 1.7 and 1.8 don't support vtk graphics this part has
			// to convert to if-else
			if (command.equals(LVSTrackingEditPanel.COMMAND_LT)) {
				trackingEditDialog.getTrackingEditPanel().changeLT();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_LI)) {
				trackingEditDialog.getTrackingEditPanel().changeLI();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_LM)) {
				trackingEditDialog.getTrackingEditPanel().changeLM();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_LR)) {
				trackingEditDialog.getTrackingEditPanel().changeLR();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_LP)) {
				trackingEditDialog.getTrackingEditPanel().changeLP();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_RT)) {
				trackingEditDialog.getTrackingEditPanel().changeRT();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_RI)) {
				trackingEditDialog.getTrackingEditPanel().changeRI();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_RM)) {
				trackingEditDialog.getTrackingEditPanel().changeRM();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_RR)) {
				trackingEditDialog.getTrackingEditPanel().changeRR();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_RP)) {
				trackingEditDialog.getTrackingEditPanel().changeRP();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_LPALM)) {
				trackingEditDialog.getTrackingEditPanel().changeLPALM();
			}else if (command.equals(LVSTrackingEditPanel.COMMAND_RPALM)) {
				trackingEditDialog.getTrackingEditPanel().changeRPALM();
			}else if (command.equals(LVSTrackingEditDialog.COMMAND_ALL)) {
				trackingEditDialog.getTrackingEditPanel().getTrackingModel().all();
			}else if (command.equals(LVSTrackingEditDialog.COMMAND_ALL_FINGERS)) {
				trackingEditDialog.getTrackingEditPanel().getTrackingModel().allFingers();
			}else if (command.equals(LVSTrackingEditDialog.COMMAND_PALMS)) {
				trackingEditDialog.getTrackingEditPanel().getTrackingModel().palms();
			}else if (command.equals(LVSTrackingEditDialog.COMMAND_LFINGERS)) {
				trackingEditDialog.getTrackingEditPanel().getTrackingModel().leftFingers();
			}else if (command.equals(LVSTrackingEditDialog.COMMAND_RFINGERS)) {
				trackingEditDialog.getTrackingEditPanel().getTrackingModel().rightFingers();
			}else if (command.equals(LVSTrackingEditDialog.COMMAND_NONE)) {
				trackingEditDialog.getTrackingEditPanel().getTrackingModel().none();
			}
			trackingEditDialog.getTrackingEditPanel().repaint();
		} else if (e.getSource() instanceof JComboBox) {
			JComboBox box = (JComboBox) e.getSource();
			String command = box.getActionCommand();
			if (command.equals(LVSTrackingEditDialog.COMMAND_FINGER_COLOR)){
				trackingEditDialog.getSwingController().getSystemPreference().setFinger_color(trackingEditDialog.getChosenColor(box.getSelectedIndex()));
				trackingEditDialog.getTrackingEditPanel().setFinger_color(trackingEditDialog.getChosenColor(box.getSelectedIndex()));
				trackingEditDialog.getTrackingEditPanel().repaint();
			} else if (command.equals(LVSTrackingEditDialog.COMMAND_PALM_COLOR)) {
				trackingEditDialog.getSwingController().getSystemPreference().setPlam_color(trackingEditDialog.getChosenColor(box.getSelectedIndex()));
				trackingEditDialog.getTrackingEditPanel().setPalm_color(trackingEditDialog.getChosenColor(box.getSelectedIndex()));
				trackingEditDialog.getTrackingEditPanel().repaint();
			}
		}
	}

}

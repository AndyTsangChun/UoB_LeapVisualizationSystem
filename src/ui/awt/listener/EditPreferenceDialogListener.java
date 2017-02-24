package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import system.model.LVSPanel;
import ui.awt.dialog.edit.preference.LVSPreferenceEditDialog;
import ui.awt.dialog.edit.tracking.LVSTrackingEditDialog;

/**
 * ************************************************************ 
 * This is a listener implementing awt.event.ActionListener 
 * Handles all the action in editPreferencePanel.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class EditPreferenceDialogListener implements ActionListener {
	private LVSPreferenceEditDialog preferencePanel;

	/**
	 * Constructor
	 * 
	 * @param preferencePanel 
	 */
	public EditPreferenceDialogListener(LVSPreferenceEditDialog preferencePanel) {
		this.preferencePanel = preferencePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			String command = e.getActionCommand();
			// Due to java 1.7 and 1.8 don't support vtk graphics this part has
			// to convert to if-else
			if (command.equals(LVSPanel.COMMAND_CANCEL)) {
				// Do sth to cancel
				preferencePanel.getSwingController().closeEditPanel();
			} else if (command.equals(LVSPanel.COMMAND_APPLY)) {
				// Do sth to apply change
				preferencePanel.saveChanges();
				preferencePanel.getSwingController().updateSystemPreference();
				preferencePanel.getSwingController().closeEditPanel();
			}
		}
//		} else if (e.getSource() instanceof JComboBox) {
//			JComboBox box = (JComboBox) e.getSource();
//			String command = box.getActionCommand();
//			if (command.equals(LVSPreferenceEditDialog.COMMAND_HAND_STYLE)) {
//				if (box.getSelectedItem().toString().equals("Left"))
//					preferencePanel.getSwingController().getSystemPreference()
//							.setLeftStyle(true);
//				else
//					preferencePanel.getSwingController().getSystemPreference()
//							.setLeftStyle(false);
//			} else if (command
//					.equals(LVSPreferenceEditDialog.COMMAND_ROTATE_SPEED)) {
//				preferencePanel
//						.getSwingController()
//						.getSystemPreference()
//						.setRotateSpeed(
//								Integer.parseInt(box.getSelectedItem()
//										.toString()));
//			}
//		}
	}

}

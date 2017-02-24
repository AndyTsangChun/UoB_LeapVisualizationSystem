package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.dialog.edit.file.LVSFileItemEditDialog;

/** ************************************************************
 * This is a listener implementing awt.event.ActionListener
 * Handles all the action in editPanel
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class EditItemDialogListener implements ActionListener{
	private LVSFileItemEditDialog itemEditPanel;

	/**
	 * Constructor
	 * @param itemEditPanel
	 */
	public EditItemDialogListener(LVSFileItemEditDialog itemEditPanel) {
		this.itemEditPanel = itemEditPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		int id = itemEditPanel.getShowingActorID();
		if (command.equals(LVSPanel.COMMAND_CANCEL)){
			// Do sth to cancel
			itemEditPanel.getSwingController().closeEditPanel();
		}else if (command.equals(LVSPanel.COMMAND_BACK)){
			itemEditPanel.getSwingController().openFileEditDialog(SwingController.FILE_EDIT_NORMAL, null);
			// Do sth to delete selected
		}else if (command.equals(LVSPanel.COMMAND_APPLY)){
			itemEditPanel.saveChange();
		}else if (command.equals(LVSFileItemEditDialog.COMMAND_THRESHOLD)){
			itemEditPanel.getSwingController().openFileEditDialog(id, command);
		}else if (command.equals(LVSFileItemEditDialog.COMMAND_CONTOUR)){
			itemEditPanel.getSwingController().openFileEditDialog(id, command);
		}else if (command.equals(LVSFileItemEditDialog.COMMAND_SLICE)){
			itemEditPanel.getSwingController().openFileEditDialog(id, command);
		}
	}

}

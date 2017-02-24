package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import system.model.LVSPanel;
import ui.awt.dialog.tutorial.LVSTutorialDialog;

/** *********************************************************** 
 * This is a listener implementing awt.event.ActionListener
 * Handles all the action in tutorialDialog
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class TutorialDialogListener implements ActionListener{
	private LVSTutorialDialog tutorialDialog;

	/**
	 * Constructor
	 * @param tutorialDialog 
	 */
	public TutorialDialogListener(LVSTutorialDialog tutorialDialog) {
		this.tutorialDialog = tutorialDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(LVSTutorialDialog.COMMAND_PREVIOUS_ITEM)){
			tutorialDialog.changeCurrentIndex(-1);
		}else if (command.equals(LVSTutorialDialog.COMMAND_NEXT_ITEM)){
			tutorialDialog.changeCurrentIndex(1);
		}else {
			tutorialDialog.changeCurrentIndexByInt(Integer.parseInt(command));
		}
	}

}

package ui.awt.listener;

import java.awt.CheckboxMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import system.controller.SwingController;
import system.model.LVSPanel;

/** ************************************************************
 * This is a listener implementing awt.event.ActionListener and awt.event.ItemListener
 * Handles all the action in menu bar
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class MenuItemListener implements ActionListener,ItemListener {
	private SwingController swing_c;
	
	/**
	 * Constructor
	 * @param swing_c Swing Controller
	 */
	public MenuItemListener(SwingController swing_c) {
		this.swing_c = swing_c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// Due to java 1.7 and 1.8 don't support vtk graphics this part has to convert to if-else
		if (command.equals(LVSPanel.COMMAND_ABOUT)){
			swing_c.openAboutDialog();
		} else if (command.equals(LVSPanel.COMMAND_NEW)){
			swing_c.getSystemController().getFileHandler().newLVSFile();
		}else if (command.equals(LVSPanel.COMMAND_OPEN)){
			swing_c.getSystemController().getFileHandler().openLVSFile();
		}else if (command.equals(LVSPanel.COMMAND_SAVE)){
			swing_c.getSystemController().getFileHandler().saveLVSFile();
		}else if (command.equals(LVSPanel.COMMAND_SAVE_AS)){
			swing_c.getSystemController().getFileHandler().saveAsLVSFile();
		}else if (command.equals(LVSPanel.COMMAND_QUIT)){
			
		}else if (command.equals(LVSPanel.COMMAND_IMPORT_OBJ)){
			swing_c.getSystemController().getObjectHandler().importObject();
		}else if (command.equals(LVSPanel.COMMAND_EDIT_OBJ)){
			swing_c.openFileEditDialog(SwingController.FILE_EDIT_NORMAL, null);
		}else if (command.equals(LVSPanel.COMMAND_EDIT_TRACKING)){
			swing_c.openTrackingEditDialog();
		}else if (command.equals(LVSPanel.COMMAND_EDIT_PREFERENCE)){
			swing_c.openPreferenceDialog();
		}else if (command.equals(LVSPanel.COMMAND_TUTORIAL)){
			swing_c.openTutorialDialog();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o instanceof CheckboxMenuItem){
			String command = ((CheckboxMenuItem) o).getActionCommand();
			if(command.equals(LVSPanel.COMMAND_TRACKING)){
				CheckboxMenuItem cb = (CheckboxMenuItem)o;
				swing_c.getSystemController().getOverLayerController().setTrackingVisibility(cb.getState());
			}else if (command.equals(LVSPanel.COMMAND_GESTURE)){
				CheckboxMenuItem cb = (CheckboxMenuItem)o;
				swing_c.getSystemController().getOverLayerController().setGestureStatusVisible(cb.getState());
			} else if (command.equals(LVSPanel.COMMAND_PRESENTATION)){
				CheckboxMenuItem cb = (CheckboxMenuItem)o;
				swing_c.switchPresentationMode(cb.getState());
			}
		}
		swing_c.updateMenuBar();
		swing_c.updateToolBar();
	}

}

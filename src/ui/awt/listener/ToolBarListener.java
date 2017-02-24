package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;

import system.controller.SwingController;
import system.model.LVSPanel;

/** *********************************************************** 
 * This is a listener implementing awt.event.ActionListener and awt.event.ItemListener
 * Handles all the action in tool bar
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class ToolBarListener implements ActionListener, ItemListener, MouseListener{
	private SwingController swing_c;

	/**
	 * Constructor
	 * 
	 * @param swing_c Swing Controller
	 */
	public ToolBarListener(SwingController swing_c) {
		this.swing_c = swing_c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			String command = button.getActionCommand();
			// Due to java 1.7 and 1.8 don't support vtk graphics this part has
			// to convert to if-else
			if (command.equals(LVSPanel.COMMAND_NEW)) {
				swing_c.getSystemController().getFileHandler().newLVSFile();
			} else if (command.equals(LVSPanel.COMMAND_OPEN)) {
				swing_c.getSystemController().getFileHandler().openLVSFile();
			} else if (command.equals(LVSPanel.COMMAND_SAVE)) {
				swing_c.getSystemController().getFileHandler().saveLVSFile();
			} else if (command.equals(LVSPanel.COMMAND_SAVE_AS)) {
				swing_c.getSystemController().getFileHandler().saveAsLVSFile();
			} else if (command.equals(LVSPanel.COMMAND_IMPORT_OBJ)){
				swing_c.getSystemController().getObjectHandler().importObject();
			} else if (command.equals(LVSPanel.COMMAND_PRE_ACTOR)) {
				swing_c.getSystemController().changeActor(-1);
			} else if (command.equals(LVSPanel.COMMAND_NEXT_ACTOR)) {
				swing_c.getSystemController().changeActor(1);
			}
		} else if (e.getSource() instanceof JComboBox) {
			JComboBox box = (JComboBox) e.getSource();
			String command = box.getActionCommand();
			if (command.equals(LVSPanel.COMMAND_OPACITY)){
				String s = (box.getSelectedItem()+"").trim();
				if (!s.equals("") && s != null){
					double opacity = (Integer.parseInt(box.getSelectedItem()+"")) / 100.0;
					swing_c.getSystemController().changeOpacity(opacity);
				}else{
					swing_c.updateToolBar();
				}
			} else if (command.equals(LVSPanel.COMMAND_COLORMAP)) {
				swing_c.getSystemController().changeColorMap(box.getSelectedIndex() - 1);
			} else if (command.equals(LVSPanel.COMMAND_REPRESENTATION)){
				swing_c.getSystemController().changeRepresentation(box.getSelectedIndex());
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() instanceof JToggleButton) {
			JToggleButton toggleButton = (JToggleButton) e.getItem();
			String command = toggleButton.getActionCommand();
			// Due to java 1.7 and 1.8 don't support vtk graphics this part has
			// to convert to if-else
			if (command.equals(LVSPanel.COMMAND_TRACKING)) {
				swing_c.getSystemController().getOverLayerController()
						.setTrackingVisibility(toggleButton.isSelected());
			} else if (command.equals(LVSPanel.COMMAND_GESTURE)) {
				swing_c.getSystemController().getOverLayerController()
						.setGestureStatusVisible(toggleButton.isSelected());
			} else if (command.equals(LVSPanel.COMMAND_PRESENTATION)){
				swing_c.switchPresentationMode(toggleButton.isSelected());
			}
			swing_c.updateMenuBar();
			swing_c.updateToolBar();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("hi");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

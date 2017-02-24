package ui.awt.panel;

import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.listener.MenuItemListener;

/** ************************************************************
 * This class extends LVSPanel, a menu bar shows all the system option.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSMenuBar extends LVSPanel{
	private MenuBar lvsMenu;
	private Menu file;
	private Menu object;
	private Menu view;
	private Menu help;
	// File
	private MenuItem newFile;
	private MenuItem openFile;
	private MenuItem saveFile;
	private MenuItem saveAsFile;
	private MenuItem editPreference;
	private MenuItem quit;
	// Object
	private MenuItem importObject;
	private MenuItem editObject;
	// View
	private CheckboxMenuItem presentation;
	private CheckboxMenuItem tracking;
	private CheckboxMenuItem gesture;
	private MenuItem editTracking;
	// Help
	private MenuItem aboutLVS;
	private MenuItem tutorial;
	private MenuItemListener menuItemListener;
	
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSMenuBar(SwingController swingController, JFrame mainFrame) {
		super(swingController, mainFrame);
		lvsMenu = new MenuBar();
		file = new Menu("File");
		help = new Menu("Help");
		object = new Menu("Object");
		view = new Menu("View");

		// File
		newFile = new MenuItem("New", new MenuShortcut(KeyEvent.VK_N));
		openFile = new MenuItem("Open", new MenuShortcut(KeyEvent.VK_O));
		saveFile = new MenuItem("Save", new MenuShortcut(KeyEvent.VK_S));
		saveAsFile = new MenuItem("Save As");
		editPreference = new MenuItem("Edit Preference");
		quit = new MenuItem("Quit");
		// Object
		importObject = new MenuItem("Import Object", new MenuShortcut(KeyEvent.VK_I));
		editObject = new MenuItem("Edit Object");
		// View 
		presentation = new CheckboxMenuItem("Presentation", false);
		presentation.setShortcut(new MenuShortcut(KeyEvent.VK_P));
		tracking = new CheckboxMenuItem("Tracking", true);
		tracking.setShortcut(new MenuShortcut(KeyEvent.VK_T));
		gesture = new CheckboxMenuItem("Gesture Status", true);
		gesture.setShortcut(new MenuShortcut(KeyEvent.VK_G));
		editTracking = new MenuItem("Edit Tracking Info");
		// Help
		aboutLVS = new MenuItem("About LVS");
		tutorial = new MenuItem("Tutorial");
		menuItemListener = new MenuItemListener(swingController);
	}

	@Override
	public void initPanel() {
		updateMenuBar();
		// File
		newFile.setActionCommand(LVSPanel.COMMAND_NEW);
		openFile.setActionCommand(LVSPanel.COMMAND_OPEN);
		saveFile.setActionCommand(LVSPanel.COMMAND_SAVE);
		saveAsFile.setActionCommand(LVSPanel.COMMAND_SAVE_AS);
		editPreference.setActionCommand(LVSPanel.COMMAND_EDIT_PREFERENCE);
		quit.setActionCommand(LVSPanel.COMMAND_QUIT);
		
		newFile.addActionListener(menuItemListener);
		openFile.addActionListener(menuItemListener);
		saveFile.addActionListener(menuItemListener);
		saveAsFile.addActionListener(menuItemListener);
		editPreference.addActionListener(menuItemListener);
		quit.addActionListener(menuItemListener);
		
		file.add(newFile);
		file.add(openFile);
		file.add(saveFile);
		file.add(saveAsFile);
		file.addSeparator();
		file.add(editPreference);
		file.addSeparator();
		file.add(quit);
		// Object
		importObject.setActionCommand(LVSPanel.COMMAND_IMPORT_OBJ);
		editObject.setActionCommand(LVSPanel.COMMAND_EDIT_OBJ);
		
		importObject.addActionListener(menuItemListener);
		editObject.addActionListener(menuItemListener);
		
		object.add(importObject);
		object.add(editObject);
		// View 
		presentation.setActionCommand(LVSPanel.COMMAND_PRESENTATION);
		tracking.setActionCommand(LVSPanel.COMMAND_TRACKING);
		gesture.setActionCommand(LVSPanel.COMMAND_GESTURE);
		editTracking.setActionCommand(LVSPanel.COMMAND_EDIT_TRACKING);
		
		presentation.addItemListener(menuItemListener);
		presentation.addActionListener(menuItemListener);
		tracking.addItemListener(menuItemListener);
		tracking.addActionListener(menuItemListener);
		gesture.addItemListener(menuItemListener);
		gesture.addActionListener(menuItemListener);
		editTracking.addActionListener(menuItemListener);
		
		view.add(presentation);
		view.addSeparator();
		view.add(tracking);
		view.add(gesture);
		view.addSeparator();
		view.add(editTracking);
		// Help
		aboutLVS.setActionCommand(LVSPanel.COMMAND_ABOUT);
		tutorial.setActionCommand(LVSPanel.COMMAND_TUTORIAL);
		
		aboutLVS.addActionListener(menuItemListener);
		tutorial.addActionListener(menuItemListener);
		
		help.add(aboutLVS);
		help.add(tutorial);
		// add menu to menubar
		lvsMenu.add(file);
		lvsMenu.add(object);
		lvsMenu.add(view);
		lvsMenu.add(help);
		
		// add menubar to the panel
		super.getMainFrame().setMenuBar(lvsMenu);
	}
	
	/**
	 * Update menu bar, if the system has a valid file "save" and other option will be enabled, else disable
	 */
	public void updateMenuBar(){
		boolean hasFile = super.getSwingController().getSystemController().getCurrentFile() != null;
		if (hasFile){
			saveFile.setEnabled(true);
			saveAsFile.setEnabled(true);
			editObject.setEnabled(true);
		}else{
			saveFile.setEnabled(false);
			saveAsFile.setEnabled(false);
			editObject.setEnabled(false);
		}
		presentation.setState(this.getSwingController().isPresentationMode());
		tracking.setState(this.getSwingController().getSystemController().getOverLayerController().isTrackingVisible());
		gesture.setState(this.getSwingController().getSystemController().getOverLayerController().isGestureStatusVisible());
	}

}

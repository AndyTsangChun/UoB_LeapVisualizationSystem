package system.file;

import javax.swing.JFrame;

import system.controller.SystemController;
import system.controller.VTKController;
import system.general.SystemPreference;
import system.model.LVSHandler;
import ui.awt.res.LVSConfigInfo;

/** ************************************************************
 * This class is Imported File Handler which handles all the operation
 * working with the imported Data file (e.g. .vtk, .stl)
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ObjectFileHandler extends LVSHandler {
	private JFrame frame;

	/**
	 * Constructor
	 * @param systemController	controller of system    
	 * @param systemPreference	User's Preference of the system
	 * @param frame	Main Frame
	 */
	public ObjectFileHandler(SystemController systemController, SystemPreference systemPreference, JFrame frame) {
		super(systemController, systemPreference, frame);
	}
	
	/**
	 * Read the file using information from file info
	 * @param fileInfo String array containing the file information, 0 is File Name, 1 is File Path
	 * @return item	File Item
	 */
	public FileItem getFileItem(String[] fileInfo){
		FileItem item = null;
		if (fileInfo[0] != null && fileInfo[1] != null){
			if (!fileInfo[0].equals("") && !fileInfo[1].equals("")){
				item = new FileItem(super.getSystemController().getCurrentFile().getNextItemID(), fileInfo[0], fileInfo[1], true, VTKController.DEFAULT_OPACITY);
				super.getSystemController().getVTKController().extractInfo(item);
				item.getSlice().setNormalValue(VTKController.SLICE_DEFAULT_NORMAL);
			}
		}
		return item;
	}
	
	/**
	 * Import a file (e.g. .vtk)
	 */
	public void importObject(){
		String[] importFileInfo = chooseReadPath(LVSConfigInfo.OBJ_FILTER_DESC, LVSConfigInfo.OBJ_FILTER_OPTION);
		if(getSystemController().getCurrentFile() == null)
			getSystemController().getFileHandler().newLVSFile();
		FileItem item = getFileItem(importFileInfo);
		if (item != null){
			getSystemController().getCurrentFile().addFile(item);
			System.out.println(importFileInfo[0] + " added.");
			getSystemController().getSwingController().updateFileTree();
			getSystemController().getSwingController().updateVTK();
			getSystemController().getSwingController().updateToolBar();
		}
	}
	
	public void editObject(){
		
	}
}

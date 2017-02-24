package system.model;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import system.controller.SystemController;
import system.general.SystemPreference;
import ui.awt.res.LVSConfigInfo;

/** ************************************************************
 * This class is the model class of all file handler in LVS.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSHandler extends LVSObject {
	private JFrame frame;

	public LVSHandler(SystemController systemController, SystemPreference systemPreference, JFrame frame) {
		super(systemController, systemPreference);
		this.frame = frame;
	}
	
	/**
	 * Use JFile chooser to choose a file
	 * 
	 * @param filterDesc Description of filter
	 * @param filterOption filter extension
	 * @return fileInfo Array holds Name and Path of the file user selected
	 */
	public String[] chooseReadPath(String filterDesc, String[] filterOption) {
		String message = "";
		String[] fileInfo = new String[2];
		JFileChooser chooser = new JFileChooser();
		// new FileNameExtensionFilter(Description, extension1, extension2 .....)
		FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDesc, filterOption);
		chooser.setFileFilter(filter);
		String path = super.getSystemPreference().getLastViewPath();
		if (path == null || path.equals(""))
			path = LVSConfigInfo.DEFAULT_FILE_PATH;
		if (path.equals(LVSConfigInfo.DEFAULT_FILE_PATH))
			chooser.setCurrentDirectory(new File(System.getProperty(path)));
		else
			chooser.setCurrentDirectory(new File(path));
		int result = chooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			fileInfo[0] = file.getName();
			fileInfo[1] = file.getAbsolutePath();
			String newPath = file.getParent();
			if (newPath != null && !newPath.equals(""))
				super.getSystemPreference().setLastViewPath(newPath);
			message = "Opened file: " + fileInfo[0] + ", Path = " + fileInfo[1];
		} else {
			message = "No File Chosen";
		}
		this.getSystemController().updateSystemStatus(message);
		return fileInfo;
	}
	
	/**
	 * Check the file exists or not
	 * @param path Path of the file user selected to read
	 * @return boolean of the file exists or not
	 */
	public boolean checkFile(String path){
		if (path != null){
			File f = new File(path);
			if(f.exists() && !f.isDirectory()) { 
			    return true;
			}
		}
		return false;
	}
	
	/**
	 * Create the file
	 * @param path Absolute path of the saving destination
	 */
	public void createFile(String path){
		File file = new File(path);
		try {
			if (file.createNewFile()){
			    System.out.println("File is created!");
			  }else{
			    System.out.println("File already exists.");
			  }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Use JFileChooser to select the saving destination
	 * 
	 * @return fileInfo An array holding Name and Absolute path of the saving destination
	 */
	public String[] chooseSavePath(){
		String[] fileInfo = new String[2];
		JFileChooser chooser = new JFileChooser();
		int rVal = chooser.showSaveDialog(frame);
		if (rVal == JFileChooser.APPROVE_OPTION) {
	        String name = chooser.getSelectedFile().getName();
	        if (name.endsWith(".lvs")){
	        	fileInfo[0] = name;
	        	fileInfo[1] = chooser.getCurrentDirectory().getAbsolutePath() + "/" + name;
	        }else{
	        	fileInfo[0] = name + ".lvs";
	        	fileInfo[1] = chooser.getCurrentDirectory().getAbsolutePath() + "/" + name + ".lvs";
	        }
	    }
		return fileInfo;
	}

}

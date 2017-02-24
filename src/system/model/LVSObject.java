package system.model;

import java.io.File;

import system.controller.SystemController;
import system.general.SystemPreference;

/** ************************************************************
 * This class is the model of all controller in LVS.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSObject {
	private SystemController systemController;
	private SystemPreference systemPreference; 
	
	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system    
	 * @param	systemPreference	User's Preference of the system
	 */
	public LVSObject(SystemController systemController, SystemPreference systemPreference){
		this.systemController = systemController;
		this.systemPreference = systemPreference;
	}
	
	/**
	 * Get Target File's Extension
	 * @param file Target File
	 * @return String Extension
	 */
	public String getExtension(File file){
		String[] tmp = file.getName().split("\\.");
		if (tmp.length > 0)
			return tmp[tmp.length-1];
		return null;
	}
	
	// Attributes Set and Get
	public SystemController getSystemController() {
		return this.systemController;
	}
	
	public SystemPreference getSystemPreference() {
		return this.systemPreference;
	}
}

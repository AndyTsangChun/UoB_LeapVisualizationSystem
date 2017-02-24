package system.model;

import system.file.FileItem;

/** ************************************************************
 * This is a model of all file item
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class FileItemObject implements java.io.Serializable{
	private FileItem item;
	private boolean isVisiable;
	
	/**
	 * Constructor
	 * @param item The file Item
	 * @param isVisiable Is this item visible
	 */
	public FileItemObject(FileItem item, boolean isVisiable) {
		this.setItem(item);
		this.setVisiable(isVisiable);
	}

	// Attributes Set and Get
	public boolean isVisiable() {
		return isVisiable;
	}

	public void setVisiable(boolean isVisiable) {
		this.isVisiable = isVisiable;
	}

	public FileItem getItem() {
		return item;
	}

	public void setItem(FileItem item) {
		this.item = item;
	}

}

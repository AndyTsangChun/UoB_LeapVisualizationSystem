package system.file.objects;

import system.file.FileItem;
import system.model.FileItemObject;

/** ************************************************************
 * This class is an Object Class of vtk threshold filter, storing
 * all the user preference applying on this filter.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ObjThreshold extends FileItemObject {
	private double[] customRange;
	
	/**
	 * Constructor
	 * @param item File Item reference to
	 * @param isVisiable Is this object visible
	 */
	public ObjThreshold(FileItem item, boolean isVisiable) {
		super(item, isVisiable);
	}

	// Attributes Set and Get
	public double[] getCustomRange() {
		return customRange;
	}

	public void setCustomRange(double[] customRange) {
		this.customRange = customRange;
	}

}

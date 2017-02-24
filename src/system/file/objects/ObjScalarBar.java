package system.file.objects;

import system.file.FileItem;
import system.model.FileItemObject;

/** ************************************************************
 * This class is an Object Class of vtk scalar bar, storing
 * all the user preference applying on this widget.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ObjScalarBar extends FileItemObject {
	/**
	 * Constructor
	 * @param item File Item reference to
	 * @param isVisiable Is this object visible
	 */
	public ObjScalarBar(FileItem item, boolean isVisiable) {
		super(item, isVisiable);
	}

}

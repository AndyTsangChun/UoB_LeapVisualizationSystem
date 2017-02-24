package system.file.objects;

import system.file.FileItem;
import system.model.FileItemObject;

/** ************************************************************
 * This class is an Object Class of vtk cutter, storing
 * all the user preference applying on this algorithm.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ObjSlice extends FileItemObject {
	private double[] normalValue;
	private double xTrans,yTrans,zTrans;
	private double transformRate;
	
	/**
	 * Constructor
	 * @param item File Item reference to
	 * @param isVisiable Is this object visible
	 */
	public ObjSlice(FileItem item, boolean isVisiable) {
		super(item, isVisiable);
	}

	// Attributes Set and Get
	public double[] getNormalValue() {
		return normalValue;
	}

	public void setNormalValue(double[] normalValue) {
		this.normalValue = normalValue;
	}

	public double getXTrans() {
		return xTrans;
	}

	public void setXTrans(double xTrans) {
		this.xTrans = xTrans;
	}

	public double getYTrans() {
		return yTrans;
	}

	public void setYTrans(double yTrans) {
		this.yTrans = yTrans;
	}

	public double getZTrans() {
		return zTrans;
	}

	public void setZTrans(double zTrans) {
		this.zTrans = zTrans;
	}

	public double getTransformRate() {
		return transformRate;
	}

	public void setTransformRate(double transformRate) {
		this.transformRate = transformRate;
	}
}

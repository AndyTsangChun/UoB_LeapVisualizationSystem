package system.file.objects;

import system.file.FileItem;
import system.model.FileItemObject;

/** ************************************************************
 * This class is an Object Class of vtk contour filter, storing
 * all the user preference applying on this filter.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class ObjContour extends FileItemObject {
	private double[] customRange;
	private double[] actualValue;
	private int step;
	
	/**
	 * Constructor
	 * @param item File Item reference to
	 * @param isVisiable Is this object visible
	 */
	public ObjContour(FileItem item, boolean isVisiable) {
		super(item, isVisiable);
	}
	
	/**
	 * Generate filter value base on range user set
	 */
	public void generateRange(){
		double min = customRange[0];
		double max = customRange[1];
		double variance = (max - min) / (step - 1);
		actualValue = new double[step];
		for (int i = 0 ; i < (step - 1)  ; i++){
			actualValue[i] = min + (i * variance);
		}
		actualValue[step - 1] = max;
	}

	// Attributes Set and Get
	public double[] getCustomRange() {
		return customRange;
	}

	public void setCustomRange(double[] customRange) {
		this.customRange = customRange;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	public double[] getActualValue() {
		return actualValue;
	}

	public void setActualValue(double[] actualValue) {
		this.actualValue = actualValue;
	}
}

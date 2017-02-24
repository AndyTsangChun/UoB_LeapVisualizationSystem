package system.model;

/** *********************************************************** 
 * This is a model of LVSColorMap containing colorSpace, name, nanColor and RGBPoints.
 * Each of this object should represent a colorMap JSON file.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class LVSColorMap {
	private String colorSpace;
	private String name;
	private double[] nanColor;
	private double[][] RGBPoints;
	
	public LVSColorMap() {
	}

	// Attributes Set and Get
	public String getColorSpace() {
		return colorSpace;
	}

	public void setColorSpace(String colorSpace) {
		this.colorSpace = colorSpace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getNanColor() {
		return nanColor;
	}

	public void setNanColor(double[] nanColor) {
		this.nanColor = nanColor;
	}

	public double[][] getRGBPoints() {
		return RGBPoints;
	}

	public void setRGBPoints(double[][] rGBPoints) {
		RGBPoints = rGBPoints;
	}

}

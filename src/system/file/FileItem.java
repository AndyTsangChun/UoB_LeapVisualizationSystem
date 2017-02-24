package system.file;

import system.file.objects.ObjContour;
import system.file.objects.ObjScalarBar;
import system.file.objects.ObjSlice;
import system.file.objects.ObjThreshold;
import system.file.objects.ObjVolume;

/** ************************************************************
 * This class is an Object Class which holds all the attribute of
 * an imported model object file. The object consist of a ID, file
 *  name and the path. Other attributes are used to facilitate the
 *  GUI representation.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class FileItem implements java.io.Serializable{
	private int id;
	private String name;
	private String path;
	private boolean isVisible;
	private double opacity;
	private int representation;
	private ObjContour contour;
	private ObjScalarBar scalarBar;
	private ObjSlice slice;
	private ObjThreshold threshold;
	private ObjVolume volume;
	private double[] defaultRange;
	private double dataDeviation; // deviation
	
	/**
	 * Constructor
	 * @param id ID of file item
	 * @param name Name of the file item
	 * @param path Path of the file item
	 * @param isVisible Is this file item visible
	 */
	public FileItem(int id, String name, String path, boolean isVisible, double opacity) {
		this.setName(name);
		this.setId(id);
		this.setPath(path);
		this.setVisible(isVisible);
		this.opacity = opacity;
		contour = new ObjContour(this, false);
		scalarBar = new ObjScalarBar(this, false);
		slice = new ObjSlice(this, false);
		threshold = new ObjThreshold(this, false);
		volume = new ObjVolume(this,false);
	}

	// Attributes Set and Get
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisable) {
		this.isVisible = isVisable;
	}
	
	public String toString(){
		return this.name;
	}

	public ObjContour getContour() {
		return contour;
	}

	public void setContour(ObjContour contour) {
		this.contour = contour;
	}

	public ObjSlice getSlice() {
		return slice;
	}

	public void setSlice(ObjSlice slice) {
		this.slice = slice;
	}

	public ObjScalarBar getScalarBar() {
		return scalarBar;
	}

	public void setScalarBar(ObjScalarBar scalarBar) {
		this.scalarBar = scalarBar;
	}

	public ObjThreshold getThreshold() {
		return threshold;
	}

	public void setThreshold(ObjThreshold threshold) {
		this.threshold = threshold;
	}

	public double[] getDefaultRange() {
		return defaultRange;
	}

	public void setDefaultRange(double[] defaultRange) {
		this.defaultRange = defaultRange;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public int getRepresentation() {
		return representation;
	}

	public void setRepresentation(int representation) {
		this.representation = representation;
	}

	public double getDataDeviation() {
		return dataDeviation;
	}

	public void setDataDeviation(double dataDeviation) {
		this.dataDeviation = dataDeviation;
	}
}

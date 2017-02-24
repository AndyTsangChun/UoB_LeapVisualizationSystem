package system.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import system.bugreport.ExceptionCatcher;
import system.controller.SystemController;
import system.general.SystemPreference;
import system.model.LVSColorMap;
import system.model.LVSObject;

/** *********************************************************** 
 * This is a decoder for JSON colorMap file export from Paraview
 * that imported to LVS. The file will be parse to an LVSColorMap
 * Object and stored in a list for further GUI purpose.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class ColorMappingDecoder extends LVSObject{
	private File folder;
	private List<LVSColorMap> colorMaps;
	/**
	 * Constructor
	 * @param systemController	controller of system    
	 * @param systemPreference	User's Preference of the system
	 */
	public ColorMappingDecoder(SystemController systemController, SystemPreference systemPreference) {
		super(systemController, systemPreference);
		colorMaps = new ArrayList<LVSColorMap>();
	}
	
	/**
	 * Load ColorMaps from directory.
	 * @param folder Target folder
	 */
	public void loadColorMaps(File folder){
		if (folder.listFiles() != null){
			for (File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            loadColorMaps(fileEntry);
		        } else {
		        	if (getExtension(fileEntry).equals("json")){
		        		colorMaps.add(readFileContent(fileEntry));
		        	}
		        }
		    }
		}
	}
	
	/**
	 * Read the JSON file into LVSColorMap and add to ColorMap
	 * @param file Input JSON file
	 * @return colorMap LVSColorMap parsed from the JSON file
	 */
	public LVSColorMap readFileContent(File file){
		LVSColorMap colorMap = new LVSColorMap();
		BufferedReader br = null;
		try {
			String newLine;
			String content = "";
			br = new BufferedReader(new FileReader(file));
			while ((newLine = br.readLine()) != null) {
				content += newLine;
			}
			if (content != null){
				JSONParser parser = new JSONParser();
				JSONArray array = (JSONArray)parser.parse(content);
				JSONObject obj = (JSONObject) array.get(0);
				String name = obj.get("Name").toString();
				String colorSpace = obj.get("ColorSpace").toString();
				JSONArray points = (JSONArray) obj.get("RGBPoints");
				double[][] RGBPoints = convertToRGBPoints(points.toArray());
				JSONArray nan = (JSONArray) obj.get("NanColor");
				double[] nanColor = null;
				if (nan != null)
					nanColor = convertToNanColor(nan.toArray());
				colorMap.setName(name);
				colorMap.setColorSpace(colorSpace);
				colorMap.setNanColor(nanColor);
				colorMap.setRGBPoints(RGBPoints);
			}
		} catch (Exception e) {
			ExceptionCatcher.logException(e);
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return colorMap;
	}
	
	/**
	 * Convert an object array into RGBPoints array(double[][]).
	 * @param points Object array
	 * @return RGBPoints Double 2D array
	 */
	public double[][] convertToRGBPoints(Object[] points){
		double[][] RGBPoints = new double[points.length / 4][4];
		for (int i = 0 ; i < (points.length / 4) ; i++ ){
			int c = (4 * i);
			RGBPoints[i][0] = Double.parseDouble(points[0 + c]+"");
			RGBPoints[i][1] = Double.parseDouble(points[1 + c]+"");
			RGBPoints[i][2] = Double.parseDouble(points[2 + c]+"");
			RGBPoints[i][3] = Double.parseDouble(points[3 + c]+"");
		}
		return RGBPoints;
	}
	
	/**
	 * Convert an object array into NanColor array(double[]);
	 * @param points Object array
	 * @return NanColor Double array
	 */
	public double[] convertToNanColor(Object[] points){
		double[] NanColor = new double[points.length];
		for (int i = 0 ; i < points.length ; i++ ){
			NanColor[i] = Double.parseDouble(points[i]+"");
		}
		return NanColor;
	}
	
	// Attributes Set and Get
	public File getFolder(){
		return this.folder;
	}

	public List<LVSColorMap> getColorMaps() {
		return colorMaps;
	}
}

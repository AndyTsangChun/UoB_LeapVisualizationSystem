package system.general;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import system.controller.SystemController;
import system.model.LVSColorMap;
import system.util.ColorMappingDecoder;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Screen;
import com.leapmotion.leap.ScreenList;

/** ************************************************************
 * This class controls the setting and updating of system
 * preference.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class SystemPreference {
	private SystemController systemController;
	private Properties properties;
	public final String PROPERTIES_FILE_NAME = "lvs.properties";
	public final String COLORMAP_DIRECTORY = "rsc/colorMapping";
	private boolean showTutorial;
	private boolean isTesting;
	private boolean isPrinting;
	private boolean isSingleHand;
	private boolean isLeftStyle;
	private boolean isZClick;
	private int rotate_speed;
	private boolean isShowLeftThumb;
	private boolean isShowLeftIndex;
	private boolean isShowLeftMiddle;
	private boolean isShowLeftRing;
	private boolean isShowLeftPinky;
	private boolean isShowRightThumb;
	private boolean isShowRightIndex;
	private boolean isShowRightMiddle;
	private boolean isShowRightRing;
	private boolean isShowRightPinky;
	private boolean isShowLeftPlam;
	private boolean isShowRightPlam;
	private String lastViewPath;

	// Others preference not from the file
	public static int MENU_BAR_OFFSET = 0;
	private Controller controller;
	private Screen screen;
	private static int screenWidth;
	private static int screenHeight;
	private Color finger_color;
	private Color plam_color;
	private List<LVSColorMap> colorMaps;
	private int currentColorMap = -1;
	private int currentFilter = 0;
	
	/**
	 * Constructor 
	 *
	 * @param  controller	Leap Motion controller
	 */
	public SystemPreference(SystemController systemController, Controller controller){
		this.systemController = systemController;
		this.controller = controller;
		this.setScreen();
		this.properties = new Properties();
		readSystemPreference();
		ColorMappingDecoder decoder = new ColorMappingDecoder(systemController, this);
		decoder.loadColorMaps(new File(COLORMAP_DIRECTORY));
		colorMaps = decoder.getColorMaps();
	}
	
	/**
	 * Function reading system preference from document "lvs.properties"
	 * If the file doesn't exists, create the file
	 * 
	 */
	public void readSystemPreference(){
		FileInputStream in = null;
		try {
			in = new FileInputStream(PROPERTIES_FILE_NAME);
			properties.load(in) ;
			showTutorial = Boolean.valueOf(properties.getProperty("showTutorial"));
			isTesting = Boolean.valueOf(properties.getProperty("test"));
			isPrinting = Boolean.valueOf(properties.getProperty("print"));
			isSingleHand = Boolean.valueOf(properties.getProperty("single_hand"));
			isLeftStyle = Boolean.valueOf(properties.getProperty("left_style"));
			isZClick = Boolean.valueOf(properties.getProperty("zclick"));
			rotate_speed = Integer.valueOf(properties.getProperty("rotate_speed"));
			isShowLeftThumb = Boolean.valueOf(properties.getProperty("isShowLeftThumb"));
			isShowLeftIndex = Boolean.valueOf(properties.getProperty("isShowLeftIndex"));
			isShowLeftMiddle = Boolean.valueOf(properties.getProperty("isShowLeftMiddle"));
			isShowLeftRing = Boolean.valueOf(properties.getProperty("isShowLeftRing"));
			isShowLeftPinky = Boolean.valueOf(properties.getProperty("isShowLeftPinky"));
			isShowRightThumb = Boolean.valueOf(properties.getProperty("isShowRightThumb"));
			isShowRightIndex = Boolean.valueOf(properties.getProperty("isShowRightIndex"));
			isShowRightMiddle = Boolean.valueOf(properties.getProperty("isShowRightMiddle"));
			isShowRightRing = Boolean.valueOf(properties.getProperty("isShowRightRing"));
			isShowRightPinky = Boolean.valueOf(properties.getProperty("isShowRightPinky"));
			isShowLeftPlam = Boolean.valueOf(properties.getProperty("isShowLeftPlam"));
			isShowRightPlam = Boolean.valueOf(properties.getProperty("isShowRightPlam"));
			lastViewPath = String.valueOf(properties.getProperty("lastViewPath"));
			finger_color = stringToColor(String.valueOf(properties.getProperty("fingerColor")));
			plam_color = stringToColor(String.valueOf(properties.getProperty("plamColor")));
			// = Boolean.valueOf(properties.getProperty(""));
		} catch (FileNotFoundException e) {
			System.out.println("Properties Not Found.");
			System.out.println("Creating Properties File...");
			this.writeDefaultPreference();
			System.out.println("Properties File Created.");
			this.readSystemPreference();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(in != null){
					System.out.println("Properties Loaded.");
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Update the file
	 */
	public void updatePreference(){
		OutputStream output = null;
		try {
			output = new FileOutputStream("lvs.properties");

			// set the properties value
			properties.setProperty("showTutorial", "" + this.showTutorial);
			properties.setProperty("test", "" + this.isTesting());
			properties.setProperty("print", "" + this.isPrinting());
			properties.setProperty("single_hand", "" + this.isSingleHand());
			properties.setProperty("left_style", "" + this.isLeftStyle());
			properties.setProperty("zclick", "" + this.isZClick());
			properties.setProperty("rotate_speed", "" + this.getRotateSpeed());
			properties.setProperty("isShowLeftThumb", "" + this.isShowLeftThumb());
			properties.setProperty("isShowLeftIndex" , "" + this.isShowLeftIndex());
			properties.setProperty("isShowLeftMiddle" , "" + this.isShowLeftMiddle());
			properties.setProperty("isShowLeftRing" , "" + this.isShowLeftRing());
			properties.setProperty("isShowLeftPinky" , "" + this.isShowLeftPinky());
			properties.setProperty("isShowRightThumb" , "" + this.isShowRightThumb());
			properties.setProperty("isShowRightIndex" , "" + this.isShowRightIndex());
			properties.setProperty("isShowRightMiddle" , "" + this.isShowRightMiddle());
			properties.setProperty("isShowRightRing" , "" + this.isShowRightRing());
			properties.setProperty("isShowRightPinky" , "" + this.isShowRightPinky());
			properties.setProperty("isShowLeftPlam" , "" + this.isShowLeftPalm());
			properties.setProperty("isShowRightPlam" , "" + this.isShowRightPalm());
			properties.setProperty("lastViewPath", this.getLastViewPath());
			properties.setProperty("fingerColor", colorToString(this.getFinger_color()));
			properties.setProperty("plamColor", colorToString(this.getPalm_color()));
			//properties.setProperty("", "");
			
			// save properties to project root folder
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	/**
	 * Function call to write default preference
	 */
	public void writeDefaultPreference(){
		OutputStream output = null;
		try {
			output = new FileOutputStream("lvs.properties");

			// set the properties value
			properties.setProperty("showTutorial", "true");
			properties.setProperty("test", "false");
			properties.setProperty("print", "false");
			properties.setProperty("single_hand", "true");
			properties.setProperty("left_style", "false");
			properties.setProperty("zclick", "true");
			properties.setProperty("rotate_speed", "3");
			properties.setProperty("scalar_bar_widget", "true");
			properties.setProperty("box_widget", "false");
			properties.setProperty("spline_widget", "false");
			properties.setProperty("distance_widget", "false");
			properties.setProperty("angle_widget", "false");
			properties.setProperty("bidimensional_widget", "false");
			properties.setProperty("isShowLeftThumb", "false");
			properties.setProperty("isShowLeftIndex" , "false");
			properties.setProperty("isShowLeftMiddle" , "false");
			properties.setProperty("isShowLeftRing" , "false");
			properties.setProperty("isShowLeftPinky" , "false");
			properties.setProperty("isShowRightThumb" , "false");
			properties.setProperty("isShowRightIndex" , "false");
			properties.setProperty("isShowRightMiddle" , "false");
			properties.setProperty("isShowRightRing" , "false");
			properties.setProperty("isShowRightPinky" , "false");
			properties.setProperty("isShowLeftPlam" , "true");
			properties.setProperty("isShowRightPlam" , "true");
			properties.setProperty("lastViewPath", "user.home");
			properties.setProperty("fingerColor", "0,255,0");
			properties.setProperty("plamColor", "0,0,255");
			//properties.setProperty("", "");
			
			// save properties to project root folder
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	/**
	 * Convert String from preference to Color
	 * @param s String
	 * @return Color Color
	 */
	public Color stringToColor(String s){
		Color color = null;
		String[] tmp = s.split("\\,");
		color = new Color(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
		
		return color;
	}
	
	/**
	 * Convert Color to String for storing in preference
	 * @param color Color
	 * @return s String
	 */
	public String colorToString(Color color){
		String s = null;
		s = color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		return s;
	}
	
	/**
	 * Get colorMap user chosen, if currentColorMap = -1 it state for default colorMap(Red->Blue)
	 * @return LVSColorMap
	 */
	public LVSColorMap getChosenColorMap(){
		if (currentColorMap > -1)
			return colorMaps.get(currentColorMap);
		else
			return null;
	}
	
	// Attributes Set and Get
	/**
	 * Setting the screen size base on the primary screen detected by the controller
	 *
	 */
	public void setScreen(){
		if (controller != null){
			ScreenList screenList = controller.locatedScreens();
			screen = screenList.get(0);
			// Mapping Coord
			screenWidth = screen.widthPixels();
			screenHeight = screen.heightPixels();
		}
	}
	
	public void setTesting(boolean isTesting){
		this.isTesting = isTesting;
	}
	
	public boolean isTesting(){
		return this.isTesting;
	}
	
	public void setSingleHand(boolean isSingleHand){
		this.isSingleHand = isSingleHand;
	}
	
	public boolean isSingleHand(){
		return this.isSingleHand;
	}
	
	public void setLeftStyle(boolean isLeftStyle){
		this.isLeftStyle = isLeftStyle;
	}
	
	public boolean isLeftStyle(){
		return this.isLeftStyle;
	}

	public void setPrinting(boolean isPrinting){
		this.isPrinting = isPrinting;
	}
	
	public boolean isPrinting(){
		return this.isPrinting;
	}
	
	public void setZClick(boolean isZClick){
		this.isZClick = isZClick;
	}
	
	public boolean isZClick(){
		return this.isZClick;
	}
	
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
	public int getScreenHeight(){
		return this.screenHeight;
	}
	
	public void setRotateSpeed(int rotate_speed) {
		this.rotate_speed = rotate_speed;
	}

	public int getRotateSpeed(){
		return this.rotate_speed;
	}
	
	public boolean isShowLeftThumb() {
		return isShowLeftThumb;
	}

	public void setShowLeftThumb(boolean isShowLeftThumb) {
		this.isShowLeftThumb = isShowLeftThumb;
	}

	public boolean isShowLeftIndex() {
		return isShowLeftIndex;
	}

	public void setShowLeftIndex(boolean isShowLeftIndex) {
		this.isShowLeftIndex = isShowLeftIndex;
	}

	public boolean isShowLeftMiddle() {
		return isShowLeftMiddle;
	}

	public void setShowLeftMiddle(boolean isShowLeftMiddle) {
		this.isShowLeftMiddle = isShowLeftMiddle;
	}

	public boolean isShowLeftRing() {
		return isShowLeftRing;
	}

	public void setShowLeftRing(boolean isShowLeftRing) {
		this.isShowLeftRing = isShowLeftRing;
	}

	public boolean isShowLeftPinky() {
		return isShowLeftPinky;
	}

	public void setShowLeftPinky(boolean isShowLeftPinky) {
		this.isShowLeftPinky = isShowLeftPinky;
	}

	public boolean isShowTutorial() {
		return showTutorial;
	}

	public void setShowTutorial(boolean showTutorial) {
		this.showTutorial = showTutorial;
	}

	public boolean isShowRightThumb() {
		return isShowRightThumb;
	}

	public void setShowRightThumb(boolean isShowRightThumb) {
		this.isShowRightThumb = isShowRightThumb;
	}

	public boolean isShowRightIndex() {
		return isShowRightIndex;
	}

	public void setShowRightIndex(boolean isShowRightIndex) {
		this.isShowRightIndex = isShowRightIndex;
	}

	public boolean isShowRightMiddle() {
		return isShowRightMiddle;
	}

	public void setShowRightMiddle(boolean isShowRightMiddle) {
		this.isShowRightMiddle = isShowRightMiddle;
	}

	public boolean isShowRightRing() {
		return isShowRightRing;
	}

	public void setShowRightRing(boolean isShowRightRing) {
		this.isShowRightRing = isShowRightRing;
	}

	public boolean isShowRightPinky() {
		return isShowRightPinky;
	}

	public void setShowRightPinky(boolean isShowRightPinky) {
		this.isShowRightPinky = isShowRightPinky;
	}

	public boolean isShowLeftPalm() {
		return isShowLeftPlam;
	}

	public void setShowLeftPalm(boolean isShowLeftPlam) {
		this.isShowLeftPlam = isShowLeftPlam;
	}

	public boolean isShowRightPalm() {
		return isShowRightPlam;
	}

	public void setShowRightPalm(boolean isShowRightPlam) {
		this.isShowRightPlam = isShowRightPlam;
	}

	public String getLastViewPath() {
		return lastViewPath;
	}

	public void setLastViewPath(String lastViewPath) {
		this.lastViewPath = lastViewPath;
	}

	public List<LVSColorMap> getColorMaps() {
		return colorMaps;
	}

	public void setColorMaps(List<LVSColorMap> colorMaps) {
		this.colorMaps = colorMaps;
	}

	public int getCurrentColorMap() {
		return currentColorMap;
	}

	public void setCurrentColorMap(int currentColorMap) {
		this.currentColorMap = currentColorMap;
	}

	public int getCurrentFilter() {
		return currentFilter;
	}

	public void setCurrentFilter(int currentFilter) {
		this.currentFilter = currentFilter;
	}

	public Color getFinger_color() {
		return finger_color;
	}

	public void setFinger_color(Color finger_color) {
		this.finger_color = finger_color;
	}

	public Color getPalm_color() {
		return plam_color;
	}

	public void setPlam_color(Color plam_color) {
		this.plam_color = plam_color;
	}
}

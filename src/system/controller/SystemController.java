package system.controller;

import system.bugreport.ExceptionCatcher;
import system.file.LVSFile;
import system.file.LVSFileHandler;
import system.file.ObjectFileHandler;
import system.general.LVSMessageFactory;
import system.general.SystemPreference;
import system.res.SystemTextureManager;
import ui.awt.res.SwingTextureManager;
import vtk.vtkPanel;

import com.leapmotion.leap.Controller;

/** ************************************************************
 * This class is the controller which controls and holds all the 
 * other controller in the system. It act as the transport center
 * of all the other controllers and holding the system status as 
 * well. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class SystemController {
	public static final int STATUS_SETTING = 0;
	public static final int STATUS_ONGOING = 1;
	public static final int STATUS_END = 2;	
	private static int SYSTEM_STATUS;
	
	private Controller controller;
	private GestureController gesture_c;
	private LeapMotionFrameController move_c;
	private CursorController cursor_c;
	private VTKController vtk_c;
	private VTKCameraController camera_c;
	private LeapMotionListener listener;
	private SystemPreference systemPreference;
	private SwingController swing_c;
	private OverLayerController over_c;

	private LVSMessageFactory messageFactory;
	private LVSFileHandler fileHandler;
	private ObjectFileHandler objectHandler;
	
	private LVSFile currentFile;
	
	/**
	 * Constructor
	 * 
	 */
	public SystemController() {
		SystemTextureManager.loadTexture();
		SwingTextureManager.loadTexture();
		
		// Create others
		controller = new Controller();
		systemPreference = new SystemPreference(this,controller);
		listener = new LeapMotionListener(this);
		System.out.println("LVS Setting Up...");
		System.out.println("Native Path: " + System.getProperty("java.library.path"));
		SYSTEM_STATUS = STATUS_SETTING;
		
		// Create controllers
		cursor_c = new CursorController(this, systemPreference);
		move_c = new LeapMotionFrameController(this, systemPreference);
		gesture_c = new GestureController(controller, this, systemPreference);
		vtk_c = new VTKController(this, systemPreference);
		camera_c = new VTKCameraController(this, systemPreference, vtk_c);
		over_c = new OverLayerController(this, systemPreference);
		swing_c = new SwingController(this, systemPreference);
		
		messageFactory = new LVSMessageFactory(this);
		fileHandler = new LVSFileHandler(this, systemPreference, swing_c.getFrame());
		objectHandler = new ObjectFileHandler(this, systemPreference, swing_c.getFrame());
	}
	
	/**
	 * Function called to run the system
	 * Launching the both vtk and Leap Motion
	 * 
	 */
	public void run(){
		systemPreference.setScreen();
		swing_c.setupFrame();
		swing_c.setupNormalMode();
		//swing_c.setupPresentationMode();
		vtkPanel panel = vtk_c.runVTK();
		swing_c.addVTKPanel(panel);
		over_c.setup();
		SYSTEM_STATUS = STATUS_ONGOING;
		System.out.println("LVS Launched");
		if (this.getSystemPreference().isShowTutorial())
			swing_c.openTutorialDialog();
		checkLeapMotionConnection();
		while (SYSTEM_STATUS == STATUS_ONGOING) {
			
		}
		if (SYSTEM_STATUS == STATUS_END){
			this.onExit();
		}
	}
	
	/**
	 * Check the connection status of leap motion controller, recursion if not connected
	 */
	public void checkLeapMotionConnection(){
		if (controller.isConnected()){
			controller.addListener(listener);
		} else {
			//System.out.println("Leap Motion Not Connected");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				ExceptionCatcher.logException(e);
			}
			this.checkLeapMotionConnection();
		}
	}
	
	/**
	 * Function called to update the system status
	 * @param message String of system status
	 */
	public void updateSystemStatus(String message){
		this.getSwingController().updateStatusBar(message);
	}
	
	/**
	 * Function called when end of the system
	 */
	public void onExit(){
		// Remove the sample listener when done
		controller.removeListener(listener);
		this.checkLeapMotionConnection();
	}
	
	/*
	 * Function called to terminate the system
	 */
	public void closeSystem(){
   	 	this.swing_c.updateSystemPreference();
   	 	System.out.println("LVS exited.");
        System.exit(0);
	}
	
	/**
	 * Function call to change the actor currently displaying in VTK Panel
	 * @param option Option is either 1 or -1
	 */
	public void changeActor(int option){
		if (this.getCurrentFile() != null){
			switch(option){
			case 1:
				// Next Actor
				this.getCurrentFile().nextActor();
				break;
			case -1:
				// Previous Actor
				this.getCurrentFile().previousActor();
				break;
			}
			this.getSwingController().updateToolBar();
			this.getSwingController().updateMenuBar();
			this.getSwingController().updateFileTree();
			this.getSwingController().updateVTK();
		}
	}
	
	/**
	 * Function call to change opacity
	 * @param opacity Opacity of current item
	 */
	public void changeOpacity(double opacity){
		if (getCurrentFile() != null){
			int id = getCurrentFile().getShowingActor();
			if (id != -1){
				getCurrentFile().getItemById(id).setOpacity(opacity);
				getSwingController().updateVTK();
			}
		}
	}
	
	/**
	 * Function call to change the current colorMap
	 * @param option Index of colorMap List in systemPreference
	 */
	public void changeColorMap(int option){
		systemPreference.setCurrentColorMap(option);
		this.getSwingController().updateToolBar();
		this.getSwingController().updateVTK();
	}
	
	/**
	 * Function call to change representation method
	 * @param option Representation of current item
	 */
	public void changeRepresentation(int option){
		if (this.getCurrentFile() != null){
			int id = getCurrentFile().getShowingActor();
			if (id != -1){
				getCurrentFile().getItemById(id).setRepresentation(option);
				getSwingController().updateVTK();
			}
		}
	}
	
	// Attributes Set and Get
	public void setSystemStatus(int status){
		this.SYSTEM_STATUS = status;
	}
	
	public int getSystemStatus(){
		return this.SYSTEM_STATUS;
	}
	
	public Controller getLeapMotionController(){
		return this.controller;
	}
	
	public GestureController getGestureController() {
		return this.gesture_c;
	}

	public LeapMotionFrameController getLeapMotionFrameController() {
		return this.move_c;
	}

	public CursorController getCursorController() {
		return this.cursor_c;
	}
	
	public VTKController getVTKController() {
		return this.vtk_c;
	}
	
	public VTKCameraController getVTKCameraController(){
		return this.camera_c;
	}

	public SystemPreference getSystemPreference() {
		return this.systemPreference;
	}
	
	public LeapMotionListener getLeapMotionListner(){
		return this.listener;
	}
	
	public SwingController getSwingController(){
		return swing_c;
	}
	
	public OverLayerController getOverLayerController() {
		return over_c;
	}

	public LVSFile getCurrentFile() {
		return currentFile;
	}

	/**
	 * Set current file and update GUI
	 * @param currentFile
	 */
	public void setCurrentFile(LVSFile currentFile) {
		this.currentFile = currentFile;
		this.getSwingController().changeFrameName(currentFile.getFileName());
		this.getSwingController().updateMenuBar();
		this.getSwingController().updateFileTree();
		this.getSwingController().updateToolBar();
		if (currentFile.getFileList().size() > 0)
			this.getSwingController().updateVTK(); 
	}

	public LVSFileHandler getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(LVSFileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	public ObjectFileHandler getObjectHandler() {
		return objectHandler;
	}

	public void setObjectHandler(ObjectFileHandler objectHandler) {
		this.objectHandler = objectHandler;
	}

	public LVSMessageFactory getMessageFactory() {
		return messageFactory;
	}

	public void setMessageFactory(LVSMessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}
}

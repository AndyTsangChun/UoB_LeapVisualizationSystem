package system.model;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import system.controller.SwingController;

/** ************************************************************
 * This class is the model class of all Swing JPanel in LVS
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public abstract class LVSPanel extends JPanel{
	public static final String COMMAND_ABOUT = "about";
	public static final String COMMAND_APPLY = "apply";
	public static final String COMMAND_BACK = "back";
	public static final String COMMAND_CANCEL = "cancel";
	public static final String COMMAND_COLORMAP = "color_map";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_GESTURE = "gesture";
	public static final String COMMAND_NEW = "new";
	public static final String COMMAND_OPEN = "open";
	public static final String COMMAND_OPACITY = "opacity";
	public static final String COMMAND_PRESENTATION = "presentation";
	public static final String COMMAND_QUIT = "quit";
	public static final String COMMAND_REPRESENTATION = "representation";
	public static final String COMMAND_SAVE = "save";
	public static final String COMMAND_SAVE_AS = "save_as";
	public static final String COMMAND_TRACKING = "tracking";
	public static final String COMMAND_TUTORIAL = "tutorial";
	public static final String COMMAND_PRE_ACTOR = "pre_actor";
	public static final String COMMAND_NEXT_ACTOR = "next_actor";
	public static final String COMMAND_IMPORT_OBJ = "import_obj";
	public static final String COMMAND_EDIT_OBJ = "edit_obj";
	public static final String COMMAND_EDIT_TRACKING = "edit_tracking";
	public static final String COMMAND_EDIT_PREFERENCE = "edit_preference";
	private SwingController swingController;
	private JFrame mainFrame;

	/**
	 * Constructor
	 * @param swingController The swing controller manage all the swing component
	 * @param mainFrame The LVS main Frame
	 */
	public LVSPanel(SwingController swingController, JFrame mainFrame) {
		this.swingController = swingController;
		this.setMainFrame(mainFrame);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	/**
	 * Abstract Method use to initialize panel
	 */
	public abstract void initPanel();
	
	public SwingController getSwingController() {
		return swingController;
	}

	// Attributes Set and Get
	public void setSwingController(SwingController swingController) {
		this.swingController = swingController;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}

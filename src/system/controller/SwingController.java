package system.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

import javax.swing.DefaultDesktopManager;
import javax.swing.DesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import system.file.LVSFile;
import system.general.SystemPreference;
import system.model.LVSObject;
import system.model.LVSPanel;
import ui.awt.dialog.about.LVSAboutDialog;
import ui.awt.dialog.edit.file.LVSFileEditDialog;
import ui.awt.dialog.edit.file.LVSFileItemEditDialog;
import ui.awt.dialog.edit.preference.LVSPreferenceEditDialog;
import ui.awt.dialog.edit.tracking.LVSTrackingEditDialog;
import ui.awt.dialog.edit.tracking.TrackingModel;
import ui.awt.dialog.tutorial.LVSTutorialDialog;
import ui.awt.panel.LVSFileTreePanel;
import ui.awt.panel.LVSMenuBar;
import ui.awt.panel.LVSStatusBar;
import ui.awt.panel.LVSToolBar;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;
import vtk.vtkPanel;

import com.apple.eawt.Application;

/** ************************************************************
 * This class is the controller of the GUI by using Swing.
 * The controller is used to manage and coordinate between
 * all swing component. It also holds the UI representation 
 * mode status. Everything that pass from other logic controllers
 * to the GUI must via this controller.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class SwingController extends LVSObject {
	public static final int FILE_EDIT_NORMAL = -1;
	public static final char NORMAL_MODE = 'n';
	public static final char PRESENTATION_MODE = 'p';
	private JFrame lvsMainFrame;
	private vtkPanel vtkPanel = null;
	private JSplitPane mainContainer;
	private LVSMenuBar menuBar;
	private LVSMenuBar menuBar2;
	private LVSToolBar toolBar;
	private JPanel vtkContainer;
	private LVSFileTreePanel fileTreePanel;
	private LVSStatusBar statusBar;
	private JDialog dialog;
	private LVSPanel dialogContentPanel;
	private TrackingModel trackingModel;
	private JDesktopPane desktopPane;
	private boolean isInternalFrame = false;
	private boolean isPresentationMode = false;

	/**
	 * Constructor
	 * 
	 * @param	systemController	controller of system
	 * @param	systemPreference	User's Preference of the system
	 */
	public SwingController(SystemController systemController, SystemPreference systemPreference) {
		super(systemController, systemPreference);
		lvsMainFrame = new JFrame("LVS");
		vtkContainer = new JPanel();
		fileTreePanel = new LVSFileTreePanel(this, lvsMainFrame);
		menuBar = new LVSMenuBar(this, lvsMainFrame);
		menuBar2 = new LVSMenuBar(this, super.getSystemController().getOverLayerController().getFrame());
		toolBar = new LVSToolBar(this, lvsMainFrame);
		statusBar = new LVSStatusBar(this, lvsMainFrame);
		trackingModel = new TrackingModel(systemPreference);
		// dialog
		dialog = new JDialog();
		dialog.addWindowListener(new WindowAdapter(){
			  public void windowClosed(WindowEvent e){
			    updateVTK();
			  }
			});
	}
	
	/**
	 * Update System Preference
	 */
	public void updateSystemPreference(){
		super.getSystemPreference().updatePreference();
	}
	
	/**
	 * Step up the main frame and all components
	 */
	public void setupFrame(){
		// Setting up Main Frame
		lvsMainFrame.setSize((int)(super.getSystemPreference().getScreenWidth() * LVSSwingInfo.SCREEN_RATIO), (int)(super.getSystemPreference().getScreenHeight() * LVSSwingInfo.SCREEN_RATIO));
		lvsMainFrame.setLocation(LVSSwingInfo.INIT_LOCATION_X, LVSSwingInfo.INIT_LOCATION_Y);
		lvsMainFrame.setLayout(new BorderLayout());
		lvsMainFrame.setExtendedState(lvsMainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		lvsMainFrame.setIconImage(SwingTextureManager.LVS_LOGO_IMAGE);
		// Check if is OSX enable full screen mode
		if (isMacOSX()) {
			SystemPreference.MENU_BAR_OFFSET = 22;
            enableFullScreenMode(lvsMainFrame);
    		Application application = Application.getApplication();
    		application.setDockIconImage(SwingTextureManager.LVS_LOGO_IMAGE);
        }
		//This part should be modularize if further functions is needed
		lvsMainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	        	 updateSystemPreference();
	             System.exit(0);
	         }
	    });    
		// Setup mode common content
		menuBar.initPanel();
		menuBar2.initPanel();		
		fileTreePanel.initPanel();
		toolBar.initPanel();
		statusBar.initPanel();
	}
	
	/**
	 * Set visibility of overLayer  
	 * @param isVisible
	 */
	public void setOverLayerVisibility(boolean isVisible){
		super.getSystemController().getOverLayerController().setOverLayerVisibilty(isVisible);
	}
	
	/**
	 * Function call to setup the normal mode content
	 * Internal Frame is currently disabled 
	 */
	public void setupNormalMode(){
		vtkContainer.setLayout(new BorderLayout());
		vtkContainer.setPreferredSize(new Dimension(getFrame().getWidth() - LVSSwingInfo.FILE_TREE_W, getFrame().getHeight() - LVSSwingInfo.STATUS_BAR_H - LVSSwingInfo.TOOL_BAR_H));
		vtkContainer.setMinimumSize(new Dimension(super.getSystemPreference().getScreenWidth() / 2, super.getSystemPreference().getScreenHeight()));		

		if (isInternalFrame){
			// Setting up Desktop Panel
			desktopPane = new JDesktopPane();
			desktopPane.setBackground(Color.WHITE);
			
			DesktopManager manager = new DefaultDesktopManager() {
		        /** This moves the <code>JComponent</code> and repaints the damaged areas. */
		        @Override
		        public void setBoundsForFrame(JComponent f, int newX, int newY, int newWidth, int newHeight) {
		            boolean didResize = (f.getWidth() != newWidth || f.getHeight() != newHeight);
		            if (!inBounds((JInternalFrame) f, newX, newY, newWidth, newHeight)) return;
		            f.setBounds(newX, newY, newWidth, newHeight);
		            if(didResize) {
		                f.validate();
		            } 
		        }

		        protected boolean inBounds(JInternalFrame f, int newX, int newY, int newWidth, int newHeight) {
		            if (newX < 0 || newY < 0) return false;
		            if (newX + newWidth > f.getDesktopPane().getWidth()) return false;
		            if (newY + newHeight > f.getDesktopPane().getHeight()) return false;
		            return true;
		        }

		    };
		    desktopPane.setDesktopManager(manager);
			
			lvsMainFrame.add(desktopPane, BorderLayout.CENTER);
			
			JInternalFrame tIF = new JInternalFrame("File Explorer", true, true, true, true);
			tIF.setContentPane(fileTreePanel);
			tIF.setSize(200, lvsMainFrame.getHeight());
			tIF.setLocation(0, 0);
			tIF.setVisible(true);
			tIF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			desktopPane.add(tIF);
		}else{
			// Setting up Main Panel		
			mainContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,fileTreePanel,vtkContainer);
			mainContainer.setPreferredSize(new Dimension(this.getSystemPreference().getScreenWidth(), this.getSystemPreference().getScreenHeight()));
			
			lvsMainFrame.add(mainContainer, BorderLayout.CENTER);
		}
		if (vtkPanel != null){
			this.addVTKPanel(vtkPanel);
		}
		updateToolBar();
		lvsMainFrame.add(toolBar, BorderLayout.NORTH);
		lvsMainFrame.add(statusBar, BorderLayout.SOUTH);
		lvsMainFrame.setVisible(true);
		lvsMainFrame.validate();
		lvsMainFrame.repaint();
	}
	
	/**
	 * Function call to set up presentation mode content
	 */
	public void setupPresentationMode(){
		vtkContainer.setLayout(new BorderLayout());
		vtkContainer.setPreferredSize(new Dimension(getFrame().getWidth(), getFrame().getHeight()));
		if (vtkPanel != null){
			this.addVTKPanel(vtkPanel);
		}
		updateToolBar();
		lvsMainFrame.add(vtkContainer, BorderLayout.CENTER);
		lvsMainFrame.setVisible(true);
		lvsMainFrame.validate();
		lvsMainFrame.repaint();
	}

	/**
	 * Function call to add vtk panel obtain from VTK Controller into its container
	 * @param panel VTK Panel
	 */
	public void addVTKPanel(vtkPanel panel){
		vtkPanel = panel;
		if (isInternalFrame){
			JInternalFrame vIF = new JInternalFrame("VTK Panel", true, true, true, true);
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			p.add(panel, BorderLayout.CENTER);
			vIF.setContentPane(p);
			vIF.setSize(desktopPane.getWidth() - 200,desktopPane.getHeight());
			vIF.setLocation(200, 0);
			vIF.setVisible(true);
			vIF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			desktopPane.add(vIF);
		}else{
			this.vtkContainer.add(panel, BorderLayout.CENTER);	
		}
	}
	
	/**
	 * Setup the target dialog
	 * @param dialog Target Dialog
	 */
	public void setDialog(JDialog dialog){
		// set content panel
		dialogContentPanel.initPanel();
		dialog.add(dialogContentPanel);
		// set dialog
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setAlwaysOnTop(true);
		dialog.setModalityType (ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
		dialogContentPanel.revalidate();
		dialogContentPanel.repaint();
		dialog.validate();
		dialog.repaint();
	}
	
	/**
	 * Function call to open tutorial dialog
	 */
	public void openTutorialDialog(){
		if (dialogContentPanel != null)
			dialog.remove(dialogContentPanel);
		int w = this.getSystemPreference().getScreenWidth();
		int h = this.getSystemPreference().getScreenHeight();
		int panel_w = LVSSwingInfo.TUTORIAL_DIALOG_W;
		int panel_h = LVSSwingInfo.TUTORIAL_DIALOG_H;
		if (w < panel_w || h < panel_h){
			panel_w = (int)(w * LVSSwingInfo.TUTORIAL_DIALOG_W_RATIO);
			panel_h = h * 2 / 3 + LVSSwingInfo.TUTORIAL_BUTTON_H + LVSTutorialDialog.PANEL_OFFSET;
		}
		dialog.setResizable(false);
		dialog.setSize(new Dimension(panel_w, panel_h));
		dialog.setLocation((int)((w / 2) - (panel_w / 2)), (int)(h * LVSSwingInfo.EDIT_PANEL_Y_RATIO));
		dialogContentPanel = new LVSTutorialDialog(this, lvsMainFrame);
		setDialog(dialog);
	}
	
	/**
	 * Function call to open preference dialog
	 */
	public void openPreferenceDialog(){
		if (dialogContentPanel != null)
			dialog.remove(dialogContentPanel);
		int w = this.getSystemPreference().getScreenWidth();
		int h = this.getSystemPreference().getScreenHeight();
		int panel_w = (int)(w * LVSSwingInfo.EDIT_PREFERENCE_W_RATIO);
		int panel_h = (int)(h * LVSSwingInfo.EDIT_PREFERENCE_H_RATIO);
		dialog.setSize(new Dimension(panel_w, panel_h));
		dialog.setLocation((int)((w / 2) - (panel_w / 2)), (int)(h * LVSSwingInfo.EDIT_PANEL_Y_RATIO));
		dialogContentPanel = new LVSPreferenceEditDialog(this, lvsMainFrame);
		setDialog(dialog);
	}
	
	/**
	 * Function call to open about dialog
	 */
	public void openAboutDialog(){
		if (dialogContentPanel != null)
			dialog.remove(dialogContentPanel);
		int w = this.getSystemPreference().getScreenWidth();
		int h = this.getSystemPreference().getScreenHeight();
		int panel_w = LVSSwingInfo.ABOUT_PANEL_W;
		int panel_h = LVSSwingInfo.ABOUT_PANEL_H;
		dialog.setSize(new Dimension(panel_w, panel_h));
		dialog.setLocation((int)((w / 2) - (panel_w / 2)), (int)(h * LVSSwingInfo.EDIT_PANEL_Y_RATIO));
		dialogContentPanel = new LVSAboutDialog(this, lvsMainFrame);
		setDialog(dialog);
	}
	
	/**
	 * Function call to open tracking edit dialog
	 */
	public void openTrackingEditDialog(){
		if (dialogContentPanel != null)
			dialog.remove(dialogContentPanel);
		int w = this.getSystemPreference().getScreenWidth();
		int h = this.getSystemPreference().getScreenHeight();
		int panel_w = (int)(w * LVSSwingInfo.EDIT_TRACKING_DIALOG_W_RATIO);
		int panel_h = (int)(h * LVSSwingInfo.EDIT_TRACKING_DIALOG_H_RATIO);
		dialog.setSize(new Dimension(panel_w, panel_h));
		dialogContentPanel = new LVSTrackingEditDialog(this, lvsMainFrame, trackingModel);
		dialog.setLocation((int)((w / 2) - (panel_w / 2)), (int)(h * LVSSwingInfo.EDIT_PANEL_Y_RATIO));
		setDialog(dialog);
	}
	
	/**
	 * Function call to open lvs file edit dialog
	 * 
	 * @param id IF > 0 equals to id of file item, else if -1 equals to file edit panel 
	 */
	public void openFileEditDialog(int id, String option){
		//this.closeEditPanel();
		if (dialogContentPanel != null)
			dialog.remove(dialogContentPanel);
		int w = this.getSystemPreference().getScreenWidth();
		int h = this.getSystemPreference().getScreenHeight();
		int panel_w = 0;
		int panel_h = (int)(h * LVSSwingInfo.EDIT_FILE_DIALOG_H_RATIO);
		if(id == FILE_EDIT_NORMAL){
			panel_w = (int)(w * LVSSwingInfo.EDIT_FILE_DIALOG_W_RATIO);
			dialog.setSize(new Dimension(panel_w, panel_h));
			dialogContentPanel = new LVSFileEditDialog(this, lvsMainFrame, this.getSystemController().getCurrentFile());
		}else if (id >= 0){
			panel_w = (int)(w * LVSSwingInfo.EDIT_ITEM_PANEL_W_RATIO);
			dialog.setSize(new Dimension(panel_w, panel_h));
			dialogContentPanel = new LVSFileItemEditDialog(this,lvsMainFrame, this.getSystemController().getCurrentFile(), id, option);
		}
		dialog.setLocation((int)((w / 2) - (panel_w / 2)), (int)(h * LVSSwingInfo.EDIT_PANEL_Y_RATIO));
		setDialog(dialog);
	}

	public void changeEditDialogOnTop(boolean b){
		dialog.setVisible(b);
		dialog.setAlwaysOnTop(b);
	}
	
	/**
	 * Function call to close dialog
	 */
	public void closeEditPanel(){
		if (dialog != null){
			dialog.dispose();
		}
	}
	
	/**
	 * Function call to update menuBar to enable or disable some button  
	 * @param file LVSFile system currently using
	 */
	public void fileLoaded(LVSFile file){
		if (file != null){
			this.menuBar.updateMenuBar();
			this.menuBar2.updateMenuBar();
		}
	}
	
	/**
	 * Function call to switch mode, changing the UI for specific mode
	 * @param b Boolean of presentation mode
	 */
	public void switchPresentationMode(boolean b){
		this.setPresentationMode(b);
		if (isPresentationMode()){
			lvsMainFrame.remove(toolBar);
			lvsMainFrame.remove(statusBar);
			lvsMainFrame.remove(mainContainer);
			setupPresentationMode();
		}else{
			setupNormalMode();
			lvsMainFrame.remove(vtkContainer);
		}
	}
	
	/**
	 * Function call to enable full screen
	 * For OSX only
	 * @param window
	 */
	public void enableFullScreenMode(Window window) {
        String className = "com.apple.eawt.FullScreenUtilities";
        String methodName = "setWindowCanFullScreen";
 
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, new Class<?>[] {
                    Window.class, boolean.class });
            method.invoke(null, window, true);
        } catch (Throwable t) {
            String message = "Full screen mode is not supported";
            super.getSystemController().updateSystemStatus(message);
            super.getSystemController().getMessageFactory().showMessageDialog(2, message, null);
            t.printStackTrace();
        }
    }
	
	/**
	 * Check is OS currently running on is Mac OSX
	 * @return boolean
	 */
	private boolean isMacOSX() {
        return System.getProperty("os.name").indexOf("Mac OS X") >= 0;
    }
	
	/**
	 * Change Leap Motion connection status UI representation
	 * @param isConnected LeapMotion Connection Status
	 */
	public void changeLeapMotionStatus(boolean isConnected){
		if (isConnected)
			this.getToolBar().getLmStatus().setIcon(new ImageIcon(SwingTextureManager.ON_ICON_IMAGE));
		else
			this.getToolBar().getLmStatus().setIcon(new ImageIcon(SwingTextureManager.OFF_ICON_IMAGE));
	}
	
	/**
	 * Change the Title of the Main Frame
	 * @param name Name of the LVSFile currently using
	 */
	public void changeFrameName(String name){
		if(!name.equals("") && name != null)
			this.lvsMainFrame.setTitle("LVS - " + name);
		else
			this.lvsMainFrame.setTitle("LVS");
	}
	
	/**
	 * Update the status to edit when ever there is a changes
	 * @param status
	 */
	public void setFileStatus(int status){
		if (this.getSystemController().getCurrentFile() != null)
			this.getSystemController().getCurrentFile().setStatus(status);
	}
	
	/**
	 * Function call to update File Tree UI
	 * Separate Update UI Function is use to prevent unnecessary UI repaint
	 */
	public void updateFileTree(){
		this.fileTreePanel.updateTree();
		this.setFileStatus(LVSFile.STATUS_EDITED);
	}
	
	/**
	 * Function call to update Menu Bar UI
	 * Separate Update UI Function is use to prevent unnecessary UI repaint
	 */
	public void updateMenuBar(){
		this.menuBar.updateMenuBar();
		this.menuBar2.updateMenuBar();
		this.setFileStatus(LVSFile.STATUS_EDITED);
	}
	
	/**
	 * Function call to update VTK UI
	 * Separate Update UI Function is use to prevent unnecessary UI repaint
	 */
	public void updateVTK(){
		this.getSystemController().getVTKController().updateVTKPanel();
		this.setFileStatus(LVSFile.STATUS_EDITED);
	}
	
	/**
	 * Function call to update Tool Bar UI
	 * Separate Update UI Function is use to prevent unnecessary UI repaint
	 */
	public void updateToolBar(){
		this.getToolBar().updateToolBar();
		this.setFileStatus(LVSFile.STATUS_EDITED);
	}
	
	/**
	 * Function call to update Status Bar UI
	 * Separate Update UI Function is use to prevent unnecessary UI repaint
	 */
	public void updateStatusBar(String message){
		this.getStatusBar().updateStatus(message);
	}

	// Attributes Set and Get
	
	public Dimension getVTKContainerSize(){
		return new Dimension(vtkContainer.getWidth(), vtkContainer.getHeight());
	}
	
	public JFrame getFrame(){
		return this.lvsMainFrame;
	}

	public LVSMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(LVSMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public LVSToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(LVSToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public LVSFileTreePanel getFileTreePanel() {
		return fileTreePanel;
	}

	public void setFileTreePanel(LVSFileTreePanel fileTreePanel) {
		this.fileTreePanel = fileTreePanel;
	}

	public LVSStatusBar getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(LVSStatusBar statusBar) {
		this.statusBar = statusBar;
	}

	public boolean isPresentationMode() {
		return isPresentationMode;
	}

	public void setPresentationMode(boolean isPresentationMode) {
		this.isPresentationMode = isPresentationMode;
	}

	public TrackingModel getTrackingModel() {
		return trackingModel;
	}

	public void setTrackingModel(TrackingModel trackingModel) {
		this.trackingModel = trackingModel;
	}
}

package system.model;

import system.controller.VTKController;
import system.controller.WidgetController;
import system.file.FileItem;
import vtk.vtkRenderWindowInteractor;

/** ************************************************************
 * This is an abstract class for all vtkWidget in the system
 * Undefined method initWidget() and update() is used for initialize widget with lookuptables 
 * or other vtkObject and update base on some widget require the reference of actor respectively
 * 
 * @author	Andy Tsang 
 * @version	1.1
 * ***********************************************************/
public abstract class VTKWidget{
	private WidgetController widgetController;
	private VTKController vtkController;
	private vtkRenderWindowInteractor interactor;
	private FileItem item;
	private String name = "";
	private boolean ENABLED;
	
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param interactor the renderWindowInteractor currently using
	 */
	public VTKWidget(String name, WidgetController widgetController, vtkRenderWindowInteractor interactor){
		this.name = name;
		this.interactor = interactor;
		this.widgetController = widgetController;
		this.widgetController.addWidget(this);
	}
	
	/**
	 * Constructor 
	 * 
	 * @param vtkController controller of all vtk component
	 * @param interactor the renderWindowInteractor currently using
	 */
	public VTKWidget(String name, VTKController vtkController, vtkRenderWindowInteractor interactor, FileItem item){
		this.name = name;
		this.interactor = interactor;
		this.setVTKController(vtkController);
		this.setFileItem(item);
	}
	
	/**
	 * Abstract method for different widget to initialize with lookuptable or other vtkObject
	 */
	public abstract void initWidget();
	
	/**
	 * Abstract method for different widget to update, for instance, actor reference is required
	 */
	public abstract void update();
	
	/**
	 * Function printing the state of the widget
	 */
	public void printState(){
		String message = "";
		if (ENABLED){
			message = this.getName() + " Turned On";
		}else{
			message = this.getName() + " Turned Off";
		}
		vtkController.getSystemController().updateSystemStatus(message);
	}
	
	// Attributes Set and Get
	public WidgetController getWidgetController(){
		return this.widgetController;
	}

	public vtkRenderWindowInteractor getInteractor() {
		return interactor;
	}

	public void setInteractor(vtkRenderWindowInteractor interactor) {
		this.interactor = interactor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setEabled(boolean b){
		this.ENABLED = b;
	}
	
	public boolean isEabled(){
		return this.ENABLED;
	}

	public FileItem getFileItem() {
		return item;
	}

	public void setFileItem(FileItem item) {
		this.item = item;
	}

	public VTKController getVTKController() {
		return vtkController;
	}

	public void setVTKController(VTKController vtkController) {
		this.vtkController = vtkController;
	}
}

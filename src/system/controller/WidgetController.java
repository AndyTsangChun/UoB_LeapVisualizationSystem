package system.controller;

import java.util.ArrayList;
import java.util.List;

import system.general.SystemPreference;
import system.model.LVSObject;
import system.model.VTKWidget;
import system.vtk.AngleWidget;
import system.vtk.BoxWidget;
import system.vtk.DistanceWidget;
import system.vtk.SplineWidget;
import vtk.vtkLookupTable;
import vtk.vtkRenderWindowInteractor;

/** ************************************************************
 * This class is the controller which control all the default
 * widget inculding  add, delete and enable.
 * 
 * The class suppose to use vtkWidgetSet however the library crush 
 * base on casting issue after update other vtk component like 
 * readers. Therefore, list was use for now since there is not 
 * observable advantage from using vtkWidgetSet for just to store 
 * all the widget.
 * 
 * This controller can be further implement to modularize different 
 * VTK Widget.
 * 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class WidgetController extends LVSObject{
	private VTKController vtkController;
	//private vtkWidgetSet widgetSet;
	private List<VTKWidget> widgetSet;
	private vtkLookupTable lookupTable;
	/**
	 * Constructor
	 * @param systemController	controller of system    
	 * @param systemPreference	User's Preference of the system
	 */
	public WidgetController(SystemController systemController, SystemPreference systemPreference, VTKController vtkController) {
		super(systemController, systemPreference);
		this.vtkController = vtkController;
		this.lookupTable = new vtkLookupTable();
		this.lookupTable.Build();
		this.widgetSet = new ArrayList<VTKWidget>();
		this.createWidget();
	}
	
	/**
	 * Function call to create all widget object
	 */
	public void createWidget(){
		vtkRenderWindowInteractor interactor = this.getVTKController().getVTKRenderWindowInteractor();
		//ScalarBarWidget scalarBarWidget = new ScalarBarWidget(this, lookupTable, interactor);
		BoxWidget boxWidget = new BoxWidget(this, interactor);
		SplineWidget splineWidget = new SplineWidget(this, interactor);
		DistanceWidget distanceWidget = new DistanceWidget(this, interactor);
		AngleWidget angleWidget = new AngleWidget(this, interactor);
	}
	
	/**
	 * Function call to init all widget
	 */
	public void initWidgets(){
		for (int i = 0 ; i < widgetSet.size() ; i++){
			widgetSet.get(i).initWidget();
		}
	}
	
	public void updateWidget(){
		for (int i = 0 ; i < widgetSet.size() ; i++){
			widgetSet.get(i).update();
		}
	}
	
	/**
	 * Adding the widget 
	 * 
	 * @param widget vtk widget wish to be add
	 */
	public void addWidget(VTKWidget widget){
		widgetSet.add(widget);
	}

	/**
	 * Deleting the widget
	 * 
	 * @param id ID is same as the position of the widget
	 */
	public void deleteWidget(int id){
		widgetSet.remove(id);
	}
	
	// Attributes Set and Get
	/**
	 * Getting widget by id
	 * 
	 * @param id id of the widget wish to be return
	 * @return widget 
	 */
	public VTKWidget getWidget(int id){
		return widgetSet.get(id);
	}
	
	/**
	 * Setting widget enable as to choose display or not
	 * 
	 * @param id id of the widget wish to to change the display option
	 */
	public void setEnable(int id){
		VTKWidget widget = widgetSet.get(id);
		if (widget.isEabled())
			widget.setEabled(false);
		else
			widget.setEabled(true);
		widget.printState();
	}
	
	public List<VTKWidget> getWidgetSet(){
		return this.widgetSet;
	}
	
	public vtkLookupTable getLookupTable(){
		return this.lookupTable;
	}

	public VTKController getVTKController() {
		return vtkController;
	}
}

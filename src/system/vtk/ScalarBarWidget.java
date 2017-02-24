package system.vtk;

import system.controller.VTKController;
import system.controller.WidgetController;
import system.file.FileItem;
import system.model.VTKWidget;
import vtk.vtkColorTransferFunction;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkScalarBarActor;
import vtk.vtkScalarBarWidget;
import vtk.vtkScalarsToColors;

/**
 * This is the ScalarBar Widget class which provide a colored index
 * 
 * @author	Andy Tsang 
 * @version	1.1
 */
public class ScalarBarWidget extends VTKWidget{
	private vtkScalarsToColors colorScalar;
	private vtkScalarBarActor scalarBar;
	private vtkScalarBarWidget scalarWidget;
	
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param colorScalar Containing color scalar
	 * @param interactor the renderWindowInteractor currently using
	 */
	public ScalarBarWidget(WidgetController widgetController, vtkScalarsToColors colorScalar, vtkRenderWindowInteractor interactor) {
		super("ScalarBarWidget", widgetController, interactor);
		this.colorScalar = colorScalar;
	}
	
	/**
	 * Constructor 
	 * 
	 * @param vtkController controller of all vtk component
	 * @param colorScalar Containing color scalar
	 * @param interactor the renderWindowInteractor currently using
	 */
	public ScalarBarWidget(VTKController vtkController, vtkScalarsToColors colorScalar, vtkRenderWindowInteractor interactor, FileItem item) {
		super("ScalarBarWidget", vtkController, interactor, item);
		this.colorScalar = colorScalar;
	}

	@Override
	public void initWidget() {
		scalarBar = new vtkScalarBarActor();
		scalarBar.SetOrientationToHorizontal();
		scalarBar.SetLookupTable(this.getColorScalar());
		//
		scalarWidget = new vtkScalarBarWidget();
		scalarWidget.SetInteractor(super.getInteractor());
		scalarWidget.SetScalarBarActor(scalarBar);
		scalarWidget.SetSelectable(1);
		// Disable atm
		if(super.getFileItem().getScalarBar().isVisiable()){
			scalarWidget.On();
			super.setEabled(true);
			super.printState();
		}else{
			scalarWidget.Off();
			super.setEabled(false);
			super.printState();
		}
	}

	@Override
	public void update() {
		scalarWidget.On();
	}

	public vtkScalarsToColors getColorScalar() {
		return colorScalar;
	}

	public vtkScalarBarWidget getScalarWidget() {
		return scalarWidget;
	}

	public void setScalarWidget(vtkScalarBarWidget scalarWidget) {
		this.scalarWidget = scalarWidget;
	}
}

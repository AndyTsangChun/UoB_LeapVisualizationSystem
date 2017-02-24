package system.vtk;

import system.controller.WidgetController;
import system.model.VTKWidget;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkSplineWidget2;

/**
 * This is the Spline Widget class which is a widget provide a editable line with 5 points
 * 
 * @author	Andy Tsang 
 * @version	1.1
 */
public class SplineWidget extends VTKWidget{
	private vtkSplineWidget2 sp;
	
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param interactor the renderWindowInteractor currently using
	 */
	public SplineWidget(WidgetController widgetController, vtkRenderWindowInteractor interactor) {
		super("SplineWidget", widgetController, interactor);
	}

	@Override
	public void initWidget() {
		sp = new vtkSplineWidget2();
		sp.SetInteractor(super.getInteractor());
		// Disable atm
		if (false){
			sp.On();
			super.setEabled(true);
			super.printState();
		}
	}

	@Override
	public void update() {
		
	}

}

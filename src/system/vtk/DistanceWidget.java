package system.vtk;

import system.controller.WidgetController;
import system.model.VTKWidget;
import vtk.vtkDistanceWidget;
import vtk.vtkRenderWindowInteractor;

/**
 * This is the Distance Widget class which is a widget provides a ruler.
 * 
 * @author	Andy Tsang 
 * @version	1.1
 */
public class DistanceWidget extends VTKWidget {
	private vtkDistanceWidget distanceWidget;
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param interactor the renderWindowInteractor currently using
	 */
	public DistanceWidget(WidgetController widgetController, vtkRenderWindowInteractor interactor) {
		super("DistanceWidget", widgetController, interactor);
	}

	@Override
	public void initWidget() {
		distanceWidget = new vtkDistanceWidget();
		distanceWidget.SetInteractor(super.getInteractor());
		distanceWidget.CreateDefaultRepresentation();
		// Disable atm
		if (false){
			distanceWidget.On();
			super.setEabled(true);
			super.printState();
		}
	}

	@Override
	public void update() {
	}

}

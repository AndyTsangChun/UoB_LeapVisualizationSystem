package system.vtk;

import system.controller.WidgetController;
import system.model.VTKWidget;
import vtk.vtkAngleWidget;
import vtk.vtkRenderWindowInteractor;

/**
 * This is the Angle Widget class which is a widget provides angle reference.
 * 
 * @author	Andy Tsang 
 * @version	1.1
 */
public class AngleWidget extends VTKWidget {
	private vtkAngleWidget angleWidget;
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param interactor the renderWindowInteractor currently using
	 */
	public AngleWidget(WidgetController widgetController, vtkRenderWindowInteractor interactor) {
		super("AngleWidget", widgetController, interactor);
	}

	@Override
	public void initWidget() {
		angleWidget = new vtkAngleWidget();
		angleWidget.CreateDefaultRepresentation();
		// Disable atm
		if (false){
			angleWidget.On();
			super.setEabled(true);
			super.printState();
		}
	}

	@Override
	public void update() {
	}

}

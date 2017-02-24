package system.vtk;

import system.controller.WidgetController;
import system.model.VTKWidget;
import vtk.vtkActor;
import vtk.vtkBoxWidget;
import vtk.vtkRenderWindowInteractor;

/**
 * This is the Box Widget class which is a widget provide the outer volume of the object in a box shape.
 * 
 * @author	Andy Tsang 
 * @version	1.1
 */
public class BoxWidget extends VTKWidget{
	private vtkBoxWidget boxWidget;
	
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param interactor the renderWindowInteractor currently using
	 */
	public BoxWidget(WidgetController widgetController, vtkRenderWindowInteractor interactor) {
		super("BoxWidget", widgetController, interactor);
	}

	@Override
	public void initWidget() {
		boxWidget = new vtkBoxWidget();
		boxWidget.SetInteractor(super.getInteractor());
		boxWidget.SetPlaceFactor(1.25);
		//!!!!!! need to modify to allow to select
		if (super.getWidgetController().getVTKController().getActorList().size() > 0){
			vtkActor actor = super.getWidgetController().getVTKController().getActorList().get(0);
			boxWidget.SetProp3D(actor);
		}
		// Disable atm
		if (false){
			boxWidget.On();
			super.setEabled(true);
			super.printState();
		}
	}

	@Override
	public void update() {
		if (super.getWidgetController().getVTKController().getActorList().size() > 0){
			vtkActor actor = super.getWidgetController().getVTKController().getActorList().get(0);
			boxWidget.SetProp3D(actor);
		}
	}

}

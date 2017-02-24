package system.vtk;

import lvs.model.VTKWidget;
import system.controller.WidgetController;
import vtk.vtkBiDimensionalWidget;
import vtk.vtkRenderWindowInteractor;

/**
 * This is the BiDimension Widget class which is a widget provides two dimensional aixes.
 * 
 * @author	Andy Tsang 
 * @version	1.0
 */
public class BiDimensionWidget extends VTKWidget {
	private vtkBiDimensionalWidget bidimensionWidget;
	/**
	 * Constructor 
	 * 
	 * @param widgetController controller of all widget
	 * @param interactor the renderWindowInteractor currently using
	 */
	public BiDimensionWidget(WidgetController widgetController, vtkRenderWindowInteractor interactor) {
		super("BiDimensionWidget", widgetController, interactor);
	}

	@Override
	public void initWidget() {
		bidimensionWidget = new vtkBiDimensionalWidget();
		if (super.getWidgetController().getSystemPreference().isBiDimensionalOn()){
			bidimensionWidget.On();
		}
	}

	@Override
	public void update() {
	}

}

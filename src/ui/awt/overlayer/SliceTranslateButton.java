package ui.awt.overlayer;

import java.awt.Color;
import java.awt.Image;

import system.file.FileItem;
import system.model.OverLayerButton;
import ui.awt.panel.LVSOverLayerPanel;
import ui.awt.res.LVSSwingInfo;

/** ************************************************************
 * An implementation of overlayerButton.
 * The sliceTranslateButton referring to the slice filter and
 * slice transform function.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class SliceTranslateButton extends OverLayerButton {

	public SliceTranslateButton(LVSOverLayerPanel overLayerPanel, int index, Image icon) {
		super(overLayerPanel, index, icon);
	}
	
	@Override
	public void performAction(){
		super.performAction();
		super.getOverLayerPanel().getOverLayerController().getSystemController().getVTKController().setSliceTrans(super.isTurnOn());
		super.getOverLayerPanel().getOverLayerController().getSystemController().getSwingController().updateVTK();
	}

	@Override
	public boolean isButtonVisible() {
		if (super.getOverLayerPanel().getOverLayerController().getSystemController().getVTKController().getVTKPanel() != null && super.getOverLayerPanel().getOverLayerController().getSystemController().getVTKController().getVTKPanel().isShowing()){
			if (super.getOverLayerPanel().getOverLayerController().getSystemController().getCurrentFile() != null){
				FileItem item = super.getOverLayerPanel().getOverLayerController().getSystemController().getCurrentFile().getCurrentActor();
				if(item != null){
					if(item.getSlice().isVisiable()){
						return true;
					}
				}
			}
		}
		return false;
	}

}

package ui.awt.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import system.controller.OverLayerController;
import system.model.OverLayerButton;

/** ************************************************************
 * This is a listener implementing awt.event.ActionListener
 * Handles all the action in overlayer
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class OverLayerListener implements ActionListener{
	private OverLayerController overlayerController;
	
	public OverLayerListener(OverLayerController overlayerController) {
		this.overlayerController = overlayerController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof OverLayerButton){
			((OverLayerButton)e.getSource()).performAction();
		}
	}

}

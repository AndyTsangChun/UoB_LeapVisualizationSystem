package ui.awt.panel;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import system.controller.SwingController;
import system.model.LVSPanel;

/** ************************************************************
 * This class extends LVSPanel, a status bar show the system status
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSStatusBar extends LVSPanel {
	private JLabel statusLabel;
	
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSStatusBar(SwingController swingController, JFrame mainFrame) {
		super(swingController, mainFrame);
		statusLabel = new JLabel("");
	}

	@Override
	public void initPanel() {
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setPreferredSize(new Dimension(super.getMainFrame().getWidth(), 16));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(statusLabel);
	}

	/**
	 * Update the status label
	 * @param status Incoming status desc
	 */
	public void updateStatus(String status){
		this.statusLabel.setText(status);
		this.statusLabel.repaint();
	}
	
	public JLabel getStatusLabel(){
		return this.statusLabel;
	}
}

package ui.awt.dialog.about;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import layout.TableLayout;
import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

/** ************************************************************
 * This class extends LVSPanel, an about dialog.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSAboutDialog extends LVSPanel{
	private JLabel title, logo, version, author, supervisor, desc;
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSAboutDialog(SwingController swingController, JFrame mainFrame) {
		super(swingController, mainFrame);
		
		title = new JLabel();
		logo = new JLabel();
		version = new JLabel();
		author = new JLabel();
		supervisor = new JLabel();
		desc = new JLabel();
	}

	@Override
	public void initPanel() {
		double[][] size = {{TableLayout.FILL},{25,10,50,10,25,10,25,10,25,10,50}};
		this.setLayout(new TableLayout(size));
		this.setPreferredSize(new Dimension(LVSSwingInfo.ABOUT_PANEL_W, LVSSwingInfo.ABOUT_PANEL_H));
		this.setSize(new Dimension(LVSSwingInfo.ABOUT_PANEL_W, LVSSwingInfo.ABOUT_PANEL_H));
		
		title.setText(LVSStringInfo.LVS_NAME);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(title.getFont ().deriveFont (20.0f));
		Image image = SwingTextureManager.LVS_LOGO_IMAGE.getScaledInstance( LVSSwingInfo.LVS_LOGO_SIZE, LVSSwingInfo.LVS_LOGO_SIZE,  java.awt.Image.SCALE_SMOOTH ) ;	
		logo.setIcon(new ImageIcon(image));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		version.setText(LVSStringInfo.VERSION);
		version.setHorizontalAlignment(SwingConstants.CENTER);
		author.setText(LVSStringInfo.AUTHOR);
		author.setHorizontalAlignment(SwingConstants.CENTER);
		supervisor.setText(LVSStringInfo.SUPERVISOR);
		supervisor.setHorizontalAlignment(SwingConstants.CENTER);
		desc.setText(LVSStringInfo.LVS_DESC);
		desc.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.add(title,"0,0");
		this.add(logo,"0,2");
		this.add(version,"0,4");
		this.add(author,"0,6");
		this.add(supervisor,"0,8");
		this.add(desc,"0,10");
	}

	// Attribute Set and Get

}

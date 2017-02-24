package ui.awt.dialog.edit.preference;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import layout.TableLayout;
import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.listener.EditPreferenceDialogListener;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;

/** ************************************************************
 * This class extends LVSPanel, a preference edit panel which allows
 * user to edit the system preference.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSPreferenceEditDialog extends LVSPanel {
	private final String[] handStyleSet = {"Left", "Right"};
	private final String[] rotateSpeedSet = {"1", "2","3","4","5"};
	private JComboBox handStyle;
	private JComboBox rotateSpeed;
	private JCheckBox showTutorial;
	private JPanel top;
	private JPanel bottom;
	private JButton cancel;
	private JButton apply;
	private EditPreferenceDialogListener listener;
	private int frame_w;
	private int frame_h;
	private int panel_h;
	private int panel_w;

	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSPreferenceEditDialog(SwingController swingController,
			JFrame mainFrame) {
		super(swingController, mainFrame);
		handStyle = new JComboBox(handStyleSet);
		rotateSpeed = new JComboBox(rotateSpeedSet);
		showTutorial = new JCheckBox();
		top = new JPanel();
		bottom = new JPanel();
		apply = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_APPLY);
		cancel = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_CANCEL);
		listener = new EditPreferenceDialogListener(this);
	}

	@Override
	public void initPanel() {
		this.setLayout(new BorderLayout());
		frame_w = super.getSwingController().getSystemPreference()
				.getScreenWidth();
		frame_h = super.getSwingController().getSystemPreference()
				.getScreenHeight();
		panel_h = (int) (frame_h * LVSSwingInfo.EDIT_PREFERENCE_H_RATIO);
		panel_w = (int) (frame_w * LVSSwingInfo.EDIT_PREFERENCE_W_RATIO);
		this.setSize(new Dimension(panel_w, panel_h));
		this.setPreferredSize(new Dimension(panel_w, panel_h));
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottom.setPreferredSize(new Dimension(panel_w,
				LVSSwingInfo.EDIT_PANEL_BOT_H));

		// init bottom panel
		cancel.setActionCommand(LVSPanel.COMMAND_CANCEL);
		cancel.addActionListener(listener);
		apply.setActionCommand(LVSPanel.COMMAND_APPLY);
		apply.addActionListener(listener);
		bottom.add(cancel);
		bottom.add(apply);

		// init top panel
		double[][] panelSize = {
				{ TableLayout.FILL, LVSSwingInfo.EDIT_PREFERENCE_BUTTON_W },
				{ LVSSwingInfo.EDIT_PREFERENCE_BUTTON_H,
						LVSSwingInfo.EDIT_PREFERENCE_BUTTON_H,
						LVSSwingInfo.EDIT_PREFERENCE_BUTTON_H  } };// {{col0,col1...},{row0,row1...}}
		TableLayout t_layout = new TableLayout(panelSize);
		top.setLayout(t_layout);
		JLabel handStyleLabel = new JLabel(LVSStringInfo.EDIT_PANEL_HAND_STYLE);
		top.add(handStyleLabel , "0,0");
		if(!super.getSwingController().getSystemPreference().isLeftStyle())
			handStyle.setSelectedIndex(1);
		top.add(handStyle, "1,0");
		JLabel rotateSpeedLabel = new JLabel(LVSStringInfo.EDIT_PANEL_ROTATE_SPEED);
		top.add(rotateSpeedLabel, "0,1");
		rotateSpeed.setSelectedIndex(super.getSwingController().getSystemPreference().getRotateSpeed() - 1);
		top.add(rotateSpeed, "1,1");
		JLabel showTutorialLabel = new JLabel(LVSStringInfo.EDIT_PANEL_SHOW_TUTORIAL);
		top.add(showTutorialLabel, "0,2");
		showTutorial.setSelected(super.getSwingController().getSystemPreference().isShowTutorial());
		top.add(showTutorial,"1,2");

		this.add(top, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	/**
	 * Save all preference changes to the systemPreference object
	 */
	public void saveChanges(){
		if(handStyle.getSelectedItem().toString().equals("Left"))
			getSwingController().getSystemPreference().setLeftStyle(true);
		else
			getSwingController().getSystemPreference().setLeftStyle(false);

		getSwingController().getSystemPreference().setRotateSpeed(Integer.parseInt(rotateSpeed.getSelectedItem().toString()));
		getSwingController().getSystemPreference().setShowTutorial(showTutorial.isSelected());
	}

}

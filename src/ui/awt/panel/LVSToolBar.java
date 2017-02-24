package ui.awt.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import system.controller.SwingController;
import system.model.LVSColorMap;
import system.model.LVSPanel;
import ui.awt.listener.ToolBarListener;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

/** ************************************************************
 * This class extends LVSPanel, a tool bar panel contains all
 * LVS tool, for instance changing display actor
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSToolBar extends LVSPanel {
	private String[] representationSet = {"Surface","Points","Wire Frame"};
	private JToolBar fileToolBar;
	private JToolBar viewToolBar;
	private JButton new_B;
	private JButton open_B;
	private JButton save_B;
	private JButton saveAs_B;
	private JButton import_B;
	private JButton preActor_B;
	private JButton nextActor_B;
	private JTextField actor_Label;
	private JToggleButton track_TB;
	private JToggleButton gesture_TB;
	private JToggleButton presentation_TB;
	private JComboBox opacity;
	private JComboBox colorMap;
	private JComboBox representation;
	private JLabel lmStatus;
	private ToolBarListener toolBarListener;
	private int previousOpacity = 0;
	private Dimension d = new Dimension(LVSSwingInfo.TOOLBAR_BUTTON_W, LVSSwingInfo.TOOLBAR_BUTTON_H);

	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSToolBar(SwingController swingController, JFrame mainFrame) {
		super(swingController, mainFrame);
		toolBarListener = new ToolBarListener(super.getSwingController());
		new_B = new JButton(new ImageIcon(SwingTextureManager.NEW_FILE_ICON_IMAGE));
		open_B = new JButton(new ImageIcon(SwingTextureManager.OPEN_ICON_IMAGE));
		save_B = new JButton(new ImageIcon(SwingTextureManager.SAVE_ICON_IMAGE));
		saveAs_B = new JButton(new ImageIcon(SwingTextureManager.SAVE_AS_ICON_IMAGE));
		import_B = new JButton(new ImageIcon(SwingTextureManager.IMPORT_ICON_IMAGE));
		preActor_B = new JButton(new ImageIcon(SwingTextureManager.LEFT_ICON_IMAGE));
		actor_Label = new JTextField("");
		nextActor_B = new JButton(new ImageIcon(SwingTextureManager.RIGHT_ICON_IMAGE));
		track_TB = new JToggleButton();
		gesture_TB = new JToggleButton();
		presentation_TB = new JToggleButton();
		opacity = new JComboBox();
		colorMap = new JComboBox();
		representation = new JComboBox();
		lmStatus = new JLabel();
	}

	@Override
	public void initPanel() {
		this.setPreferredSize(new Dimension(super.getMainFrame().getWidth(), LVSSwingInfo.TOOL_BAR_H));
		// Not sure why the layout have space between top -10 has to be set to show properly
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, -15));
		this.setAlignmentY(TOP_ALIGNMENT);
		fileToolBar = new JToolBar();
		fileToolBar.setRollover(true);
		fileToolBar.setFloatable(false);
		fileToolBar.setMargin(new Insets(0,0,0,0));
		fileToolBar.setAlignmentY(SwingConstants.TOP);
		fileToolBar.setAlignmentX(TOP_ALIGNMENT);
		viewToolBar = new JToolBar();
		viewToolBar.setRollover(true);
		viewToolBar.setFloatable(false);
		viewToolBar.setMargin(new Insets(0,0,0,0));
		viewToolBar.setAlignmentY(SwingConstants.TOP);
		viewToolBar.setAlignmentX(TOP_ALIGNMENT);
		
		new_B.setToolTipText(LVSStringInfo.TOOLTIPS_NEW);
		new_B.setActionCommand(LVSPanel.COMMAND_NEW);
		this.formatButton(new_B);
		fileToolBar.add(new_B);
		
		open_B.setToolTipText(LVSStringInfo.TOOLTIPS_OPEN);
		open_B.setActionCommand(LVSPanel.COMMAND_OPEN);
		this.formatButton(open_B);
		fileToolBar.add(open_B);
		
		save_B.setToolTipText(LVSStringInfo.TOOLTIPS_SAVE);
		save_B.setActionCommand(LVSPanel.COMMAND_SAVE);
		this.formatButton(save_B);
		fileToolBar.add(save_B);
		
		saveAs_B.setToolTipText(LVSStringInfo.TOOLTIPS_SAVE_AS);
		saveAs_B.setActionCommand(LVSPanel.COMMAND_SAVE_AS);
		this.formatButton(saveAs_B);
		fileToolBar.add(saveAs_B);

		viewToolBar.addSeparator();
		import_B.setToolTipText(LVSStringInfo.TOOLTIPS_IMPORT);
		import_B.setActionCommand(COMMAND_IMPORT_OBJ);
		this.formatButton(import_B);
		viewToolBar.add(import_B);
		
		viewToolBar.addSeparator();
		preActor_B.setToolTipText(LVSStringInfo.TOOLTIPS_PRE_ACTOR);
		preActor_B.setActionCommand(LVSPanel.COMMAND_PRE_ACTOR);
		this.formatButton(preActor_B);
		viewToolBar.add(preActor_B);
		
		actor_Label.setHorizontalAlignment(SwingConstants.CENTER);
		actor_Label.setEditable(false);
		actor_Label.setSize(d);
		actor_Label.setColumns(2);
		viewToolBar.add(actor_Label);
		
		nextActor_B.setToolTipText(LVSStringInfo.TOOLTIPS_NEXT_ACTOR);
		nextActor_B.setActionCommand(LVSPanel.COMMAND_NEXT_ACTOR);
		this.formatButton(nextActor_B);
		viewToolBar.add(nextActor_B);

		viewToolBar.addSeparator();
		track_TB.setActionCommand(LVSPanel.COMMAND_TRACKING);
		track_TB.setIcon(new ImageIcon(SwingTextureManager.TRACKING_ICON_IMAGE));
		track_TB.setSize(d);
		track_TB.addItemListener(toolBarListener);
		viewToolBar.add(track_TB);
		
		viewToolBar.addSeparator();
		gesture_TB.setActionCommand(LVSPanel.COMMAND_GESTURE);
		gesture_TB.setIcon(new ImageIcon(SwingTextureManager.GESTURE_ICON_IMAGE));
		gesture_TB.setSize(d);
		gesture_TB.addItemListener(toolBarListener);
		viewToolBar.add(gesture_TB);
		
		viewToolBar.addSeparator();
		JLabel opacityIcon = new JLabel(new ImageIcon(SwingTextureManager.OPACITY_ICON_IMAGE));
		viewToolBar.add(opacityIcon);
		for (int i = 0 ; i <= 100 ; i += 10){
			opacity.addItem(i);
		}
		opacity.setMaximumSize(new Dimension(LVSSwingInfo.TOOLBAR_OPACITY_CB_W, LVSSwingInfo.TOOLBAR_COMBOBOX_H));
		opacity.setActionCommand(LVSPanel.COMMAND_OPACITY);
		opacity.addActionListener(toolBarListener);
		opacity.setEditable(true);
		viewToolBar.add(opacity);
		
		viewToolBar.addSeparator();
		JLabel colorMapLabel = new JLabel(new ImageIcon(SwingTextureManager.COLORMAP_ICON_IMAGE));
		viewToolBar.add(colorMapLabel);
		List<LVSColorMap> colorMaps = super.getSwingController().getSystemPreference().getColorMaps();
		// Add default
		colorMap.addItem("default");
		for (int i = 0 ; i < colorMaps.size() ; i ++){
			colorMap.addItem(colorMaps.get(i).getName());
		}
		colorMap.setPreferredSize(new Dimension(LVSSwingInfo.TOOLBAR_COLORMAP_CB_W, LVSSwingInfo.TOOLBAR_COMBOBOX_H));
		colorMap.setMaximumSize(new Dimension(LVSSwingInfo.TOOLBAR_COLORMAP_CB_W, LVSSwingInfo.TOOLBAR_COMBOBOX_H));
		colorMap.setActionCommand(LVSPanel.COMMAND_COLORMAP);
		colorMap.addActionListener(toolBarListener);
		viewToolBar.add(colorMap);
		
		JLabel representationLabel = new JLabel(new ImageIcon(SwingTextureManager.REPRESENTATION_ICON_IMAGE));
		viewToolBar.add(representationLabel);
		for (int i = 0 ; i < representationSet.length ; i++){
			representation.addItem(representationSet[i]);
		}
		representation.setPreferredSize(new Dimension(LVSSwingInfo.TOOLBAR_COLORMAP_CB_W, LVSSwingInfo.TOOLBAR_COMBOBOX_H));
		representation.setMaximumSize(new Dimension(LVSSwingInfo.TOOLBAR_COLORMAP_CB_W, LVSSwingInfo.TOOLBAR_COMBOBOX_H));
		representation.setActionCommand(LVSPanel.COMMAND_REPRESENTATION);
		representation.addActionListener(toolBarListener);
		viewToolBar.add(representation);
		
		viewToolBar.addSeparator();
		lmStatus.setIcon(new ImageIcon(SwingTextureManager.OFF_ICON_IMAGE));
		viewToolBar.add(lmStatus);
		
		viewToolBar.addSeparator();
		presentation_TB.setActionCommand(LVSPanel.COMMAND_PRESENTATION);
		presentation_TB.setIcon(new ImageIcon(SwingTextureManager.PRESENTATION_ICON_IMAGE));
		presentation_TB.setSize(d);
		presentation_TB.addItemListener(toolBarListener);
		viewToolBar.add(presentation_TB);
		
		// Add tool bar to panel
		this.add(fileToolBar);
		this.add(viewToolBar);
	}
	
	/**
	 * Update tool bar UI
	 */
	public void updateToolBar(){
		if (super.getSwingController().getSystemController().getCurrentFile() != null){
			int id = super.getSwingController().getSystemController().getCurrentFile().getShowingActor();
			actor_Label.setText(id+"");
			if (super.getSwingController().getSystemController().getCurrentFile().getFileList().size() > 0){
				int value = (int)(super.getSwingController().getSystemController().getCurrentFile().getItemById(id).getOpacity() * 100);
				if (previousOpacity != value){
					opacity.setSelectedItem(value);
					previousOpacity = value;
				}
			}
		}
		track_TB.setSelected(super.getSwingController().getSystemController().getOverLayerController().isTrackingVisible());
		gesture_TB.setSelected(super.getSwingController().getSystemController().getOverLayerController().isGestureStatusVisible());
		presentation_TB.setSelected(super.getSwingController().isPresentationMode());
	}
	
	/**
	 * Format JButton
	 * @param button
	 */
	public void formatButton(JButton button){
		button.setSize(d);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addActionListener(toolBarListener);
	}

	public JLabel getLmStatus() {
		return lmStatus;
	}
}

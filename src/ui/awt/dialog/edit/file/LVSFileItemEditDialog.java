package ui.awt.dialog.edit.file;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import layout.TableLayout;
import system.controller.SwingController;
import system.file.FileItem;
import system.file.LVSFile;
import system.model.LVSPanel;
import ui.awt.listener.EditItemDialogListener;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

/** ************************************************************
 * This class extends LVSPanel, a file item edit panel which allows
 * user to edit specific file's algorithm option.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSFileItemEditDialog extends LVSPanel{
	public static final String COMMAND_CONTOUR = "contour";
	public static final String COMMAND_THRESHOLD = "threshold";
	public static final String COMMAND_SLICE = "slice";
	private int showingActorID; 
	private String option;
	private LVSFile file;
	private FileItem item;
	private JPanel top;
	private JPanel bottom;
	private JButton cancel;
	private JButton back;
	private JButton apply;
	private JButton threshold;
	private JButton contour;
	private JButton slice;
	private JLabel itemName;
	private EditItemDialogListener editItemPanelListener;
	private JScrollPane topScrollPane;
	private ThresholdPanel thresholdPanel;
	private SlicePanel slicePanel;
	private ContourPanel contourPanel;
	private int frame_w;
	private int frame_h;
	private int panel_h;
	private int panel_w;
	
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 * @param file	LVSFile system currently working with
	 * @param id File Item ID
	 * @param option Tab Option
	 */
	public LVSFileItemEditDialog(SwingController swingController, JFrame mainFrame, LVSFile file, int id, String option) {
		super(swingController, mainFrame);
		this.file = file;
		this.showingActorID = id;
		this.item = file.getItemById(id);
		this.option = option;
		editItemPanelListener = new EditItemDialogListener(this);
		
		top = new JPanel();
		bottom = new JPanel();
		topScrollPane = new JScrollPane();
		
		itemName = new JLabel("");
		threshold = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_THRESHOLD);
		contour = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_CONTOUR);
		slice = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_SLICE);
		
		apply = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_APPLY);
		back = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_BACK);
		cancel = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_CANCEL);
	}

	@Override
	public void initPanel() {
		this.setLayout(new BorderLayout());
		frame_w = super.getSwingController().getSystemPreference().getScreenWidth();
		frame_h = super.getSwingController().getSystemPreference().getScreenHeight();
		panel_h = (int)(frame_h * LVSSwingInfo.EDIT_FILE_DIALOG_H_RATIO);
		panel_w = (int)(frame_w * LVSSwingInfo.EDIT_ITEM_PANEL_W_RATIO);
		this.setSize(new Dimension(panel_w, panel_h));
		this.setPreferredSize(new Dimension(panel_w, panel_h));
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottom.setPreferredSize(new Dimension(panel_w, LVSSwingInfo.EDIT_PANEL_BOT_H));
		
		// item name
		itemName.setText(String.format(LVSStringInfo.EDIT_PANEL_ITEM_TITLE, file.getItemById(showingActorID).getName()));
		itemName.setBorder(new EmptyBorder(5,5,5,10));
		// init top panel
		setTopPanel(option);
		
		// init bottom panel
		cancel.setActionCommand(LVSPanel.COMMAND_CANCEL);
		cancel.addActionListener(editItemPanelListener);
		back.setActionCommand(LVSPanel.COMMAND_BACK);
		back.addActionListener(editItemPanelListener);
		apply.setActionCommand(LVSPanel.COMMAND_APPLY);
		apply.addActionListener(editItemPanelListener);
		bottom.add(cancel);
		bottom.add(back);
		bottom.add(apply);
		
		this.add(itemName, BorderLayout.NORTH);
		this.add(top, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	/**
	 * Format button
	 * @param button Button wish to config
	 */
	public void formatButton(JButton button){
		int w = super.getSwingController().getSystemPreference().getScreenWidth();
		int panel_w = (int)(w * LVSSwingInfo.EDIT_ITEM_PANEL_W_RATIO);
		int button_w = panel_w - (LVSSwingInfo.EDIT_ITEM_OFFSET * 2);
		button.setSize(button_w, LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H);
		button.addActionListener(editItemPanelListener);
		Image icon = SwingTextureManager.BUTTON_BG_1_IMAGE;
		icon = icon.getScaledInstance(w, LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H,  java.awt.Image.SCALE_SMOOTH ) ;
		button.setIcon(new ImageIcon(icon));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setBorder(new LineBorder(Color.LIGHT_GRAY));
		button.setForeground(Color.BLACK);
	}
	
	/**
	 * Setup the config panel base on user option
	 * @param option User option
	 */
	public void setTopPanel(String option){
		Dimension topSize = new Dimension((int)panel_w - (LVSSwingInfo.EDIT_ITEM_OFFSET * 2), panel_h - LVSSwingInfo.EDIT_PANEL_BOT_H);
		top.setSize(topSize);
		top.setMaximumSize(topSize);
		top.setPreferredSize(topSize);
		threshold.setActionCommand(COMMAND_THRESHOLD);
		formatButton(threshold);
		contour.setActionCommand(COMMAND_CONTOUR);
		formatButton(contour);
		slice.setActionCommand(COMMAND_SLICE);
		formatButton(slice);
		
		// Try to perform as expandable bar 
		if (option.equals(COMMAND_THRESHOLD)){
			double[][] panelSize = {{TableLayout.FILL},{LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H,TableLayout.PREFERRED,LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H, LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H}};// {{col0,col1...},{row0,row1...}}
			TableLayout t_layout = new TableLayout(panelSize);
			top.setLayout(t_layout);
			// init top panel
			thresholdPanel = new ThresholdPanel(item, this);
			thresholdPanel.initPanel();
			top.add(threshold,"0,0");
			top.add(thresholdPanel,"0,1");
			top.add(contour,"0,2");
			top.add(slice,"0,3");
		}else if (option.equals(COMMAND_CONTOUR)){
			double[][] panelSize = {{TableLayout.FILL},{LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H,LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H, TableLayout.PREFERRED, LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H}};// {{col0,col1...},{row0,row1...}}
			TableLayout t_layout = new TableLayout(panelSize);
			top.setLayout(t_layout);
			// init top panel
			contourPanel = new ContourPanel(item, this);
			contourPanel.initPanel();
			top.add(threshold,"0,0");
			top.add(contour,"0,1");
			top.add(contourPanel,"0,2");
			top.add(slice,"0,3");
		}else if (option.equals(COMMAND_SLICE)){
			double[][] panelSize = {{TableLayout.FILL},{LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H,LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H, LVSSwingInfo.EDIT_ITEM_TITLE_BUTTON_H, TableLayout.PREFERRED}};// {{col0,col1...},{row0,row1...}}
			TableLayout t_layout = new TableLayout(panelSize);
			top.setLayout(t_layout);
			// init top panel
			slicePanel = new SlicePanel(item, this);
			slicePanel.initPanel();
			top.add(threshold,"0,0");
			top.add(contour,"0,1");
			top.add(slice,"0,2");
			top.add(slicePanel,"0,3");
		}
		this.add(top, BorderLayout.CENTER);
		top.revalidate();
		this.revalidate();
	}
	
	/**
	 * Save user's changes
	 */
	public void saveChange(){
		System.out.println("Trying to save " + option + " changes.");
		if (option.equals(COMMAND_THRESHOLD)){
			thresholdPanel.saveChange();
		}else if(option.equals(COMMAND_SLICE)){
			slicePanel.saveChange();
		}else if(option.equals(COMMAND_CONTOUR)){
			contourPanel.saveChange();
		}
	}

	// Attributes Set and Get
	public int getShowingActorID(){
		return this.showingActorID;
	}
	
	public String getOption(){
		return this.option;
	}
}

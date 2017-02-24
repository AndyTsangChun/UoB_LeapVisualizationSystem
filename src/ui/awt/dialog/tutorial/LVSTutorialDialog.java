package ui.awt.dialog.tutorial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.listener.TutorialDialogListener;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

/** ************************************************************
 * This class extends LVSPanel, a tutorial dialog. Using a
 * JLabel to display the pre-configured tutorial image and
 * switch by Jbuttons. This dialog could be modified to a more
 * complex tuturial focusing on different topics. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSTutorialDialog extends LVSPanel{
	public static final String COMMAND_PREVIOUS_ITEM = "previous_item";
	public static final String COMMAND_NEXT_ITEM = "next_item";
	public static final int PANEL_OFFSET = 5;
	private int currentIndex = 0;
	private JLabel image;
	private JPanel bot;
	private JButton next, previous;
	private List<Image> imageList;
	private List<JButton> buttonList;
	private TutorialDialogListener listener;
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSTutorialDialog(SwingController swingController, JFrame mainFrame) {
		super(swingController, mainFrame);
		imageList = new ArrayList<Image>();
		buttonList = new ArrayList<JButton>();
		imageList.add(SwingTextureManager.TUT_M_1_IMAGE);
		imageList.add(SwingTextureManager.TUT_M_2_IMAGE);
		imageList.add(SwingTextureManager.TUT_M_3_IMAGE);
		imageList.add(SwingTextureManager.TUT_M_4_IMAGE);
		imageList.add(SwingTextureManager.TUT_M_5_IMAGE);
		imageList.add(SwingTextureManager.TUT_M_6_IMAGE);
		imageList.add(SwingTextureManager.TUT_GESTURE_IMAGE);
		imageList.add(SwingTextureManager.TUT_MOVEMENT_IMAGE);
		imageList.add(SwingTextureManager.TUT_SWIPE_IMAGE);
		imageList.add(SwingTextureManager.TUT_CLICK_IMAGE);
		imageList.add(SwingTextureManager.TUT_ZOOM_IMAGE);
		
		bot = new JPanel();
		next = new JButton();
		previous = new JButton();
		
		listener  = new TutorialDialogListener(this);
	}

	@Override
	public void initPanel() {
		this.setLayout(new BorderLayout());
		BorderLayout layout = (BorderLayout)this.getLayout();
		layout.setVgap(0);
		
		int width = super.getSwingController().getSystemPreference().getScreenWidth();
		int height = super.getSwingController().getSystemPreference().getScreenHeight(); 
		int w = LVSSwingInfo.TUTORIAL_DIALOG_W;
		int h = LVSSwingInfo.TUTORIAL_DIALOG_H;
		if (width < LVSSwingInfo.TUTORIAL_DIALOG_W || height < LVSSwingInfo.TUTORIAL_DIALOG_H){
			w = (int)(width * LVSSwingInfo.TUTORIAL_DIALOG_W_RATIO);
			h = w * 2 / 3 + LVSSwingInfo.TUTORIAL_BUTTON_H + PANEL_OFFSET;
		}
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(new Dimension(w, h));
		
		for(int i = 0 ; i < imageList.size() ; i++){
			Image im = imageList.get(i);
			// offset because of borderLayout
			int offsetX = 20;
			int offsetY = 50;
			im = im.getScaledInstance( w - offsetX, h - LVSSwingInfo.TUTORIAL_BUTTON_H - PANEL_OFFSET - offsetY,  java.awt.Image.SCALE_SMOOTH ) ;
			imageList.set(i, im);
		}
		
		image = new JLabel(new ImageIcon(getCurrentImage()));
		image.setBorder(new EmptyBorder(0,0,0,0));
		Dimension d = new Dimension(w, h - LVSSwingInfo.TUTORIAL_BUTTON_H - PANEL_OFFSET);
		image.setSize(d);
		image.setMinimumSize(d);
		image.setMaximumSize(d);
		image.setPreferredSize(d);
		
		bot.setSize(new Dimension(LVSSwingInfo.TUTORIAL_DIALOG_W, LVSSwingInfo.TUTORIAL_BUTTON_H + PANEL_OFFSET));
		bot.setMaximumSize(new Dimension(LVSSwingInfo.TUTORIAL_DIALOG_W, LVSSwingInfo.TUTORIAL_BUTTON_H + PANEL_OFFSET));
		previous.setIcon(new ImageIcon(SwingTextureManager.LEFT_ICON_20_IMAGE));
		previous.setActionCommand(COMMAND_PREVIOUS_ITEM);
		formatButton(previous);
		bot.add(previous);
		for (int i = 0 ; i < imageList.size() ; i++){
			JButton button = new JButton(new ImageIcon(SwingTextureManager.TUT_ITEM_IMAGE));
			button.setActionCommand(""+i);
			formatButton(button);
			buttonList.add(button);
			bot.add(button);
		}
		next.setIcon(new ImageIcon(SwingTextureManager.RIGHT_ICON_20_IMAGE));
		next.setActionCommand(COMMAND_NEXT_ITEM);
		formatButton(next);
		bot.add(next);
		this.add(image, BorderLayout.CENTER);
		this.add(bot, BorderLayout.SOUTH);
		setIndexUIActivate(currentIndex);
	}
	
	/**
	 * Format JButton
	 * @param button
	 */
	public void formatButton(JButton button){
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addActionListener(listener);
	}
	
	public void setIndexUIActivate(int index){
		buttonList.get(index).setIcon(new ImageIcon(SwingTextureManager.TUT_ITEM2_IMAGE));
	}
	
	public void setIndexUIDeactivate(int index){
		buttonList.get(index).setIcon(new ImageIcon(SwingTextureManager.TUT_ITEM_IMAGE));
	}
	
	public void changeCurrentIndexByInt(int index){
		setIndexUIDeactivate(currentIndex);
		currentIndex = index;
		setIndexUIActivate(currentIndex);
		image.setIcon(new ImageIcon(getCurrentImage()));
		this.revalidate();
	}
	
	/**
	 * Option will be -1 for decrement or 1 for increment
	 * @param option
	 */
	public void changeCurrentIndex(int option){
		int newIndex = currentIndex + option;
		setIndexUIDeactivate(currentIndex);
		if (newIndex >= 0 && newIndex < imageList.size()){
			currentIndex = newIndex;
		}else if (newIndex < 0){
			currentIndex = imageList.size() - 1;
		}else if (newIndex >= imageList.size()){
			currentIndex = 0;
		}
		setIndexUIActivate(currentIndex);
		image.setIcon(new ImageIcon(getCurrentImage()));
		this.revalidate();
	}
	
	public Image getCurrentImage(){
		return imageList.get(currentIndex);
	}

	// Attribute Set and Get

}

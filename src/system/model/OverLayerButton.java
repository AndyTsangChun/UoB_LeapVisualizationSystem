package system.model;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ui.awt.listener.OverLayerListener;
import ui.awt.panel.LVSOverLayerPanel;
import ui.awt.res.LVSSwingInfo;
import vtk.vtkPanel;

/**
 * This a an abstract class of OverLayerButton, extended from JButton.
 * Including attributes like different status icons, size and position.
 * 
 * @author atc1992andy
 * @version 1.2
 */
public abstract class OverLayerButton extends JButton{
	private LVSOverLayerPanel overLayerPanel;
	private Image defaultIcon;
	private Image hoverIcon;
	private Image clickIcon;
	private Image enableIcon;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private int index;
	private boolean isTurnOn;
	
	/**
	 * Constructor
	 * @param overLayerPanel OverLayerPanel that will draw on
	 * @param index Reference Index on the panel
	 * @param defaultIcon Default Image Icon
	 */
	public OverLayerButton(LVSOverLayerPanel overLayerPanel, int index, Image defaultIcon) {
		this.overLayerPanel = overLayerPanel;
		this.index = index;
		this.defaultIcon = defaultIcon;
		this.setIcon(new ImageIcon(defaultIcon));
		updatePosition();
		width = height = LVSSwingInfo.OVERLAYER_BUTTON_SIZE;
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		overLayerPanel.add(this);
		this.addActionListener(new OverLayerListener(overLayerPanel.getOverLayerController()));
	}

	/**
	 * Updating this position to responding to the changes of main frame 
	 */
	public void updatePosition(){
		if(overLayerPanel.getOverLayerController().getSystemController().getVTKController() != null){
			vtkPanel panel = overLayerPanel.getOverLayerController().getSystemController().getVTKController().getVTKPanel();
			if (panel != null && panel.isShowing()){
				int x = panel.getLocationOnScreen().x;
				// Not sure why the panel has 22 pixel difference with actual position, need to check
				int y = panel.getLocationOnScreen().y - 22;
				posX = x + LVSSwingInfo.OVERLAYER_BUTTON_OFFSET_X;
				posY = y + LVSSwingInfo.OVERLAYER_BUTTON_OFFSET_Y + (index * (LVSSwingInfo.OVERLAYER_BUTTON_OFFSET_X + LVSSwingInfo.OVERLAYER_BUTTON_SIZE));
				setBounds(posX, posY, width, height);
			}
		}
	}
	
	/**
	 * Abstract method use to define is this button visible according to the functionality of this button.
	 * @return boolean
	 */
	public abstract boolean isButtonVisible();
	
	/**
	 * Perform Mapped Action
	 */
	public void performAction(){
		if (isTurnOn()){
			setTurnOn(false);
			this.setIcon(new ImageIcon(this.getDefaultIcon()));
		} else{
			setTurnOn(true);
			this.setIcon(new ImageIcon(this.getEnableIcon()));
		}
	}
	
	// Attributes Set and Get
	public Image getDefaultIcon() {
		return defaultIcon;
	}

	public void setDefaultIcon(Image defaultIcon) {
		this.defaultIcon = defaultIcon;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean isTurnOn() {
		return isTurnOn;
	}

	public void setTurnOn(boolean isTurnOn) {
		this.isTurnOn = isTurnOn;
	}

	public LVSOverLayerPanel getOverLayerPanel() {
		return overLayerPanel;
	}

	public void setOverLayerPanel(LVSOverLayerPanel overLayerPanel) {
		this.overLayerPanel = overLayerPanel;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Image getHoverIcon() {
		if (hoverIcon == null)
			return defaultIcon;
		return hoverIcon;
	}

	public void setHoverIcon(Image hoverIcon) {
		this.hoverIcon = hoverIcon;
	}

	public Image getClickIcon() {
		if (clickIcon == null)
			return defaultIcon;
		return clickIcon;
	}

	public void setClickIcon(Image clickIcon) {
		this.clickIcon = clickIcon;
	}

	public Image getEnableIcon() {
		if (enableIcon == null)
			return defaultIcon;
		return enableIcon;
	}

	public void setEnableIcon(Image enableIcon) {
		this.enableIcon = enableIcon;
	}

}

package ui.awt.dialog.edit.tracking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import system.controller.SwingController;
import system.model.LVSPanel;
import ui.awt.res.LVSSwingInfo;
import ui.awt.res.SwingTextureManager;

/** ************************************************************
 * This class extends LVSPanel, graphically showing movement 
 * tracking information. Component including fingers and palm
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSTrackingEditPanel extends LVSPanel {
	public static final String COMMAND_RPALM = "RightPalm";
	public static final String COMMAND_LPALM = "LeftPalm";
	public static final String COMMAND_RP = "RightPinky";
	public static final String COMMAND_RR = "RightRing";
	public static final String COMMAND_RM = "RightMiddle";
	public static final String COMMAND_RI = "RightIndex";
	public static final String COMMAND_RT = "RightThumb";
	public static final String COMMAND_LP = "LeftPinky";
	public static final String COMMAND_LR = "LeftRing";
	public static final String COMMAND_LM = "LeftMiddle";
	public static final String COMMAND_LI = "LeftIndex";
	public static final String COMMAND_LT = "LeftThumb";
	private LVSTrackingEditDialog editDialog;
	private TrackingModel trackingModel;
	private final Color points_color = new Color(150,150,150);
	private final Color disable_color = new Color(150,150,150);
	private Color finger_color;
	private Color palm_color;
	private final int finger_offset = 20;
	private int selectable_size;
	private int points_size;
	private int panel_w, panel_h;
	private int ltsx,ltex,lisx,liex,lmsx,lmex,lrsx,lrex,lpsx,lpex,lpalmx,lpalmex,rtsx,rtex,risx,riex,rmsx,rmex,rrsx,rrex,rpsx,rpex,rpalmx,rpalmex;
	private int ltsy,ltey,lisy,liey,lmsy,lmey,lrsy,lrey,lpsy,lpey,lpalmy,lpalmey,rtsy,rtey,risy,riey,rmsy,rmey,rrsy,rrey,rpsy,rpey,rpalmy,rpalmey;
	private JButton lt,li,lm,lr,lp,rt,ri,rm,rr,rp,lpalm,rpalm;

	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSTrackingEditPanel(SwingController swingController, LVSTrackingEditDialog editDialog, JFrame mainFrame, TrackingModel trackingModel) {
		super(swingController, mainFrame);
		this.setEditDialog(editDialog);
		this.trackingModel = trackingModel;
		
		lt = new JButton();
		li = new JButton();
		lm = new JButton();
		lr = new JButton();
		lp = new JButton();
		rt = new JButton();
		ri = new JButton();
		rm = new JButton();
		rr = new JButton();
		rp = new JButton();
		lpalm = new JButton();
		rpalm = new JButton();
	}

	@Override
	public void initPanel() {
		this.setLayout(null);
		panel_h = editDialog.getHeight();
		panel_w = (int)(editDialog.getWidth() * LVSSwingInfo.EDIT_TRACKING_PANEL_RATIO);
		this.setSize(new Dimension(panel_w, panel_h));
		this.setPreferredSize(new Dimension(panel_w, panel_h));
		this.setSize(new Dimension(panel_w, panel_h));

		int x_offset = panel_w / 10;
		selectable_size = (int)(x_offset * 0.7);
		points_size = (int)(x_offset * 0.4);
		int start_x = panel_w / 20;
		int finger_y = panel_h / 5;
		int finger_y2 = panel_h / 3; 
		int palm_y = panel_h / 2;
		int palm_y2 = panel_h * 13 / 20;
		int palm_y3 = panel_h * 4 / 5;
		// Left
		lpsx = start_x;
		lpex = lpalmex = x_offset;
		lrsx = lrex = start_x + x_offset;
		lmsx = lmex = lpalmx = start_x + (x_offset * 2); 
		lisx = liex = ltex = start_x + (x_offset * 3);
		ltsx = start_x + (x_offset * 4);
		// Right
		rpsx = start_x + (x_offset * 9);
		rpex = rpalmex = x_offset * 9;
		rrsx = rrex = start_x + (x_offset * 8);
		rmsx = rmex = rpalmx = start_x + (x_offset * 7);
		risx = riex = rtex = start_x + (x_offset * 6);
		rtsx = start_x + (x_offset * 5);
		// Common
		lmsy = rmsy = finger_y - finger_offset;
		lrsy = lisy = rrsy = risy = finger_y;
		lpsy = rpsy = finger_y2;
		ltsy = rtsy = palm_y - finger_offset;
		lpey = lrey = lmey = liey = rpey = rrey = rmey = riey = palm_y;
		lpalmy = rpalmy = palm_y2;
		ltey = lpalmey = rtey = rpalmey = palm_y3;
		
		// Set default color
		if (super.getSwingController().getSystemPreference().getFinger_color() != null)
			finger_color = super.getSwingController().getSystemPreference().getFinger_color();
		else
			finger_color = Color.GREEN;
		if (super.getSwingController().getSystemPreference().getPalm_color() != null)
			palm_color = super.getSwingController().getSystemPreference().getPalm_color();
		else
			palm_color = Color.BLUE;
		
		// Set Button
		int offset = selectable_size / 2;
		// Left
		formatButton(lt);
		lt.setActionCommand(LVSTrackingEditPanel.COMMAND_LT);
		lt.setLocation(ltsx - offset, ltsy - offset);
		formatButton(li);
		li.setActionCommand(LVSTrackingEditPanel.COMMAND_LI);
		li.setLocation(lisx - offset, lisy - offset);
		formatButton(lm);
		lm.setActionCommand(LVSTrackingEditPanel.COMMAND_LM);
		lm.setLocation(lmsx - offset, lmsy - offset);
		formatButton(lr);
		lr.setActionCommand(LVSTrackingEditPanel.COMMAND_LR);
		lr.setLocation(lrsx - offset, lrsy - offset);
		formatButton(lp);
		lp.setActionCommand(LVSTrackingEditPanel.COMMAND_LP);
		lp.setLocation(lpsx - offset, lpsy - offset);
		// Right
		formatButton(rt);
		rt.setActionCommand(LVSTrackingEditPanel.COMMAND_RT);
		rt.setLocation(rtsx - offset, rtsy - offset);
		formatButton(ri);
		ri.setActionCommand(LVSTrackingEditPanel.COMMAND_RI);
		ri.setLocation(risx - offset, risy - offset);
		formatButton(rm);
		rm.setActionCommand(LVSTrackingEditPanel.COMMAND_RM);
		rm.setLocation(rmsx - offset, rmsy - offset);
		formatButton(rr);
		rr.setActionCommand(LVSTrackingEditPanel.COMMAND_RR);
		rr.setLocation(rrsx - offset, rrsy - offset);
		formatButton(rp);
		rp.setActionCommand(LVSTrackingEditPanel.COMMAND_RP);
		rp.setLocation(rpsx - offset, rpsy - offset);
		
		//palm
		formatButton(lpalm);
		lpalm.setActionCommand(LVSTrackingEditPanel.COMMAND_LPALM);
		lpalm.setLocation(lpalmx - offset, lpalmy - offset);
		formatButton(rpalm);
		rpalm.setActionCommand(LVSTrackingEditPanel.COMMAND_RPALM);
		rpalm.setLocation(rpalmx - offset, rpalmy - offset);
		
		this.add(lt);
		this.add(li);
		this.add(lm);
		this.add(lr);
		this.add(lp);
		this.add(rt);
		this.add(ri);
		this.add(rm);
		this.add(rr);
		this.add(rp);
		this.add(lpalm);
		this.add(rpalm);
	}
	
	/**
	 * Format target Button
	 * @param button Target Button
	 */
	public void formatButton(JButton button){
		button.setSize(selectable_size, selectable_size);
		button.addActionListener(editDialog.getListener());
		Image icon = SwingTextureManager.GREY_CIRCLE_IMAGE;
		icon = icon.getScaledInstance(selectable_size, selectable_size,  java.awt.Image.SCALE_SMOOTH );
		button.setIcon(new ImageIcon(icon));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
	}
	
	@Override
	public void paint(Graphics g){		
		this.paintComponents(g);
		Graphics2D g2d = (Graphics2D)g;
		
		int offset = selectable_size / 2;
		// draw fingers	
		// Left
		g2d.setColor(getColor(trackingModel.isShowLeftPinky(), 0));
		g2d.fillOval(lpsx - offset, lpsy - offset, selectable_size, selectable_size);
		g2d.drawLine(lpsx, lpsy, lpex, lpey);
		g2d.setColor(getColor(trackingModel.isShowLeftRing(), 0));
		g2d.fillOval(lrsx - offset, lrsy - offset, selectable_size, selectable_size);
		g2d.drawLine(lrsx, lrsy, lrex, lrey);
		g2d.setColor(getColor(trackingModel.isShowLeftMiddle(), 0));
		g2d.fillOval(lmsx - offset, lmsy - offset, selectable_size, selectable_size);
		g2d.drawLine(lmsx, lmsy, lmex, lmey);
		g2d.setColor(getColor(trackingModel.isShowLeftIndex(), 0));
		g2d.fillOval(lisx - offset, lisy - offset, selectable_size, selectable_size);
		g2d.drawLine(lisx, lisy, liex, liey);
		g2d.setColor(getColor(trackingModel.isShowLeftThumb(), 0));
		g2d.fillOval(ltsx - offset, ltsy - offset, selectable_size, selectable_size);
		g2d.drawLine(ltsx, ltsy, ltex, ltey);
		// Right
		g2d.setColor(getColor(trackingModel.isShowRightThumb(), 0));
		g2d.fillOval(rtsx - offset, rtsy - offset, selectable_size, selectable_size);
		g2d.drawLine(rtsx, rtsy, rtex, rtey);
		g2d.setColor(getColor(trackingModel.isShowRightIndex(), 0));
		g2d.fillOval(risx - offset, risy - offset, selectable_size, selectable_size);
		g2d.drawLine(risx, risy, riex, riey);
		g2d.setColor(getColor(trackingModel.isShowRightMiddle(), 0));
		g2d.fillOval(rmsx - offset, rmsy - offset, selectable_size, selectable_size);
		g2d.drawLine(rmsx, rmsy, rmex, rmey);
		g2d.setColor(getColor(trackingModel.isShowRightRing(), 0));
		g2d.fillOval(rrsx - offset, rrsy - offset, selectable_size, selectable_size);
		g2d.drawLine(rrsx, rrsy, rrex, rrey);
		g2d.setColor(getColor(trackingModel.isShowRightPinky(), 0));
		g2d.fillOval(rpsx - offset, rpsy - offset, selectable_size, selectable_size);
		g2d.drawLine(rpsx, rpsy, rpex, rpey);

		// draw palm center
		g2d.setColor(getColor(trackingModel.isShowLeftPalm(), 1));
		g2d.fillOval(lpalmx - offset, lpalmy - offset, selectable_size, selectable_size);
		g2d.setColor(getColor(trackingModel.isShowRightPalm(), 1));
		g2d.fillOval(rpalmx - offset, rpalmy - offset, selectable_size, selectable_size);
		
		// draw palm
		offset = (points_size / 2);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(lpex, lpey, ltex - lpex, ltey - lpey);
		g2d.drawRect(riex, riey, rpalmex - riex, rpalmey - riey);
		g2d.setColor(points_color);
		g2d.fillOval(lpex - offset, lpey - offset, points_size, points_size);
		g2d.fillOval(liex - offset, liey - offset, points_size, points_size);
		g2d.fillOval(lpalmex - offset, lpalmey - offset, points_size, points_size);
		g2d.fillOval(ltex - offset, ltey - offset, points_size, points_size);
		g2d.fillOval(rpex - offset, rpey - offset, points_size, points_size);
		g2d.fillOval(riex - offset, riey - offset, points_size, points_size);
		g2d.fillOval(rpalmex - offset, rpalmey - offset, points_size, points_size);
		g2d.fillOval(rtex - offset, rtey - offset, points_size, points_size);

	}
	
	/**
	 * Return the color for different type of points
	 * @param showing State is enable or not
	 * @param option
	 * @return
	 */
	public Color getColor(boolean showing, int option){
		if (showing && option == 0){
			return finger_color;
		}else if (showing && option == 1){
			return palm_color;
		}else{
			return disable_color;
		}
	}
	
	public void changeLT(){
		if (trackingModel.isShowLeftThumb())
			trackingModel.setShowLeftThumb(false);
		else
			trackingModel.setShowLeftThumb(true);
	}
	
	public void changeLI(){
		if (trackingModel.isShowLeftIndex())
			trackingModel.setShowLeftIndex(false);
		else
			trackingModel.setShowLeftIndex(true);
	}
	
	public void changeLM(){
		if (trackingModel.isShowLeftMiddle())
			trackingModel.setShowLeftMiddle(false);
		else
			trackingModel.setShowLeftMiddle(true);
	}
	
	public void changeLR(){
		if (trackingModel.isShowLeftRing())
			trackingModel.setShowLeftRing(false);
		else
			trackingModel.setShowLeftRing(true);
	}
	
	public void changeLP(){
		if (trackingModel.isShowLeftPinky())
			trackingModel.setShowLeftPinky(false);
		else
			trackingModel.setShowLeftPinky(true);
	}
	
	public void changeRT(){
		if (trackingModel.isShowRightThumb())
			trackingModel.setShowRightThumb(false);
		else
			trackingModel.setShowRightThumb(true);
	}
	
	public void changeRI(){
		if (trackingModel.isShowRightIndex())
			trackingModel.setShowRightIndex(false);
		else
			trackingModel.setShowRightIndex(true);
	}
	
	public void changeRM(){
		if (trackingModel.isShowRightMiddle())
			trackingModel.setShowRightMiddle(false);
		else
			trackingModel.setShowRightMiddle(true);
	}
	
	public void changeRR(){
		if (trackingModel.isShowRightRing())
			trackingModel.setShowRightRing(false);
		else
			trackingModel.setShowRightRing(true);
	}
	
	public void changeRP(){
		if (trackingModel.isShowRightPinky())
			trackingModel.setShowRightPinky(false);
		else
			trackingModel.setShowRightPinky(true);
	}
	
	public void changeLPALM(){
		if (trackingModel.isShowLeftPalm())
			trackingModel.setShowLeftPalm(false);
		else
			trackingModel.setShowLeftPalm(true);
	}
	
	public void changeRPALM(){
		if (trackingModel.isShowRightPalm())
			trackingModel.setShowRightPalm(false);
		else
			trackingModel.setShowRightPalm(true);
	}
	
	public TrackingModel getTrackingModel() {
		return trackingModel;
	}

	public void setTrackingModel(TrackingModel trackingModel) {
		this.trackingModel = trackingModel;
	}

	public LVSTrackingEditDialog getEditDialog() {
		return editDialog;
	}

	public void setEditDialog(LVSTrackingEditDialog editDialog) {
		this.editDialog = editDialog;
	}

	public void setFinger_color(Color finger_color) {
		this.finger_color = finger_color;
	}

	public void setPalm_color(Color palm_color) {
		this.palm_color = palm_color;
	}
}

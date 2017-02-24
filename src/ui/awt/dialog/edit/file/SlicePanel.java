package ui.awt.dialog.edit.file;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import layout.TableLayout;

import org.jdesktop.xswingx.PromptSupport;

import system.bugreport.ExceptionCatcher;
import system.file.FileItem;
import system.model.EditItemFilterPanel;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;

/** *********************************************************** 
 * This is an object class which contain all swing component
 * of configuring VTK slice.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class SlicePanel extends EditItemFilterPanel {
	private JTextField xTF;
	private JTextField yTF;
	private JTextField zTF;

	/**
	 * Constructor
	 * @param item FileItem currently showing
	 * @param itemEditPanel Edit Panel
	 */
	public SlicePanel(FileItem item, LVSFileItemEditDialog itemEditPanel) {
		super(item, itemEditPanel);
	}

	@Override
	public void initPanel() {
		int tw = LVSSwingInfo.TABLE_LAYOUT_GRID_W;
		int th = LVSSwingInfo.TABLE_LAYOUT_GRID_H;
		double[][] size = {{tw,tw,tw,TableLayout.FILL}, {th,th}};
		double[] normal = getItem().getSlice().getNormalValue();
		String x = "0";
		String y = "0";
		String z = "0";
		if(normal != null){
			x = "" + normal[0];
			y = "" + normal[1];
			z = "" + normal[2];
		}
		this.setLayout(new TableLayout(size));
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		JLabel normalLabel = new JLabel(LVSStringInfo.EDIT_PANEL_NORMAL_LABEL);
		xTF = new JTextField(x);
		xTF.setToolTipText(LVSStringInfo.EDIT_PANEL_X_TOOLTIPS);
		PromptSupport.setPrompt(LVSStringInfo.EDIT_PANEL_X_TOOLTIPS , xTF);
		yTF = new JTextField(y);
		yTF.setToolTipText(LVSStringInfo.EDIT_PANEL_Y_TOOLTIPS);
		PromptSupport.setPrompt(LVSStringInfo.EDIT_PANEL_Y_TOOLTIPS , yTF);
		zTF = new JTextField(z);
		zTF.setToolTipText(LVSStringInfo.EDIT_PANEL_Z_TOOLTIPS);
		PromptSupport.setPrompt(LVSStringInfo.EDIT_PANEL_Z_TOOLTIPS , zTF);
		this.add(normalLabel, "0,0,2,0");
		this.add(xTF, "0,1");
		this.add(yTF, "1,1");
		this.add(zTF, "2,1");
	}

	@Override
	public void saveChange() {
		String xs = xTF.getText();
		String ys = yTF.getText();
		String zs = zTF.getText();
		double x = 0.0;
		double y = 0.0;
		double z = 0.0;
		boolean hasError = false;
		try{
			x = Double.parseDouble(checkString(xs)?xs:"0");
			y = Double.parseDouble(checkString(ys)?ys:"0");
			z = Double.parseDouble(checkString(zs)?zs:"0");
		} catch (NumberFormatException e){
			hasError = true;
			super.showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_DOUBLE);
			ExceptionCatcher.logException(e);
		}
		if (!hasError){
			double[] normalValue = {x,y,z};
			super.getItem().getSlice().setNormalValue(normalValue);
			savedNotifyMsg();
		}
	}

}

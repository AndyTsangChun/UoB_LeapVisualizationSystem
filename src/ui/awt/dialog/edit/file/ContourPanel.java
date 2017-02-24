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
 * of configuring VTK contour.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class ContourPanel extends EditItemFilterPanel {
	private JTextField actualValueField;
	private JTextField stepTF;
	
	/**
	 * Constructor
	 * @param item FileItem currently showing
	 * @param itemEditPanel Edit Panel
	 */
	public ContourPanel(FileItem item, LVSFileItemEditDialog itemEditPanel) {
		super(item, itemEditPanel);
	}

	@Override
	public void initPanel() {
		int tw = LVSSwingInfo.TABLE_LAYOUT_GRID_W;
		int th = LVSSwingInfo.TABLE_LAYOUT_GRID_H;
		double[][] size = {{tw,tw,TableLayout.FILL}, {th,th,th,th,th,th,th}};
		double[] actualValue = getItem().getContour().getActualValue();
		int step = getItem().getContour().getStep();
		String value = super.ArrayToString(actualValue);
		this.setLayout(new TableLayout(size));
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		this.addDefaultRangeToPanel(this, LVSFileItemEditDialog.COMMAND_CONTOUR);
		
		JLabel stepLabel = new JLabel(LVSStringInfo.EDIT_PANEL_STEP_VALUE_LABEL);
		JLabel hints = new JLabel(LVSStringInfo.HINTS_CONTOUR_STEP);
		hints.setForeground(Color.RED);
		hints.setFont(hints.getFont().deriveFont (10.0f));
		stepTF = new JTextField(step+"");
		PromptSupport.setPrompt(LVSStringInfo.EDIT_PANEL_STEP_TOOLTIPS, stepTF);
		stepTF.setToolTipText(LVSStringInfo.EDIT_PANEL_STEP_TOOLTIPS);
		
		JLabel valueLabel = new JLabel(LVSStringInfo.EDIT_PANEL_ACTUAL_VALUE_LABEL);
		JLabel hints2 = new JLabel(LVSStringInfo.HINTS_CONTOUR_ACTUAL_VALUE);
		hints2.setForeground(Color.RED);
		hints2.setFont(hints.getFont().deriveFont (10.0f));
		actualValueField = new JTextField(value);
		PromptSupport.setPrompt(LVSStringInfo.EDIT_PANEL_ACTUAL_VALUE_TOOLTIPS , actualValueField);
		actualValueField.setToolTipText(LVSStringInfo.EDIT_PANEL_ACTUAL_VALUE_TOOLTIPS);
		
		this.add(stepLabel, "0,4");
		this.add(stepTF, "1,4");
		this.add(hints, "2,4");
		this.add(valueLabel, "0,5,1,5");
		this.add(actualValueField, "0,6,1,6");
		this.add(hints2, "2,6");
	}

	@Override
	public void saveChange() {
		double[] actualValues = stringToArray(actualValueField.getText());
		double[] customRange = super.getCustomRangeFromUI();
		String stepS = stepTF.getText();
		if (actualValues != null){
			actualValues = super.sortArray(actualValues);
			if (super.checkRange(actualValues)){
				super.getItem().getContour().setActualValue(actualValues);
				super.getItem().getContour().setCustomRange(null);
				super.getItem().getContour().setStep(0);
				super.getItemEditPanel().getSwingController().openFileEditDialog(super.getItem().getId(), LVSFileItemEditDialog.COMMAND_CONTOUR);
				savedNotifyMsg();
			}else{
				super.showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_OUT_OF_RANGE);
			}
		}else if (customRange != null && checkString(stepS)){
			if (Integer.parseInt(stepS) <= 1){
				super.showMsg(2, LVSStringInfo.ERROR_WRONG_STEP);
			}
			if (checkRange(customRange)){
				try{
					int step = Integer.parseInt(stepS);
					super.getItem().getContour().setCustomRange(customRange);
					super.getItem().getContour().setStep(step);
					super.getItem().getContour().generateRange();
					super.getItemEditPanel().getSwingController().openFileEditDialog(super.getItem().getId(), LVSFileItemEditDialog.COMMAND_CONTOUR);
					savedNotifyMsg();
				} catch (NumberFormatException e){
					super.showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_INT);
					ExceptionCatcher.logException(e);
				}
			}else{
				super.showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_OUT_OF_RANGE);
			}
		}
	}
	
	/**
	 * Convert the string to double array 
	 * @param string String want to convert {1,2,3}
	 * @return array Result array
	 */
	public double[] stringToArray(String string){
		string = string.trim();
		if (string.equals("") || string.length() <= 2){
			return null;
		}
		string = string.substring(1, string.length() - 1);
		String[] tmp = string.split(",");
		tmp = checkArrayHasEmptyCell(tmp);
		if (tmp.length <= 0){
			return null;
		}
		double[] array = new double[tmp.length];
		boolean hasError = false;
		try{
			for (int i = 0 ; i < tmp.length ; i++){
				array[i] = Double.parseDouble(tmp[i]);
			}			
		} catch (NumberFormatException e){
			hasError = true;
			super.showMsg(2, LVSStringInfo.ERROR_WRONG_FORMAT_ACTUAL_VALUE);
			ExceptionCatcher.logException(e);
		}
		if (hasError){
			array = null;
		}
		
		return array;
	}

}

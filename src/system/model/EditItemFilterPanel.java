package system.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.xswingx.PromptSupport;

import system.bugreport.ExceptionCatcher;
import system.file.FileItem;
import system.util.QuickSort;
import ui.awt.dialog.edit.file.LVSFileItemEditDialog;
import ui.awt.res.LVSStringInfo;

/** *********************************************************** 
 * This is an abstract class of file item edit panel. Each 
 * implementation should refer to one VTK algorithm.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public abstract class EditItemFilterPanel extends JPanel {
	private LVSFileItemEditDialog itemEditPanel;
	private FileItem item;
	private JTextField minRangeTF;
	private JTextField maxRangeTF;
	
	/**
	 * Constructor
	 * @param item FileItem currently showing
	 * @param itemEditPanel Edit Panel
	 */
	public EditItemFilterPanel(FileItem item, LVSFileItemEditDialog itemEditPanel) {
		this.item = item;
		this.itemEditPanel = itemEditPanel;
	}

	/**
	 * Abstract method to initialize panel
	 */
	public abstract void initPanel();
	
	/**
	 * Abstract method to save changes
	 */
	public abstract void saveChange();
	
	/**
	 * Function call to add default range and custom range UI component to target panel 
	 * @param panel Target panel
	 * @param filterOption Filter option of VTK algorithm trying to modify (e.g Threshold, Contour)
	 */
	public void addDefaultRangeToPanel(JPanel panel, String filterOption){
		String tips;
		String customMin = "";
		String customMax = "";
		double[] customRange = null;
		if (filterOption.equals(LVSFileItemEditDialog.COMMAND_THRESHOLD)){
			customRange = item.getThreshold().getCustomRange();
		}else if (filterOption.equals(LVSFileItemEditDialog.COMMAND_CONTOUR)){
			customRange = item.getContour().getCustomRange();
		}
		if (customRange != null){
			customMin = ""+customRange[0];
			customMax = ""+customRange[1];
		}
		JLabel defaultRangeLabel = new JLabel(String.format(LVSStringInfo.EDIT_PANEL_DEFAULT_RANGE_LABEL, item.getDefaultRange()[0], item.getDefaultRange()[1]));
		JLabel customRangeLabel = new JLabel(LVSStringInfo.EDIT_PANEL_CUSTOM_RANGE_LABEL);
		JLabel minRangeLabel = new JLabel(LVSStringInfo.EDIT_PANEL_MINIMUM_LABEL);
		minRangeTF = new JTextField(customMin);
		tips = String.format(LVSStringInfo.EDIT_PANEL_MINIMUM_TOOLTIPS, item.getDefaultRange()[0]);
		PromptSupport.setPrompt(tips, minRangeTF);
		minRangeTF.setSize(100, 30);
		minRangeTF.setToolTipText(tips);
		JLabel maxRangeLabel = new JLabel(LVSStringInfo.EDIT_PANEL_MAXIMUM_LABEL);
		maxRangeTF = new JTextField(customMax);
		tips = String.format(LVSStringInfo.EDIT_PANEL_MAXIMUM_TOOLTIPS, item.getDefaultRange()[1]);
		PromptSupport.setPrompt(tips, maxRangeTF);
		maxRangeTF.setSize(100, 30);
		maxRangeTF.setToolTipText(tips);
		panel.add(defaultRangeLabel, "0,0,2,0");
		panel.add(customRangeLabel, "0,1,1,1");
		panel.add(minRangeLabel, "0,2");
		panel.add(minRangeTF, "1,2");
		panel.add(maxRangeLabel, "0,3");
		panel.add(maxRangeTF, "1,3");
	}
	
	/**
	 * Changing a double array to string (e.g. {1,2,3})
	 * @param array Input Double Array
	 * @return String 
	 */
	public String ArrayToString(double[] array){
		String s = "";
		if (array != null){
			s = "{";
			for (int i = 0 ; i < array.length ; i++){
				s += array[i];
				if (i < array.length - 1){
					s += ",";
				}
			}
			s += "}";
		}
		return s;
	}
	
	/**
	 * Getting user input from UI component
	 * @return customRange Double array
	 */
	public double[] getCustomRangeFromUI(){
		double[] customRange = new double[2];
		String minS = minRangeTF.getText();
		String maxS = maxRangeTF.getText();
		if (checkString(minS) && checkString(maxS)){
			try{
				customRange[0] = Double.parseDouble(minS);
				customRange[1] = Double.parseDouble(maxS);
			} catch (NumberFormatException e){
				showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_DOUBLE);
				ExceptionCatcher.logException(e);
				return null;
			}
		}
		return customRange;
	}
	
	/**
	 * Remove empty cell in the array. (e.g. {1,,3} -> {1,3})
	 * @param array Input String array
	 * @return array Modified String array
	 */
	public String[] checkArrayHasEmptyCell(String[] array){
		List<String> tmp = new ArrayList<String>();
		for (int i = 0 ; i < array.length ; i++){
			if (!array[i].equals("")){
				tmp.add(array[i]);
			}
		}
		array = new String[tmp.size()];
		for (int i = 0 ; i < tmp.size() ; i++){
			array[i] = tmp.get(i);
		}
		
		return array;
	}
	
	/**
	 * Check the value in the double array is within default range or not.
	 * @param array Array want to check
	 * @return valid State whether the value in the array is within default range
	 */
	public boolean checkRange(double[] array){
		double low = item.getDefaultRange()[0];
		double high = item.getDefaultRange()[1];
		boolean valid = true;
		for (int i = 0 ; i < array.length ; i++){
			if (array[i] < low || array[i] > high){
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	/**
	 * Check the string is empty or not
	 * @param s String want to check
	 * @return valid State whether the string is empty or not
	 */
	public boolean checkString(String s){
		boolean valid = true;
		if (s.equals("") || s == null){
			valid = false;
		}
		return valid;
	}
	
	/**
	 * Use Quick sort to sort the target array
	 * @param array Array wish to sort
	 * @return result Sorted array
	 */
	public double[] sortArray(double[] array){
		QuickSort sort = new QuickSort();
		double[] result = sort.sort(array);
		
		return result;
	}
	
	/**
	 * Show notification after saving changes
	 */
	public void savedNotifyMsg(){
		String message = LVSStringInfo.NOTIFY_SAVED;
		showMsg(4, message);
		getItemEditPanel().getSwingController().getSystemController().updateSystemStatus(message);
	}
	
	/**
	 * Show message 
	 * @param option Message type
	 * @param msg Content of the message
	 */
	public void showMsg(int option, String msg){
		getItemEditPanel().getSwingController().changeEditDialogOnTop(false);
		getItemEditPanel().getSwingController().getSystemController().getMessageFactory().showMessageDialog(option, msg, null);
		getItemEditPanel().getSwingController().changeEditDialogOnTop(true);
	}
	
	// Attributes Set and Get
	public FileItem getItem(){
		return item;
	}

	public JTextField getMinRangeTF() {
		return minRangeTF;
	}

	public JTextField getMaxRangeTF() {
		return maxRangeTF;
	}

	public LVSFileItemEditDialog getItemEditPanel() {
		return itemEditPanel;
	}
}

package ui.awt.dialog.edit.file;

import java.awt.Color;

import javax.swing.BorderFactory;

import layout.TableLayout;
import system.bugreport.ExceptionCatcher;
import system.file.FileItem;
import system.model.EditItemFilterPanel;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;

/** *********************************************************** 
 * This is an object class which contain all swing component
 * of configuring VTK threshold.
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class ThresholdPanel extends EditItemFilterPanel {
	/**
	 * Constructor
	 * @param item FileItem currently showing
	 * @param itemEditPanel Edit Panel
	 */
	public ThresholdPanel(FileItem item, LVSFileItemEditDialog itemEditPanel) {
		super(item, itemEditPanel);
	}

	@Override
	public void initPanel() {
		int tw = LVSSwingInfo.TABLE_LAYOUT_GRID_W;
		int th = LVSSwingInfo.TABLE_LAYOUT_GRID_H;
		double[][] size = {{tw,tw,TableLayout.FILL}, {th,th,th,th}};
		this.setLayout(new TableLayout(size));
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		this.addDefaultRangeToPanel(this, LVSFileItemEditDialog.COMMAND_THRESHOLD);
	}

	@Override
	public void saveChange() {
		double[] customRange = super.getCustomRangeFromUI();
		if (customRange != null){
			if (checkRange(customRange)){
				try{
					super.getItem().getThreshold().setCustomRange(customRange);
					super.savedNotifyMsg();
				} catch (NumberFormatException e){
					super.showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_INT);
					ExceptionCatcher.logException(e);
				}
			}else{
				super.showMsg(2, LVSStringInfo.ERROR_WRONG_VALUE_OUT_OF_RANGE);
			}
		}
	}
}

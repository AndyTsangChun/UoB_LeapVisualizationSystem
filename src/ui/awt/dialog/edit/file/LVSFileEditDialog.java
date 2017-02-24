package ui.awt.dialog.edit.file;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import system.controller.SwingController;
import system.file.FileItem;
import system.file.LVSFile;
import system.model.LVSPanel;
import ui.awt.component.ButtonColumn;
import ui.awt.listener.EditFileDialogListener;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;

/** ************************************************************
 * This class extends LVSPanel, a file edit panel which allows
 * user to edit imported file information.
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSFileEditDialog extends LVSPanel{
	private static final String[] columnNames = {"ID", "Name", "Path", "Edit", "X"};
	private LVSFile file;
	private JTable table;
	private JPanel top;
	private JPanel bottom;
	private JButton cancel;
	private JButton delete;
	private JButton apply;
	private EditFileDialogListener editFilePanelListener;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 * @param file	LVSFile system currently working with
	 */
	public LVSFileEditDialog(SwingController swingController, JFrame mainFrame, LVSFile file) {
		super(swingController, mainFrame);
		this.file = file;
		editFilePanelListener = new EditFileDialogListener(this);
		
		top = new JPanel();
		top.setLayout(new BorderLayout());
		bottom = new JPanel();

		apply = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_APPLY);
		delete = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_DELETE);
		cancel = new JButton(LVSStringInfo.EDIT_PANEL_BUTTON_CANCEL);
	}

	@Override
	public void initPanel() {
		this.setLayout(new BorderLayout());
		int w = super.getSwingController().getSystemPreference().getScreenWidth();
		int h = super.getSwingController().getSystemPreference().getScreenHeight();
		int panel_h = (int)(h * LVSSwingInfo.EDIT_FILE_DIALOG_H_RATIO);
		int panel_w = (int)(w * LVSSwingInfo.EDIT_FILE_DIALOG_W_RATIO);
		this.setPreferredSize(new Dimension(panel_w, panel_h));
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottom.setPreferredSize(new Dimension(panel_w, LVSSwingInfo.EDIT_PANEL_BOT_H));
		// init top panel
		
		table = null;
		if (file != null){
			model = loadModel(file);
			table = new JTable(model){
	            @Override
	            public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return Integer.class;
	                    case 1:
	                        return String.class;
	                    case 2:
	                        return String.class;
	                    case 3:
	                        return JButton.class;
	                    default:
	                        return Boolean.class;
	                }
	            }
	        };
	        setupTable(table);
			scrollPane = new JScrollPane(table);
	        top.add(scrollPane, BorderLayout.CENTER);
		}
		// init bottom panel
		cancel.setActionCommand(LVSPanel.COMMAND_CANCEL);
		cancel.addActionListener(editFilePanelListener);
		delete.setActionCommand(LVSPanel.COMMAND_DELETE);
		delete.addActionListener(editFilePanelListener);
		apply.setActionCommand(LVSPanel.COMMAND_APPLY);
		apply.addActionListener(editFilePanelListener);
		bottom.add(cancel);
		bottom.add(delete);
		bottom.add(apply);
		
		this.add(top, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}
	
	/**
	 * Load all the FileItem from the list to table model
	 * @param file	LVSFile system currently working with
	 * @return model DefaultTableModel
	 */
	public DefaultTableModel loadModel(LVSFile file){
		List<FileItem> list = file.getFileList();
		Object[][] data = null;
		data = new Object[list.size()][5];
		if (list.size() > 0){
			for (int i = 0 ; i < list.size() ; i++){
				data[i][0] = list.get(i).getId();
				data[i][1] = list.get(i).getName();
				data[i][2] = list.get(i).getPath();
				data[i][3] = "Edit";
				data[i][4] = false;
			}
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		
		return model;
	}
	
	/**
	 * Delete the selected object
	 */
	public void deleteObj(){
		for (int i = 0 ; i < table.getRowCount() ; i++){
			boolean isDelete = (Boolean) table.getValueAt(i, 4);
			if (isDelete){
				int id = (Integer) table.getValueAt(i, 0);
				file.deleteFile(id);
				model.fireTableRowsDeleted(i, i);
			}
		}
		model = loadModel(file);
		table.setModel(model);
		model.fireTableDataChanged();
		setupTable(table);
		this.validate();
		this.repaint();
		super.getSwingController().updateFileTree();
		super.getSwingController().updateVTK();
		super.getSwingController().updateToolBar();
	}
	
	/**
	 * Set up the table 
	 * @param table The table displaying
	 */
	public void setupTable(JTable table){
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		Action edit = new AbstractAction(){
			public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int id = (Integer) table.getValueAt(Integer.valueOf(e.getActionCommand()), 0);
		        getSwingController().openFileEditDialog(id, LVSFileItemEditDialog.COMMAND_THRESHOLD);
		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, edit, 3);
        // Styling
        TableColumnModel columnModel = table.getColumnModel();
    	columnModel.getColumn(0).setMaxWidth(20);
    	columnModel.getColumn(3).setMaxWidth(50);
    	columnModel.getColumn(4).setMaxWidth(20);
	}

}

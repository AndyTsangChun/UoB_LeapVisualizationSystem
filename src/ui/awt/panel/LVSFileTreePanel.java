package ui.awt.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import system.controller.SwingController;
import system.file.FileItem;
import system.file.LVSFile;
import system.model.LVSPanel;
import ui.awt.component.tree.CheckBoxLeaf;
import ui.awt.component.tree.CheckBoxNode;
import ui.awt.component.tree.CheckBoxNodeRenderer;
import ui.awt.listener.FileTreeSelectionListener;
import ui.awt.res.LVSStringInfo;
import ui.awt.res.LVSSwingInfo;

/** ************************************************************
 * This class extends LVSPanel, a File Tree display all imported
 * file and selected algorithms. User can select or de-select 
 * the check box to show or hide the filtered dataSet. 
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class LVSFileTreePanel extends LVSPanel {
	public final int CONTOUR_OPTION = 0;
	public final int SLICE_OPTION = 1;
	public final int THRESHOLD_OPTION = 2;
	public final int SCALARBAR_OPTION = 3;
	private JTree tree;
	private FileTreeSelectionListener treeListener;
	private DefaultMutableTreeNode root;
	private JScrollPane treeView;
	
	/**
	 * Constructor
	 * @param swingController	Swing Controller
	 * @param mainFrame	LVS MainFrame
	 */
	public LVSFileTreePanel(SwingController swingController, JFrame mainFrame) {
		super(swingController, mainFrame);
		root = new DefaultMutableTreeNode("LVSFile");
		tree = new JTree(root);
		treeListener = new FileTreeSelectionListener(this, tree);
	}

	@Override
	public void initPanel() {
		this.setPreferredSize(new Dimension (LVSSwingInfo.FILE_TREE_W, super.getMainFrame().getHeight()));
		//this.setSize(new Dimension (LVSPanelInfo.FILE_TREE_W, super.getMainFrame().getHeight() - LVSPanelInfo.GESTURE_PANEL_H));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		tree.setRootVisible(false);
	    tree.addMouseListener(treeListener);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setBorder(new EmptyBorder(0, 0, 0, 0));
		treeView = new JScrollPane(tree);
		treeView.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.add(treeView, BorderLayout.CENTER);
		
		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer(this);
	    tree.setCellRenderer(renderer);
	}
	
	/**
	 * Update File Tree model and UI
	 */
	public void updateTree(){
		LVSFile file = super.getSwingController().getSystemController().getCurrentFile();
		List<FileItem> fileList = file.getFileList();
		root.removeAllChildren();
		for (int i = 0 ; i < fileList.size() ; i++){
			FileItem item = fileList.get(i);
			CheckBoxLeaf[] leaves = {
			        new CheckBoxLeaf(LVSStringInfo.TREE_CONTOUR, false, CONTOUR_OPTION, item),
			        new CheckBoxLeaf(LVSStringInfo.TREE_SLICE, false, SLICE_OPTION, item),
			        new CheckBoxLeaf(LVSStringInfo.TREE_THRESHOLD, false, THRESHOLD_OPTION, item),
			        new CheckBoxLeaf(LVSStringInfo.TREE_SCALAR_BAR, false, SCALARBAR_OPTION, item) };
			CheckBoxNode node = new CheckBoxNode(item.getName(), leaves, i);
			root.add(node);
		}
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		model.reload(root);
		tree.updateUI();
		tree.validate();
		tree.repaint();
	}
}

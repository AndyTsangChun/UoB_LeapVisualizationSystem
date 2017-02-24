package ui.awt.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import system.file.FileItem;
import ui.awt.component.tree.CheckBoxLeaf;
import ui.awt.panel.LVSFileTreePanel;

/** ************************************************************
 * This is a listener implementing awt.event.MouseAdapter
 * Handles all the action in file tree
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class FileTreeSelectionListener extends MouseAdapter{
	private LVSFileTreePanel treePanel;
	private JTree tree;

	/**
	 * Constructor
	 * @param treePanel The panel displaying the file tree
	 * @param tree The file tree
	 */
	public FileTreeSelectionListener(LVSFileTreePanel treePanel, JTree tree) {
		this.treePanel = treePanel;
		this.tree = tree;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
    	TreePath path = tree.getPathForLocation(me.getX(),me.getY());
    	if (path != null) {
            Object node = path.getLastPathComponent();
            if (node instanceof CheckBoxLeaf){
            	CheckBoxLeaf leaf = (CheckBoxLeaf)node;
    			if (leaf == null)
    				return;
    			// Change model to reflect to UI
    			if (leaf.isSelected()){
    				leaf.setSelected(false);
    			}else{
    				leaf.setSelected(true);
    			}
    			// Change item
    			FileItem fileItem = leaf.getFileItem();
    			if (leaf.getOption() == treePanel.CONTOUR_OPTION){
    				fileItem.getContour().setVisiable(leaf.isSelected());
    			}else if (leaf.getOption() == treePanel.SLICE_OPTION){
    				fileItem.getSlice().setVisiable(leaf.isSelected());
    			}else if (leaf.getOption() == treePanel.THRESHOLD_OPTION){
    				fileItem.getThreshold().setVisiable(leaf.isSelected());
    			}else if (leaf.getOption() == treePanel.SCALARBAR_OPTION){
    				fileItem.getScalarBar().setVisiable(leaf.isSelected());
    			}
    			tree.repaint();
    	        //treePanel.getSwingController().getSystemController().getCurrentFile().setShowingActor(fileItem.getId());
    	        treePanel.getSwingController().updateVTK();
            }
    	}
    }
}

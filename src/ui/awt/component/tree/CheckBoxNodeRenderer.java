package ui.awt.component.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import ui.awt.panel.LVSFileTreePanel;

/** ************************************************************ 
 * This class is the rendering of custom tree node
 * This class is modified from an example
 * http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm
 * 
 * @author Andy Tsang
 * @version 1.2
 * ***********************************************************/
public class CheckBoxNodeRenderer implements TreeCellRenderer {
	private LVSFileTreePanel treePanel;
	private JCheckBox leafRenderer = new JCheckBox();
	private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
	Color selectionBorderColor, selectionForeground, selectionBackground,
			textForeground, textBackground;

	/**
	 * Constructor
	 * @param treePanel	Panel displaying the tree
	 */
	public CheckBoxNodeRenderer(LVSFileTreePanel treePanel) {
		Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			leafRenderer.setFont(fontValue);
		}
		Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		this.treePanel = treePanel;
		leafRenderer.setFocusPainted((booleanValue != null) && (booleanValue.booleanValue()));

		selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}
	
	/**
	 * This function controls what you want to be rendered
	 * @return leafRenderer The object want to be rendered, it could be a checkBox, button or panel
	 */
	protected JCheckBox getLeafRenderer() {
		return leafRenderer;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row,
			boolean hasFocus) {
		Component returnValue;
		if (isLeaf) {
			if (selected) {
				leafRenderer.setForeground(selectionForeground);
				leafRenderer.setBackground(selectionBackground);
				leafRenderer.setSelected(true);
			} else {
				leafRenderer.setForeground(textForeground);
				leafRenderer.setBackground(textBackground);
				leafRenderer.setSelected(false);
			}
			leafRenderer.repaint();

			if (value instanceof CheckBoxLeaf) {
					CheckBoxLeaf leaf = (CheckBoxLeaf) value;
					leafRenderer.setText(leaf.getText());
					if (leaf.getOption() == treePanel.CONTOUR_OPTION){
						leafRenderer.setSelected(leaf.getFileItem().getContour().isVisiable());
					}else if (leaf.getOption() == treePanel.SLICE_OPTION){
						leafRenderer.setSelected(leaf.getFileItem().getSlice().isVisiable());
					}else if (leaf.getOption() == treePanel.THRESHOLD_OPTION){
						leafRenderer.setSelected(leaf.getFileItem().getThreshold().isVisiable());
					}else if (leaf.getOption() == treePanel.SCALARBAR_OPTION){
						leafRenderer.setSelected(leaf.getFileItem().getScalarBar().isVisiable());
					}
					leafRenderer.setEnabled(tree.isEnabled());
			}
			
			returnValue = leafRenderer;
		} else {
			returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree,
					value, selected, expanded, isLeaf, row, hasFocus);
		}
		return returnValue;
	}
}

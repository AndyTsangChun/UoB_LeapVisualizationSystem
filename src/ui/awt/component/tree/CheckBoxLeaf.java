package ui.awt.component.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import system.file.FileItem;

/** ************************************************************
 * This is a leaf model each leaf equals to a algorithm option
 * This class is modified from an example
 * http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class CheckBoxLeaf extends DefaultMutableTreeNode{
	private String text;
	private boolean selected;
	private int option;
	private FileItem fileItem;
	
	/**
	 * Constructor
	 * @param text	Displaying Text
	 * @param selected	Is the node selected
	 * @param option	VTK algorithm option
	 * @param fileItem	File item
	 */
	public CheckBoxLeaf(String text, boolean selected, int option, FileItem fileItem) {
		this.text = text;
		this.selected = selected;
		this.setOption(option);
		this.setFileItem(fileItem);
	}
	
	/**
	 * Constructor
	 * @param text	Displaying Text
	 * @param selected	Is the node selected
	 */
	public CheckBoxLeaf(String text, boolean selected) {
		this.text = text;
		this.selected = selected;
		this.setOption(-1);
		this.setFileItem(null);
	}

	// Attributes Set and Get
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return getClass().getName() + "[" + text + "/" + selected + "]";
	}

	public FileItem getFileItem() {
		return fileItem;
	}

	public void setFileItem(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

}

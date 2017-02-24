package ui.awt.component.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;


/** ************************************************************
 * This is a node model, each node equals to an imported file
 * This class is modified from an example
 * http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm
 * 
 * @author	Andy Tsang 
 * @version	1.2
 * ***********************************************************/
public class CheckBoxNode extends DefaultMutableTreeNode{
	private int id;
	private String name;
	private List<Object> list;

	public CheckBoxNode(String name) {
		this.name = name;
	}

	public CheckBoxNode(String name, CheckBoxLeaf elements[], int id) {
		this.name = name;
		this.id = id;
		this.list = new ArrayList<Object>();
		for (int i = 0, n = elements.length; i < n; i++) {
			this.add(elements[i]);
		}
	}

	public String toString() {
		return "["+id+"][" + name + "]";
	}
}

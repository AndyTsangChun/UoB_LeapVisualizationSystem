package test.integration;

import static org.junit.Assert.assertEquals;

import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.file.FileItem;
import system.file.LVSFile;
import ui.awt.dialog.edit.file.LVSFileEditDialog;

public class LVSFileEditPanelTest {
	private static final String[] columnNames = {"ID", "Name", "Path", "Edit", "X"};
	private LVSFileEditDialog editPanel;
	private LVSFile file;
	private FileItem item1;
	private FileItem item2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		editPanel = new LVSFileEditDialog(null,null,null);
		file = new LVSFile("Test","Test");
		item1 = new FileItem(0, "Item1", "Item1",true, 1);
		item2 = new FileItem(1, "Item2", "Item2",true, 1);
	}

	@Test
	public void testLoadModel() {
		file.addFile(item1);
		file.addFile(item2);
		DefaultTableModel actual = editPanel.loadModel(file);
		Object[][] data = new Object[][]{{0,"Item1", "Item1", "Edit", false},{1,"Item2", "Item2", "Edit", false}};
		DefaultTableModel expected = new DefaultTableModel(data, columnNames);
		assertEquals(expected.getColumnName(0), actual.getColumnName(0));
		assertEquals(expected.getColumnName(1), actual.getColumnName(1));
		assertEquals(expected.getColumnName(2), actual.getColumnName(2));
		assertEquals(expected.getColumnName(3), actual.getColumnName(3));
		assertEquals(expected.getColumnName(4), actual.getColumnName(4));
		assertEquals(expected.getRowCount(), actual.getRowCount());
		assertEquals(expected.getValueAt(0, 0), actual.getValueAt(0, 0));
		assertEquals(expected.getValueAt(0, 1), actual.getValueAt(0, 1));
		assertEquals(expected.getValueAt(1, 0), actual.getValueAt(1, 0));
		assertEquals(expected.getValueAt(1, 1), actual.getValueAt(1, 1));
		
	}

}

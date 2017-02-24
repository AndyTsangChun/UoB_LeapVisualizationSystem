package test.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.file.FileItem;
import system.file.LVSFile;

public class LVSFileTest {
	private final static int id = 5;
	private LVSFile file;
	private FileItem item;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		file = new LVSFile("Test","Test");
		item = new FileItem(id,"Test","Test",false,1);
	}

	@Test
	public void testAddFile() {
		int actual;
		int expected = 1;
		file.addFile(item);
		actual = file.getFileList().size();
		assertEquals("File list size should be one after adding an item.", expected, actual);
	}

	@Test
	public void testDeleteFile() {
		int actual;
		int expected = 0;
		file.addFile(item);
		file.deleteFile(id);
		actual = file.getFileList().size();
		assertEquals("File list size should be 0", expected, actual);
	}

}

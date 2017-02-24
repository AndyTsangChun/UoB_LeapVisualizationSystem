package test.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.file.FileItem;

public class FileItemTest {
	private FileItem item;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		item = new FileItem(0, "Test", "No", true, 0.5);
	}

	@Test
	public void testCreateObject() {
		boolean actual;
		boolean expected = true;
		
		actual = item.isVisible();
		assertEquals("Should be true", expected, actual);
		
		expected = false;
		actual = item.getContour().isVisiable();
		assertEquals("Should be false", expected, actual);
		actual = item.getSlice().isVisiable();
		assertEquals("Should be false", expected, actual);
		actual = item.getThreshold().isVisiable();
		assertEquals("Should be false", expected, actual);
		actual = item.getScalarBar().isVisiable();
		assertEquals("Should be false", expected, actual);
	}
}

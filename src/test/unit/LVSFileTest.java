package test.unit;

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
	}

	@Test
	public void testPreviousActor() {
		int actual;
		int expected = 0;
		file.previousActor();
		actual = file.getShowingActor();
		assertEquals("showingActor should not be less than 0", expected, actual);
	}

	@Test
	public void testNextActor() {
		int actual;
		int expected = 0;
		file.addFile(item);
		file.nextActor();
		actual = file.getShowingActor();
		assertEquals("showingActor should not be greater than size - 1 ", actual, expected);
		
		expected = 1;
		file.addFile(item);
		file.nextActor();
		actual = file.getShowingActor();
		assertEquals("showingActor should be 1", actual, expected);
	}

}

package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.controller.SystemController;
import system.file.LVSFile;

public class SystemControllerTest {
	private SystemController systemController;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		systemController = new SystemController();
		LVSFile file = new LVSFile("Test","Test");
		systemController.setCurrentFile(file);
	}

	@Test
	public void testUpdateSystemStatus() {
		String actual;
		String expected = "Testing";
		systemController.updateSystemStatus(expected);
		actual = systemController.getSwingController().getStatusBar().getStatusLabel().getText();
		assertEquals("Result should be same string \"Testing\"", expected, actual);
	}

	@Test
	public void testChangeActor() {
		int actual;
		int expected = 0;
		systemController.changeActor(-1);
		actual = systemController.getCurrentFile().getShowingActor();
		assertEquals("ShowingActor should be 0", expected, actual);
		systemController.changeActor(1);
		actual = systemController.getCurrentFile().getShowingActor();
		assertEquals("ShowingActor should be 0", expected, actual);
	}

	@Test
	public void testSetSystemStatus() {
		int actual = systemController.getSystemStatus();
		int expected = systemController.STATUS_SETTING;
		assertEquals("System should be in setting state", expected, actual);
	}

}

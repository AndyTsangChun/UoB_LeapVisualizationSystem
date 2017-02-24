package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.controller.CursorController;

public class CursorControllerTest {
	private CursorController cursorController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cursorController = new CursorController(null,null);
	}

	@Test
	public void testHoldMouse() {
		boolean actual;
		boolean expected = true;
		cursorController.setLEFT_CLICK_DOWN_FLAG(true);
		cursorController.holdMouse();
		actual = cursorController.isLEFT_CLICK_DOWN_FLAG();
		assertEquals("Should be true", expected, actual);
		cursorController.setLEFT_CLICK_DOWN_FLAG(false);
		cursorController.holdMouse();
		actual = cursorController.isLEFT_CLICK_DOWN_FLAG();
		assertEquals("Should be true", expected, actual);
	}

	@Test
	public void testShiftHoldLeft() {
		boolean actual;
		boolean expected = true;
		cursorController.shiftHoldLeft();
		actual = cursorController.isLEFT_CLICK_DOWN_FLAG();
		assertEquals("Should be true", expected, actual);
		actual = cursorController.isSHIT_DOWN_FLAG();
		assertEquals("Should be true", expected, actual);
		
	}
	
	@Test
	public void testReleaseInput(){
		boolean actual;
		boolean expected = false;
		cursorController.setLEFT_CLICK_DOWN_FLAG(true);
		cursorController.setSHIT_DOWN_FLAG(true);
		cursorController.releaseInput();
		actual = cursorController.isLEFT_CLICK_DOWN_FLAG();
		assertEquals("Should be false", expected, actual);
		actual = cursorController.isSHIT_DOWN_FLAG();
		assertEquals("Should be false", expected, actual);
		// case 2
		cursorController.setLEFT_CLICK_DOWN_FLAG(false);
		cursorController.setSHIT_DOWN_FLAG(false);
		cursorController.releaseInput();
		actual = cursorController.isLEFT_CLICK_DOWN_FLAG();
		assertEquals("Should be false", expected, actual);
		actual = cursorController.isSHIT_DOWN_FLAG();
		assertEquals("Should be false", expected, actual);
	}

}

package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.controller.CursorController;
import system.controller.GestureController;
import system.controller.SystemController;
import system.leapmotion.gesture.util.CustomGestureType;

public class GestureControlTest {
	private SystemController systemController;
	private GestureController gestureController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		systemController = new SystemController();
		gestureController = new GestureController(null, systemController, null);
	}

	@Test
	public void testCheck_custom_Gesture() {
		CustomGestureType actual;
		CustomGestureType expected = CustomGestureType.UNKNOWN_GESTURE;
		gestureController.setPreviousGesture(CustomGestureType.CLAP_GESTURE);
		gestureController.checkPerviousGesture(CustomGestureType.UNKNOWN_GESTURE);
		actual = gestureController.getPreviousGesture();
		assertEquals(actual,expected);
	}

}

package test.integration;

import static org.junit.Assert.assertEquals;

import java.awt.Image;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.controller.OverLayerController;
import system.leapmotion.gesture.ClapGesture;
import system.leapmotion.gesture.util.CustomGestureType;
import system.model.CustomGesture;
import system.res.SystemTextureManager;

public class OverLayerControllerTest {
	private OverLayerController overLayerController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		overLayerController = new OverLayerController(null,null);
	}

	@Test
	public void testUpdateGestureStatus() {
		CustomGesture customGesture = new ClapGesture(null);
		overLayerController.updateGestureStatus(customGesture, null);
		String actual = overLayerController.getOverLayerPanel().getGestureString();
		Image actual2 = overLayerController.getOverLayerPanel().getGestureImage();
		String expected = CustomGestureType.CLAP_GESTURE.getGestureName();
		Image expected2 = SystemTextureManager.CLAP_GESTURE_IMAGE;
		assertEquals(expected, actual);
		assertEquals(expected2, actual2);
	}

}

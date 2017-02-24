package test.unit;

import static org.junit.Assert.assertNotNull;

import java.awt.Image;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ui.awt.res.SwingTextureManager;

public class SwingTextureManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		SwingTextureManager.loadTexture();
	}

	@Test
	public void testLoad() {
		Image actual = null;
		actual = SwingTextureManager.BUTTON_BG_1_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.LVS_LOGO_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.COLORMAP_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.GESTURE_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.LEFT_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.LEFT_ICON_20_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.NEW_FILE_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.OFF_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.ON_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.OPEN_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.OPACITY_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.PRESENTATION_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.REPRESENTATION_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.RIGHT_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.RIGHT_ICON_20_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.SAVE_AS_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.SAVE_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TRACKING_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.IMPORT_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.BUTTON_BG_1_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.BUTTON_BG_2_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.GREY_CIRCLE_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.DEFAULT_SLICE_TRANSLATE_ICON_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.ENABLE_SLICE_TRANSLATE_ICON_IMAGE;
		assertNotNull(actual);
		// Tutorial
		actual = SwingTextureManager.TUT_ITEM_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_ITEM2_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_M_1_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_M_2_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_M_3_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_M_4_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_M_5_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_M_6_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_GESTURE_IMAGE;
		assertNotNull(actual);
		actual = SwingTextureManager.TUT_MOVEMENT_IMAGE;
		assertNotNull(actual);
	}

}

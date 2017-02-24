package test.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.general.SystemPreference;
import ui.awt.dialog.edit.tracking.TrackingModel;

public class TackingModelTest {
	private SystemPreference systemPreference;
	private TrackingModel trackingModel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		systemPreference = new SystemPreference(null,null);
		trackingModel = new TrackingModel(systemPreference);
	}

	@Test
	public void testNone() {
		boolean actual;
		boolean expected = false;
		
		trackingModel.none();
		actual = systemPreference.isShowLeftIndex() && systemPreference.isShowLeftMiddle() && systemPreference.isShowLeftPalm() && systemPreference.isShowLeftPinky() && systemPreference.isShowLeftRing() && systemPreference.isShowLeftThumb() && systemPreference.isShowRightIndex() && systemPreference.isShowRightMiddle() && systemPreference.isShowRightPalm() && systemPreference.isShowRightPinky() && systemPreference.isShowRightRing() && systemPreference.isShowRightThumb();
		assertEquals("Should be false", expected, actual);
	}
	
	@Test
	public void testAll() {
		boolean actual;
		boolean expected = true;
		
		trackingModel.none();
		trackingModel.all();
		actual = systemPreference.isShowLeftIndex() && systemPreference.isShowLeftMiddle() && systemPreference.isShowLeftPalm() && systemPreference.isShowLeftPinky() && systemPreference.isShowLeftRing() && systemPreference.isShowLeftThumb() && systemPreference.isShowRightIndex() && systemPreference.isShowRightMiddle() && systemPreference.isShowRightPalm() && systemPreference.isShowRightPinky() && systemPreference.isShowRightRing() && systemPreference.isShowRightThumb();
		assertEquals("Should be true", expected, actual);
	}
	
	@Test
	public void testAllFingers() {
		boolean actual;
		boolean expected = true;
		
		trackingModel.none();
		trackingModel.allFingers();
		actual = systemPreference.isShowLeftIndex() && systemPreference.isShowLeftMiddle() && systemPreference.isShowLeftPinky() && systemPreference.isShowLeftRing() && systemPreference.isShowLeftThumb() && systemPreference.isShowRightIndex() && systemPreference.isShowRightMiddle() && systemPreference.isShowRightPinky() && systemPreference.isShowRightRing() && systemPreference.isShowRightThumb();
		assertEquals("Should be true", expected, actual);
	}
	
	@Test
	public void testLeftFingers() {
		boolean actual;
		boolean expected = true;
		
		trackingModel.none();
		trackingModel.leftFingers();
		actual = systemPreference.isShowLeftIndex() && systemPreference.isShowLeftMiddle() && systemPreference.isShowLeftPinky() && systemPreference.isShowLeftRing()  && systemPreference.isShowLeftThumb();
		assertEquals("Should be true", expected, actual);
	}
	
	@Test
	public void testRightFingers() {
		boolean actual;
		boolean expected = true;
		
		trackingModel.none();
		trackingModel.rightFingers();
		actual = systemPreference.isShowRightIndex() && systemPreference.isShowRightMiddle() && systemPreference.isShowRightPinky() && systemPreference.isShowRightRing() && systemPreference.isShowRightThumb();
		assertEquals("Should be true", expected, actual);
	}
	
	@Test
	public void testPalms() {
		boolean actual;
		boolean expected = true;
		
		trackingModel.none();
		trackingModel.palms();
		actual = systemPreference.isShowLeftPalm() && systemPreference.isShowRightPalm();
		assertEquals("Should be true", expected, actual);
	}
}

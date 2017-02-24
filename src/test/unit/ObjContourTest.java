package test.unit;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.file.objects.ObjContour;

public class ObjContourTest {
	private ObjContour objContour;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		objContour = new ObjContour(null, true);
	}

	@Test
	public void testGenerateRange() {
		double[] actual;
		double[] expected = {1,2,3,4,5};
		double delta = 0;
		double[] range = {1,5};
		int step = 5;
		objContour.setCustomRange(range);
		objContour.setStep(step);
		objContour.generateRange();
		actual = objContour.getActualValue();
		assertArrayEquals("The value should be {1,2,3,4,5}", expected, actual, delta);
		
		expected = new double[]{2,2.33,2.66,3};
		delta = 0.1;
		range = new double[]{2,3};
		step = 4;
		objContour.setCustomRange(range);
		objContour.setStep(step);
		objContour.generateRange();
		actual = objContour.getActualValue();
		assertArrayEquals("The value should be {2,2.33,2.66,3}", expected, actual, delta);
	}

}

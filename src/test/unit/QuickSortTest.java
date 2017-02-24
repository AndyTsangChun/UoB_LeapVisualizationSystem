package test.unit;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.util.QuickSort;

public class QuickSortTest {
	private QuickSort quickSort;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		quickSort = new QuickSort();
	}

	@Test
	public void testCreateObject() {
		double[] actual = {};
		double delta = 0;
		double[] expected = {1,3,5,6};
		double[] sampleData = {6,1,5,3};
		
		actual = quickSort.sort(sampleData);
		assertArrayEquals("The array is not sorted.", expected, actual, delta);
	}
}

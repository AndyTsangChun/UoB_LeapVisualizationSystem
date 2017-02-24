package system.util;

/** *********************************************************** 
 * Quick sort
 * 
 * @author Andy Tsang
 * @version 1.1
 * ***********************************************************/
public class QuickSort {
    private double[] array;
    private int length;
    
	public double[] sort(double[] inputArr) {
        
        if (inputArr == null || inputArr.length == 0) {
            return null;
        }
        this.array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
        
        return array;
    }
 
    private void quickSort(int lowerIndex, int higherIndex) {
         
    	int i = lowerIndex;
    	int j = higherIndex;
    	double pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    private void exchangeNumbers(int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

package ktak.algos.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class SweeperSortTest {
    
    private static final int VALUE_BOUND = 100;
    private static final Random rand = new Random(System.currentTimeMillis());
    private static final Comparator<Integer> cmp = (i1, i2) -> i1.compareTo(i2);
    
    @Test
    public void test() {
        
        for (int size=0; size < 100; size++) {
            
            Integer[] a = randomArray(size);
            for (int k=1; k <= size+1; k++) {
                Integer[] copy = Arrays.copyOf(a, a.length);
                SweeperSort.sort(copy, k, cmp);
                Assert.assertTrue(isSorted(copy, cmp));
            }
            
        }
        
    }
    
    private static <T> boolean isSorted(T[] a, Comparator<T> cmp) {
        
        for (int i=1; i < a.length; i++) {
            if (cmp.compare(a[i-1], a[i]) == 1)
                return false;
        }
        return true;
        
    }
    
    private static Integer[] randomArray(int size) {
        
        Integer[] a = new Integer[size];
        for (int i=0; i < size; i++) {
            a[i] = rand.nextInt(VALUE_BOUND);
        }
        return a;
        
    }
    
}

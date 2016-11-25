package ktak.algos.sorting;

import java.util.Comparator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import ktak.immutablejava.List;

public class MergeSortTest {
    
    private static final int VALUE_BOUND = 100;
    private static final Random rand = new Random(System.currentTimeMillis());
    private static final Comparator<Integer> cmp = (i1, i2) -> i1.compareTo(i2);
    
    @Test
    public void test() {
        
        for (int size=0; size < 1000; size++) {
            Assert.assertTrue(isSorted(MergeSort.sort(randomList(size), cmp), cmp));
        }
        
    }
    
    private static <T> boolean isSorted(List<T> lst, Comparator<T> cmp) {
        
        return lst.match(
                (nil) -> true,
                (cons1) -> cons1.right.match(
                        (nil) -> true,
                        (cons2) -> cmp.compare(cons1.left, cons2.left) <= 0 ?
                                isSorted(cons1.right, cmp) :
                                false));
        
    }
    
    private static List<Integer> randomList(int size) {
        
        return size == 0 ?
                new List.Nil<>() :
                randomList(size-1).cons(rand.nextInt(VALUE_BOUND));
        
    }
    
}

package ktak.algos.sorting;

import java.util.ArrayList;
import java.util.Comparator;

import ktak.algos.heaps.ArrayListHeap;

public class SweeperSort {
    
    private SweeperSort() {}
    
    /**
     * sorting algorithm with runtime complexity O(n/k * n * log(k+1)) where
     * n is the size of the array and k is the second parameter that
     * determines the size of the heap used. when k = 1 the complexity is
     * O(n^2) and the algorithm behaves like selection sort. when k = n the
     * complexity is O(n * log(n)) and the algorithm behaves like heap sort
     */
    public static <T> void sort(T[] a, int k, Comparator<T> cmp) {
        
        if (k <= 0)
            throw new IllegalArgumentException();
        
        ArrayList<T> heap = new ArrayList<T>(k+1);
        
        for (int elementsSorted=0; elementsSorted < a.length; elementsSorted += k) {
            
            for (int i=0; i < k+1 && i < a.length-elementsSorted; i++) {
                ArrayListHeap.add(heap, a[i], cmp);
            }
            
            for (int i=0; i < a.length-elementsSorted; i++) {
                
                a[i] = ArrayListHeap.remove(heap, cmp);
                
                if (i+k+1 < a.length-elementsSorted)
                    ArrayListHeap.add(heap, a[i+k+1], cmp);
                
            }
            
        }
        
    }
    
}

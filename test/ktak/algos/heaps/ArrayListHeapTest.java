package ktak.algos.heaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class ArrayListHeapTest {
    
    private static final int VALUE_BOUND = 100;
    private static final Random rand = new Random(System.currentTimeMillis());
    private static final Comparator<Integer> cmp = (i1, i2) -> i1.compareTo(i2);
    
    @Test
    public void testHeapify() {
        
        for (int size=0; size < 1000; size++) {
            
            ArrayList<Integer> heap = randomArrayList(size);
            ArrayListHeap.heapify(heap, cmp);
            Assert.assertTrue(isHeap(heap, cmp));
            
        }
        
    }
    
    @Test
    public void testAdd() {
        
        for (int startSize=0; startSize < 10; startSize++) {
            
            ArrayList<Integer> heap = randomArrayList(startSize);
            ArrayListHeap.heapify(heap, cmp);
            Assert.assertTrue(isHeap(heap, cmp));
            
            for (int additionalElement=0; additionalElement < 10; additionalElement++) {
                ArrayListHeap.add(heap, rand.nextInt(VALUE_BOUND), cmp);
                Assert.assertTrue(isHeap(heap, cmp));
            }
            
        }
        
    }
    
    @Test
    public void testRemove() {
        
        for (int size=1; size < 100; size++) {
            
            ArrayList<Integer> heap = randomArrayList(size);
            ArrayListHeap.heapify(heap, cmp);
            Assert.assertTrue(isHeap(heap, cmp));
            
            for (int elementsRemoved=0; elementsRemoved < size; elementsRemoved++) {
                Integer smallest = ArrayListHeap.remove(heap, cmp);
                Assert.assertTrue(isSmallest(smallest, heap, cmp));
                Assert.assertTrue(isHeap(heap, cmp));
            }
            
        }
        
    }
    
    private static <T> boolean isSmallest(T val, List<T> list, Comparator<T> cmp) {
        
        for (T t : list) {
            if (cmp.compare(val, t) == 1)
                return false;
        }
        return true;
        
    }
    
    private static ArrayList<Integer> randomArrayList(int size) {
        
        ArrayList<Integer> a = new ArrayList<>(size);
        for (int i=0; i < size; i++) {
            a.add(rand.nextInt(VALUE_BOUND));
        }
        return a;
        
    }
    
    private static <T> boolean isHeap(ArrayList<T> list, Comparator<T> cmp) {
        
        for (int parentIndex=0; parentIndex < list.size(); parentIndex++) {
            
            int leftChildIndex = leftChildIndex(parentIndex);
            int rightChildIndex = rightChildIndex(parentIndex);
            
            if (leftChildIndex < list.size() &&
                    cmp.compare(list.get(parentIndex), list.get(leftChildIndex)) == 1) {
                return false;
            }
            if (rightChildIndex < list.size() &&
                    cmp.compare(list.get(parentIndex), list.get(rightChildIndex)) == 1) {
                return false;
            }
            
        }
        
        return true;
        
    }
    
    private static int leftChildIndex(int parentIndex) {
        return parentIndex*2 + 1;
    }
    
    private static int rightChildIndex(int parentIndex) {
        return parentIndex*2 + 2;
    }
    
}

package ktak.algos.heaps;

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListHeap {
    
    private ArrayListHeap() {}
    
    private static final int MAX_PARENT_INDEX = (Integer.MAX_VALUE-2)/2;
    
    /**
     * turns an ArrayList into a heap in-place with O(n) time complexity
     */
    public static <T> void heapify(ArrayList<T> list, Comparator<T> cmp) {
        
        if (list.size() <= 1)
            return;
        
        int startIndex = parentIndex(list.size()-1);
        
        while (startIndex >= 0) {
            siftDown(list, startIndex, list.size()-1, cmp);
            startIndex--;
        }
        
    }
    
    public static <T> void add(ArrayList<T> list, T val, Comparator<T> cmp) {
        
        list.add(val);
        siftUp(list, 0, list.size()-1, cmp);
        
    }
    
    public static <T> T remove(ArrayList<T> list, Comparator<T> cmp) {
        
        if (list.isEmpty())
            return null;
        
        T top = list.get(0);
        
        list.set(0, list.get(list.size()-1));
        list.remove(list.size()-1);
        
        siftDown(list, 0, list.size()-1, cmp);
        
        return top;
        
    }
    
    private static int parentIndex(int childIndex) {
        return (childIndex-1)/2;
    }
    
    private static int leftChildIndex(int parentIndex) {
        if (parentIndex > MAX_PARENT_INDEX) {
            return Integer.MAX_VALUE;
        }
        return parentIndex*2 + 1;
    }
    
    private static int rightChildIndex(int parentIndex) {
        if (parentIndex > MAX_PARENT_INDEX) {
            return Integer.MAX_VALUE;
        }
        return parentIndex*2 + 2;
    }
    
    private static <T> void siftDown(ArrayList<T> list, int startIndex, int endIndex, Comparator<T> cmp) {
        
        int currentIndex = startIndex;
        
        while (leftChildIndex(currentIndex) <= endIndex) {
            
            int swapIndex = currentIndex;
            int leftChildIndex = leftChildIndex(currentIndex);
            if (cmp.compare(list.get(currentIndex), list.get(leftChildIndex)) == 1) {
                swapIndex = leftChildIndex;
            }
            int rightChildIndex = rightChildIndex(currentIndex);
            if (rightChildIndex <= endIndex &&
                    cmp.compare(list.get(swapIndex), list.get(rightChildIndex)) == 1) {
                swapIndex = rightChildIndex;
            }
            if (swapIndex == currentIndex)
                return;
            
            T temp = list.get(currentIndex);
            list.set(currentIndex, list.get(swapIndex));
            list.set(swapIndex, temp);
            currentIndex = swapIndex;
            
        }
        
    }
    
    private static <T> void siftUp(ArrayList<T> list, int startIndex, int endIndex, Comparator<T> cmp) {
        
        int childIndex = endIndex;
        
        while (childIndex > startIndex) {
            
            int parentIndex = parentIndex(childIndex);
            if (cmp.compare(list.get(parentIndex), list.get(childIndex)) == 1) {
                
                T temp = list.get(parentIndex);
                list.set(parentIndex, list.get(childIndex));
                list.set(childIndex, temp);
                childIndex = parentIndex;
                
            } else {
                return;
            }
            
        }
        
    }
    
}

package ktak.algos.sorting;

import java.util.Comparator;

import ktak.immutablejava.Function;
import ktak.immutablejava.List;
import ktak.immutablejava.Tuple;

public class MergeSort {
    
    private MergeSort() {}
    
    /**
     * Performs a bottom-up merge sort on an immutable list, eliminating
     * the need to get the length of the list and traversing it to its
     * halfway point in order to make the recursive calls as in the
     * top-down version
     */
    public static <T> List<T> sort(List<T> lst, Comparator<T> cmp) {
        return sort(base(), lst, cmp, new List.Nil<>());
    }
    
    private static <T> List<T> merge(List<T> lst1, List<T> lst2, Comparator<T> cmp) {
        
        return lst1.match(
                (nil) -> lst2,
                (cons1) -> lst2.match(
                        (nil) -> lst1,
                        (cons2) -> cmp.compare(cons1.left, cons2.left) == -1 ?
                                merge(cons1.right, lst2, cmp).cons(cons1.left) :
                                merge(lst1, cons2.right, cmp).cons(cons2.left)));
        
    }
    
    /**
     * The base case for merge sort takes a list and returns a tuple of
     * a sorted list of one or zero (if the input list was empty) elements
     * and the rest of the input list
     */
    private static <T> Function<List<T>,Tuple<List<T>,List<T>>> base() {
        
        return lst -> lst.match(
                (nil) -> Tuple.create(lst, lst),
                (cons) -> Tuple.create(new List.Nil<T>().cons(cons.left), cons.right));
        
    }
    
    /**
     * Takes a partial merge sort function that sorts lists of up to N
     * elements and returns a partial merge sort function that sorts
     * lists of up to 2*N elements
     */
    private static <T> Function<List<T>,Tuple<List<T>,List<T>>> doubleMerge(
            Function<List<T>,Tuple<List<T>,List<T>>> partialMerge,
            Comparator<T> cmp) {
        
        return lst -> {
            Tuple<List<T>,List<T>> res1 = partialMerge.apply(lst);
            return res1.right.match(
                    (nil) -> res1,
                    (cons1) -> {
                        Tuple<List<T>,List<T>> res2 = partialMerge.apply(res1.right);
                        return Tuple.create(merge(res1.left, res2.left, cmp), res2.right);
                    });
        };
        
    }
    
    /**
     * Applies a partial merge sort function to a list. If this doesn't
     * sort the entire list it makes a recursive call on the remainder
     * of the list and doubles the number of elements that the partial
     * merge sort function sorts, passing along the accumulated sorted
     * list. If the partial merge sort function begins with the base case
     * and the accumulator begins as an empty list, this function will
     * sort segments of the input list in powers of two, passing along
     * the accumulator with length one less than that power of two.
     * accumulator length / partial merge sort elements:
     * 0/1,1/2,3/4,7/8,15/16,...
     */
    private static <T> List<T> sort(
            Function<List<T>,Tuple<List<T>,List<T>>> partialMerge,
            List<T> lst,
            Comparator<T> cmp,
            List<T> acc) {
        
        Tuple<List<T>,List<T>> res1 = partialMerge.apply(lst);
        return res1.right.match(
                (nil) -> merge(acc, res1.left, cmp),
                (cons) -> sort(
                        doubleMerge(partialMerge, cmp),
                        res1.right,
                        cmp,
                        merge(acc, res1.left, cmp)));
        
    }
    
}

/*
    AUTHOR:
         Jeremy Deppen

    SUMMARY:
         A popular and effective in-place sorting algorithm.
         The way it works is a pivot is chosen (via random element,
         last element, or some other way) and it puts all values less
         than or equal to it to the left of it in the array and all values
         greater go to its left in the array. This guarantees that the
         pivot value is in its right place in the array. This is what's
         happening in the partition() method. Then, we recursively sort the
         left portion of the array and then the right portion by calling
         partition() again on the two subarray's. We keep doing this until
         we have subarrays of size 1. Then as the stack shrinks, the subarrays
         come back together for one sorted array.

     RUNTIME:
         worst case:   O(n²)     *worst case is very uncommon here*
         average case: O(nlogn)
 */


import java.util.*;

public class QuickSort
{
    public QuickSort()
    { }

    public int[] sort(int[] arr, int low, int high)
    {
        if(low >= high) return arr;
        else
        {
            int pivotIndex = partition(arr, low, high);
            arr = sort(arr, low, pivotIndex-1);
            arr = sort(arr, pivotIndex+1, high);
        }
        return arr;
    }

    private int partition(int[] arr, int low, int high)
    {
        int pivotValue = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++)
        {
            if(arr[j] <= pivotValue)
            {
                i++;
                arr = swap(arr, i, j);
            }
        }
        i++;
        arr = swap(arr, i, high);
        return i;
    }

    private int[] swap(int[] arr, int num1, int num2)
    {
        int temp = arr[num1];
        arr[num1] = arr[num2];
        arr[num2] = temp;
        return arr;
    }

    public static void main(String[] args) {
        Scanner inScan = new Scanner(System.in);
        QuickSort qs = new QuickSort();
        int[] arr = new int[5];
        for(int i = 0; i < 5; i++) {
            System.out.println("Enter an int");
            arr[i] = inScan.nextInt();
        }

        System.out.println("\nUnsorted: ");
        for(int i = 0; i < 5; i++)
            System.out.print(arr[i] + " ");
        System.out.println("\n");

        arr = qs.sort(arr, 0, 4);

        System.out.println("Sorted:");
        for(int i = 0; i < 5; i++)
            System.out.print(arr[i] + " ");
    }
}

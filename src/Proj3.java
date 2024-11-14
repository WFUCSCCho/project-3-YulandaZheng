/********************************************************************
 * @file: Proj3.java
 * @description: This program implements the project 3 java main class
 * @author: Yulanda Zheng
 * @date: November 14, 2024
**********************************************************************/
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations

    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {

        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }


    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {

        //find partitions and make arrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        ArrayList<T> leftarr = new ArrayList<T>(n1);
        ArrayList<T> rightarr = new ArrayList<T>(n2);

        for(int i = 0; i < n1; i++) { //fill left array
            leftarr.add( a.get(left + i));
        }
        for(int i = 0; i < n2; i++) {
            rightarr.add( a.get(mid + i + 1));
        }

        int i = 0;
        int j = 0;

        //merging the parts
        int k = left;
        while (i < n1 && j < n2) {
            if (leftarr.get(i).compareTo(rightarr.get(j)) <= 0) { //compare the arrays then merge in order
                a.set(k, leftarr.get(j));
                i++;
            }
            else {
                a.set(k, rightarr.get(j));
                j++;
            }
            k++;
        }

        //filling left array
        while (i < n1) {
            a.set(i, a.get(i));
            i++;
            k++;
        }

        //filling right array
        while (j < n2) {
            a.set(j, a.get(j));
            j++;
            k++;
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {

        if(left < right) {

            int mid = partition(a, left, right); //partitioning
            quickSort(a, left, mid-1);  //recursively sort left
            quickSort(a, mid + 1, right); //recursively sort right
        }

    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {

        int pivot = left;
        T sortObj = a.get(right);

        for (int i = left; i < right; i++) {
            if (a.get(i).compareTo(sortObj) < 0) { //if smaller than pivot
                swap(a, i, pivot); //swapping
                pivot++;
            }
        }

        swap(a, pivot, right);
        return pivot; // return pivot
        }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {

        //build the heap
        for(int i = (right-1)/2 ; i >= left; i--){
            heapify(a, i, right);
        }

        //take element from heap and move and sort
        for(int i = right; i >= left; i--){

            swap(a, left, i);
            heapify(a, left, i-1);
        }

    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {

        int min = left;
        int leftChild = min * 2 + 1;
        int rightChild = min * 2 + 2;

        //check if child is smaller
        if(leftChild <= right  && a.get(leftChild).compareTo(a.get(min)) > 0) { //left child, if smaller than swap
            min = leftChild;
        }
        if (rightChild <= right  && a.get(rightChild).compareTo(a.get(min)) > 0) { //right child, if smaller than swap
            min = rightChild;
        }

        //if root isn't the min
        if (min != left) {
            swap(a, min, left);
            heapify(a, min, right); //recursive call
        }

    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        boolean swapped = false;
        int numSwaps = 0;

        for(int i = 0; i < size; i++) {
            swapped = false;
            for(int j = 0; j < size - 1 - i; j++) {
                if(a.get(j).compareTo(a.get(j+1)) > 0) {
                    swap(a, j, j+1);
                    numSwaps++;
                    swapped = true;
                }
            }
            if(!swapped) {
                break; // parsing through without any swaps
            }
        }


        return numSwaps;


    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {

        boolean isSorted = false;
        int numSwaps = 0;

        while(!isSorted) {
            isSorted = true;

            //Odd index sort
            for(int i = 1; i < size - 2; i+=2) {
                if(a.get(i).compareTo(a.get(i+1)) > 0) {
                    swap(a, i, i+1);
                    numSwaps++;
                    isSorted = false;
                }
            }

            //Even index sort
            for (int i = 0; i < size-1; i+=2){
                if(a.get(i).compareTo(a.get(i+1)) > 0) {
                    swap(a, i, i+1);
                    numSwaps++;
                    isSorted = false;
                }
            }
        }

        return numSwaps;
    }

    public static void main(String [] args)  throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 3) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        String sortCommand = args[1];
        int numLines = Integer.parseInt(args[2]);
        String outputLine;
        int swapNum;

        //the array lists
        ArrayList<Cat> arr = new ArrayList<>();
        ArrayList<Cat> sortedArr = new ArrayList<>();
        ArrayList<Cat> shuffledArr = new ArrayList<>();
        ArrayList<Cat> reversesortedArr = new ArrayList<>();
        Cat kitty;

        //For calculating time
        long start;
        long end;
        long temp;

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        //Ignore first line
        inputFileNameScanner.nextLine();

        //putting lines in an array list
        for(int i = 0; i < numLines; i++) {
            String tempLine = inputFileNameScanner.nextLine();
            String[] line = tempLine.split(",");
            kitty = new Cat(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]),line[3],line[4]);
            arr.add(kitty);
        }

        //sorting the list based on command and then printing out the runtime and comparisons to 'analysis.txt' and sorted lists to 'sorted.txt'
        switch(sortCommand) {

            //QuickSort
            case "QuickSort":

                //sorted list
                Collections.sort(arr);
                start = System.nanoTime();
                quickSort(arr, 0, arr.size()-1);
                end = System.nanoTime();
                outputLine = "Sorted QuickSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                sortedArr = arr;

                //shuffled list
                Collections.shuffle(arr);
                start = System.nanoTime();
                quickSort(arr, 0, arr.size()-1);
                end = System.nanoTime();
                outputLine = "Shuffled QuickSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                shuffledArr = arr;

                //reversed sorted list
                Collections.sort(arr, Collections.reverseOrder());
                start = System.nanoTime();
                quickSort(arr, 0, arr.size()-1);
                end = System.nanoTime();
                outputLine = "Reversed Sorted QuickSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                reversesortedArr = arr;

                //printing the lists into the sorted.txt file
                outputLine = "Sorted List:" + sortedArr.toString() + "\nShuffled List:" + shuffledArr.toString() + "\nReverse Sorted List:" + reversesortedArr.toString() + "\n";
                writeToFile(outputLine, "sorted.txt", false );

                break;

            //HeapSort
            case "HeapSort":

                //sorted list
                Collections.sort(arr);
                start = System.nanoTime();
                heapSort(arr, 0, arr.size()-1);
                end = System.nanoTime();
                outputLine = "Sorted HeapSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                sortedArr = arr;

                //shuffled list
                Collections.shuffle(arr);
                start = System.nanoTime();
                heapSort(arr, 0, arr.size()-1);
                end = System.nanoTime();
                outputLine = "Shuffled HeapSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                shuffledArr = arr;

                //reversed sorted list
                Collections.sort(arr, Collections.reverseOrder());
                start = System.nanoTime();
                heapSort(arr, 0, arr.size()-1);
                end = System.nanoTime();
                outputLine = "Reversed Sorted HeapSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt",true);
                reversesortedArr = arr;

                //printing the lists into the sorted.txt file
                outputLine = "Sorted List:" + sortedArr.toString() + "\nShuffled List:" + shuffledArr.toString() + "\nReverse Sorted List:" + reversesortedArr.toString() + "\n";
                writeToFile(outputLine, "sorted.txt", false );

                break;

            //BubbleSort
            case "BubbleSort":

                //sorted list
                Collections.sort(arr);
                start = System.nanoTime();
                swapNum = bubbleSort(arr,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Sorted BubbleSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                System.out.println("Number of Swaps: " + swapNum);
                writeToFile(outputLine, "analysis.txt", true);
                writeToFile("Number of Swaps : " + swapNum, "analysis.txt", true);
                sortedArr = arr;

                //shuffled list
                Collections.shuffle(arr);
                start = System.nanoTime();
                swapNum = bubbleSort(arr,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Shuffled BubbleSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                System.out.println("Number of Swaps: " + swapNum);
                writeToFile(outputLine, "analysis.txt", true);
                writeToFile("Number of Swaps : " + swapNum, "analysis.txt", true);
                shuffledArr = arr;

                //reversed sorted list
                Collections.sort(arr, Collections.reverseOrder());
                start = System.nanoTime();
                swapNum = bubbleSort(arr,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Reversed Sorted BubbleSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                System.out.println("Number of Swaps: " + swapNum);
                writeToFile(outputLine, "analysis.txt", true);
                writeToFile("Number of Swaps : " + swapNum, "analysis.txt", true);
                reversesortedArr = arr;

                //printing the lists into the sorted.txt file
                outputLine = "Sorted List:" + sortedArr.toString() + "\nShuffled List:" + shuffledArr.toString() + "\nReverse Sorted List:" + reversesortedArr.toString() + "\n";
                writeToFile(outputLine, "sorted.txt", false );

                break;

            //MergeSort
            case "MergeSort":

                //sorted list
                Collections.sort(arr);
                start = System.nanoTime();
                System.out.println(arr.toString());
                mergeSort(arr,0,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Sorted MergeSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                sortedArr = arr;

                //shuffled list
                Collections.shuffle(arr);
                start = System.nanoTime();
                mergeSort(arr,0,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Shuffled MergeSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                writeToFile(outputLine, "analysis.txt", true);
                shuffledArr = arr;

                //reversed sorted list
                Collections.sort(arr, Collections.reverseOrder());
                start = System.nanoTime();
                mergeSort(arr,0,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Reversed Sorted MergeSort of " + numLines+ " lines took " + (end - start) + "ns.";
                writeToFile(outputLine, "analysis.txt", true);
                reversesortedArr = arr;
                System.out.println(outputLine);

                //printing the lists into the sorted.txt file
                outputLine = "Sorted List:" + sortedArr.toString() + "\nShuffled List:" + shuffledArr.toString() + "\nReverse Sorted List:" + reversesortedArr.toString() + "\n";
                writeToFile(outputLine, "sorted.txt", false );

                break;

            //Odd-Even Transposition
            case "Odd-EvenSort":

                //sorted list
                Collections.sort(arr);
                start = System.nanoTime();
                swapNum = transpositionSort(arr,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Sorted Odd-EvenTranspositionSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                System.out.println("Number of Swaps: " + swapNum);
                writeToFile(outputLine, "analysis.txt", true);
                writeToFile("Number of Swaps : " + swapNum, "analysis.txt", true);
                sortedArr = arr;

                //shuffled list
                Collections.shuffle(arr);
                start = System.nanoTime();
                swapNum = transpositionSort(arr,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Shuffled Odd-EvenTranspositionSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                System.out.println("Number of Swaps: " + swapNum);
                writeToFile(outputLine, "analysis.txt", true);
                writeToFile("Number of Swaps : " + swapNum, "analysis.txt", true);
                shuffledArr = arr;

                //reversed sorted list
                Collections.sort(arr, Collections.reverseOrder());
                start = System.nanoTime();
                swapNum = transpositionSort(arr,arr.size()-1);
                end = System.nanoTime();
                outputLine = "Reversed Sorted Odd-EvenTranspositionSort of " + numLines+ " lines took " + (end - start) + "ns.";
                System.out.println(outputLine);
                System.out.println("Number of Swaps: " + swapNum);
                writeToFile(outputLine, "analysis.txt", true);
                writeToFile("Number of Swaps : " + swapNum, "analysis.txt", true);
                reversesortedArr = arr;

                //printing the lists into the sorted.txt file
                outputLine = "Sorted List:" + sortedArr.toString() + "\nShuffled List:" + shuffledArr.toString() + "\nReverse Sorted List:" + reversesortedArr.toString() + "\n";
                writeToFile(outputLine, "sorted.txt", false );

                break;

            default:
                System.out.print("Invalid Command");
        }



    }
    //Filewriter method
    public static void writeToFile (String content, String filePath, boolean type) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath,type))){

            pw.println(content);

        } catch (IOException e) {
            System.out.println("Error writing to file");

        }
    }


}

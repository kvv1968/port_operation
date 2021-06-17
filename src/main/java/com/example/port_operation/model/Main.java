package com.example.port_operation.model;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[]array = {64, 42, 76, 41, 51, 35, 16, 24, 57, 42, 74, 55, 36};
        guickSort(array, 0, array.length -1);
    }

    public static void guickSort(int[] array, int from , int to) {
        if (from < to){
            int divideIndex = partition(array, from, to);
            printSortStep(array, from, to, divideIndex);
            guickSort(array, from, divideIndex -1);
            guickSort(array, divideIndex, to);
        }

    }

    private static void printSortStep(int[] array, int from, int to, int divideIndex) {
        System.out.print(arrayToString(array));
        System.out.print("\npartition at index: " + divideIndex);
        System.out.print(", left: " + arrayToString(Arrays.copyOfRange(array, from, divideIndex)));
        System.out.println(", right: " + arrayToString(Arrays.copyOfRange(array, divideIndex, to + 1)) + "\n");
    }

    private static int partition(int[] array, int from, int to) {
        int richtIndex = to;
        int leftIndex = from;
        int pivot = array[from];
        while (leftIndex <= richtIndex){
            while (array[leftIndex] < pivot){
                leftIndex++;
            }
            while (array[richtIndex] > pivot){
                richtIndex--;
            }
            if (leftIndex <= richtIndex){
                swap(array, richtIndex, leftIndex);
                leftIndex++;
                richtIndex--;
            }
        }
        return leftIndex;
    }
    public static void swap(int[] array, int index1, int index2){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}

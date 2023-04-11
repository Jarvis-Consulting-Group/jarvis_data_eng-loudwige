package ca.jrvs.practice.dataStructure;

public class ArrayExercise {

  public static String[] arrayToUpperCase(String[] array) {
    for (int i = 0; i < array.length; i++) {
      array[i] = array[i].toUpperCase();
    }
    return array;
  }

  public void incrementArray(int[] array) {
    for (int i = 0; i < array.length; i++) {
      array[i] = array[i] + 1;
    }
  }
}

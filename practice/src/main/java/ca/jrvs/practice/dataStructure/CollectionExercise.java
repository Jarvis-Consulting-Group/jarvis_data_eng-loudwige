package ca.jrvs.practice.dataStructure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

public class CollectionExercise {
  public static void addToEndOfLinkedList(LinkedList<Integer> numbers, int value){
    numbers.add(value);
  }

  public static void addToStartOfLinkedList(LinkedList<Integer> numbers, int value){
    numbers.addFirst(value);
  }

  public static void removeItemFromTopOfStack(Deque<Integer> stack){
    stack.pop();
  }

  public static void addItemToTreeSet(Set<String> set, String item){
    set.add(item);
  }
}

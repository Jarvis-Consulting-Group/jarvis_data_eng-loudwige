package ca.jrvs.practice.dataStructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public class CollectionsExerciseTest {
  private static final CollectionExercise collectionExercise = new CollectionExercise();

  @Test
  public void testAddSixToEndOLinkedList(){
    LinkedList<Integer> numbers = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
    collectionExercise.addToEndOfLinkedList(numbers, 6);
    assertEquals(Integer.valueOf(6), numbers.getLast());
  }

  @Test
  public void testAddZeroToStartOfLinkedList(){
    LinkedList<Integer> numbers = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
    collectionExercise.addToStartOfLinkedList(numbers, 0);
    assertEquals(Integer.valueOf(0), numbers.getFirst());
  }

  @Test
  public void testRemoveItemFromTopOfStack(){
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);
    collectionExercise.removeItemFromTopOfStack(stack);
    assertEquals(Integer.valueOf(4), stack.peek());
  }

  @Test
  public void testAddItemToTreeSet(){
    Set<String> set = new TreeSet<>();
    set.add("red");
    set.add("orange");
    set.add("yellow");
    collectionExercise.addItemToTreeSet(set, "green");
    assertTrue(set.contains("green"));
  }
}

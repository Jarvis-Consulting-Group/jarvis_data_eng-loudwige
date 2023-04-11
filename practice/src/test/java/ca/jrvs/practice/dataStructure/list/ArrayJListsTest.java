package ca.jrvs.practice.dataStructure.list;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ArrayJListsTest {

  @Test
  public void add() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Hello");
    assertEquals(jList.get(0), "Hello");
  }

  @Test
  public void toArray() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Game");
    jList.add("Play");
    Object[] expectedArray = {"Game", "Play"};
    Object[] actualArray = jList.toArray();
    assertArrayEquals(expectedArray, actualArray);
  }

  @Test
  public void size() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Hello");
    jList.add("Hi");
    assertEquals(jList.size(), 2);
  }

  @Test
  public void isEmpty() {
    JList<String> jList = new ArrayJLists<>();
    assertTrue(jList.isEmpty());
  }

  @Test
  public void indexOf() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Hello");
    assertEquals(jList.indexOf("Hello"), 0);
  }

  @Test
  public void contains() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Hello");
    jList.add("Hi");
    assertTrue(jList.contains("Hi"));
  }

  @Test
  public void get() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Hello");
    assertEquals(jList.get(0), "Hello");
  }

  @Test
  public void remove() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Hello");
    String element = jList.remove(0);
    assertEquals(element, "Hello");
  }

  @Test
  public void clear() {
    JList<String> jList = new ArrayJLists<>();
    jList.add("Item");
    jList.remove(0);
    assertTrue(jList.isEmpty());
  }
}
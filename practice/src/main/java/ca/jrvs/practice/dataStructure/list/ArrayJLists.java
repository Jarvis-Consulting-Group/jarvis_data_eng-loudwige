package ca.jrvs.practice.dataStructure.list;
import java.util.Arrays;

public class ArrayJLists<E> implements JList<E> {

  /**
   * Default initial capacity
   */
  private static final int DEFAULT_CAPACITY = 10;
  /**
   * The array buffer into which the elements of the ArrayList are stored.
   * The capacity of the ArrayList is the length of this array buffer.
   */
  transient Object[] elementData;
  private int modCount = 0;

  /**
   * The size of the ArrayList(the number of elements it contains).
   */
  private int size;

  /**
   * Constructs an empty list with the specified initial capacity.
   * @param initialCapacity the initial capacity of the list
   * @throws IllegalArgumentException if the specified initial capacity is negative
   */
  public ArrayJLists(int initialCapacity) {
    if(initialCapacity > 0){
      this.elementData = new Object[initialCapacity];
    } else{
      throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
  }

  /**
   * Constructs an empty list with an initial capacity of ten
   */
  public ArrayJLists() {
    this(DEFAULT_CAPACITY);
  }




  @Override
  public boolean add(E o) {
   modCount++;
   add(o, elementData, size);
   return true;
  }

  public void add(E o, Object[] elementData, int s){
    if(s == elementData.length){
      elementData = grow();
    }
    elementData[s] = o;
    size = s + 1;
  }

  private Object[] grow(int minCapacity){
    int oldCapacity = elementData.length;
    if(size >= oldCapacity){
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      return elementData = Arrays.copyOf(elementData, newCapacity);
    } else{
      return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
    }
  }
  private Object[] grow(){
    return grow(size + 1);
  }
  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int indexOf(Object o) {
    for(int i = 0; i < size; i++){
      if(o.equals(elementData[i])) return i;
    }
    return -1;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  @Override
  public E get(int index) {
    checkIndex(index);
    return (E) elementData[index];
  }

  private void checkIndex(int index){
    if(index < 0 || index >= size){
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
  }

  @Override
  public E remove(int index) {
    checkIndex(index);
    E element = (E) elementData[index];
    for(int j = index; j < size - 1; j++){
      elementData[j] = elementData[j + 1];
    }
    elementData[size - 1] = null;
    size--;
    return element;
  }

  @Override
  public void clear() {
    Object[] elements = elementData;
    for(int to = size, i = size = 0; i < to; i++){
      elements[i] = null;
    }
  }
}

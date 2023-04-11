package ca.jrvs.practice.dataStructure.list;

public interface JList<E>{

  boolean add(E e);

  Object[] toArray();

  public int size();
  public boolean isEmpty();

  int indexOf(Object o);

  boolean contains(Object o);

  E get(int index);

  E remove(int index);

  void clear();
}

import java.util.Iterator;
import edu.princeton.cs.algs4.In;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {   return size == 0;   }

    public int size()                        // return the number of items on the deque
    {   return size;    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst != null) oldfirst.prev = first;
        
        if (size == 0) last = first;
        
        size++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new java.lang.IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (oldlast != null) oldlast.next = last;

        if (size == 0) first = last;

        size++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (first == null) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;

        if (size == 1) last = null;
        size--;

        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (last == null) throw new java.util.NoSuchElementException();

        Item item = last.item;
        last = last.prev;

        if (size == 1) first = null;
        size--;

        return item;
    }

    public Iterator<Item> iterator() {  return new ListIterator();  }        // return an iterator over items in order from front to end
        
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext() {  return current != null; }
        public void remove() {  throw new java.lang.UnsupportedOperationException();    }
        public Item next()
        {
            if (current == null) throw new java.util.NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }    

    }

   public static void main(String[] args)   // unit testing (optional)
   {
        In in = new In(args[0]);        
        // Deque deque = new Deque();

        // deque.addFirst(1);
        // System.out.println(deque.size());
        // System.out.println(deque.removeFirst());

        // deque.addLast(1);
        // System.out.println(deque.size());
        // System.out.println(deque.removeLast());

        // deque.addFirst(3);
        // deque.addFirst(2);
        // deque.addFirst(1);
        // System.out.println(deque.size());
        // System.out.println(deque.removeLast());
        // System.out.println(deque.removeLast());
        // System.out.println(deque.removeLast());

        // deque.addLast(1);
        // deque.addLast(2);
        // deque.addLast(3);
        // System.out.println(deque.size());
        // System.out.println(deque.removeFirst());
        // System.out.println(deque.removeFirst());
        // System.out.println(deque.removeFirst());

        // deque.addFirst(null);

        // deque.removeLast();
   }
}

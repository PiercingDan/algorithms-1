import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // Bag 
    private int N = 0;
    private Item[] s;


    public RandomizedQueue()                 // construct an empty randomized queue
    {
        s = (Item[]) new Object[1];
    }    
    public boolean isEmpty() {  return N == 0;    }                 // is the randomized queue empty?
    public int size() {  return N;    }                       // return the number of items on the randomized queue
    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        if (N == s.length) resize(2 * s.length); // resize 
         
        s[N++] = item;
    }
    
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }


    public Item dequeue()                    // remove and return a random item
    {
        if (N == 0) throw new java.util.NoSuchElementException();

        int num = StdRandom.uniform(0, N);

        if (num != N - 1) // Switch last and selected
        {
           Item tmp = s[N - 1];
           s[N - 1] = s[num];
           s[num] = tmp;
        }     

        if (N < s.length / 4) resize(s.length / 2);

        return s[--N]; // hack, subtract N first
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (N == 0) throw new java.util.NoSuchElementException();
        int num = StdRandom.uniform(0, N);
        return s[num];
    }

    public Iterator<Item> iterator() {  return new RandomArrayIterator();  }         // return an independent iterator over items in random order

    private class RandomArrayIterator implements Iterator<Item>
    {
        private int n = N;
        private int i;

        public boolean hasNext() {  return n > 0;    }
        public void remove() {  throw new java.util.NoSuchElementException();   }
        public Item next () {
            if (n == 0) throw new java.util.NoSuchElementException();

            i = StdRandom.uniform(0, n);
            Item tmp = s[n - 1];
            s[n - 1] = s[i];
            s[i] = tmp;

            return s[--n];
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        // RandomizedQueue randqueue = new RandomizedQueue();

        // randqueue.enqueue(1);
        // randqueue.enqueue(2);
        // randqueue.enqueue(3);
        // randqueue.enqueue(4);
        // randqueue.enqueue(5);

        // System.out.println(randqueue.sample());
        // System.out.println(randqueue.dequeue());
        // System.out.println(randqueue.dequeue());

        // System.out.println(randqueue.size());

        // for (Object i : randqueue)
        //     System.out.println(i);
    }
}

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Permutation {
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> randqueue = new RandomizedQueue();

        while (!StdIn.isEmpty())
        {
            randqueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++)
        {
            System.out.println(randqueue.dequeue());
        }
    }

}

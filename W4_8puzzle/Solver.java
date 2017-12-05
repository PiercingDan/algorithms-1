import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {

    private boolean solvable;
    private int m;
    private Stack solution = new Stack<Board>();

    private static class SearchNode implements Comparable<SearchNode>
    {

        private Board board;
        private int moves;
        private SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode prev)
        {
            this.board = board;
            this.previous = prev;
            this.moves = moves;
        }

        public int compareTo(SearchNode that)
        {
            return (this.board.manhattan() + this.moves - that.board.manhattan() - that.moves);
        }

    }


    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new java.lang.IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ();
        pq.insert(new SearchNode(initial, 0, null));

        MinPQ<SearchNode> pqtwin = new MinPQ();
        pqtwin.insert(new SearchNode(initial.twin(), 0, null));


        while (true)
        {            
            // This Board
            SearchNode sn = pq.delMin();

            // System.out.println("=========================");
            // System.out.println("Move number: " + sn.moves);
            // System.out.println(sn.board);


            if (sn.board.isGoal())    
            {   
                this.solvable = true;
                this.m = sn.moves;

                // Adding the proper solution trace
                while (sn != null)
                {
                    solution.push(sn.board);
                    sn = sn.previous;
                }
               
                break;
            }
            
            for (Board board: sn.board.neighbors())
            {
                if ((sn.previous == null) || (!board.equals(sn.previous.board))) 
                {
                    pq.insert(new SearchNode(board, sn.moves + 1, sn));
                }
            } 

            // Twin Board

            sn = pqtwin.delMin();

            if (sn.board.isGoal())    {   this.solvable = false; this.m = -1; break;    }
            
            for (Board board: sn.board.neighbors())
            {
                if ((sn.previous == null) || (!board.equals(sn.previous.board))) 
                {
                    pqtwin.insert(new SearchNode(board, sn.moves + 1, sn));
                }
            } 
        } 

    }


    public boolean isSolvable()            // is the initial board solvable?
    {
       return this.solvable; 
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return this.m;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (solvable) return (Iterable<Board>) solution; 
        else return null;
    }

    public static void main(String[] args) 
    {
    // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

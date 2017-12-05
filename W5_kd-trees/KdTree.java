import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.TreeSet;

public class KdTree {

    private Node root;

    public KdTree() {}                               // construct an empty set of tree 


    private static class Node {
        private Point2D p;      // the point
        // private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int N;

        public Node(Point2D p, int N)
        {   this.p = p; this.N = N; }

    }

    public boolean isEmpty()                      // is the set empty? 
    {   return (root == null);    }

    public int size()                         // number of tree in the set 
    {   return size(root);   }    

    private int size(Node x)
    {
        if (x == null)   return 0;
        else             return x.N;
    }




    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new java.lang.IllegalArgumentException();
        root = insert(root, p, 0);    
    }

    private Node insert(Node x, Point2D p, int level)
    {
        if (x == null) return new Node(p, 1);
        if (p.compareTo(x.p) == 0)  return x; // Point already exists

        // Determine level of Node, for comparison, even is x-comparison, odd is y-comparison

        int cmp;

        if ((level % 2) == 0)   cmp = Point2D.X_ORDER.compare(p, x.p); // x - order
        else    cmp = Point2D.Y_ORDER.compare(p, x.p);  // y - order

        if      (cmp <= 0)   x.lb  = insert(x.lb, p, level + 1) ;   // same compared value, I just send it to the left, contains is written assuming this
        else if (cmp > 0)    x.rt = insert(x.rt, p, level + 1);
        x.N = size(x.lb) + size(x.rt) + 1;

        return x;
    }




    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int level)
    {
        if (x == null) return false;
        // System.out.println(p);
        // System.out.println(x.p);
        // System.out.println(p.compareTo(x.p));

        if (p.compareTo(x.p) == 0)  return true;

        int cmp;

        if ((level % 2) == 0)   cmp = Point2D.X_ORDER.compare(p, x.p); // x - order
        else    cmp = Point2D.Y_ORDER.compare(p, x.p);  // y - order

        if      (cmp <= 0)   return contains(x.lb, p, level + 1);
        else if (cmp > 0)    return contains(x.rt, p, level + 1);

        return false; // Placeholder
    }

    public void draw()                         // draw all tree to standard draw 
    {
        // Getting Fatal Errors with StdDraw

        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.01);

    }


    public Iterable<Point2D> range(RectHV rect)             // all tree that are inside the rectangle (or on the boundary) 
    {
        
        if (rect == null) throw new java.lang.IllegalArgumentException();

        return (Iterable<Point2D>) range(root, rect, 0, new TreeSet<Point2D>());

    }

    // pass in the Set each time so this search function may add to it
    private TreeSet<Point2D> range(Node x, RectHV rect, int level, TreeSet<Point2D> set)
    {
        if (x == null) return set;

        if (rect.contains(x.p)) set.add(x.p);

        if ((level % 2 ) == 0) // X - comparison
        {
            // Below 
            if (rect.xmax() < x.p.x()) range(x.lb, rect, level + 1, set);
            // Above
            else if (rect.xmin() > x.p.x()) range(x.rt, rect, level + 1, set);
            // Intersecting
            else if ((rect.xmax() >= x.p.x()) && (rect.xmin() <= x.p.x()))
            {
                range(x.lb, rect, level + 1, set);
                range(x.rt, rect, level + 1, set);
            }
        }

        else // Y - comparison
        {
            // Below 
            if (rect.ymax() < x.p.y()) range(x.lb, rect, level + 1, set);
            // Above
            else if (rect.ymin() > x.p.y()) range(x.rt, rect, level + 1, set);
            // Intersecting
            else if ((rect.ymax() >= x.p.y()) && (rect.ymin() <= x.p.y()))
            {
                range(x.lb, rect, level + 1, set);
                range(x.rt, rect, level + 1, set);
            }
        }

        return set;
    }  


    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (contains(p)) return p;
        if (root == null) return null;
        


        return nearest(root, p, 0, root.p);    

    } 

    // Java does not support multi-valued returns, so I am calculating distance function more times than I have too
    // best as in closest point so far

    private Point2D nearest(Node x, Point2D p, int level, Point2D best)
    {
        
        if (x == null) return best;

        // New Champion
        if (p.distanceSquaredTo(best) > p.distanceSquaredTo(x.p)) best = x.p;

        // distSplitSquared = distance squared to splitting line defined by that node

        int cmp;
        double distSplitSquared; 

        if ((level % 2) == 0) {
            cmp = Point2D.X_ORDER.compare(p, x.p); // x - order
            distSplitSquared = (x.p.x() - p.x()) * (x.p.x() - p.x()); 
        }
        else {
            cmp = Point2D.Y_ORDER.compare(p, x.p);  // y - order
            distSplitSquared = (x.p.y() - p.y()) * (x.p.y() - p.y());  
        }

        //LB preference
        if (cmp <= 0)
        {
            best = nearest(x.lb, p, level + 1, best);
                
            // Do I need to check other subtree?
            if (p.distanceSquaredTo(best) > distSplitSquared) best = nearest(x.rt, p, level + 1, best);
        }

        //RT Preference
        else if (cmp > 0)
        {
            best = nearest(x.rt, p, level + 1, best);
                
            // Do I need to check other subtree?
            if (p.distanceSquaredTo(best) > distSplitSquared) best = nearest(x.lb, p, level + 1, best);
        }  

        return best;

    }
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        
        // KdTree kdtree = new KdTree();


        // kdtree.insert(new Point2D(0., 1.));
        // kdtree.insert(new Point2D(-1, -1.));
        // Point2D p1 = new Point2D(0., 0.);
        // kdtree.insert(p1);
        // kdtree.insert(new Point2D(2., 1.));
        // System.out.println(kdtree.isEmpty());
        // System.out.println(kdtree.size());

        // System.out.println(kdtree.contains(new Point2D(0., 0.)));
        // System.out.println(kdtree.contains(new Point2D(1., 0.)));

        // RectHV rect = new RectHV(-1, -1, 1, 1);

        // for (Point2D p : kdtree.range(rect))
        // {
        //     System.out.println(p);
        // }

        // System.out.println(kdtree.nearest(p1));

        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();

        Point2D origin = new Point2D(0., 0.);
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        System.out.println(kdtree.isEmpty());
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(new Point2D(0.052657, 0.723349)));
        System.out.println(kdtree.contains(new Point2D(0.052656, 0.723349)));

        int count = 0;
        for (Point2D p : kdtree.range(rect))
        {
            count++;
            System.out.println(p);
        }
        

        System.out.println(count);
        System.out.println();
        System.out.println(kdtree.nearest(origin));
    }

}

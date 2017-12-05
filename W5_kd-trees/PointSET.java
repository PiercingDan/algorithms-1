import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.TreeSet;


public class PointSET {


    private final TreeSet<Point2D> points;

    public PointSET()                               // construct an empty set of points 
    {
        points = new TreeSet<Point2D>(); 
    }

    public boolean isEmpty()                      // is the set empty? 
    {   return points.isEmpty();    }

    public int size()                         // number of points in the set 
    {   return points.size();   }    

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new java.lang.IllegalArgumentException();

        points.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (p == null) throw new java.lang.IllegalArgumentException();

        return points.contains(p);
    }

    public void draw()                         // draw all points to standard draw 
    {
        // Getting Fatal Errors with StdDraw

        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.01);

        for (Point2D p : points)
        {
            p.draw();
        } 
    }


    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        
        if (rect == null) throw new java.lang.IllegalArgumentException();

        TreeSet<Point2D> inRange = new TreeSet<Point2D>();

        for (Point2D p : points)
        {
            if (rect.contains(p)) inRange.add(p);
        }

        return (Iterable<Point2D>) inRange;

    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (p == null) throw new java.lang.IllegalArgumentException();

        Point2D np = null; // nearest point 
        double min = Double.POSITIVE_INFINITY; // Minimum distance

        for (Point2D q : points)    {
            
            double dist = p.distanceSquaredTo(q);

            if (dist < min)  {
                min = dist;
                np = q;
            }
        }

        return np;    

    } 

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        
        // PointSET ps = new PointSET();

        // Point2D p1 = new Point2D(0., 0.);

        // ps.insert(new Point2D(-0.5, 0.));
        // ps.insert(new Point2D(-0.1, 0.1));
        // ps.insert(new Point2D(2., 1.));

        // System.out.println(ps.isEmpty());
        // System.out.println(ps.size());
        // System.out.println(ps.contains(new Point2D(0., 0.)));

        // RectHV rect = new RectHV(-1, -1, 1, 1);

        // for (Point2D p : ps.range(rect))
        // {
        //     System.out.println(p);
        // }

        // System.out.println(ps.nearest(p1));
        

        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();

        Point2D origin = new Point2D(0., 0.);
        Point2D inf = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        RectHV rect = new RectHV(0., 0., 0.5, 0.5);
        // System.out.println(origin.distanceSquaredTo(null));

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        
        for (Point2D p : brute.range(rect))
        {
            System.out.println(p);
        }

        System.out.println();
        System.out.println(brute.nearest(origin));
    }


}

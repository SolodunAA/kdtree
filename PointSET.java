import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> setOfPoints;

    public PointSET() {
        setOfPoints = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return setOfPoints.isEmpty();
    }

    public int size() {
        return setOfPoints.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        setOfPoints.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return setOfPoints.contains(p);
    }

    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (Point2D point : setOfPoints) {
            point.draw();
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> listIntPoint = new ArrayList<Point2D>();
        for(Point2D m: setOfPoints){
            if(rect.contains(m)){
                listIntPoint.add(m);
            }
        }
        return listIntPoint;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if(setOfPoints.isEmpty()) return null;
        Point2D near = null;
        for(Point2D m: setOfPoints){
            if(near == null || p.distanceSquaredTo(m) < p.distanceSquaredTo(near)){
                near = m;
            }
        }
        return near;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                  // unit testing of the methods (optional)
}

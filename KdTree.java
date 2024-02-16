import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {

    private int sizeQ = 0;
    private class Node{
        Point2D point;
        Node leftchild;
        Node rightchild;
        boolean isVertical;

        public Node(Point2D point, boolean isVertical){
            this.point = point;
            this.isVertical = isVertical;
        }
    }

    private Node root;

    public KdTree() {
        root = null;
    }                    // construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    }                  // is the set empty?

    public int size() {
        return sizeQ;
    }              // number of points in the set


    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if(!this.contains(p)) {


            if (root == null) {
                root = new Node(p, true);
                sizeQ = sizeQ + 1;
            } else {
                Node current = root;
                Node parent = root;
                boolean isLeft = false;

                while (current != null) {
                    parent = current;
                    if (current.isVertical) {
                        if (p.x() < current.point.x()) {
                            current = current.leftchild;
                            isLeft = true;
                        } else {
                            current = current.rightchild;
                            isLeft = false;
                        }
                    } else {
                        if (p.y() < current.point.y()) {
                            current = current.leftchild;
                            isLeft = true;
                        } else {
                            current = current.rightchild;
                            isLeft = false;
                        }
                    }
                }

                if (isLeft) {
                    parent.leftchild = new Node(p, !parent.isVertical);
                } else {
                    parent.rightchild = new Node(p, !parent.isVertical);
                }
                sizeQ = sizeQ + 1;
            }
        }
    }    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if(root == null) return false;
        Node current = root;

        while(current != null){
            if(current.point.equals(p)){
                return true;
            } else {
                if (current.isVertical) {
                    if (p.x() < current.point.x()) {
                        current = current.leftchild;
                    } else {
                        current = current.rightchild;
                    }
                } else {
                    if (p.y() < current.point.y()) {
                        current = current.leftchild;
                    } else {
                        current = current.rightchild;
                    }
                }
            }
        }
        return false;
    }    // does the set contain point p?

    public void draw() {

    }            // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> listInPoint = new ArrayList<Point2D>();
        searchRect(root, listInPoint, rect);
        return listInPoint;
    }      // all points that are inside the rectangle (or on the boundary)

    private void searchRect(Node rootNode, ArrayList<Point2D> listInPoint, RectHV rect) {
        if (rootNode == null) return;

        if (rect.contains(rootNode.point)) {
            listInPoint.add(rootNode.point);
        }
        if (rootNode.isVertical) {
            if (rect.xmax() < rootNode.point.x()) {
                searchRect(rootNode.leftchild, listInPoint, rect);
            } else if (rect.xmin() > rootNode.point.x()) {
                searchRect(rootNode.rightchild, listInPoint, rect);
            } else {
                searchRect(rootNode.leftchild, listInPoint, rect);
                searchRect(rootNode.rightchild, listInPoint, rect);
            }
        } else {
            if (rect.ymax() < rootNode.point.y()) {
                searchRect(rootNode.leftchild, listInPoint, rect);
            } else if (rect.ymin() > rootNode.point.y()) {
                searchRect(rootNode.rightchild, listInPoint, rect);
            } else {
                searchRect(rootNode.leftchild, listInPoint, rect);
                searchRect(rootNode.rightchild, listInPoint, rect);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D searchingPoint = root.point;
        double dist = p.distanceSquaredTo(root.point);
        searchNearest(root, searchingPoint, dist, p);
        return searchingPoint;
    }     // a nearest neighbor in the set to point p; null if the set is empty

    private void searchNearest(Node rootNode, Point2D searchPoint, double dist, Point2D p){
        if(rootNode == null) return;
        if(p.distanceSquaredTo(rootNode.point) < dist){
            dist = p.distanceSquaredTo(rootNode.point);
            searchPoint = rootNode.point;
        }
        searchNearest(root.leftchild, searchPoint, dist, p);
        searchNearest(root.rightchild, searchPoint, dist, p);
    }

}

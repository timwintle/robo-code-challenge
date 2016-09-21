package geom;

import util.DUtils;

import java.awt.geom.Point2D;



public class Circle {
    public double h;
    public double k;
    public double r;

    public Circle(double x, double y, double r) {
        this.h = x;
        this.k = y;
        this.r = r;
    }

    public Circle(Point2D.Double p, double r) {
        this(p.x, p.y, r);
    }

    public Point2D.Double[] intersects(LineSeg seg) {
        return intersects(seg, false);
    }

    public Point2D.Double[] intersects(LineSeg seg, boolean inverted) {
        double a = (seg.m * seg.m) + 1;
        double b = 2 * ((seg.b * seg.m) - (k * seg.m) - h);
        double c = (h * h) + (k * k) + (seg.b * seg.b) - (2 * seg.b * k) - (r * r);

        Point2D.Double[] solutions = new Point2D.Double[]{null, null};
        int i = 0;

        if (a == Double.POSITIVE_INFINITY) {
            LineSeg invSeg = new LineSeg(seg.y1, seg.x1, seg.y2, seg.x2);
            Circle invCircle = new Circle(k, h, r);
            Point2D.Double[] invSolutions = 
                invCircle.intersects(invSeg, true);

            for (int x = 0; x < invSolutions.length; x++) {
                if (invSolutions[x] != null) {
                    double t = invSolutions[x].x;
                    invSolutions[x].x = invSolutions[x].y;
                    invSolutions[x].y = t;
                }
            }

            return invSolutions;
        }

        double discrim = (b * b) - (4 * a * c);
        if (discrim < 0) {
            return solutions;
        }

        double sqrtDiscrim = Math.sqrt(discrim);
        double x1 = (-b + sqrtDiscrim) / (2 * a);
        double y1 = (seg.m * x1) + seg.b;

        if (x1 > seg.xMin && x1 < seg.xMax) {
            solutions[i++] = new Point2D.Double(x1, y1);
        }

        if (sqrtDiscrim > 0) {
            double x2 = (-b - sqrtDiscrim) / (2 * a);
            double y2 = (seg.m * x2) + seg.b;
            if (x2 > seg.xMin && x2 < seg.xMax) {
                solutions[i++] = new Point2D.Double(x2, y2);
            }
        }

        return solutions;
    }

    public boolean contains(Point2D.Double p) {
        double z = DUtils.square(p.x - h) + DUtils.square(p.y - k);
        return (z < r * r);
    }
}

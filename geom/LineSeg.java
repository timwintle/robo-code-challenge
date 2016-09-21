package geom;

import java.awt.geom.Point2D;


// y = mx + b
public class LineSeg {
  public double m;
  public double b;
  public double xMin;
  public double xMax;
  public double yMin;
  public double yMax;
  public double x1;
  public double y1;
  public double x2;
  public double y2;

  public LineSeg(double x1, double y1, double x2, double y2) {
    if (x1 == x2) {
      m = Double.POSITIVE_INFINITY;
      b = Double.NaN;
      xMin = xMax = x1;
    } else {
      m = (y2 - y1) / (x2 - x1);
      b = y1 - (m * x1);
      xMin = Math.min(x1, x2);
      xMax = Math.max(x1, x2);
    }
    yMin = Math.min(y1, y2);
    yMax = Math.max(y1, y2);

    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  public LineSeg(Point2D.Double p1, Point2D.Double p2) {
    this(p1.x, p1.y, p2.x, p2.y);
  }

  public Point2D.Double intersects(LineSeg seg) {
    if (m == seg.m || (b == Double.NaN && seg.b == Double.NaN)) {
      return null;
    } else if (b == Double.NaN || seg.b == Double.NaN) {
      LineSeg seg1 = new LineSeg(y1, x1, y2, x2);
      LineSeg seg2 = new LineSeg(seg.y1, seg.x1, seg.y2, seg.x2);
      Point2D.Double inverse = seg1.intersects(seg2);
      return new Point2D.Double(inverse.y, inverse.x);
    }

    double x = (seg.b - this.b) / (this.m - seg.m);
    double y = m * x + b;
    return new Point2D.Double(x, y);
  }
}

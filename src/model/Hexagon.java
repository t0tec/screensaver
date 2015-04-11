package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Hexagon extends Shape {

  private double[] xPoints;
  private double[] yPoints;

  public Hexagon(int x, int y, int width, Color color) {
    super(x, y, width, width * 175 / 200, color);
    xPoints = calculateXPoints();
    yPoints = calculateYPoints();
  }

  private double[] calculateXPoints() {
    double[] x = new double[6];
    x[0] = super.getX() + getWidth() / 4;
    x[1] = super.getX() + getWidth() * 3 / 4;
    x[2] = super.getX() + getWidth();
    x[3] = super.getX() + getWidth() * 3 / 4;
    x[4] = super.getX() + getWidth() / 4;
    x[5] = super.getX();
    return x;
  }

  private double[] calculateYPoints() {
    double[] y = new double[6];
    y[0] = super.getY();
    y[1] = super.getY();
    y[2] = super.getY() + getHeight() / 2;
    y[3] = super.getY() + getHeight();
    y[4] = super.getY() + getHeight();
    y[5] = super.getY() + getHeight() / 2;
    return y;
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(getColor());
    gc.setStroke(Color.BLACK);
    gc.fillPolygon(this.xPoints, this.yPoints, 6);
    gc.strokePolygon(this.xPoints, this.yPoints, 6);
  }

}

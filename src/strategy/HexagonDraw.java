package strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class HexagonDraw implements DrawBehaviour {

  private double[] calculateXPoints(int x, int width) {
    double[] xPoints = new double[6];
    xPoints[0] = x + width / 4;
    xPoints[1] = x + width * 3 / 4;
    xPoints[2] = x + width;
    xPoints[3] = x + width * 3 / 4;
    xPoints[4] = x + width / 4;
    xPoints[5] = x;
    return xPoints;
  }

  private double[] calculateYPoints(int y, int height) {
    double[] yPoints = new double[6];
    yPoints[0] = y;
    yPoints[1] = y;
    yPoints[2] = y + height / 2;
    yPoints[3] = y + height;
    yPoints[4] = y + height;
    yPoints[5] = y + height / 2;
    return yPoints;
  }

  @Override
  public void draw(GraphicsContext gc, int x, int y, int width, int height, Color color) {
    double[] xPoints = calculateXPoints(x, width);
    double[] yPoints = calculateYPoints(y, height);
    gc.setFill(color);
    gc.setStroke(Color.BLACK);
    gc.fillPolygon(xPoints, yPoints, 6);
    gc.strokePolygon(xPoints, yPoints, 6);
  }
}

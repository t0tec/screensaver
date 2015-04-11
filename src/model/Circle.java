package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Circle extends Shape {

  public Circle(int x, int y, int diameter, Color color) {
    super(x, y, diameter, diameter, color);
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(getColor());
    gc.fillOval(getX(), getY(), getWidth(), getHeight());
    gc.setStroke(Color.BLACK);
    gc.strokeOval(getX(), getY(), getWidth(), getHeight());
  }
}

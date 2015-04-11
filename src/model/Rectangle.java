package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Rectangle extends Shape {

  public Rectangle(int x, int y, int width, int height, Color color) {
    super(x, y, width, height, color);
  }

  @Override
  public void draw(GraphicsContext gc) {
    gc.setFill(getColor());
    gc.fillRect(getX(), getY(), getWidth(), getHeight());
    gc.setStroke(Color.BLACK);
    gc.strokeRect(getX(), getY(), getWidth(), getHeight());
  }
}

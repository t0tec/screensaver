package strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ShapeElement {

  private DrawBehaviour drawBehaviour;

  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final Color color;

  public ShapeElement(int x, int y, int width, int height, Color color) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
  }

  public void drawShapeElement(GraphicsContext gc) {
    this.drawBehaviour.draw(gc, getX(), getY(), getWidth(), getHeight(), getColor());
  }

  public void setDrawBehaviour(DrawBehaviour drawBehaviour) {
    this.drawBehaviour = drawBehaviour;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public Color getColor() {
    return this.color;
  }
}

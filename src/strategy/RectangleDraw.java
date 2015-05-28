package strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class RectangleDraw implements DrawBehaviour {

  @Override
  public void draw(GraphicsContext gc, int x, int y, int width, int height, Color color) {
    gc.setFill(color);
    gc.fillRect(x, y, width, height);
    gc.setStroke(Color.BLACK);
    gc.strokeRect(x, y, width, height);
  }
}

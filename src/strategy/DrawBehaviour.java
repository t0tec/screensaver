package strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DrawBehaviour {

  void draw(GraphicsContext gc, int x, int y, int width, int height, Color color);
}

package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Circle;
import model.Hexagon;
import model.Rectangle;
import model.Shape;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ScreenSaver extends Application {

  private ArrayList<Shape> shapes;
  private static final int MIN_WIDTH = 50;
  private static final int MIN_HEIGHT = 50;
  private static final int MAX_WIDTH = 120;
  private static final int MAX_HEIGHT = 120;
  private List<Class<? extends Shape>> shapeList;
  private Map<String, Class<?>[]> constructorArgs;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    Group root = new Group();
    primaryStage.setTitle("Screen Saver");

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    final Canvas canvas = new Canvas(screenBounds.getWidth(), screenBounds.getHeight());

    root.getChildren().add(canvas);

    Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    scene.setCursor(Cursor.DISAPPEAR);

    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ESCAPE) {
          Platform.exit();
        }
      }
    });

    scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Platform.exit();
      }
    });

    primaryStage.setScene(scene);
    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    primaryStage.setFullScreen(true);
    primaryStage.setResizable(false);
    final GraphicsContext gc = canvas.getGraphicsContext2D();

    shapeList = new ArrayList<>();
    shapeList.add(Circle.class);
    shapeList.add(Hexagon.class);
    shapeList.add(Rectangle.class);

    constructorArgs = new HashMap<>();
    constructorArgs.put(Circle.class.getName(),
                        new Class<?>[]{int.class, int.class, int.class, Color.class});
    constructorArgs.put(Hexagon.class.getName(),
                        new Class<?>[]{int.class, int.class, int.class, Color.class});
    constructorArgs.put(Rectangle.class.getName(),
                        new Class<?>[]{int.class, int.class, int.class, int.class, Color.class});
    shapes = new ArrayList<>();

//    Timer timer = new Timer();
//    timer.scheduleAtFixedRate(new TimerTask() {
//      @Override
//      public void run() {
//        addRandomShape();
//        for (Shape shape : shapes) {
//          shape.draw(gc);
//        }
//      }
//    }, 1000, 2000); // 1s delay, 2s loop

//    new AnimationTimer() { // Does this for every frame (~60fps)
//      @Override
//      public void handle(long now) {
//        addRandomShape();
//        for (Shape shape : shapes) {
//          shape.draw(gc);
//        }
//      }
//    }.start();

    final Timeline timeline =
        new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            addRandomShape();
            for (Shape shape : shapes) {
              shape.draw(gc);
            }
          }
        }), new KeyFrame(Duration.seconds(2))); // loop every 2 seconds

    timeline.setDelay(Duration.millis(1000));
    timeline.setCycleCount(Timeline.INDEFINITE); // Do this for eternity
    timeline.play();

    primaryStage.show();
  }

  private void addRandomShape() {
    Random random = new Random();
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    int left = random.nextInt((int) screenBounds.getWidth());
    int top = random.nextInt((int) screenBounds.getHeight());
    int width = random.nextInt(MAX_WIDTH - MIN_WIDTH + 1) + MIN_WIDTH;
    while ((left + width) > screenBounds.getWidth()) {
      left = random.nextInt((int) screenBounds.getWidth());
    }
    int height = random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;
    while ((top + Math.max(width, height)) > screenBounds.getHeight()) {
      top = random.nextInt((int) screenBounds.getHeight());
    }
    int type = random.nextInt(shapeList.size());
    Class<? extends Shape> shape = shapeList.get(type);
    Constructor constructor;
    try {
      constructor = shape.getConstructor(constructorArgs.get(shape.getName()));
      if (constructor.getParameterTypes().length == 4) {
        shapes.add((Shape) constructor.newInstance(left, top, width, Color
            .rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))));
      } else {
        shapes.add((Shape) constructor.newInstance(
            left, top, width, height, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))));
      }
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException ex) {
      System.err.printf("Error with creating %s: %s%n", shape.getSimpleName(), ex.getMessage());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}

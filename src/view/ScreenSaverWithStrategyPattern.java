package view;

import java.util.ArrayList;
import java.util.List;
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
import strategy.CircleDraw;
import strategy.DrawBehaviour;
import strategy.HexagonDraw;
import strategy.RectangleDraw;
import strategy.ShapeElement;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ScreenSaverWithStrategyPattern extends Application {

  private ArrayList<ShapeElement> shapes;
  private static final int MIN_WIDTH = 50;
  private static final int MIN_HEIGHT = 50;
  private static final int MAX_WIDTH = 120;
  private static final int MAX_HEIGHT = 120;
  private List<DrawBehaviour> drawBehaviours;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    Group root = new Group();
    primaryStage.setTitle("Screen Saver (With strategy patterns, different drawing strategies)");

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

    drawBehaviours = new ArrayList<>();
    drawBehaviours.add(new CircleDraw());
    drawBehaviours.add(new RectangleDraw());
    drawBehaviours.add(new HexagonDraw());

    shapes = new ArrayList<>();

    final Timeline timeline =
        new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            addRandomShape();
            for (ShapeElement shape : shapes) {
              shape.drawShapeElement(gc);
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

    ShapeElement shape = new ShapeElement(left, top, width, height, Color
        .rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

    int drawBehaviourType = random.nextInt(drawBehaviours.size());
    shape.setDrawBehaviour(drawBehaviours.get(drawBehaviourType));

    shapes.add(shape);
  }

  public static void main(String[] args) {
    launch(args);
  }
}

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class View extends Application {
    private Torus torus;
    private int n = 50, m = n;
    private int width = 800;
    private int height = width;
    private int blocksSize = width/n;
    private Snake snake;
    public static void main(String[] args) {
        launch(args); // start JavaFX-engine
    }

    public void start(Stage primaryStage) {
        torus = new Torus(n, m);
        snake = torus.getSnake();
        Image img = new Image("BackDrop.jpg");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(height);
        imgV.setFitWidth(width);

        Canvas canvas = new Canvas(width, height); //canvastørrelse angives

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBoard(gc, torus);

        StackPane root = new StackPane();
        root.getChildren().addAll(imgV, canvas);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Snake");

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() { //https://stackoverflow.com/questions/29962395/how-to-write-a-keylistener-for-javafx
            @Override
            public void handle(KeyEvent event) {   
                switch (event.getCode()) {
                case UP: snake.setDir(Direction.UP); break;
                case DOWN: snake.setDir(Direction.DOWN); break;
                case LEFT: snake.setDir(Direction.LEFT); break;
                case RIGHT: snake.setDir(Direction.RIGHT); break;
                default: return;
                }
                if(torus.isSnakeAlive()) {
                    torus.move();
                    drawBoard(gc, torus);
                } else {
                    gc.setFill(javafx.scene.paint.Color.RED);
                    gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                }           
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawBoard(GraphicsContext gc, Torus torus) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); //clear Canvas https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas

        for (int i = 0; i < width / blocksSize; i++) {
            gc.strokeLine(i * blocksSize, 0, i * blocksSize, width);
            gc.strokeLine(0, i * blocksSize, height, i * blocksSize);
        }
        for(int i = 0; i < torus.getN(); i++) {
            for(int k = 0; k < torus.getM(); k++) {
                Object elem = torus.getState()[k][i];
                if(elem == snake) {
                    gc.setFill(javafx.scene.paint.Color.RED);
                    gc.fillRect(i * blocksSize, k * blocksSize, blocksSize, blocksSize);
                } 
                else if(elem == null) {

                } else {
                    gc.setFill(javafx.scene.paint.Color.ORANGE);
                    gc.fillRect(i * blocksSize, k * blocksSize, blocksSize, blocksSize); 
                }
            }
        }
    }

}

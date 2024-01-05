//run the following 2 commands to play:
//javac -classpath . *.java
//java View.java n m
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class View extends Application {
    private int width = 800;
    private int height = width;
    private int blocksSize;
    private GraphicsContext gc;
    private Controller control;
    private Game game;
    private Text score;
    private BorderPane root2;
    private int scoreCount;
    private int n,m;

    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        List<String> args = getParameters().getRaw();
        if (args.size() != 2) {
                throw new IllegalArgumentException("Must be 2 arguments");
        }
        n = Integer.parseInt(args.get(0));
        m = Integer.parseInt(args.get(1));
        if (n < 5 || n > 100 || m < 5 || m > 100) {
                throw new IllegalArgumentException("Must be 2 arguments");
        }
        blocksSize = width / n;
    }

    public void start(Stage primaryStage) {
        game = new Game(n, m);
        control = new Controller(game, this);
        Canvas canvas = new Canvas(width, height); //canvastørrelse angives
        gc = canvas.getGraphicsContext2D();
        drawBoard();

        score = new Text(15, 35, "Score: " + 0);
        score.setFont(new Font("Ariel", 32));
        score.setFill(javafx.scene.paint.Color.BLACK); //https://docs.oracle.com/javafx/2/ui_controls/label.htm har ændret til text

        StackPane root1 = new StackPane();
        root1.getChildren().addAll(getImageView("BackDrop.jpg"), canvas);
        root2 = new BorderPane();
        root2.getChildren().add(score);

        Scene scene = new Scene(new StackPane(root1, root2));

        primaryStage.setTitle("Snake");
        scene.setOnKeyPressed(control::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateScore(int snakeLength) {
        scoreCount = snakeLength;
        score.setText("Score: " + scoreCount);
    }

    public void message(boolean win) {
        String message = win ? "GOOD JOB" : "YOU LOSE";
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        score.setText(message + "\nScore: " + scoreCount);
        score.setTextAlignment(TextAlignment.CENTER);
        root2.setCenter(score);
    }

    public void drawBoard() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); //clear Canvas https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas
        for (int i = 0; i < width / blocksSize; i++) {
            gc.strokeLine(i * blocksSize, 0, i * blocksSize, width);
            gc.strokeLine(0, i * blocksSize, height, i * blocksSize);
        }
        Object[][] state = game.getState();
        for(int i = 0; i < state.length; i++) {
            for(int k = 0; k < state[i].length; k++) {
                Object elem = state[k][i];
                if(elem instanceof Snake) {
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
        for (int i = 0; i < width / blocksSize; i++) {
            gc.strokeLine(i * blocksSize, 0, i * blocksSize, width);
            gc.strokeLine(0, i * blocksSize, height, i * blocksSize);
        }
    }


    public ImageView getImageView(String pathString) {
        Image img = new Image(pathString);
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(height);
        imgV.setFitWidth(width);
        return imgV;
    }
}

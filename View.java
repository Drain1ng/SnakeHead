//run the following 2 commands to play:
//javac -classpath . *.java
//java View.java n m
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.*;
import javafx.stage.Screen;

public class View extends Application {
    private int width;
    private int height;
    private int blocksSize;
    private GraphicsContext gc;
    private GraphicsContext gcBack;
    private GraphicsContext gcSnake;
    private Controller control;
    private Game game;
    private Text score, scoreEnd;
    private BorderPane root2;
    private int scoreCount;
    private int n,m;

    public static void main(String[] args) {
        launch(args);
    }

    //Thomas
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
        setDims();
    }
    //Chris & Bastian
    public void start(Stage primaryStage) {
        game = new Game(n, m);
        control = new Controller(game, this);
        Canvas[] board = drawGame();
        StackPane root1 = new StackPane();
        root1.getChildren().addAll(board[0], board[1], board[2]);
        root2 = new BorderPane();
        initiateText();
        Scene scene = new Scene(new StackPane(root1, root2));
        primaryStage.setTitle("Snake");
        scene.setOnKeyPressed(control::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Bastian
    public void updateScore(int snakeLength) {
        scoreCount = snakeLength;
        score.setText("  Score: " + scoreCount);
    }
    //Bastian
    public void message(boolean win) {
        String message = win ? "GOOD JOB" : "YOU LOSE";
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        scoreEnd.setText(message + "\nScore: " + scoreCount);
        score.setText("");
    }
    //Christian, Thomas
    public void drawGrid() {
        gc.setStroke(Color.GRAY);
        for (int i = 0; i < width / blocksSize; i++) {
            gc.strokeLine(i * blocksSize, 0, i * blocksSize, height);
        }
        for (int i = 0; i < height / blocksSize; i++) {
            gc.strokeLine(0, i * blocksSize, width, i * blocksSize);
        }

    }

    //clear Canvas https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas
    //Chris, Bastian, Christian
    public void updateSnake() {
        gcSnake.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        List<Point> body = game.getBody();
        Point food = game.getFood();
        for (Point snake: body) {
            if(game.getSnakeHead() == snake) {
                gcSnake.setFill(javafx.scene.paint.Color.GREEN);
                gcSnake.fillRect(snake.getX() * blocksSize, snake.getY() * blocksSize, blocksSize, blocksSize);
            } else {
                gcSnake.setFill(javafx.scene.paint.Color.LIGHTGREEN);
                gcSnake.fillRect(snake.getX() * blocksSize, snake.getY() * blocksSize, blocksSize, blocksSize);
            }
        }
        gcSnake.setFill(javafx.scene.paint.Color.ORANGE);
        gcSnake.fillRect(food.getX() * blocksSize, food.getY() * blocksSize, blocksSize, blocksSize);
    }
    //Bastian
    public void initiateText() {
        score = new Text("  Score: " + 0);
        scoreEnd = new Text("");
        root2.setTop(score);
        root2.setCenter(scoreEnd);
        score.setFont(new Font("Ariel", 32));
        score.setFill(Color.GREEN);
        scoreEnd.setFont(new Font("Ariel", 32));
        scoreEnd.setTextAlignment(TextAlignment.CENTER);
    }

    //Thomas
    public void setDims() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int maxWidth = (int) screenBounds.getWidth();
        int maxHeight = (int) (screenBounds.getHeight() - screenBounds.getHeight() / 10);
        int max = n < m ? m : n;
        blocksSize = (maxHeight < maxWidth ? maxHeight : maxWidth) / max;
        width = m * blocksSize;
        height = n * blocksSize;
        while (width + m <= maxWidth && height + n <= maxHeight) {
            blocksSize++;
            width += m;
            height += n;
        }
    }
    //Christian
    public void drawBackground() {
        gcBack.setFill(Color.BLACK);
        gcBack.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawGrid();
    }
    //Chris
    public Canvas[] drawGame() {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        Canvas background = new Canvas(width, height);
        gcBack = background.getGraphicsContext2D();
        Canvas snake = new Canvas(width, height);
        gcSnake = snake.getGraphicsContext2D();
        drawBackground();
        updateSnake();
        Canvas[] board = {background, snake, canvas};
        return board;
    }
}

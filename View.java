//run the following 2 commands to play:
//javac -classpath . *.java
//java View.java n m
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;
import javafx.stage.Screen;

public class View extends Application {
    private int width;
    private int height;
    private int blocksSize;
    private GraphicsContext gc;
    private Controller control;
    private Game game;
    private Text score, scoreEnd;
    private BorderPane root2;
    private int scoreCount;
    private int n,m;

    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        //https://openjfx.io/javadoc/17/javafx.graphics/javafx/application/Application.html#getParameters()
        //https://stackoverflow.com/questions/24611789/how-to-pass-parameters-to-javafx-application
        //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.Parameters.html#getRaw--
        //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.Parameters.html
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

    public void start(Stage primaryStage) {
        game = new Game(n, m);
        control = new Controller(game, this);
        Canvas canvas = new Canvas(width, height); //canvastørrelse angives
        gc = canvas.getGraphicsContext2D();
        drawBoard();
        StackPane root1 = new StackPane();
        root1.getChildren().addAll(getImageView("BackDrop.jpg"), canvas);
        root2 = new BorderPane();
        initiateText();
        Scene scene = new Scene(new StackPane(root1, root2));
        primaryStage.setTitle("Snake");
        scene.setOnKeyPressed(control::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateScore(int snakeLength) {
        scoreCount = snakeLength;
        score.setText("  Score: " + scoreCount);
    }

    public void message(boolean win) {
        String message = win ? "GOOD JOB" : "YOU LOSE";
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        scoreEnd.setText(message + "\nScore: " + scoreCount);
        score.setText("");
    }

    public void drawBoard() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); //clear Canvas https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas
        Object[][] state = game.getState();
        for(int i = 0; i < state.length; i++) {
            for(int k = 0; k < state[i].length; k++) {
                Object elem = state[i][k];
                if(elem instanceof Snake) {
                    gc.setFill(javafx.scene.paint.Color.RED);
                    gc.fillRect(k * blocksSize, i * blocksSize, blocksSize, blocksSize);
                }
                else if(elem == null) {

                } else {
                    gc.setFill(javafx.scene.paint.Color.ORANGE);
                    gc.fillRect(k * blocksSize, i * blocksSize, blocksSize, blocksSize);
                }
            }
        }
        for (int i = 0; i < width / blocksSize; i++) {
            gc.strokeLine(i * blocksSize, 0, i * blocksSize, height);
        }
        for (int i = 0; i < height / blocksSize; i++) {
            gc.strokeLine(0, i * blocksSize, width, i * blocksSize);
        }

    }

    public void initiateText() {
        score = new Text("  Score: " + 0);
        scoreEnd = new Text("");
        root2.setTop(score);
        root2.setCenter(scoreEnd);
        score.setFont(new Font("Ariel", 32));
        scoreEnd.setFont(new Font("Ariel", 32));
        scoreEnd.setTextAlignment(TextAlignment.CENTER);
    }


    public ImageView getImageView(String pathString) {
        Image img = new Image(pathString);
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(height);
        imgV.setFitWidth(width);
        return imgV;
    }

    public void setDims() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int maxWidth = (int) screenBounds.getWidth();
        int maxHeight = (int) (screenBounds.getHeight() - screenBounds.getHeight() / 10);
        int max = n < m ? m : n;
        blocksSize = maxHeight / max;
        width = m * blocksSize;
        height = n * blocksSize;
        while (width + m <= maxWidth && height + n <= maxHeight) {
            blocksSize++;
            width += m;
            height += n;
        }
    }
}

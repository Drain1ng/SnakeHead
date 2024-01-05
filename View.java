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
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class View extends Application {
    private int n = 20, m = n;
    private int width = 800;
    private int height = width;
    private int blocksSize = width/n;
    private GraphicsContext gc;
    private Controller control;
    private Game game;
    private Text score;
    private BorderPane root2;
    private int scoreCount;
    public static void main(String[] args) {
        //størrelsen af torussen skal angives som kommandolinjeparametre til
        //programmet, idet I er tilladt at antage n, m ∈ {5, · · · , 100}
        //implementer her (søg eventuelt på command line paramaters eller kig sidste kursus igennem)
        launch(args); // start JavaFX-engine
    }

    public void start(Stage primaryStage) {
        game = new Game(n, m);
        control = new Controller(game, this);
        Image img = new Image("BackDrop.jpg");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(height);
        imgV.setFitWidth(width);
        Canvas canvas = new Canvas(width, height); //canvastørrelse angives
        gc = canvas.getGraphicsContext2D();
        drawBoard();

        score = new Text(15, 35, "Score: " + 0);
        score.setFont(new Font("Ariel", 32));
        score.setFill(javafx.scene.paint.Color.BLACK); //https://docs.oracle.com/javafx/2/ui_controls/label.htm har ændret til text

        StackPane root1 = new StackPane();
        root1.getChildren().addAll(imgV, canvas);
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

    public void deathMessage() {
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        score.setText("HAHAHA TABER!\nScore: " + scoreCount);
        score.setTextAlignment(TextAlignment.CENTER);
        root2.setCenter(score);
    }

    public void drawBoard() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); //clear Canvas https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas

        for(int i = 0; i < game.getN(); i++) {
            for(int k = 0; k < game.getM(); k++) {
                Object elem = game.getState()[k][i];
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

}

//run the following 2 commands to play:
//javac -classpath . *.java
//java View.java n m
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import java.io.File;
import javafx.scene.media.*;
import java.util.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.PipedReader;
import java.util.*;
import javax.swing.Action;
import javafx.stage.Screen;
import javafx.util.Duration;

public class View extends Application {
    private int width;
    private int height;
    private int blocksSize;
    private GraphicsContext gc;
    private GraphicsContext gcBack;
    private GraphicsContext gcSnake;
    private Controller control;
    private Game game;
    private Text score;
    private BorderPane root2;
    private int n,m;
    private Image head, apple;
    private ImageView headV, appleV;
    private SnapshotParameters parameters;
    private Stage primaryStage;
    private Initiatescenes sceneMENU;
    private StackPane root1;
    private Scene gameScene;

    public static void main(String[] args) {
        launch(args);
    }

    //https://openjfx.io/javadoc/17/javafx.graphics/javafx/application/Application.html#getParameters()
    //https://stackoverflow.com/questions/24611789/how-to-pass-parameters-to-javafx-application
    //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.Parameters.html#getRaw--
    //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.Parameters.html
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



    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        game = new Game(n, m);
        control = new Controller(game, this);
        sceneMENU = new Initiatescenes();
        initiateButtons();
        initiateGameStart();
        showStartMenu();
    }

    public void playGame() {
        updateSnake(0);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(gameScene);
        control.startGame();
    }

    
    public void showStartMenu() {
        primaryStage.setTitle("Snake");
        primaryStage.setScene(sceneMENU.getMenu());
        primaryStage.show();
    }

    public void showGameDiff() {
        primaryStage.setTitle("Difficulties");
        primaryStage.setScene(sceneMENU.getNewGame());
    }


    public void showSettings() {
        primaryStage.setTitle("Settings");
        primaryStage.setScene(sceneMENU.getSettings());
    }

    public void initiateButtons() {
        sceneMENU.getGameDiffBTN().setOnAction(control::showGamediffBTN);
        sceneMENU.getSettingsMenuBTN().setOnAction(control::showSettingsBTN);
        sceneMENU.getLeaderboardBTN().setOnAction(control::showLeaderBoardBTN);
        sceneMENU.normalGameBTN().setOnAction(control::startNormalGame);
        sceneMENU.gameDiffMenuBackBTN().setOnAction(control::mainmenuscreen);
        sceneMENU.settingsMenuBackBTN().setOnAction(control::mainmenuscreen);
        sceneMENU.getRetryBTN().setOnAction(control::restart);
    }


    public void updateScore() {
        score.setText("  Score: " + game.getScore());
    }

    public void showEndGame(boolean win) {
        String message = (win ? "GAME WON" : "GAME LOST") + "\n Score: " + game.getScore() ;
        sceneMENU.getEndText().setText(message);
        primaryStage.setTitle("Endgame");
        primaryStage.setScene(sceneMENU.getEndScene());
        centerPrimaryStage();
        primaryStage.show();
    }

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
    public void updateSnake(int deg) {
        gcSnake.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        List<Point> body = game.getBody();
        Point food = game.getFood();
        for (Point snake : body) {
            if(game.getSnakeHead() == snake) {
                gcSnake.drawImage(rotateImage(deg), snake.getX() * blocksSize, snake.getY() * blocksSize, blocksSize, blocksSize);
            } else {
                gcSnake.setFill(Color.rgb(0, 119, 0)); //samme value som slange billede
                gcSnake.fillRoundRect(snake.getX() * blocksSize + 0.05 * blocksSize, snake.getY() * blocksSize + 0.05 * blocksSize, blocksSize * 0.9, blocksSize* 0.90, blocksSize * 0.5, blocksSize * 0.5);
            }
        }
        gcSnake.drawImage(apple, food.getX() * blocksSize, food.getY() * blocksSize, blocksSize, blocksSize);
    }

    public void initiateText() {
        score = new Text("  Score: " + 0);
        root2.setTop(score);
        score.setFont(new Font("Ariel", 32));
        score.setFill(Color.GREEN);
    }

    public void initiatePicture() {
        parameters = new SnapshotParameters();
        head = new Image("Head.jpg");
        headV = new ImageView(head);
        apple = new Image("Apple.jpg");
    }

    public Image rotateImage(int deg) {
        headV.setRotate(deg);
        parameters.setFill(Color.TRANSPARENT);
        return headV.snapshot(parameters, null);
    }

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

    public void centerPrimaryStage() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height * 1.1) / 2);
    }

    public void drawBackground() {
        gcBack.setFill(Color.BLACK);
        gcBack.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawGrid();
    }

    public void initiateGameStart() {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        Canvas background = new Canvas(width, height);
        gcBack = background.getGraphicsContext2D();
        Canvas snake = new Canvas(width, height);
        gcSnake = snake.getGraphicsContext2D();
        root1 = new StackPane();
        drawBackground();
        initiatePicture();
        root1.getChildren().addAll(background, snake, canvas);
        root2 = new BorderPane();
        initiateText();
        gameScene = new Scene(new StackPane(root1, root2));
        gameScene.setOnKeyPressed(control::handleKeyPress);
    }
}

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
    private Text score, scoreEnd;
    private BorderPane root2;
    private int n,m;
    private Image head, apple;
    private ImageView headV, appleV;
    private SnapshotParameters parameters;
    private Stage primaryStage;

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
        showStartMenu();
    }

    public void showStartMenu() {
        BorderPane menu = new BorderPane();
        Button newGame = new Button("New Game");
        newGame.setMinWidth(200);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showGameDiff();
            }
        });

        Button settings = new Button("Settings");
        settings.setMinWidth(200);

        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSettings();
            }
        });
        Button highScores = new Button("High Scores");
        highScores.setMinWidth(200);
        highScores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //showLeaderBoard();
            }
        });
        VBox mainbtns = new VBox(newGame, settings, highScores);
        mainbtns.setAlignment(Pos.CENTER);
        mainbtns.setSpacing(5);
        menu.setCenter(mainbtns);
        Scene scene = new Scene(menu, 600, 300);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void playGame() {
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
        centerPrimaryStage();
        primaryStage.show();
        control.startGame();
    }

    public void showGameDiff() {

        BorderPane root = new BorderPane();
        Button easy = new Button("Easy - Haha, NOOB");
        easy.setMinWidth(300);
        Button normal = new Button("Normal");
        normal.setMinWidth(300);
        normal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playGame();
            }
        });
        Button hard = new Button("When I spot Chris, my pena is ____");
        hard.setMinWidth(300);
        Button extreme = new Button("Extreme");
        extreme.setMinWidth(300);

        Image back = new Image("BackButton.jpg");
        ImageView view = new ImageView(back);
        view.setFitHeight(20);
        view.setFitWidth(20);
        Button backBtn = new Button();
        backBtn.setPrefSize(20, 20);
        backBtn.setGraphic(view);

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showStartMenu();
            }
        });

        VBox difficulties = new VBox(easy, normal, hard, extreme);
        difficulties.setAlignment(Pos.CENTER);
        difficulties.setSpacing(5);
        root.setCenter(difficulties);
        root.setTop(backBtn);
        Scene gameDiffs = new Scene(root, 600, 300);
        primaryStage.setTitle("Difficulties");
        primaryStage.setScene(gameDiffs);
        primaryStage.show();
    }


    public void showSettings() {

        Label heightCaption = new Label("Board Height");
        Slider height = new Slider(5, 100, 5);
        height.setShowTickMarks(true);
        height.setShowTickLabels(true);
        height.setMajorTickUnit(5);
        height.setBlockIncrement(10);
        height.snapToTicksProperty();
        Label heightValue = new Label(Double.toString(height.getValue()));

        Label widthCaption = new Label("Board Width");
        Slider width = new Slider(5, 100, 5);
        width.setShowTickMarks(true);
        width.setShowTickLabels(true);
        width.setMajorTickUnit(5);
        Label widthValue = new Label(Double.toString((width.getValue())));

        CheckBox music = new CheckBox("Music");
        CheckBox soundEffects = new CheckBox("Sound Effects");

        Image back = new Image("BackButton.jpg");
        ImageView view = new ImageView(back);
        view.setFitHeight(20);
        view.setFitWidth(20);
        Button backBtn = new Button();
        backBtn.setPrefSize(20, 20);
        backBtn.setGraphic(view);

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showStartMenu();
            }
        });

        BorderPane root = new BorderPane();
        VBox gameSettings = new VBox(height, width, music, soundEffects);
        gameSettings.setAlignment(Pos.CENTER);
        root.setTop(backBtn);
        root.setCenter(gameSettings);
        Scene settings = new Scene(root, 600, 300);
        primaryStage.setTitle("Settings");
        primaryStage.setScene(settings);
        primaryStage.show();
    }


    public void updateScore() {
        score.setText("  Score: " + game.getScore());
    }

    public void message(boolean win) {
        String message = win ? "GOOD JOB" : "YOU LOSE";
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        scoreEnd.setText(message + "\nScore: " + game.getScore());
        score.setText("");
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
        scoreEnd = new Text("");
        root2.setTop(score);
        root2.setCenter(scoreEnd);
        score.setFont(new Font("Ariel", 32));
        score.setFill(Color.GREEN);
        scoreEnd.setFont(new Font("Ariel", 32));
        scoreEnd.setTextAlignment(TextAlignment.CENTER);
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

    public Canvas[] drawGame() {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        Canvas background = new Canvas(width, height);
        gcBack = background.getGraphicsContext2D();
        Canvas snake = new Canvas(width, height);
        gcSnake = snake.getGraphicsContext2D();
        initiatePicture();
        drawBackground();
        updateSnake(0);
        Canvas[] board = {background, snake, canvas};
        return board;
    }
}

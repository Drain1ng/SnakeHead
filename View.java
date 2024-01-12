//run the following 2 commands to play:
//javac -classpath . *.java
//java View.java n m
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import javafx.scene.media.*;
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
    private Text score;
    private BorderPane root2;
    private int n,m;
    private Image head, apple;
    private ImageView headV;
    private SnapshotParameters parameters;
    private Stage primaryStage;
    private Initiatescenes sceneMENU;
    private StackPane root1;
    private Scene gameScene;
    private AudioClip eatSFX, dieSFX;
    private MediaPlayer menuMusic, gameMusic;

    public static void main(String[] args) {
        launch(args);
    }

    //https://openjfx.io/javadoc/17/javafx.graphics/javafx/application/Application.html#getParameters()
    //https://stackoverflow.com/questions/24611789/how-to-pass-parameters-to-javafx-application
    //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.Parameters.html#getRaw--
    //https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.Parameters.html
    public void init() {
        n = 10;
        m = 10;
        setDims();
    }
    
    //Chris, Christian, Bastian
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        game = new Game(n, m);
        control = new Controller(game, this);
        sceneMENU = new Initiatescenes(game,this);
        initiateSound();
        initiateButtons();
        initiateGameStart();
        showStartMenu();
        centerPrimaryStage(300, 600);
        primaryStage.setTitle("SNAKE");
        primaryStage.show();
    }
    
    //Christian
    public void playGame() {
        updateScore();
        updateSnake(0);
        primaryStage.setScene(gameScene);
        centerGame();
        playMusic(false);
        control.startGame();
    }

    //Christian
    public void showStartMenu() {
        primaryStage.setScene(sceneMENU.getMenu());
        playMusic(true);
        
    }
    
    //Christian
    public void showGameDiff() {
        primaryStage.setScene(sceneMENU.getNewGame());
    }

    //Christian
    public void showSettings() {
        primaryStage.setScene(sceneMENU.getSettings());
    }

    //Christian
    public void showLeaderBoard() {
        sceneMENU.updateScoresText();
        primaryStage.setScene(sceneMENU.getLeaderboard());
    }

    //Chris
    public void initiateButtons() {
        sceneMENU.getGameDiffBTN().setOnAction(control::showGamediffBTN);
        sceneMENU.getSettingsMenuBTN().setOnAction(control::showSettingsBTN);
        sceneMENU.getLeaderboardBTN().setOnAction(control::showLeaderBoardBTN);
        sceneMENU.getStartNormalBTN().setOnAction(control::startNormalGame);
        sceneMENU.getStartEasyBTN().setOnAction(control::startEasyGame);
        sceneMENU.getStartHardBTN().setOnAction(control::startHardGame);
        sceneMENU.getStartExtremeBTN().setOnAction(control::startExtremeGame);
        sceneMENU.gameDiffMenuBackBTN().setOnAction(control::mainmenuscreen);
        sceneMENU.settingsMenuBackBTN().setOnAction(control::mainmenuscreen);
        sceneMENU.endGameBackBTN().setOnAction(control::mainmenuscreen);
        sceneMENU.getRetryBTN().setOnAction(control::restart);
        sceneMENU.getLeaderboardBackButton().setOnAction(control::mainmenuscreen);
        sceneMENU.musicCheckBox().setOnAction(control::soundOff);
    }

    //Bastian
    public void updateScore() {
        score.setText("  Score: " + game.getScore());
    }

    //Chris, Bastian
    public void showEndGame(boolean win) {
        String message = (win ? "GAME WON" : "GAME LOST") + "\n Score: " + game.getScore() ;
        sceneMENU.getEndText().setText(message);
        centerPrimaryStage(300, 600);
        primaryStage.setScene(sceneMENU.getEndScene());
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

    //Chris, Bastian
    public void updateSnake(int deg) {
        gcSnake.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        List<Point> body = game.getBody();
        Point food = game.getFood();
        for (Point snake : body) {
            if(game.getSnakeHead() == snake) {
                gcSnake.drawImage(rotateImage(deg), snake.getX() * blocksSize, snake.getY() * blocksSize, blocksSize, blocksSize);
            } else {
                gcSnake.setFill(Color.rgb(0, 119, 0));
                gcSnake.fillRoundRect(snake.getX() * blocksSize + 0.05 * blocksSize, snake.getY() * blocksSize + 0.05 * blocksSize, blocksSize * 0.9, blocksSize* 0.90, blocksSize * 0.5, blocksSize * 0.5);
            }
        }
        gcSnake.drawImage(apple, food.getX() * blocksSize, food.getY() * blocksSize, blocksSize, blocksSize);
    }

    //Bastian
    public void initiateText() {
        score = new Text("  Score: " + 0);
        root2.setTop(score);
        score.setFont(new Font("Ariel", 32));
        score.setFill(Color.GREEN);
    }
    //Bastian
    public void initiatePicture() {
        parameters = new SnapshotParameters();
        head = new Image("Head.jpg");
        headV = new ImageView(head);
        apple = new Image("apple.png");
    }
    //Bastian + all sound methods
    public void initiateSound() {
        //SFX
        String eatSound = new File("Eat.wav").toURI().toString();
        eatSFX = new AudioClip(eatSound);
        String deathSound = new File("Death.wav").toURI().toString();
        dieSFX = new AudioClip(deathSound);
        //music
        String menuMusicFile = new File("MenuMusic3.mp3").toURI().toString();
        Media menuTrack = new Media(menuMusicFile);
        menuMusic = new MediaPlayer(menuTrack);
        String gameMusicFile = new File("GameMusic2.mp3").toURI().toString();
        Media gameTrack = new Media(gameMusicFile);
        gameMusic = new MediaPlayer(gameTrack);
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }
    public void playEatSFX() {
        if (sceneMENU.SFKCheckBox().isSelected()) {
            eatSFX.play();
        }
    }

    public void playDieSFX() {
        if (sceneMENU.SFKCheckBox().isSelected()) {
            dieSFX.play();
        }
    }

    public void musicOff() {
        if (sceneMENU.musicCheckBox().isSelected()) {
            menuMusic.play();
        } else {
            menuMusic.pause();
        }
    }

    public void playMusic(boolean menu) {
        dimMusic(true);
        if (menu) {
            gameMusic.pause();
            if (sceneMENU.musicCheckBox().isSelected()) {
                menuMusic.play();
            }
        } else {
            menuMusic.pause();
            if (sceneMENU.musicCheckBox().isSelected()) {
                gameMusic.play();
            }
        }
    }
    
    public void dimMusic(boolean playing) {
        if(playing) {
            gameMusic.setVolume(1);
        } else {
            gameMusic.setVolume(0.3);
        }
    }

    //Bastian
    public Image rotateImage(int deg) {
        headV.setRotate(deg);
        parameters.setFill(Color.TRANSPARENT);
        return headV.snapshot(parameters, null);
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
    
    //Thomas
    public void centerGame() {
        centerPrimaryStage(this.height,this.width);
    }
    
    //https://stackoverflow.com/questions/29350181/how-to-center-a-window-properly-in-java-fx
    //Thomas
    public void centerPrimaryStage(int height, int width) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height * 1.1) / 2);
    }

    //Christian
    public void drawBackground() {
        gcBack.setFill(Color.BLACK);
        gcBack.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawGrid();
    }

    //Chris
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

    public double getUserM() {
        return sceneMENU.getUserM();
    }

    public double getUserN() {
        return sceneMENU.getUserN();
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public void setN(int newN) {
        n = newN;
    }

    public void setM(int newM) {
        m = newM;
    }
}

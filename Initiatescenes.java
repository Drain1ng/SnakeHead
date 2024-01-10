import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class Initiatescenes {
    private Scene gameDiffs;
    private Scene settings;
    private Scene mainmenu;
    private Scene endGame;
    private Scene leaderboard;
    private Button gameDiffMenu;
    private Button settingsMenuBTN;
    private Button leaderboardBTN;
    private Button startNormalBTN;
    private Button gameDiffMenuBackBTN;
    private Button settingsMenuBackBTN;
    private Button endGameBackBTN;
    private Button leaderboardBackBTN;
    private Text endText;
    private Text scoresText;
    private Button retryBTN;
    private Slider nSlider;
    private Slider mSlider;
    private Game game;


    public Initiatescenes(Game game) {
        this.game = game;
        initiateStartmenu();
        initiateNewGame();
        initiateSettings();
        initiateEndGame();
        initiateLeaderBoard();
    }

    public Text getEndText() {
        return endText;
    }

    public Button getRetryBTN() {
        return retryBTN;
    }

    public Button getGameDiffBTN() {
        return gameDiffMenu;
    }

    public Button getSettingsMenuBTN() {
        return settingsMenuBTN;
    }

    public Button getLeaderboardBTN() {
        return leaderboardBTN;
    }

    public Button normalGameBTN() {
        return startNormalBTN;
    }

    public Button gameDiffMenuBackBTN() {
        return gameDiffMenuBackBTN;
    }

    public Button settingsMenuBackBTN() {
        return settingsMenuBackBTN;
    }

    public Button endGameBackBTN() {
        return endGameBackBTN;
    }

    public Scene getMenu() {
        return mainmenu;
    }

    public Scene getNewGame() {
        return gameDiffs;
    }

    public Scene getSettings() {
        return settings;
    }

    public Scene getEndScene() {
        return endGame;
    }

    public double getUserN() {
        return nSlider.getValue();
    }

    public double getUserM() {
        return mSlider.getValue();
    }

    public Scene getLeaderboard() {
        return leaderboard;
    }

    public Button getLeaderboardBackButton() {
        return leaderboardBackBTN;
    }

    // Make Scenes
    public void initiateStartmenu() {
        BorderPane menu = new BorderPane();
        gameDiffMenu = new Button("New Game");
        gameDiffMenu.setMinWidth(200);
        settingsMenuBTN = new Button("Settings");
        settingsMenuBTN.setMinWidth(200);
        leaderboardBTN = new Button("High Scores");
        leaderboardBTN.setMinWidth(200);
        VBox mainbtns = new VBox(gameDiffMenu, settingsMenuBTN, leaderboardBTN);
        mainbtns.setAlignment(Pos.CENTER);
        mainbtns.setSpacing(5);
        menu.setCenter(mainbtns);
        mainmenu = new Scene(menu, 600, 300);
    }

    public void initiateNewGame() {
        BorderPane root = new BorderPane();
        // Not finished button(EASY)
        Button easy = new Button("Easy - Haha, NOOB");
        easy.setMinWidth(300);
        //
        startNormalBTN = new Button("Normal");
        startNormalBTN.setMinWidth(300);
        // Not finished button(HARD)
        Button hard = new Button("When I spot Chris, my pena is ____");
        hard.setMinWidth(300);
        // Not finished button(EXTREME)
        Button extreme = new Button("Extreme");
        extreme.setMinWidth(300);

        Image back = new Image("BackButton.jpg");
        ImageView view = new ImageView(back);
        view.setFitHeight(20);
        view.setFitWidth(20);
        gameDiffMenuBackBTN = new Button();
        gameDiffMenuBackBTN.setPrefSize(20, 20);
        gameDiffMenuBackBTN.setGraphic(view);

        VBox difficulties = new VBox(easy, startNormalBTN, hard, extreme);
        difficulties.setAlignment(Pos.CENTER);
        difficulties.setSpacing(5);
        root.setTop(gameDiffMenuBackBTN);
        root.setCenter(difficulties);
        gameDiffs = new Scene(root, 600, 300);
    }

    public void initiateSettings() {
        Label heightCaption = new Label("Board Height");
        nSlider = new Slider(5, 100, 5);
        nSlider.setShowTickMarks(true);
        nSlider.setMajorTickUnit(5);
        nSlider.setBlockIncrement(1);
        nSlider.setSnapToTicks(true);
        nSlider.setMaxWidth(300);
        Label heightValue = new Label(Integer.toString(5));
        nSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                heightValue.setText(Integer.toString((int)nSlider.getValue()));
            }

        });

        HBox heightHBox = new HBox(heightCaption, nSlider, heightValue);
        heightHBox.setAlignment(Pos.CENTER);
        heightHBox.setSpacing(20);

        Label widthCaption = new Label("Board Width");
        mSlider = new Slider(5, 100, 5);
        mSlider.setShowTickMarks(true);
        mSlider.setMajorTickUnit(5);
        mSlider.setBlockIncrement(1);
        mSlider.setSnapToTicks(true);
        mSlider.setMaxWidth(300);
        Label widthValue = new Label(Integer.toString(5));
        mSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                widthValue.setText(Integer.toString((int)mSlider.getValue()));
            }

        });

        HBox widthHBox = new HBox(widthCaption, mSlider, widthValue);
        widthHBox.setAlignment(Pos.CENTER);
        widthHBox.setSpacing(20);

        CheckBox music = new CheckBox("Music");
        CheckBox soundEffects = new CheckBox("Sound Effects");

        Image back = new Image("BackButton.jpg");
        ImageView view = new ImageView(back);
        view.setFitHeight(20);
        view.setFitWidth(20);
        settingsMenuBackBTN = new Button();
        settingsMenuBackBTN.setPrefSize(20, 20);
        settingsMenuBackBTN.setGraphic(view);
        BorderPane root = new BorderPane();
        VBox gameSettings = new VBox(heightHBox, widthHBox, music, soundEffects);
        gameSettings.setAlignment(Pos.CENTER);
        root.setTop(settingsMenuBackBTN);
        root.setCenter(gameSettings);
        settings = new Scene(root, 600, 300);
    }

    public void initiateEndGame() {
        VBox gameoverBox = new VBox();
        gameoverBox.setSpacing(10);
        endText = new Text("");
        retryBTN = new Button("Play again");
        endGameBackBTN = new Button("Back To Main Menu");
        retryBTN.setMinWidth(150);
        endGameBackBTN.setMinWidth(150);
        gameoverBox.getChildren().addAll(endText, retryBTN, endGameBackBTN);
        gameoverBox.setAlignment(Pos.CENTER);
        endText.setTextAlignment(TextAlignment.CENTER);
        endText.setFont(new Font("STENCIL", 50));
        endGame = new Scene(gameoverBox, 600, 300);
    }

    public void initiateLeaderBoard() {
        scoresText = new Text("");
        updateScoresText();
        Image back = new Image("BackButton.jpg");
        ImageView imgView = new ImageView(back);
        imgView.setFitHeight(20);
        imgView.setFitWidth(20);
        leaderboardBackBTN = new Button();
        BorderPane root = new BorderPane(scoresText);
        root.setTop(leaderboardBackBTN);
        leaderboardBackBTN.setPrefSize(20, 20);
        leaderboardBackBTN.setGraphic(imgView);
        leaderboard = new Scene(root, 600, 300);
    }

    public void updateScoresText() {
        String str = "HIGH SCORES:";
        for (int i : game.getLeaderBoardScores()) {
            str += "\n" + i;
        }
        scoresText.setText(str);
    }

}

//Mainly the scenes have been made by Christian and Chris.
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Initiatescenes {
    private Scene gameDiffs;
    private Scene settings;
    private Scene mainmenu;
    private Scene endGame;
    private Scene leaderboard;
    private Button gameDiffMenu;
    private Button settingsMenuBTN;
    private Button leaderboardBTN;
    private Button startEasyBTN;
    private Button startNormalBTN;
    private Button startHardBTN;
    private Button startExtremeBTN;
    private Button gameDiffMenuBackBTN;
    private Button settingsMenuBackBTN;
    private Button endGameBackBTN;
    private Button leaderboardBackBTN;
    private Text endText;
    private Text scoresText;
    private Button retryBTN;
    private Slider nSlider;
    private Slider mSlider;
    private CheckBox music, soundEffects;
    private Game game;
    private View view;


    public Initiatescenes(Game game, View view) {
        this.view = view;
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

    public Button getStartNormalBTN() {
        return startNormalBTN;
    }

    public Button getStartEasyBTN() {
        return startEasyBTN;
    }

    public Button getStartHardBTN() {
        return startHardBTN;
    }

    public Button getStartExtremeBTN() {
        return startExtremeBTN;
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

    public CheckBox musicCheckBox() {
        return music;
    }

    public CheckBox SFKCheckBox() {
        return soundEffects;
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

    public BorderPane setTitle(String title) {
        BorderPane titleBP = new BorderPane();
        Text titleText = new Text(title);
        titleBP.setTop(titleText);
        titleText.setFont(new Font("Stencil", 55));
        BorderPane.setAlignment(titleText, Pos.CENTER);
        return titleBP;
    }

    public void initiateStartmenu() {
        BorderPane titleBP = setTitle("WELCOME TO SNAKE");
        gameDiffMenu = new Button("New Game");
        gameDiffMenu.setMinWidth(200);
        settingsMenuBTN = new Button("Settings");
        settingsMenuBTN.setMinWidth(200);
        leaderboardBTN = new Button("High Scores");
        leaderboardBTN.setMinWidth(200);
        VBox mainbtns = new VBox(gameDiffMenu, settingsMenuBTN, leaderboardBTN);
        mainbtns.setAlignment(Pos.CENTER);
        mainbtns.setSpacing(10);
        titleBP.setCenter(mainbtns);
        mainmenu = new Scene(new StackPane(titleBP), 600, 300);
    }

    public void initiateNewGame() {
        BorderPane newGameMenu = new BorderPane();
        BorderPane titleBP = setTitle("SELECT DIFFICULTY");
        startEasyBTN = new Button("EASY");
        startEasyBTN.setMinWidth(300);
        startNormalBTN = new Button("NORMAL");
        startNormalBTN.setMinWidth(300);
        startHardBTN = new Button("HARD");
        startHardBTN.setMinWidth(300);
        startExtremeBTN = new Button("EXTREME");
        startExtremeBTN.setMinWidth(300);
        VBox difficulties = new VBox(startEasyBTN, startNormalBTN, startHardBTN, startExtremeBTN);
        difficulties.setAlignment(Pos.CENTER);
        difficulties.setSpacing(10);
        gameDiffMenuBackBTN = new Button();
        makeBackButton(gameDiffMenuBackBTN);
        newGameMenu.setTop(gameDiffMenuBackBTN);
        newGameMenu.setCenter(difficulties);
        gameDiffs = new Scene(new StackPane(titleBP, newGameMenu), 600, 300);
    }

    public void initiateSettings() {

        BorderPane settingsMenu = new BorderPane();
        BorderPane titleBP = setTitle("SETTINGS");

        Label heightCaption = new Label("Board Height");
        nSlider = new Slider(5, 100, view.getN());
        nSlider.setShowTickMarks(true);
        nSlider.setMajorTickUnit(5);
        nSlider.setBlockIncrement(1);
        nSlider.setSnapToTicks(true);
        nSlider.setMaxWidth(300);
        Label heightValue = new Label(Integer.toString(view.getN()));
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
        mSlider = new Slider(5, 100, view.getM());
        mSlider.setShowTickMarks(true);
        mSlider.setMajorTickUnit(5);
        mSlider.setBlockIncrement(1);
        mSlider.setSnapToTicks(true);
        mSlider.setMaxWidth(300);
        Label widthValue = new Label(Integer.toString(view.getM()));
        mSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                widthValue.setText(Integer.toString((int)mSlider.getValue()));
            }

        });

        HBox widthHBox = new HBox(widthCaption, mSlider, widthValue);
        widthHBox.setAlignment(Pos.CENTER);
        widthHBox.setSpacing(20);
        music = new CheckBox("Music");
        soundEffects = new CheckBox("Sound Effects");
        music.setSelected(true);
        soundEffects.setSelected(true);
        settingsMenuBackBTN = new Button();
        makeBackButton(settingsMenuBackBTN);
        
        VBox gameSettings = new VBox(heightHBox, widthHBox, music, soundEffects);
        gameSettings.setAlignment(Pos.CENTER);
        settingsMenu.setTop(settingsMenuBackBTN);
        settingsMenu.setCenter(gameSettings);
        settings = new Scene(new StackPane(titleBP, settingsMenu), 600, 300);
    }
    
    //Chris
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

    //Bastian, Chris
    public void initiateLeaderBoard() {
        BorderPane hsMenu = new BorderPane();
        BorderPane titleBP = setTitle("HIGH SCORES");
        scoresText = new Text("");
        scoresText.setFont(new Font("STENCIL", 30));
        updateScoresText();
        leaderboardBackBTN = new Button();
        makeBackButton(leaderboardBackBTN);
        VBox root = new VBox(scoresText);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        hsMenu.setTop(leaderboardBackBTN);
        hsMenu.setCenter(root);
        leaderboard = new Scene(new StackPane(titleBP, hsMenu), 600, 300);
    }

    //Christian
    public Button makeBackButton(Button backBtn) {
        Image back = new Image("BackButton.jpg");
        ImageView imgView = new ImageView(back);
        imgView.setFitHeight(20);
        imgView.setFitWidth(20);
        backBtn.setPrefSize(20, 20);
        backBtn.setGraphic(imgView);
        return backBtn;
    }

    //Bastian
    public void updateScoresText() {
        String str = "";
        for (int i : game.getLeaderBoardScores()) {
            str += i + "\n";
        }
        scoresText.setText(str);
    }

}

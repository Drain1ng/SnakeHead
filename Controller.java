import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Controller {
    private Game game;
    private View view;
    private double loopInterval;
    private boolean isPaused;
    private Timeline timeline;
    private int lastScore;

    //Thomas
    public void startGame() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(loopInterval), this::updateGame));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
        isPaused = false;
    }
    //Thomas, Bastian
    public void togglePause() {
        if (isPaused) {
            timeline.play();
            view.dimMusic(true);
        } else {
            timeline.pause();
            view.dimMusic(false);
        }
        isPaused = !isPaused;
    }

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
    }

    //Thomas, Bastian
    public void updateGame(Event event) {
        game.update();
        view.updateScore();
        int score = game.getScore();
        if (lastScore != score) {
            lastScore = score;
            view.playEatSFX();
        }
        if (game.isWon()) {
            endGame(true);
            view.dimMusic(false);
        } else if(!game.isLost()) {
            view.updateSnake(getDirectionDegrees());
        } else {
            endGame(false);
            view.playDieSFX();
            view.dimMusic(false);
        }
    }
    //Thomas, Bastian
    public void endGame(boolean win) {
        timeline.stop();
        game.updateLeaderBoard(game.getScore());
        view.showEndGame(win);
    }
    
    //Bastian
    public int getDirectionDegrees() {
        switch (game.getDirection()) {
            case UP:
                return 90;
            case DOWN:
                return 270;
            case LEFT:
                return 0;
            case RIGHT:
                return 180;
            default:
                return 0;
        }
    }
    //Bastian, Thomas
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            togglePause();
        }
        if (!isPaused) {
            switch (event.getCode()) {
                case UP, W:
                    game.setDirIfValid(Direction.UP);
                    break;
                case DOWN, S:
                    game.setDirIfValid(Direction.DOWN);
                    break;
                case LEFT, A:
                    game.setDirIfValid(Direction.LEFT);
                    break;
                case RIGHT, D:
                    game.setDirIfValid(Direction.RIGHT);
                    break;
            }
        }

    }
    //Chris, Bastian
    public void restart(ActionEvent event) {
        restart();
        view.dimMusic(true);
    }
    //Thomas, Chris
    public void restart() {
        game.restartGame();
        lastScore = game.getScore();
        view.playGame();
    }

    //Christian, Chris
    public void showGamediffBTN(ActionEvent event) {
        view.showGameDiff();
    }

    public void showSettingsBTN(ActionEvent event) {
        view.showSettings();
    }

    public void showLeaderBoardBTN(ActionEvent event) {
        view.showLeaderBoard();
    }
    //GAMEDIFFICULTY BUTTONS. Only extreme scales to board size
    //Christian, Thomas, Chris
    public void startEasyGame(ActionEvent event) {
        this.loopInterval = 0.7;
        restart();
    }

    public void startNormalGame(ActionEvent event) {
        this.loopInterval = 0.4;
        restart();
    }

    public void startHardGame(ActionEvent event) {
        this.loopInterval = 0.25;
        restart();
    }

    public void startExtremeGame(ActionEvent event) {
        this.loopInterval = 0.25 / ((double) Math.sqrt(game.getBoardSize()) / 5);
        restart();
    }

    //Bastian
    public void soundOff(ActionEvent event) {
        view.musicOff();
    }

    //BACKBUTTONS Thomas, Chris
    public void mainmenuscreen(ActionEvent event) {
        int newN = (int) view.getUserN();
        int newM = (int) view.getUserM();
        if (newM != view.getM() || newN != view.getN()) {
            game.restartGame(newN, newM);
            view.setM(newM);
            view.setN(newN);
            view.setDims();
            view.initiateGameStart();
        }
        view.showStartMenu();
    }
}

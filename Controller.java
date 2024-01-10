import java.util.InputMismatchException;

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

    public void startGame() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(loopInterval), this::updateGame));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
        isPaused = false;
    }

    public void togglePause() {
        if (isPaused) {
            timeline.play();
        } else {
            timeline.pause();
        }
        isPaused = !isPaused;
    }

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
    }

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
        } else if(!game.isLost()) {
            view.updateSnake(getDirectionDegrees());
        } else {
            endGame(false);
            view.playDieSFX();
        }
    }

    public void endGame(boolean win) {
        timeline.stop();
        game.updateLeaderBoard(game.getScore());
        view.showEndGame(win);
    }

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
    //Restart game
    public void restart(ActionEvent event) {
        game.restartGame();
        view.playGame();
    }

    //STARTMENU BUTTONS
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
    public void startEasyGame(ActionEvent event) {
        this.loopInterval = 0.7;
        view.playGame();
    }

    public void startNormalGame(ActionEvent event) {
        this.loopInterval = 0.4;
        view.playGame();
    }

    public void startHardGame(ActionEvent event) {
        this.loopInterval = 0.25;
        view.playGame();
    }

    public void startExtremeGame(ActionEvent event) {
        this.loopInterval = 0.25 / ((double) Math.sqrt(game.getBoardSize()) / 5);
        view.playGame();
    }



    //BACKBUTTONS
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






    /*
    public void newGameBtnHandle(ActionEvent event) {
        System.out.println(3);
        view.showGameDiff();
    }
    */

}

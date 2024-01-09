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

    public void startGame() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), this::updateGame));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
        isPaused = false;
    }

    public void pause() {
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
        if (game.isWon()) {
            view.message(true);
        } else if(!game.isLost()) {
            view.updateSnake(getDirectionDegrees());
        } else {
            view.message(false);
        }
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
            pause();
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


    /*
    public void newGameBtnHandle(ActionEvent event) {
        System.out.println(3);
        view.showGameDiff();
    }
    */

}

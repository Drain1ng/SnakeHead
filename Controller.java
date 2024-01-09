import java.util.InputMismatchException;

import javafx.scene.input.KeyEvent;

public class Controller {
    private Game game;
    private View view;

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W: game.setDirIfValid(Direction.UP); break;
            case DOWN, S: game.setDirIfValid(Direction.DOWN); break;
            case LEFT, A: game.setDirIfValid(Direction.LEFT); break;
            case RIGHT, D: game.setDirIfValid(Direction.RIGHT); break;
            default: return;
        }
        game.update();
        view.updateScore(game.getScore());
        if (game.isWon()) {
            view.message(true);
        } else if(!game.isLost()) {
            view.updateSnake();
        } else {
            view.message(false);
        }
    }
}

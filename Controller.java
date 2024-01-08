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
        try {
            switch (event.getCode()) {
                case UP, W: game.setDir(Direction.UP); break;
                case DOWN, S: game.setDir(Direction.DOWN); break;
                case LEFT, A: game.setDir(Direction.LEFT); break;
                case RIGHT, D: game.setDir(Direction.RIGHT); break;
                default: return;
            }
            game.update();
            view.updateScore(game.getScore());
            if (game.isGameWon()) {
                view.message(true);
            } else if(!game.isGameOver()) {
                view.updateSnake();
            } else {
                view.message(false);
            }
        } catch (IllegalArgumentException e) {
            // do nothing in case of invalid direction
        }
    }
}

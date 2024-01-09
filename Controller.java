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
        int deg = 0;
        try {
            switch (event.getCode()) {
                case UP, W: 
                    game.setDir(Direction.UP);
                    deg = 90;
                    break;
                case DOWN, S: 
                    game.setDir(Direction.DOWN);
                    deg = 270;
                    break;
                case LEFT, A: 
                    game.setDir(Direction.LEFT); 
                    break;
                case RIGHT, D: 
                    game.setDir(Direction.RIGHT);
                    deg = 180; 
                    break;
                default: return;
            }
            game.update();
            view.updateScore(game.getScore());
            if (game.isGameWon()) {
                view.message(true);
            } else if(!game.isGameOver()) {
                view.updateSnake(deg);
            } else {
                view.message(false);
            }
        } catch (InputMismatchException e) {
            // do nothing in case of invalid direction
        }
    }
}

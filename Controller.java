import java.util.InputMismatchException;

import javafx.event.ActionEvent;
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
            view.updateScore();
            if (game.isWon()) {
                view.message(true);
            } else if(!game.isLost()) {
                view.updateSnake(deg);
            } else {
                view.message(false);
            }
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
    }


    /*
    public void newGameBtnHandle(ActionEvent event) {
        System.out.println(3);
        view.showGameDiff();
    }
    */

}

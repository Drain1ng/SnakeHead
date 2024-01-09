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
        switch (event.getCode()) {
            case UP, W: 
                game.setDirIfValid(Direction.UP);
                deg = 90;
                break;
            case DOWN, S: 
                game.setDirIfValid(Direction.DOWN);
                deg = 270;
                break;
            case LEFT, A: 
                game.setDirIfValid(Direction.LEFT); 
                break;
            case RIGHT, D: 
                game.setDirIfValid(Direction.RIGHT);
                deg = 180; 
                break;
            default: return;
        }
        game.update();
        view.updateScore(game.getScore());
        if (game.isWon()) {
            view.message(true);
        } else if(!game.isLost()) {
            view.updateSnake(deg);
        } else {
            view.message(false);
        }
    }

    /* 
    public void newGameBtnHandle(ActionEvent event) {
        System.out.println(3);
        view.showGameDiff();
    }
    */
    
}

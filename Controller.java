import java.util.InputMismatchException;

import javafx.scene.input.KeyEvent;

public class Controller {
    private Game game;
    private View view;

    public Controller(Game game,View view) {
        this.game = game;
        this.view = view;
    }

    public void handleKeyPress(KeyEvent event) {
        try {
            switch (event.getCode()) {
                //er syntaks korrekt her?
                case UP: game.setDir(Direction.UP); break;
                case DOWN: game.setDir(Direction.DOWN); break;
                case LEFT: game.setDir(Direction.LEFT); break;
                case RIGHT: game.setDir(Direction.RIGHT); break;
                default: return;
            }
            game.update();
            if(!game.isGameOver()) {
                view.drawBoard();
            } else {
                view.drawGameOver();
            }
        } catch (InputMismatchException e) {
            // hvis der kommer en InputMismatchException (i.e. retningskiftet ikke er muligt), så gør intet
        }
    }
}

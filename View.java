import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class View extends Application {
    private int n = 20, m = n;
    private int width = 800;
    private int height = width;
    private int blocksSize = width/n;
    private GraphicsContext gc;
    private Controller control;
    private Game game;
    public static void main(String[] args) {
        //tørrelsen af torussen skal angives som kommandolinjeparametre til
        //programmet, idet I er tilladt at antage n, m ∈ {5, · · · , 100}
        //implementer her (søg eventuelt på command line paramaters eller kig sidste kursus igennem)
        launch(args); // start JavaFX-engine
    }

    public void start(Stage primaryStage) {
        game = new Game(n,m);
        control = new Controller(game,this);
        Image img = new Image("BackDrop.jpg");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(height);
        imgV.setFitWidth(width);
        Canvas canvas = new Canvas(width, height); //canvastørrelse angives
        gc = canvas.getGraphicsContext2D();
        drawBoard();
        StackPane root = new StackPane();
        root.getChildren().addAll(imgV, canvas);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Snake");
        scene.setOnKeyPressed(control::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void fillRed() {
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void drawBoard() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); //clear Canvas https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas

        for (int i = 0; i < width / blocksSize; i++) {
            gc.strokeLine(i * blocksSize, 0, i * blocksSize, width);
            gc.strokeLine(0, i * blocksSize, height, i * blocksSize);
        }
        for(int i = 0; i < game.getN(); i++) {
            for(int k = 0; k < game.getM(); k++) {
                Object elem = game.getState()[k][i];
                if(elem instanceof Snake) {
                    gc.setFill(javafx.scene.paint.Color.RED);
                    gc.fillRect(i * blocksSize, k * blocksSize, blocksSize, blocksSize);
                }
                else if(elem == null) {

                } else {
                    gc.setFill(javafx.scene.paint.Color.ORANGE);
                    gc.fillRect(i * blocksSize, k * blocksSize, blocksSize, blocksSize);
                }
            }
        }
    }

}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import java.util.*;
import javafx.stage.Screen;

public class Menu extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        BorderPane menu = new BorderPane();
        VBox mainBtns = new VBox();
        mainBtns.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10, 20, 10, 20));
        Button newGame = new Button("New Game");
        //menu.setTop(btn1);
        Button options = new Button("Options");
        //menu.setCenter(btn2);
        Button settings = new Button("Settings");
        //menu.setBottom(btn3);
        Button back = new Button("<-");
        //menu.setLeft(btn4);
        
        //menu.getChildren().addAll(btn1, btn2, btn3, btn4);
        mainBtns.getChildren().addAll(newGame, options, settings, back);
        menu.setCenter(mainBtns);
        
        Scene scene = new Scene(menu, 500, 300);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

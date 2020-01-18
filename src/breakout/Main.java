package breakout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application{
    /**
     * Start of the program.
     */

    Stage window;
    Scene scene1, scene2;

    public static void main (String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        //Label label1 = new Label("Welcome to BREAKOUT");
        //Label instructions = new Label("Use arrow keys to move, bounce ball to break bricks to win.");

        Button startButton;
        startButton = new Button();
        startButton.setText("Start Game");
        startButton.setLayoutX(120);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamePlay gameView = new GamePlay();
                try {
                    gameView.set_Level(1);
                    gameView.start(window);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Back Home Button
        Button backHomeButton = new Button("Back");
        backHomeButton.setOnAction(e -> window.setScene(scene1));
        backHomeButton.setLayoutX(40);
        backHomeButton.setLayoutY(40);

        //Rules Button
        Button rulesButton = new Button("Rules");
        rulesButton.setOnAction(e -> window.setScene(scene2));
        rulesButton.setLayoutX(120);
        rulesButton.setLayoutY(250);

        //Layout, Vertical
        //VBox layout1 = new VBox(20);
        //layout1.getChildren().addAll(label1, rulesButton, startButton);

        //Layout 2
        //VBox layout2 = new VBox(20);
        //layout2.getChildren().addAll(instructions, backHomeButton);

        Group newLayout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream("HomeMenuBackground700x600.png"));
        ImageView backgroundView = new ImageView(backgroundImage);
        newLayout.getChildren().addAll(backgroundView, rulesButton, startButton);

        Group rulesLayout = new Group();
        Image rulesImage = new Image(this.getClass().getClassLoader().getResourceAsStream("RulesScreenBackground700x600.png"));
        ImageView rulesView = new ImageView(rulesImage);
        rulesLayout.getChildren().addAll(rulesView, backHomeButton);

        //scene1 = new Scene(layout1, 700, 600);
        scene1 = new Scene(newLayout, 700, 600);
        scene2 = new Scene(rulesLayout, 700, 600);

        window.setScene(scene1);
        window.setTitle("Breakout Game");
        window.show();
    }
}
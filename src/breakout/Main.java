package breakout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application{
    /**
     * Start of the program.
     */

    Stage window;
    Scene scene1, scene2;

    MediaPlayer backGroundMusic;

    public static void main (String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        play_BackgroundAudio();
        window = primaryStage;

        Button startButton;
        startButton = new Button();
        startButton.setText("Start Game");
        startButton.setLayoutX(120);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopBackGroundAudio();
                GamePlay gameView = new GamePlay();
                try {
                    gameView.set_Level(1);
                    gameView.set_Score(0);
                    gameView.PLAYER_LIVES = 5; //MAGIC NUMBER
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

        Group newLayout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream("HomeMenuBackground700x600.png"));
        ImageView backgroundView = new ImageView(backgroundImage);
        newLayout.getChildren().addAll(backgroundView, rulesButton, startButton);

        Group rulesLayout = new Group();
        Image rulesImage = new Image(this.getClass().getClassLoader().getResourceAsStream("RulesScreenBackground700x600.png"));
        ImageView rulesView = new ImageView(rulesImage);
        rulesLayout.getChildren().addAll(rulesView, backHomeButton);

        scene1 = new Scene(newLayout, 700, 600);
        scene2 = new Scene(rulesLayout, 700, 600);

        window.setScene(scene1);
        window.setTitle("Breakout Game");
        window.show();
    }

    private void play_BackgroundAudio(){
        Media media = new Media(new File("resources\\3909-industrial-cinematic-by-kevin-macleod.mp3").toURI().toString());
        backGroundMusic = new MediaPlayer(media);
        backGroundMusic.play();
    }

    private void stopBackGroundAudio(){
        backGroundMusic.stop();
    }
}
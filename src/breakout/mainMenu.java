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
 * This class serves as the Menu screen of the game.
 */
public class mainMenu extends Application{
    private static final int startButtonXLocation = 120;
    private static final int startButtonYLocation = 300;
    private static final int rulesButtonXLocation = 120;
    private static final int rulesButtonYLocation = 250;
    private static final int backHomeButtonXLocation = 40;
    private static final int backHomeButtonYLocation = 40;
    private final int numberInitialLives = 5;
    private final int windowWidth = 700;
    private final int windowHeight = 600;
    private final int startingScore = 0;
    private final int startingLevel = 1;

    private final String windowTitle = "Breakout Game";
    private final String HomeMenuBackground = "HomeMenuBackground700x600.png";
    private final String RulesScreenBackground = "RulesScreenBackground700x600.png";
    private final String backGroundMusicPath = "resources\\3909-industrial-cinematic-by-kevin-macleod.mp3";

    private Stage window;
    private Scene titleScene, rulesScene;
    private MediaPlayer backGroundMusic;
    private Button startButton, backHomeButton, rulesButton;
    private Group homeLayout, rulesLayout;

    /**
     * Launches the menu screen
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }

    /**
     * start of the menu scene. Sets up all of the GUI elements and layout.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        play_BackgroundAudio();
        window = primaryStage;
        createButtons();
        createLayouts();
        createScenes(homeLayout, rulesLayout);
        setupWindow();
    }

    private void createLayouts() {
        homeLayout = makeHomeLayout();
        rulesLayout = makeRulesLayout();
    }

    private void createButtons() {
        startButton = createStartButton();
        backHomeButton = createBackHomeButton();
        rulesButton = createRulesButton();
        setStartButtonAction();
    }

    private void setupWindow() {
        window.setResizable(false);
        window.setScene(titleScene);
        window.setTitle(windowTitle);
        window.show();
    }

    private void createScenes(Group myHomeLayout, Group myRulesLayout) {
        titleScene = new Scene(myHomeLayout, windowWidth, windowHeight);
        rulesScene = new Scene(myRulesLayout, windowWidth, windowHeight);
    }

    private Group makeRulesLayout() {
        Group layout = new Group();
        Image rulesImage = new Image(this.getClass().getClassLoader().getResourceAsStream(RulesScreenBackground));
        ImageView rulesView = new ImageView(rulesImage);
        layout.getChildren().addAll(rulesView, backHomeButton);
        return layout;
    }

    private Group makeHomeLayout() {
        Group layout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(HomeMenuBackground));
        ImageView backgroundView = new ImageView(backgroundImage);
        layout.getChildren().addAll(backgroundView, rulesButton, startButton);
        return layout;
    }

    private void setStartButtonAction() {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopBackGroundAudio();
                GamePlay gameView = new GamePlay();
                try {
                    gameView.set_Level(startingLevel);
                    gameView.set_Score(startingScore);
                    gameView.PLAYER_LIVES = numberInitialLives;
                    gameView.start(window);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Button createRulesButton() {
        Button button;
        button = new Button("Rules");
        button.setOnAction(e -> window.setScene(rulesScene));
        button.setLayoutX(rulesButtonXLocation);
        button.setLayoutY(rulesButtonYLocation);
        return button;
    }

    private Button createBackHomeButton() {
        Button button;
        button = new Button("Back");
        button.setOnAction(e -> window.setScene(titleScene));
        button.setLayoutX(backHomeButtonXLocation);
        button.setLayoutY(backHomeButtonYLocation);
        return button;
    }

    private Button createStartButton() {
        Button button;
        button = new Button();
        button.setText("Start Game");
        button.setLayoutX(startButtonXLocation);
        button.setLayoutY(startButtonYLocation);
        return button;
    }

    private void play_BackgroundAudio(){
        Media media = new Media(new File(backGroundMusicPath).toURI().toString());
        backGroundMusic = new MediaPlayer(media);
        backGroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backGroundMusic.play();
    }

    private void stopBackGroundAudio(){
        backGroundMusic.stop();
    }
}
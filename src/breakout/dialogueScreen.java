package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * This class handles the in-between and final dialogue boxes the player interacts with.
 * @author Alex Xu
 */
public class dialogueScreen {
    Stage myStage;
    Scene scene1;

    int prevLevel;
    int nextLevel;
    int myScore;

    MediaPlayer lossAudioPlayer;
    MediaPlayer winAudioPlayer;

    private final int screenWidth = 700;
    private final int screenHeight = 600;
    private final int scoreReminderLabelYLocation = 50;
    private final int okButtonYLocation = 100;
    private final int returnHomeButtonXLocation = 40;
    private final int returnHomeButtonYLocation = 40;

    private final String winGameSoundEffectPath= "resources\\winGameEffect.mp3";
    private final String loseGameSoundEffectPath = "resources\\GameLoseSound.mp3";
    private final String winBackgroundPath = "YouWin700x600.png";
    private final String loseBackgroundPath = "GameOver700x600.png";

    /**
     * Sets the input values required for the dialogue box
     * @param pLevel previous Level
     * @param nLevel next Level
     */
    public void setLevelVals(int pLevel, int nLevel){
        prevLevel = pLevel;
        nextLevel = nLevel;
    }

    /**
     * Sets the score input required for the next Level.
     * @param score
     */
    public void set_Score(int score){
        myScore = score;
    }

    /**
     * Takes in the previous Level's stage to display the dialogue box
     * @param s
     */
    public void set_Stage(Stage s){
        myStage = s;
    }

    /**
     * Switches between the different dialogue Screens available, in between levels, game over, or game win.
     * @return
     */
    public Scene start_Scene(){
        if(nextLevel > 0) {
            scene1 = new Scene(setUpLayout(), screenWidth, screenHeight);
        }
        else if(nextLevel == 0){
            scene1 = new Scene(finish_Screen(), screenWidth, screenHeight);
        }
        else{
            scene1 = new Scene(lose_Screen(), screenWidth, screenHeight);
        }
        return scene1;
    }

    private Group setUpLayout() {
        Group layout = new Group();
        Label levelReminderLabel = setLevelReminderLabel();
        Label scoreReminderLabel = new Label("Your Current Score Is: " + myScore);
        scoreReminderLabel.setLayoutY(scoreReminderLabelYLocation);
        Button okButton = new Button();
        okButton.setText("OK");
        okButton.setLayoutY(okButtonYLocation);
        okButton.setOnAction(event -> {
            GamePlay nextGame = new GamePlay();
            nextGame.set_Level(nextLevel);
            nextGame.set_Score(myScore);
            try {
                nextGame.start(myStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        layout.getChildren().addAll(levelReminderLabel, scoreReminderLabel, okButton);
        return layout;
    }

    private Label setLevelReminderLabel() {
        if(nextLevel == 1) {
           return new Label("Move on to LEVEL " + nextLevel + "?");
        }
        else{
            return new Label("You beat LEVEL " + prevLevel + "! Move on to LEVEL " + nextLevel + "?");
        }
    }

    private Group finish_Screen(){
        play_WinAudio();
        Group layout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(winBackgroundPath));
        ImageView backgroundView = new ImageView(backgroundImage);
        Button returnHomeButton = new Button();
        returnHomeButton.setText("Return Home");
        returnHomeButton.setLayoutX(returnHomeButtonXLocation);
        returnHomeButton.setLayoutY(returnHomeButtonYLocation);
        returnHomeButton.setOnAction(event -> {
            winAudioPlayer.stop();
            mainMenu newGame = new mainMenu();
            newGame.start(myStage);
        });
        layout.getChildren().addAll(backgroundView, returnHomeButton);
        return layout;
    }

    private Group lose_Screen(){
        play_LossAudio();
        Group layout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(loseBackgroundPath));
        ImageView backgroundView = new ImageView(backgroundImage);
        Button returnHomeButton = new Button();
        returnHomeButton.setText("Return Home");
        returnHomeButton.setLayoutX(returnHomeButtonXLocation);
        returnHomeButton.setLayoutY(returnHomeButtonYLocation);
        returnHomeButton.setOnAction(event -> {
            lossAudioPlayer.stop();
            mainMenu newGame = new mainMenu();
            newGame.start(myStage);
        });
        layout.getChildren().addAll(backgroundView, returnHomeButton);
        return layout;
    }

    private void play_LossAudio(){
        Media media = new Media(new File(loseGameSoundEffectPath).toURI().toString());
        lossAudioPlayer = new MediaPlayer(media);
        lossAudioPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        lossAudioPlayer.play();
    }

    private void play_WinAudio(){
        Media media = new Media(new File(winGameSoundEffectPath).toURI().toString());
        winAudioPlayer = new MediaPlayer(media);
        winAudioPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        winAudioPlayer.play();
    }
}

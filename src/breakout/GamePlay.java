package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Test of Game
 */
public class GamePlay extends Application{
    public static final String TITLE = "Breakout Game";
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;

    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String BACKGROUND_IMAGE = "game_Background700x600.png";

    public static final Paint PADDLE_COLOR = Color.PLUM;
    public static final int PADDLE_LENGTH = 80;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_SPEED = 15;

    public static final int NUM_BOUNCERS = 1;

    public static int PLAYER_SCORE = 0;
    public static int PLAYER_LIVES = 5;

    // some things needed to remember during game
    private Scene myScene;
    private Scene next_Scene;
    private int old_score;
    Label scoreValueLabel;
    Label livesValueLabel;

    private Stage window;

    private List<Bouncer> myBouncers;
    private Rectangle myPaddle;
    private List<Brick> myBricks;

    private boolean startClick = false;

    int myLevel = 1;
    int prevLevel;
    int nextLevel;

    KeyFrame frame;
    Timeline animation;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) throws Exception {
        // attach scene to the stage and display it
        window = stage;
        window.setResizable(false); //Implement for menus as well

        myScene = setupGame(SIZE+100, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)\
        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) throws Exception {
        // create one top level collection to organize the things in the scene
        Group root = new Group();

        //Background Image:
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
        ImageView backgroundView = new ImageView(backgroundImage);

        // make some shapes and set their properties
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncers = makeBouncers(NUM_BOUNCERS, image, width, height);

        myPaddle = new Rectangle(width / 2 - PADDLE_LENGTH / 2, height / 2 + 250, PADDLE_LENGTH, PADDLE_HEIGHT);
        myPaddle.setFill(PADDLE_COLOR);

        myBricks = makeBricks(myLevel);

        //Labels to display player stats
        Label levelLabel = new Label("LEVEL:");
        levelLabel.setLayoutX(620);
        levelLabel.setLayoutY(30);

        Label levelValueLabel = new Label("" + myLevel);
        levelValueLabel.setLayoutX(620);
        levelValueLabel.setLayoutY(50);

        Label scoreLabel = new Label("SCORE:");
        scoreLabel.setLayoutX(620);
        scoreLabel.setLayoutY(80);

        scoreValueLabel = new Label("" + PLAYER_SCORE);
        scoreValueLabel.setLayoutX(620);
        scoreValueLabel.setLayoutY(100);

        Label livesLabel = new Label("LIVES LEFT: ");
        livesLabel.setLayoutX(620);
        livesLabel.setLayoutY(130);

        livesValueLabel = new Label(""+ PLAYER_LIVES);
        livesValueLabel.setLayoutX(620);
        livesValueLabel.setLayoutY(150);

        // order added to the group is the order in which they are drawn

        root.getChildren().add(backgroundView);

        root.getChildren().addAll(levelLabel, levelValueLabel, scoreLabel, scoreValueLabel, livesLabel, livesValueLabel);

        root.getChildren().add(myPaddle);
        for (Bouncer b : myBouncers) {
            root.getChildren().add(b.getView());
        }
        for (Brick brick : myBricks) {
            root.getChildren().add(brick.getView());
        }

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private List<Brick> makeBricks (int level) throws Exception {
        brickLevel brickConfig = new brickLevel();
        return brickConfig.generate_Bricks(level);
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        // update "actors" attributes
        for (Bouncer b : myBouncers) {
            b.move(elapsedTime);
        }

        //Calculate Score:
        PLAYER_SCORE = calcScore(myBricks) + old_score;
        scoreValueLabel.setText("" + PLAYER_SCORE);

        //Calculate Lives:
        calcLives(myBouncers);
        livesValueLabel.setText(""+PLAYER_LIVES);

        //Checks if Level is Beat
        if(PLAYER_SCORE == 20 && myLevel == 1){
            myLevel=2;
            prevLevel = 1;
            nextLevel = 2; //Can refactor this into a separate function later on.

            animation.stop();
            switchScreen oneTwo = new switchScreen();
            oneTwo.setLevelVals(prevLevel,nextLevel);
            oneTwo.set_Score(PLAYER_SCORE);
            oneTwo.set_Stage(window);
            next_Scene = oneTwo.start_Scene();
            window.setScene(next_Scene);
        }
        if(PLAYER_SCORE == 50 + 20 && myLevel ==2){ //Refactor later
            myLevel=3;
            prevLevel = 2;
            nextLevel = 3; //Can refactor this into a separate function later on.

            animation.stop();
            switchScreen midScreen = new switchScreen();
            midScreen.setLevelVals(prevLevel,nextLevel);
            midScreen.set_Score(PLAYER_SCORE);
            midScreen.set_Stage(window);
            next_Scene = midScreen.start_Scene();
            window.setScene(next_Scene);
        }
        if(PLAYER_SCORE == 70 + 50 && myLevel ==2){ //Refactor later
            myLevel=0;
            prevLevel = 3;
            nextLevel = 0; //Can refactor this into a separate function later on.

            animation.stop();
            switchScreen midScreen = new switchScreen();
            midScreen.setLevelVals(prevLevel,nextLevel);
            midScreen.set_Score(PLAYER_SCORE);
            midScreen.set_Stage(window);
            next_Scene = midScreen.start_Scene();
            window.setScene(next_Scene);
        }

        // Check collision with the ball with paddle
        var hit = false;
        for (Bouncer b : myBouncers) {
            if (myPaddle.getBoundsInParent().intersects(b.getView().getBoundsInParent())) {
                myPaddle.setFill(HIGHLIGHT);
                if(myPaddle.getBoundsInParent().getCenterX() < b.getView().getBoundsInParent().getCenterX()){
                    b.hitPaddle("right");
                }
                else{
                    b.hitPaddle("left");
                }
                hit = true;
            }
        }
        if (! hit) {
            myPaddle.setFill(Color.BISQUE);
        }

        //Bounce off Bricks
        for(Bouncer b: myBouncers){
            for(Brick brick : myBricks) {
                if (brick.getView().getBoundsInParent().intersects(b.getView().getBoundsInParent())) {
                    if (brick.getView().getBoundsInParent().getCenterX() < b.getView().getBoundsInParent().getCenterX()) {
                        b.hitBrick("right", brick);
                    } else {
                        b.hitBrick("left", brick);
                    }
                    brick.reduceHealth(1);
                }
            }
        }

        // bounce off all the walls
        for (Bouncer b : myBouncers) {
            b.bounce(myScene.getWidth()-100, myScene.getHeight());
        }
    }



    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if(startClick) {
            if (code == KeyCode.RIGHT && myPaddle.getX() < myScene.getWidth() - PADDLE_LENGTH - 100) {
                myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
            } else if (code == KeyCode.LEFT && myPaddle.getX() > 0) {
                myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
            }
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        for (Bouncer b : myBouncers){
            if(!startClick) {
                b.launch();
                startClick = true;
            }
        }
    }

    // create given number of bouncer objects with random attributes, but all with the same image
    private List<Bouncer> makeBouncers (int maxBouncers, Image image, int width, int height) {
        List<Bouncer> result = new ArrayList<>();
        for (int k = 0; k < maxBouncers; k++) {
            Bouncer b = new Bouncer(image, width, height);
            result.add(b);
        }
        return result;
    }

    public void set_Level(int level_val){
        myLevel = level_val;
    }
    public void set_Score(int score){
        old_score = score;
    }
    public Stage return_Stage(){return window;}

    private int calcScore(List<Brick> list){
        int score = 0;
        for(int i = 0; i<list.size(); i++){
            if(!list.get(i).BRICK_ENABLED){
                score += 10;
            }
        }
        return score;
    }

    private void calcLives(List<Bouncer> myBouncers) {
        for(Bouncer b : myBouncers){
            if (b.getView().getBoundsInParent().getMaxY() >= myScene.getHeight()){
                PLAYER_LIVES--;
            }
        }
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

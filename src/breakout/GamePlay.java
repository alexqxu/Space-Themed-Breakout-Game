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

    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String BACKGROUND_IMAGE = "game_Background700x600.png";

    public static final String INSTRUCTION_TEXT1 = "UseArrowKeys.gif";
    public static final String INSTRUCTION_TEXT2 = "ClickToBegin.gif";

    //public static final int NUM_BOUNCERS = 1;

    public static int PLAYER_SCORE = 0;
    public static int PLAYER_LIVES = 5;

    // some things needed to remember during game
    private Scene myScene;
    private Scene next_Scene;
    private int old_score;
    Label scoreValueLabel;
    Label livesValueLabel;

    private Stage window;

    //private List<Bouncer> myBouncers;
    //private Bouncer bouncer1;
    private Bouncer myBall;

    private Paddle myPaddle;
    private Rectangle myPaddleView;

    private List<Brick> myBricks;
    private List<powerUp> myPowerUps;

    private boolean longMode = false;

    private boolean startClick = false;

    int myLevel = 1;
    int prevLevel;
    int nextLevel;

    KeyFrame frame;
    Timeline animation;

    //Gifs that play, telling the player what to do.
    ImageView UseArrowKeys;
    ImageView ClickToBegin;

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

        //Instructional Text Images:
        Image instruction1 = new Image(this.getClass().getClassLoader().getResourceAsStream(INSTRUCTION_TEXT1));
        Image instruction2 = new Image(this.getClass().getClassLoader().getResourceAsStream(INSTRUCTION_TEXT2));

        UseArrowKeys = new ImageView(instruction1);
        UseArrowKeys.setLayoutX(width/2 - 50 - instruction1.getWidth()/2);                                 //MAGIC NUMBER, FIX LATER.
        UseArrowKeys.setLayoutY(height/2 - instruction1.getHeight());

        ClickToBegin = new ImageView(instruction2);
        ClickToBegin.setLayoutX(width/2 -50 - instruction2.getWidth()/2);                                  //Magic number
        ClickToBegin.setLayoutY(height/2 + instruction2.getHeight());

        // make some shapes and set their properties
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBall = makeBall(image, width, height);

        //myBouncers = makeBouncers(NUM_BOUNCERS, image, width, height);

        //myBouncers.get(0).resetSpeed();                  //take out and dont need                                                               /////////////////REFACTOR THIS LATER//////////////
        //bouncer1 = myBouncers.get(0);
        /////////////////////////

        myPaddle = new Paddle(SIZE + 100, SIZE); //magic number
        myPaddle.resetPaddleWidthToOriginal();
        myPaddleView = myPaddle.getShape();

        myBricks = makeBricks(myLevel);
        myPowerUps = makePowerUps(myBricks);

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

        root.getChildren().add(myPaddleView);

        //for (Bouncer b : myBouncers) {
        //    root.getChildren().add(b.getView());
        //}

        root.getChildren().add(myBall.getView());

        for (Brick brick : myBricks) {
            root.getChildren().add(brick.getView());
        }

        for (powerUp pu : myPowerUps){
            root.getChildren().add(pu.getView());
        }

        //ADD INSTRUCTION TEXT TO GROUP HERE.
        root.getChildren().add(UseArrowKeys);
        root.getChildren().add(ClickToBegin);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);

        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
}

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        //Get rid of instructional text at the start of each level/launching of the ball
        if(startClick){
            ClickToBegin.setImage(null);        //Refactor !!
            UseArrowKeys.setImage(null);
        }

        // update "actors" attributes
        //for (Bouncer b : myBouncers) {
        //    b.move(elapsedTime);
        //}

        myBall.move(elapsedTime);

        for (powerUp powerup : myPowerUps){
            powerup.move(elapsedTime);
        }

        //Checks if the powerups should start dropping.
        for(Brick brick : myBricks){
            for(powerUp powerup : myPowerUps) {
                if (!brick.BRICK_ENABLED && brick.getXLoc() == powerup.getXPos() && brick.getYLoc() == powerup.getYPos()) {
                    powerup.startDrop();
                }
            }
        }

        //Calculate Score:
        PLAYER_SCORE = calcScore(myBricks) + old_score;
        scoreValueLabel.setText("" + PLAYER_SCORE);

        //Calculate Lives:
        calcLives();
        livesValueLabel.setText(""+PLAYER_LIVES);
        resetBallandPaddleifDead();

        //Checks if Game is Over
        if(PLAYER_LIVES <= 0 ){
            PLAYER_LIVES = 5; //Reset player Lives back to original value. Gotta refactor them all

            animation.stop();
            switchScreen oneTwo = new switchScreen();
            oneTwo.setLevelVals(-1,-1); //magic numbers
            oneTwo.set_Score(PLAYER_SCORE);
            oneTwo.set_Stage(window);
            next_Scene = oneTwo.start_Scene();
            window.setScene(next_Scene);
        }

        //Checks if Level is Beat
        if(noBricksLeft(myBricks) && myLevel == 1){
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
        else if(noBricksLeft(myBricks) && myLevel ==2){ //Refactor later
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
        else if(noBricksLeft(myBricks) && myLevel == 3){ //Refactor later
            myLevel=1;
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
        if (myPaddle.getShape().getBoundsInParent().intersects(myBall.getView().getBoundsInParent())) {
            myPaddle.highlight();
            if(myPaddle.getShape().getBoundsInParent().getMinX() + myPaddle.getShape().getWidth()/3 > myBall.getView().getBoundsInParent().getCenterX()){
                myBall.hitPaddle("left");
            }
            else if(myPaddle.getShape().getBoundsInParent().getMaxX() - myPaddle.getShape().getWidth()/3 < myBall.getView().getBoundsInParent().getCenterX()){
                myBall.hitPaddle("right");
            }
            else{
                myBall.hitPaddle("middle");
            }
            hit = true;
        }

        if (! hit) {
            myPaddle.resetColor();
        }

        //Bounce off Bricks
        for(Brick brick : myBricks) {
            if (brick.getView().getBoundsInParent().intersects(myBall.getView().getBoundsInParent())) {
                if (brick.getView().getBoundsInParent().getCenterX() < myBall.getView().getBoundsInParent().getCenterX()) {
                    myBall.hitBrick("right", brick);
                } else {
                    myBall.hitBrick("left", brick);
                }
                brick.reduceHealth(1);
            }
        }

        // bounce off all the walls
        myBall.bounce(myScene.getWidth()-100);

        //Powerup collision with paddle
        for(powerUp powerup : myPowerUps){
            if (powerup.isEnabled() && myPaddle.getShape().getBoundsInParent().intersects(powerup.getView().getBoundsInParent())){
                if(powerup.getPowerType().equals("strength")){
                    myBall.increaseSize(1.25);
                    powerup.delete();
                }
                else if(powerup.getPowerType().equals("time")){
                    myBall.reduceSpeed(0.5);
                    powerup.delete();
                }
                else if(powerup.getPowerType().equals("length")){
                    myPaddle.handleIncreaseLengthPowerup();
                    powerup.delete();
                }
                else if(powerup.getPowerType().equals("health")){
                    PLAYER_LIVES++;
                    powerup.delete();
                }
            }
        }

        myPaddleView = myPaddle.getShape();
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {

        if (code == KeyCode.RIGHT && myPaddle.getShape().getX() < myScene.getWidth() - myPaddle.getShape().getWidth() - 100) {
            myPaddle.moveRight();
            if(!startClick){
                 myBall.setXPos(myBall.getXPos() + myPaddle.PADDLE_SPEED);
            }
        } else if (code == KeyCode.LEFT && myPaddle.getShape().getX() > 0) {
            myPaddle.moveLeft();
            if(!startClick){
                myBall.setXPos(myBall.getXPos() - myPaddle.PADDLE_SPEED);
                myBall.setXPos(myBall.getXPos() - myPaddle.PADDLE_SPEED);
            }
        }

        //CHEAT CODES:

        else if(code == KeyCode.L){
            PLAYER_LIVES++;
        }
        else if(code == KeyCode.R){
            myPaddle.resetPaddleToStartingPosition();
            myPaddle.resetPaddleWidthToOriginal();

            resetBall(myBall);
            startClick = false;
        }
        else if(code == KeyCode.DIGIT1){
            myLevel=1;
            prevLevel = 0;
            nextLevel = 1; //Can refactor this into a separate function later on.

            animation.stop();
            switchScreen oneTwo = new switchScreen();
            oneTwo.setLevelVals(prevLevel,nextLevel);
            oneTwo.set_Score(PLAYER_SCORE);
            oneTwo.set_Stage(window);
            next_Scene = oneTwo.start_Scene();
            window.setScene(next_Scene);
        }
        else if(code == KeyCode.DIGIT2){
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
        else if(code == KeyCode.DIGIT3){
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
        else if(code == KeyCode.P && startClick){
            if(longMode) {
                myPaddle.resetPaddleWidth();
                longMode = false;
            }
            else {
                myPaddle.setXLoc(0);
                myPaddle.setMaxPaddleLength();
                longMode = true;
            }
        }
        else if(code == KeyCode.D){ //Reduce health of bricks to 1.
            for(Brick brick : myBricks){
                brick.reduceHealthTo1();
            }
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if(!startClick) {
            myBall.launch();
            startClick = true;
        }
    }

    private List<Brick> makeBricks (int level) throws Exception {
        brickLevelGenerator brickConfig = new brickLevelGenerator();
        return brickConfig.generate_Bricks(level);
    }

    /*
    // create given number of bouncer objects with random attributes, but all with the same image
    private List<Bouncer> makeBouncers (int maxBouncers, Image image, int width, int height) {
        List<Bouncer> result = new ArrayList<>();
        for (int k = 0; k < maxBouncers; k++) {
            Bouncer b = new Bouncer(image, width, height);
            result.add(b);
        }
        return result;
    }

     */

    private Bouncer makeBall(Image image, int width, int height){
        return new Bouncer(image, width, height);
    }


    private List<powerUp> makePowerUps (List<Brick> brickList){
        List<powerUp> result = new ArrayList<>();
            for(Brick brick : brickList){
                powerUp p;

                if(brick.getPowerUp().equals("strength")){
                    p = new powerUp(brick.getXLoc(),brick.getYLoc(), "strength");
                }
                else if(brick.getPowerUp().equals("time")){
                    p = new powerUp(brick.getXLoc(),brick.getYLoc(), "time");
                }
                else if(brick.getPowerUp().equals("length")){
                    p = new powerUp(brick.getXLoc(),brick.getYLoc(), "length");
                }
                else if(brick.getPowerUp().equals("health")){
                    p = new powerUp(brick.getXLoc(),brick.getYLoc(), "health");
                }
                else{
                    p = new powerUp(brick.getXLoc(),brick.getYLoc(), "none");
                }
                result.add(p);
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

    private boolean noBricksLeft(List<Brick> bricks){
        for(Brick brick : bricks){
            if(brick.BRICK_ENABLED){
                return false;
            }
        }
        return true;
    }

    /*
    private void calcLives(List<Bouncer> myBouncers) {
        for(Bouncer b : myBouncers){
            if (b.getView().getBoundsInParent().getMaxY() >= myScene.getHeight()){
                PLAYER_LIVES--;
            }
        }
    }

     */
    private void calcLives(){
        if(myBall.getView().getBoundsInParent().getMaxY() >= myScene.getHeight()){
            PLAYER_LIVES--;
        }
    }

    private void resetBallandPaddleifDead(){                                        //////////REFACTOR WITH isDead later!
        if (myBall.getView().getBoundsInParent().getMaxY() >= myScene.getHeight()) {
            myPaddle.resetPaddleToStartingPosition(); //reset paddle to starting position
            resetBall(myBall);
            startClick = false;
        }
    }

    private boolean isDead(List<Bouncer> myBouncers){
        for(Bouncer b: myBouncers){
            if (b.getView().getBoundsInParent().getMaxY() >= myScene.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private void resetBall(Bouncer bouncer){
        bouncer.resetPosandVel();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
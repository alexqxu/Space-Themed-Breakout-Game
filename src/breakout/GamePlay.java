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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Class that holds game elements and calculates interactions between them.
 * @author Alex Xu
 */

public class GamePlay extends Application{
    public static final String TITLE = "Breakout Game";
    public static final int SIZE = 600;
    public static final int SIDEPANEL_SIZE = 100;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String BACKGROUND_IMAGE = "game_Background700x600.png";
    public static final String INSTRUCTION_TEXT1 = "UseArrowKeys.gif";
    public static final String INSTRUCTION_TEXT2 = "ClickToBegin.gif";

    public static int PLAYER_SCORE = 0;
    public static int PLAYER_LIVES = 5;

    private Scene myScene;
    private Scene next_Scene;
    private int old_score;
    private Label scoreValueLabel;
    private Label livesValueLabel;

    private Stage window;
    private Bouncer myBall;
    private Paddle myPaddle;
    private Rectangle myPaddleView;

    private List<Brick> myBricks;
    private List<powerUp> myPowerUps;

    private boolean longMode = false;
    private boolean startClick = false;

    private int scoreIncrementValue = 10;

    int myLevel = 1;
    int prevLevel;
    int nextLevel;

    private KeyFrame frame;
    private Timeline animation;

    private ImageView UseArrowKeys;
    private ImageView ClickToBegin;
    private int instructionalTextXAdjustment = 50;
    private int livesValueLabelXLocation = 620;
    private int livesValueLabelYLocation = 150;
    private int livesLabelYLocation = 130;
    private int scoreValueLabelXLocation = 620;
    private int scoreValueLabelYLocation = 100;
    private int scoreLabelYLocation = 80;
    private int levelValueLabelXLocation = 620;
    private int levelValueLabelYLocation = 50;
    private int levelLabelYLocation = 30;
    private int damagePerHit = 1;
    private double powerUpBallSizeIncreaseFactor = 1.25;
    private double powerUpBallSpeedDecreaseFactor = 0.5;
    private int allocatedLives = 5;

    private Group root;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) throws Exception {
        window = stage;
        window.setResizable(false);
        myScene = setupGame(SIZE+ SIDEPANEL_SIZE, SIZE);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        attachGameLoop();
    }

    /**
     * Sets the value of the upcoming level.
     * @param level_val
     */
    public void set_Level(int level_val){
        myLevel = level_val;
    }

    /**
     * Sets the score that was earned in previous levels.
     * @param score
     */
    public void set_Score(int score){
        old_score = score;
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    private Scene setupGame (int width, int height) throws Exception {
        root = new Group();
        addBackGroundImagetoGroup();
        addBalltoGroup(width, height);
        addPaddletoGroup();
        addBrickstoGroup();
        addPowerUpstoGroup();
        addLevelLabels();
        addScoreLabels();
        addLivesLabels();
        addInstructionalTexttoGroup(width, height);

        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void step (double elapsedTime) {
        if(startClick){
            removeInstructionalText();
        }
        myBall.move(elapsedTime);
        movePowerups(elapsedTime);
        startPowerUpDropsIfBrickBroken();
        refreshScore();
        refreshLives();
        launchGameOverIfLivesGone();
        handleSwitchLevel();
        handleBallCollisionWithPaddle();
        handleBounceOffBricks();
        handleBounceOffWalls();
        handlePowerUpCollisionWithPaddle();
        updatePaddleView();
    }

    /**
     * Respond according to keys the player presses
     * @param code
     */

    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && hasSpacetoMoveRight()) {
            moveBallandPaddleRight();
        } else if (code == KeyCode.LEFT && hasSpacetoMoveLeft()) {
            moveBallandPaddleLeft();
        }
        else if(code == KeyCode.L){
            PLAYER_LIVES++;
        }
        else if(code == KeyCode.R){
            handleCheatCodeR();
        }
        else if(code == KeyCode.DIGIT1){
            myLevel=1;
            prevLevel = 0;
            nextLevel = 1;
            switchToDialoguePage();
        }
        else if(code == KeyCode.DIGIT2){
            myLevel=2;
            prevLevel = 1;
            nextLevel = 2;
            switchToDialoguePage();
        }
        else if(code == KeyCode.DIGIT3){
            myLevel=3;
            prevLevel = 2;
            nextLevel = 3;
            switchToDialoguePage();
        }
        else if(code == KeyCode.P && startClick){
            handleCheatCodeP();
        }
        else if(code == KeyCode.D){
            handleCheatCodeD();
        }
    }

    /**
     * When the mouse is clicked, launch the ball in the calculated direction.
     * @param x coordinate of the click
     * @param y coordinate of the click
     */
    private void handleMouseInput (double x, double y) {
        if(!startClick) {
            calculateBallXVelocity(x);
            myBall.launch();
            startClick = true;
        }
    }

    private void handleCheatCodeD() {
        for(Brick brick : myBricks){
            brick.reduceHealthTo1();
        }
    }

    private void handleCheatCodeP() {
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

    private void handleCheatCodeR() {
        myPaddle.resetPaddleToStartingPosition();
        myPaddle.resetPaddleWidthToOriginal();
        resetBall(myBall);
        startClick = false;
    }

    private void moveBallandPaddleLeft() {
        myPaddle.moveLeft();
        if(!startClick){
            myBall.setXPos(myBall.getXPos() - myPaddle.PADDLE_SPEED);
        }
    }

    private void moveBallandPaddleRight() {
        myPaddle.moveRight();
        if(!startClick){
            myBall.setXPos(myBall.getXPos() + myPaddle.PADDLE_SPEED);
        }
    }

    private boolean hasSpacetoMoveRight(){
        return myPaddle.getShape().getX() < myScene.getWidth() - myPaddle.getShape().getWidth() - SIDEPANEL_SIZE;
    }
    private boolean hasSpacetoMoveLeft(){
        return myPaddle.getShape().getX() > 0;
    }

    private void addLivesLabels() {
        addStaticLivesLabel();
        addDynamicLivesLabel();
    }

    private void addDynamicLivesLabel() {
        livesValueLabel = new Label(""+ PLAYER_LIVES);
        livesValueLabel.setLayoutX(livesValueLabelXLocation);
        livesValueLabel.setLayoutY(livesValueLabelYLocation);
        root.getChildren().add(livesValueLabel);
    }

    private void addStaticLivesLabel() {
        Label livesLabel = new Label("LIVES LEFT: ");
        livesLabel.setLayoutX(livesValueLabelXLocation);
        livesLabel.setLayoutY(livesLabelYLocation);
        root.getChildren().add(livesLabel);
    }

    private void addScoreLabels() {
        addStaticScoreLabel();
        addDyanmicScoreLabel();
    }

    private void addDyanmicScoreLabel() {
        scoreValueLabel = new Label("" + PLAYER_SCORE);
        scoreValueLabel.setLayoutX(scoreValueLabelXLocation);
        scoreValueLabel.setLayoutY(scoreValueLabelYLocation);
        root.getChildren().add(scoreValueLabel);
    }

    private void addStaticScoreLabel() {
        Label scoreLabel = new Label("SCORE:");
        scoreLabel.setLayoutX(scoreValueLabelXLocation);
        scoreLabel.setLayoutY(scoreLabelYLocation);
        root.getChildren().add(scoreLabel);
    }

    private void addLevelLabels() {
        addStaticLevelLabel();
        addDynamicLevelLabel();
    }

    private void addDynamicLevelLabel() {
        Label levelValueLabel = new Label("" + myLevel);
        levelValueLabel.setLayoutX(levelValueLabelXLocation);
        levelValueLabel.setLayoutY(levelValueLabelYLocation);
        root.getChildren().add(levelValueLabel);
    }

    private void addStaticLevelLabel() {
        Label levelLabel = new Label("LEVEL:");
        levelLabel.setLayoutX(levelValueLabelXLocation);
        levelLabel.setLayoutY(levelLabelYLocation);
        root.getChildren().add(levelLabel);
    }

    private void addPowerUpstoGroup() {
        myPowerUps = makePowerUps(myBricks);
        for (powerUp pu : myPowerUps){
            root.getChildren().add(pu.getView());
        }
    }

    private void addBrickstoGroup() throws Exception {
        myBricks = makeBricks(myLevel);
        for (Brick brick : myBricks) {
            root.getChildren().add(brick.getView());
        }
    }

    private void addPaddletoGroup() {
        myPaddle = new Paddle(SIZE + SIDEPANEL_SIZE, SIZE);
        myPaddle.resetPaddleWidthToOriginal();
        myPaddleView = myPaddle.getShape();
        root.getChildren().add(myPaddleView);
    }

    private void addBalltoGroup(int width, int height) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBall = makeBall(image, width, height);
        root.getChildren().add(myBall.getView());
    }

    private void addInstructionalTexttoGroup(int width, int height) {
        addUseArrowKeys(width, height);
        addClicktoBegin(width, height);
    }

    private void addUseArrowKeys(int width, int height){
        Image instruction1 = new Image(this.getClass().getClassLoader().getResourceAsStream(INSTRUCTION_TEXT1));
        UseArrowKeys = new ImageView(instruction1);
        UseArrowKeys.setLayoutX(width/2 - instructionalTextXAdjustment - instruction1.getWidth()/2);
        UseArrowKeys.setLayoutY(height/2 - instruction1.getHeight());
        root.getChildren().add(UseArrowKeys);
    }

    private void addClicktoBegin(int width, int height){
        Image instruction2 = new Image(this.getClass().getClassLoader().getResourceAsStream(INSTRUCTION_TEXT2));
        ClickToBegin = new ImageView(instruction2);
        ClickToBegin.setLayoutX(width/2 -instructionalTextXAdjustment - instruction2.getWidth()/2);
        ClickToBegin.setLayoutY(height/2 + instruction2.getHeight());
        root.getChildren().add(ClickToBegin);
    }

    private void addBackGroundImagetoGroup() {
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
        ImageView backgroundView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundView);

    }

    private void attachGameLoop() {
        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void calculateBallXVelocity(double x) {
        if(x < myBall.getXPos())
            myBall.setInitialXSpeed((int)(x-myBall.getXPos()));
        else{
            myBall.setInitialXSpeed((int)-(myBall.getXPos() - x));
        }
    }

    private List<Brick> makeBricks (int level) throws Exception {
        brickLevelGenerator brickConfig = new brickLevelGenerator();
        return brickConfig.generate_Bricks(level);
    }

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

    private int calcScore(List<Brick> list){
        int score = 0;
        for(int i = 0; i<list.size(); i++){
            if(!list.get(i).getBrickEnabled()){
                score += scoreIncrementValue;
            }
        }
        return score;
    }

    private boolean noBricksLeft(List<Brick> bricks){
        for(Brick brick : bricks){
            if(brick.getBrickEnabled()){
                return false;
            }
        }
        return true;
    }

    private void calcLives(){
        if(myBall.getView().getBoundsInParent().getMaxY() >= myScene.getHeight()){
            PLAYER_LIVES--;
        }
    }

    private void resetBallandPaddleifDead(){
        if (isDead(myBall)) {
            myPaddle.resetPaddleToStartingPosition();
            resetBall(myBall);
            startClick = false;
        }
    }

    private boolean isDead(Bouncer ball){
        return ball.getView().getBoundsInParent().getMaxY() >= myScene.getHeight();
    }

    private void resetBall(Bouncer bouncer){
        bouncer.resetPosAndVel();
    }

    private void handlePowerUpCollisionWithPaddle() {
        for(powerUp powerup : myPowerUps){
            if (powerUpHitPaddle(powerup)){
                if(powerup.getPowerType().equals("strength")){
                    myBall.increaseSize(powerUpBallSizeIncreaseFactor);
                }
                else if(powerup.getPowerType().equals("time")){
                    myBall.reduceSpeed(powerUpBallSpeedDecreaseFactor);
                }
                else if(powerup.getPowerType().equals("length")){
                    myPaddle.handleIncreaseLengthPowerup();
                }
                else if(powerup.getPowerType().equals("health")){
                    PLAYER_LIVES++;
                }
                powerup.delete();
            }
        }
    }

    private boolean powerUpHitPaddle(powerUp powerup){
        return powerup.isEnabled() && myPaddle.getShape().getBoundsInParent().intersects(powerup.getView().getBoundsInParent());
    }

    private void handleBounceOffWalls() {
        myBall.bounce(myScene.getWidth()-SIDEPANEL_SIZE);
    }

    private void handleBounceOffBricks() {
        for(Brick brick : myBricks) {
            if (isHitBrick(brick)) {
                if (isHitBrickRight(brick)) {
                    myBall.hitBrick("right", brick);
                } else {
                    myBall.hitBrick("left", brick);
                }
                brick.reduceHealth(damagePerHit);
            }
        }
    }

    private boolean isHitBrick(Brick brick){
        return brick.getView().getBoundsInParent().intersects(myBall.getView().getBoundsInParent());
    }
    private boolean isHitBrickRight(Brick brick){
        return brick.getView().getBoundsInParent().getCenterX() < myBall.getView().getBoundsInParent().getCenterX();
    }

    private void handleBallCollisionWithPaddle() {
        var hit = false;
        if (isBallHitPaddle()) {
            myPaddle.highlight();
            if(isHitPaddleLeft()){
                myBall.hitPaddle("left");
            }
            else if(isHitPaddleRight()){
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
    }

    private boolean isBallHitPaddle(){
        return myPaddle.getShape().getBoundsInParent().intersects(myBall.getView().getBoundsInParent());
    }
    private boolean isHitPaddleLeft(){
        return myPaddle.getShape().getBoundsInParent().getMinX() + myPaddle.getShape().getWidth()/3 > myBall.getView().getBoundsInParent().getCenterX();
    }
    private boolean isHitPaddleRight(){
        return myPaddle.getShape().getBoundsInParent().getMaxX() - myPaddle.getShape().getWidth()/3 < myBall.getView().getBoundsInParent().getCenterX();
    }

    private void handleSwitchLevel() {
        if(levelBeat(1)){
            myLevel=2;
            prevLevel = 1;
            nextLevel = 2;
            switchToDialoguePage();
        }
        else if(levelBeat(2)){
            myLevel=3;
            prevLevel = 2;
            nextLevel = 3;
            switchToDialoguePage();
        }
        else if(levelBeat(3)){
            myLevel=1;
            prevLevel = 3;
            nextLevel = 0;
            switchToDialoguePage();
        }
    }

    private void switchToDialoguePage() {
        animation.stop();
        switchScreen midScreen = new switchScreen();
        midScreen.setLevelVals(prevLevel,nextLevel);
        midScreen.set_Score(PLAYER_SCORE);
        midScreen.set_Stage(window);
        next_Scene = midScreen.start_Scene();
        window.setScene(next_Scene);
    }

    private boolean levelBeat(int level){
        return noBricksLeft(myBricks) && myLevel == level;
    }

    private void updatePaddleView(){
        myPaddleView = myPaddle.getShape();
    }

    private void launchGameOverIfLivesGone() {
        if(isDead()){
            PLAYER_LIVES = allocatedLives;
            animation.stop();
            switchScreen gameOver = new switchScreen();
            gameOver.setLevelVals(-1,-1);
            gameOver.set_Score(PLAYER_SCORE);
            gameOver.set_Stage(window);
            next_Scene = gameOver.start_Scene();
            window.setScene(next_Scene);
        }
    }

    private boolean isDead(){
        return PLAYER_LIVES <= 0;
    }

    private void refreshLives() {
        calcLives();
        livesValueLabel.setText(""+PLAYER_LIVES);
        resetBallandPaddleifDead();
    }

    private void refreshScore() {
        PLAYER_SCORE = calcScore(myBricks) + old_score;
        scoreValueLabel.setText("" + PLAYER_SCORE);
    }

    private void startPowerUpDropsIfBrickBroken() {
        for(Brick brick : myBricks){
            for(powerUp powerup : myPowerUps) {
                if (!brick.getBrickEnabled() && brick.getXLoc() == powerup.getXPos() && brick.getYLoc() == powerup.getYPos()) {
                    powerup.startDrop();
                }
            }
        }
    }

    private void movePowerups(double elapsedTime) {
        for (powerUp powerup : myPowerUps){
            powerup.move(elapsedTime);
        }
    }

    private void removeInstructionalText() {
        ClickToBegin.setImage(null);
        UseArrowKeys.setImage(null);
    }
}
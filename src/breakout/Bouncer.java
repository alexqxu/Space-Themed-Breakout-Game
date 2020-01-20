package breakout;

import java.io.File;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The bouncer class is used to make the ball that bounces around.
 * @author: Alex Xu
 */
public class Bouncer {
    public final int BOUNCER_SIZE = 25;
    private int initialBouncerSpeed = -400;
    private final int YdistanceAwayFromCenter = 225;
    private final int paddleCausedAcceleration = 30;
    private final int brickCausedAcceleration = 25;
    private final int initialXSpeed=200;
    private ImageView myView;
    private Point2D myVelocity;

    private int initialXPos;
    private int initialYPos;

    /**
     * Create a bouncer from a given image and screenHeight and screenWidth.
     */
    public Bouncer (Image image, int screenWidth, int screenHeight) {
        myView = new ImageView(image);
        setBouncerSize();
        setInitialPosition(screenWidth, screenHeight);
        setVelocityToZero();
    }

    /**
     * Move by taking one step based on its velocity.
     *
     * Note, elapsedTime is used to ensure consistent speed across different machines.
     */
    public void move (double elapsedTime) {
        myView.setX(myView.getX() + myVelocity.getX() * elapsedTime);
        myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
    }

    /**
     * Bounce off the walls represented by the edges of the screen.
     */
    public void bounce (double screenWidth) {
        if (ballHitLeftOrRight(screenWidth)) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (ballHitTop()) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    /**
     * Make the ball bounce off the paddle. Bouncing behavior depends on which side of the paddle the ball is hit.
     * (Left, Right, Middle)
     * @param side which side of the paddle the ball is bouncing off of.
     */
    public void hitPaddle(String side){
        if(side.equals("left")){
            myVelocity = new Point2D(-1*Math.abs(myVelocity.getX()) -paddleCausedAcceleration, -myVelocity.getY());
        }
        else if (side.equals("right")){
            myVelocity = new Point2D(Math.abs(myVelocity.getX()) +paddleCausedAcceleration, -myVelocity.getY());
        }
        else{
            myVelocity = new Point2D(0, -myVelocity.getY());
        }
    }

    /**
     * Make the ball bounce off the bricks. Bouncing behavior depends on which side the brick is hit.
     * @param side which side the brick is hit, left or right
     * @param b the brick that the ball has hit.
     */
    public void hitBrick(String side, Brick b){
        if(b.BRICK_ENABLED) {
            if (side.equals("left")) {
                myVelocity = new Point2D(myVelocity.getX() - brickCausedAcceleration, -myVelocity.getY());
            } else {
                myVelocity = new Point2D(myVelocity.getX() + brickCausedAcceleration, -myVelocity.getY());
            }
            play_audio();
        }
    }

    /**
     * Launch the ball from the initial rest position.
     */
    public void launch(){
        myVelocity = new Point2D(initialXSpeed, initialBouncerSpeed);
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myView;
    }

    /**
     * Return the X position (of the left side) of the bouncer.
     * @return
     */
    public int getXPos(){
        return (int)myView.getX();
    }

    /**
     * Set the X position (left side) of the bouncer.
     * @param xPosition
     */
    public void setXPos(int xPosition){
        myView.setX(xPosition);
    }

    /**
     * Reset the position to the original position, and set velocity to 0.
     */
    public void resetPosAndVel(){
        setXPos(initialXPos);
        setYPos(initialYPos);
        setVelocityToZero();
    }

    /**
     * Change the size of the bouncer to be multiplied by a factor. The initial positions are changed so it may
     * accomodate a larger bouncer size.
     * @param increaseFactor the scaling factor for the size of the bouncer
     */
    public void increaseSize(double increaseFactor){
        double initialWidth = myView.getFitWidth();
        double afterWidth = initialWidth*increaseFactor;

        double initialHeight = myView.getFitHeight();
        double afterHeight = initialHeight*increaseFactor;

        myView.setFitWidth(afterWidth);
        myView.setFitHeight(afterHeight);

        initialYPos = (int)(initialYPos - (afterHeight - initialHeight));
        initialXPos = (int)(initialXPos - (afterWidth - initialWidth)/2);
    }

    /**
     * Reduce the speed of the bouncer by a reduction Factor
     * @param reductionFactor the scaling factor for the speed of the bouncer
     */
    public void reduceSpeed(double reductionFactor){
        myVelocity = new Point2D(myVelocity.getX() * reductionFactor, myVelocity.getY()*reductionFactor);
        initialBouncerSpeed = (int)(initialBouncerSpeed *reductionFactor);
}

    private void setInitialXPos(int xPosition){
        initialXPos = xPosition;
        setXPos(xPosition);
    }

    private void setInitialYPos(int yPosition){
        initialYPos = yPosition;
        setYPos(yPosition);
    }

    private void setInitialPosition(int screenWidth, int screenHeight) {
        // make sure it stays within the bounds
        setInitialXPos(screenWidth/2 - BOUNCER_SIZE/2);
        setInitialYPos(screenHeight/2+YdistanceAwayFromCenter);
    }

    private void setVelocityToZero() {
        // turn speed into velocity that can be updated on bounces
        myVelocity = new Point2D(0, 0);
    }

    private void setBouncerSize() {
        // make sure it stays a circle
        int size = BOUNCER_SIZE;
        myView.setFitWidth(size);
        myView.setFitHeight(size);
    }

    private void play_audio(){
        Media media = new Media(new File("resources\\pong_beep.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    private void setYPos(int yPosition){
        myView.setY(yPosition);
    }

    private boolean ballHitLeftOrRight(double screenWidth){
        return (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth());
    }
    private boolean ballHitTop(){
        return (myView.getY() < 0);
    }
}
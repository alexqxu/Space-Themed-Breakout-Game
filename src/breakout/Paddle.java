package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Class that holds information and methods related to the Paddle object of the game, which the player controls.
 * @author Alex Xu
 */
public class Paddle {
    public static final Paint PADDLE_COLOR = Color.PLUM;
    public static final int PADDLE_LENGTH = 80;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_SPEED = 15;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final int SIDEBAR_WIDTH = 100;
    public static final double increaseLengthPowerupSizeFactor = 1.5;

    private Rectangle myRectangle;
    private int myScreenWidth, myScreenHeight;
    private int initialXLocation;
    private int initialYLocation;
    private int myLength = PADDLE_LENGTH;
    private int initialYLocationAdjustment = 250;

    /**
     * Constructs the paddle object and sets its location based on screen width and height
     * @param screenWidth
     * @param screenHeight
     */
    public Paddle(int screenWidth, int screenHeight){
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        initialXLocation = myScreenWidth / 2 - myLength / 2;
        initialYLocation = myScreenHeight/2 + initialYLocationAdjustment;

        myRectangle = new Rectangle(initialXLocation, initialYLocation, PADDLE_LENGTH, PADDLE_HEIGHT);
        myRectangle.setFill(PADDLE_COLOR);
    }

    /**
     * Moves the paddle to the right
     */
    public void moveRight(){
        myRectangle.setX(myRectangle.getX() + PADDLE_SPEED);
    }

    /**
     * Moves the paddle to the left
     */
    public void moveLeft(){
        myRectangle.setX(myRectangle.getX() - PADDLE_SPEED);
    }

    /**
     * Highlights the paddle (for impact)
     */
    public void highlight(){
        myRectangle.setFill(HIGHLIGHT);
    }

    /**
     * Resets the color of the paddle to the original
     */
    public void resetColor(){
        myRectangle.setFill(PADDLE_COLOR);
    }

    /**
     * Resets the paddle length to the last known width
     */
    public void resetPaddleWidth(){
        myRectangle.setWidth(myLength);
    }

    /**
     * Resets the paddle width to the original width
     */
    public void resetPaddleWidthToOriginal(){
        myRectangle.setWidth(PADDLE_LENGTH);
        myLength = PADDLE_LENGTH;
    }

    /**
     * Sets the width of the paddle to the maximum size of the game area.
     */
    public void setMaxPaddleLength(){
        myRectangle.setWidth(myScreenWidth - SIDEBAR_WIDTH);
    }

    /**
     * Resets the paddle to the original starting location
     */
    public void resetPaddleToStartingPosition(){
        myRectangle.setX(initialXLocation);
    }

    /**
     * Moves the paddle to a location
     * @param xLoc x coordinate of location
     */
    public void setXLoc(int xLoc){
        myRectangle.setX(xLoc);
    }

    /**
     * Used for the Powerup that increases the length of the paddle
     */
    public void handleIncreaseLengthPowerup(){
        myRectangle.setWidth(myRectangle.getWidth()*increaseLengthPowerupSizeFactor);
        myLength=(int)(myLength*increaseLengthPowerupSizeFactor);
    }

    /**
     * Returns a rectangle view of the Paddle, used in the game loop to update graphics
     * @return
     */
    public Rectangle getShape(){
        return myRectangle;
    }
}

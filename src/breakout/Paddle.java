package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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

    public Paddle(int screenWidth, int screenHeight){
        myScreenWidth = screenWidth;
        myScreenHeight = screenHeight;
        initialXLocation = myScreenWidth / 2 - myLength / 2;
        initialYLocation = myScreenHeight/2 + 250;

        myRectangle = new Rectangle(initialXLocation, initialYLocation, PADDLE_LENGTH, PADDLE_HEIGHT);
        myRectangle.setFill(PADDLE_COLOR);
    }

    public void moveRight(){
        myRectangle.setX(myRectangle.getX() + PADDLE_SPEED);
    }

    public void moveLeft(){
        myRectangle.setX(myRectangle.getX() - PADDLE_SPEED);
    }

    public void highlight(){
        myRectangle.setFill(HIGHLIGHT);
    }

    public void resetColor(){
        myRectangle.setFill(PADDLE_COLOR);
    }

    //Use this for the cheat code P
    public void resetPaddleWidth(){
        myRectangle.setWidth(myLength);
    }

    public void resetPaddleWidthToOriginal(){
        myRectangle.setWidth(PADDLE_LENGTH);
        myLength = PADDLE_LENGTH;
    }

    public void setMaxPaddleLength(){
        myRectangle.setWidth(myScreenWidth - SIDEBAR_WIDTH);
    }

    public void resetPaddleToStartingPosition(){
        myRectangle.setX(initialXLocation);
    }

    public void setXLoc(int xLoc){
        myRectangle.setX(xLoc);
    }

    public void handleIncreaseLengthPowerup(){
        myRectangle.setWidth(myRectangle.getWidth()*increaseLengthPowerupSizeFactor);
        myLength=(int)(myLength*increaseLengthPowerupSizeFactor);
    }

    //Returns the rectangle shape of the paddle.
    public Rectangle getShape(){
        return myRectangle;
    }
}

package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Simple bouncer based on an image that moves and bounces.
 *
 */
public class Bouncer {
    public int BOUNCER_SPEED = -400;
    public static final int BOUNCER_SIZE = 25;

    //private boolean BALL_ENABLED = true;

    private ImageView myView;
    private Point2D myVelocity;

    int initXPos;
    int initYPos;

    /**
     * Create a bouncer from a given image with random attributes.
     */
    public Bouncer (Image image, int screenWidth, int screenHeight) {
        myView = new ImageView(image);
        // make sure it stays a circle
        int size = BOUNCER_SIZE;
        myView.setFitWidth(size);
        myView.setFitHeight(size);
        // make sure it stays within the bounds

        setinitXPos(screenWidth/2 - BOUNCER_SIZE/2); //FIX MAGIC NUMBER
        setinitYPos(screenHeight/2+225);

        // turn speed into velocity that can be updated on bounces
        myVelocity = new Point2D(0, 0);
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
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myView.getY() < 0) { //MAGIC NUMBER
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    public void hitPaddle(String side){
        if(side.equals("left")){
            myVelocity = new Point2D(-1*Math.abs(myVelocity.getX()) -10, -myVelocity.getY()); //-1*Math.abs(myVelocity.getX()), -myVelocity.getY()
        }
        else if (side.equals("right")){
            myVelocity = new Point2D(Math.abs(myVelocity.getX()) +10, -myVelocity.getY());
        }
        else{
            myVelocity = new Point2D(0, -myVelocity.getY());
        }
    }

    public void hitBrick(String side, Brick b){
        if(b.BRICK_ENABLED) {
            if (side.equals("left")) {
                myVelocity = new Point2D(myVelocity.getX() - 25, -myVelocity.getY());
            } else {
                myVelocity = new Point2D(myVelocity.getX() + 25, -myVelocity.getY());
            }
        }
    }

    public void launch(){
        myVelocity = new Point2D(200, BOUNCER_SPEED);
    }

    public int getXPos(){
        return (int)myView.getX();
    }

    public int getYPos(){
        return (int)myView.getY();
    }

    public void setXPos(int xPosition){
        myView.setX(xPosition);
    }

    public void setYPos(int yPosition){
        myView.setY(yPosition);
    }

    public void setinitXPos(int xPosition){
        initXPos = xPosition;
        setXPos(xPosition);
    }

    public void setinitYPos(int yPosition){
        initYPos = yPosition;
        setYPos(yPosition);
    }

    public void resetPosandVel(){
        setXPos(initXPos);
        setYPos(initYPos);
        myVelocity = new Point2D(0, 0);
    }

    public void increaseSize(double increaseFactor){
        double initialWidth = myView.getFitWidth();
        double afterWidth = initialWidth*increaseFactor;

        double initialHeight = myView.getFitHeight();
        double afterHeight = initialHeight*increaseFactor;

        myView.setFitWidth(afterWidth);
        myView.setFitHeight(afterHeight);

        initYPos = (int)(initYPos - (afterHeight - initialHeight));
        initXPos = (int)(initXPos - (afterWidth - initialWidth)/2);
    }

    public void reduceSpeed(double reductionFactor){
        myVelocity = new Point2D(myVelocity.getX() * reductionFactor, myVelocity.getY()*reductionFactor);
        BOUNCER_SPEED = (int)(BOUNCER_SPEED*reductionFactor); //Slows time for next launch as well, but creates problem where in the future levels time is always slowed. Will have to refactor, with a final constant, and a non-final constant. Non final constant gets changed.
    }

    public void resetSpeed(){ //may not need
        BOUNCER_SPEED = -400;
    }


    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myView;
    }


}
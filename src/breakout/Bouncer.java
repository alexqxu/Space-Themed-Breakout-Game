package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math.*;


/**
 * Simple bouncer based on an image that moves and bounces.
 *
 */
public class Bouncer {
    public static final int BOUNCER_SPEED = -300;
    public static final int BOUNCER_SIZE = 25;

    private ImageView myView;
    private Point2D myVelocity;


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
        myView.setX(screenWidth/2);
        myView.setY(screenHeight/2+225);
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
    public void bounce (double screenWidth, double screenHeight) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    public void hitPaddle(String side){
        if(side.equals("left")){
            myVelocity = new Point2D(-1*Math.abs(myVelocity.getX()), -myVelocity.getY());
        }
        else{
            myVelocity = new Point2D(Math.abs(myVelocity.getX()), -myVelocity.getY());
        }
    }

    public void hitBrick(String side, Brick b){
        if(b.BRICK_ENABLED) {
            if (side.equals("left")) {
                myVelocity = new Point2D(myVelocity.getX() - 15, -myVelocity.getY());
            } else {
                myVelocity = new Point2D(myVelocity.getX() + 15, -myVelocity.getY());
            }
        }
    }

    public void launch(){
        myVelocity = new Point2D(40, BOUNCER_SPEED);
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myView;
    }
}

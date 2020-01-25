package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract Superclass that stores information and methods for Powerup objects. The purpose of this class is to serve
 * as a superclass for all of the subclasses. This class holds variables that are universal for all types of powerups.
 * This new, refactored implementation takes advantage of inheritance, and makes adding a Powerup a matter of adding a
 * subclass with the appropriate image/graphic associated with the Powerup. This utilizes inheritance concepts learned
 * in class.
 * @author Alex Xu
 */
public abstract class powerUp {
    public static final int POWERUP_SPEED = 100;
    public static final int POWERUP_SIZE = 25;

    private Image myImage;
    private ImageView myView;
    private int initXPosition;
    private int initYPosition;
    private int mySpeed = 0;
    private boolean Enabled;

    /**
     * Constructs the powerup based on an X and Y coordinate position, as well as a String indicating the
     * type of powerup.
     * @param xPosition
     * @param yPosition
     */
    public powerUp(int xPosition, int yPosition){
        initXPosition = xPosition;
        initYPosition = yPosition;
        Enabled = true;
    }

    /**
     * Sets the Image of the powerUp, passed in from the subclasses.
     * @param imageFileName
     */
    public void setImage(String imageFileName){
        myImage = new Image(this.getClass().getClassLoader().getResourceAsStream(imageFileName));
        setView();
    }

    /**
     * Sets the ImageView of the powerUp, from the Image object.
     */
    public void setView(){
        myView = new ImageView(myImage);
        int size = POWERUP_SIZE;
        myView.setFitWidth(size);
        myView.setFitHeight(size);
        myView.setX(initXPosition);
        myView.setY(initYPosition);
    }

    /**
     * @Return the type of Powerup, different for each type of Powerup (subclass)
     */
    public abstract String getPowerType();

    /**
     * Moves the powerup, based on elapsedTime, standardized to all machines.
     * @param elapsedTime
     */
    public void move(double elapsedTime){
        myView.setY(myView.getY() + mySpeed * elapsedTime);
    }

    /**
     * Deletes a powerup object by disabling it and hiding it from view
     */
    public void delete(){
        disable();
        hideImage();
    }

    /**
     * Start dropping the powerup, from a velocity of 0.
     */
    public void startDrop(){
        mySpeed = POWERUP_SPEED;
    }

    /**
     * Return X coordinate of Powerup
     */
    public int getXPos(){
        return initXPosition;
    }

    /**
     * @Return Y coordinate of Powerup
     */
    public int getYPos(){
        return initYPosition;
    }

    /**
     * @Return whether the powerup is enabled
     */
    public boolean isEnabled(){
        return Enabled;
    }

    /**
     * Internal View of Powerup Object
     */
    public Node getView () {
        return myView;
    }


    private void hideImage(){
        myView.setImage(null);
    }

    private void disable(){
        Enabled=false;
    }
}
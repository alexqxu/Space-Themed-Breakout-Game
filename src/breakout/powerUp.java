package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that stores information and methods for Powerup objects
 * @author Alex Xu
 */
public class powerUp {
    public static final int POWERUP_SPEED = 100;
    public static final int POWERUP_SIZE = 25;

    public static final String STRENGTH_POWERUP_FILE = "strengthPowerUp.png";
    public static final String TIME_POWERUP_FILE = "timePowerUp.png";
    public static final String LENGTH_POWERUP_FILE = "lengthPowerUp.png";
    public static final String HEALTH_POWERUP_FILE = "healthPowerUp.png";

    private Image StrengthPowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(STRENGTH_POWERUP_FILE));
    private Image TimePowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(TIME_POWERUP_FILE));
    private Image LengthPowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(LENGTH_POWERUP_FILE));
    private Image HealthPowerUp = new Image(this.getClass().getClassLoader().getResourceAsStream(HEALTH_POWERUP_FILE));

    Image image;
    private ImageView myView;

    private int initXPosition;
    private int initYPosition;
    private int mySpeed = 0;
    private boolean Enabled;

    private String myPowerUpType;

    /**
     * Constructs the powerup based on an X and Y coordinate position, as well as a String indicating the
     * type of powerup.
     * @param xPosition
     * @param yPosition
     * @param type
     */
    public powerUp(int xPosition, int yPosition, String type){
        initXPosition = xPosition;
        initYPosition = yPosition;
        set_Type(type);
        myView = new ImageView(image);
        int size = POWERUP_SIZE;
        myView.setFitWidth(size);
        myView.setFitHeight(size);
        myView.setX(initXPosition);
        myView.setY(initYPosition);
        Enabled = true;
    }

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
     * @Return the type of powerup
     */
    public String getPowerType(){
        return myPowerUpType;
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

    private void set_Type(String typeOfPowerUp){
        myPowerUpType = typeOfPowerUp;
        if(typeOfPowerUp.equals("strength")){
            set_Skin(1);
        }
        else if(typeOfPowerUp.equals("time")){
            set_Skin(2);
        }
        else if(typeOfPowerUp.equals("length")){
            set_Skin(3);
        }
        else if(typeOfPowerUp.equals("health")){
            set_Skin(4);
        }
    }

    private void set_Skin(int val){
        if(val == 1){
            image = StrengthPowerUp;
        }
        else if (val == 2){
            image = TimePowerUp;
        }
        else if (val ==3){
            image = LengthPowerUp;
        }
        else{
            image = HealthPowerUp;
        }
    }

    private void hideImage(){
        myView.setImage(null);
    }

    private void disable(){
        Enabled=false;
    }
}

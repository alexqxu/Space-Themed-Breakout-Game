package breakout;

/**
 * Subclass for the timePowerUp Powerup type. Responsible for holding information regarding its image path and Powerup Type. Inherits superclass methods.
 * @author Alex Xu
 */
public class timePowerUp extends powerUp{

    private String imageFileName = "timePowerUp.png";
    private String type = "time";

    /**
     * Constructs a timePowerUp object. Calls the superclass Powerup's constructor in setting locations and image.
     * @param xPosition X Coordinate
     * @param yPosition Y Coordinate
     */
    public timePowerUp(int xPosition, int yPosition){
        super(xPosition, yPosition);
        super.setImage(imageFileName);
    }

    /**
     * Returns a String representing the powerUp type.
     * @return String type
     */
    @Override
    public String getPowerType() {
        return type;
    }
}
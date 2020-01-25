package breakout;

/**
 * Subclass for the healthPowerUp Powerup type. Responsible for holding information regarding its image path and Powerup Type. Inherits superclass methods.
 * @author Alex Xu
 */
public class healthPowerUp extends powerUp{

    private String imageFileName = "healthPowerUp.png";
    private String type = "health";

    /**
     * Constructs a healthPowerUp object. Calls the superclass Powerup's constructor in setting locations and image.
     * @param xPosition X Coordinate
     * @param yPosition Y Coordinate
     */
    public healthPowerUp(int xPosition, int yPosition){
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
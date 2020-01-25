package breakout;

/**
 * Subclass for the lengthPowerUp Powerup type. Responsible for holding information regarding its image path and Powerup Type. Inherits superclass methods.
 * @author Alex Xu
 */
public class lengthPowerUp extends powerUp{

    private String imageFileName = "lengthPowerUp.png";
    private String type = "length";

    /**
     * Constructs a lengthPowerUp object. Calls the superclass Powerup's constructor in setting locations and image.
     * @param xPosition X Coordinate
     * @param yPosition Y Coordinate
     */
    public lengthPowerUp(int xPosition, int yPosition){
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
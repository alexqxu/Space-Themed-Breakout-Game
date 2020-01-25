package breakout;

/**
 * Subclass for the strengthPowerUp Powerup type. Responsible for holding information regarding its image path and Powerup Type. Inherits superclass methods.
 * @author Alex Xu
 */
public class strengthPowerUp extends powerUp{

    private String imageFileName = "strengthPowerUp.png";
    private String type = "strength";

    /**
     * Constructs a strengthPowerUp object. Calls the superclass Powerup's constructor in setting locations and image.
     * @param xPosition X Coordinate
     * @param yPosition Y Coordinate
     */
    public strengthPowerUp(int xPosition, int yPosition){
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
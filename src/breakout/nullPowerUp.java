package breakout;

/**
 * Subclass for the nullPowerUp (none) Powerup type. Responsible for holding information regarding its image path and Powerup Type. Inherits superclass methods.
 * @author Alex Xu
 */
public class nullPowerUp extends powerUp{

    private String imageFileName = "nullPowerUp.png";
    private String type = "none";

    /**
     * Constructs a nullPowerUp object. Calls the superclass Powerup's constructor in setting locations and image.
     * @param xPosition X coordinate
     * @param yPosition Y coordinate
     */
    public nullPowerUp(int xPosition, int yPosition){
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
package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Brick class is responsible for storing and handling information for each brick object.
 */
public class Brick {
    private int brickHealth;
    private boolean brickEnabled = true;

    private final String BRICK1_IMAGE = "brickh1.png";
    private final String BRICK2_IMAGE = "brickh2.png";
    private final String BRICK3_IMAGE = "brickh3.png";

    private Image brick1Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
    private Image brick2Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK2_IMAGE));
    private Image brick3Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK3_IMAGE));

    private Image myBrickImage;
    private ImageView myBrickView;

    private String PowerUp;

    private int myXLocation;
    private int myYLocation;

    private final int horizontalSize = 75;
    private final int verticalSize = 30;

    /**
     * Brick constructor that takes a health value, x location, y location, and a powerup type.
     * @param health how many hit points the brick has
     * @param locationX x Location
     * @param locationY y Location
     * @param PowerUpType type of powerup the brick is associated with
     */
    public Brick(int health, int locationX, int locationY, String PowerUpType){
        PowerUp = PowerUpType;
        brickHealth = health;
        set_Skin(health);
        myBrickView = new ImageView(myBrickImage);
        adjustBrickSize();
        adjustBrickLocation(locationX, locationY);
    }

    /**
     * Reduces the health of the brick
     * @param value to reduce the health of the brick by
     */
    public void reduceHealth(int value){
        if(brickEnabled) {
            brickHealth = brickHealth - value;
            updateSkin();
            if (brickHealth <= 0) {
                disableBrick();
            }
        }
    }

    /**
     * Reduce the health of the brick to 1, regardless of original health.
     * Used for cheat code D.
     */
    public void reduceHealthTo1(){
        if(brickEnabled){
            brickHealth = 1;
            updateSkin();
        }
    }

    /**
     * Internal View of Brick
     */
    public Node getView () {
        return myBrickView;
    }

    /**
     * @return powerup value
     */
    public String getPowerUp(){
        return PowerUp;
    }

    /**
     * @return if the brick is enabled or not
     */
    public boolean getBrickEnabled(){return brickEnabled;}

    /**
     * @return x location of brick
     */
    public int getXLoc(){
        return myXLocation;
    }

    /**
     * @return y location of brick
     */
    public int getYLoc(){
        return myYLocation;
    }

    private void set_Skin(int health){
        if(health == 1){
            myBrickImage = brick1Image;
        }
        else if (health == 2){
            myBrickImage = brick2Image;
        }
        else{
            myBrickImage = brick3Image;
        }
    }

    private void disableBrick() {
        myBrickView.setImage(null);
        brickEnabled = false;
    }

    private void updateSkin() {
        set_Skin(brickHealth);
        myBrickView.setImage(myBrickImage);
    }

    private void adjustBrickLocation(int locX, int locY) {
        myXLocation = locX;
        myYLocation = locY;
        myBrickView.setX(locX);
        myBrickView.setY(locY);
    }

    private void adjustBrickSize() {
        myBrickView.setFitWidth(horizontalSize);
        myBrickView.setFitHeight(verticalSize);
    }
}

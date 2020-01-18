package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Brick {
    public int BRICK_HEALTH;
    public boolean BRICK_ENABLED = true;

    private static final String BRICK1_IMAGE = "brickh1.png";
    private static final String BRICK2_IMAGE = "brickh2.png";
    private static final String BRICK3_IMAGE = "brickh3.png";

    private static final String BRICK_STRENGTH_IMAGE = "brick1.gif";
    private static final String BRICK_TIME_IMAGE = "brick2.gif";
    private static final String BRICK_LENGTH_IMAGE = "brick4.gif";
    private static final String BRICK_HEALTH_IMAGE = "brick5.gif";

    Image brick1Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
    Image brick2Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK2_IMAGE));
    Image brick3Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK3_IMAGE));

    Image brickStrengthImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_STRENGTH_IMAGE));
    Image brickTimeImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_TIME_IMAGE));
    Image brickLengthImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_LENGTH_IMAGE));
    Image brickHealthImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_HEALTH_IMAGE));

    Image image;

    private ImageView myView;

    String PowerUp;

    int myXLoc;
    int myYLoc;

    public Brick(int health, int locX, int locY, String PowerUpType){
        PowerUp = PowerUpType;      //Has to be first line because set_skin references it.
        set_Skin(health);
        myView = new ImageView(image);

        int horizontalSize = 75;
        int verticalSize = 30;
        myView.setFitWidth(horizontalSize);
        myView.setFitHeight(verticalSize);

        myView.setX(locX);
        myView.setY(locY);

        myXLoc = locX;
        myYLoc = locY;

        BRICK_HEALTH = health;
    }

    public void reduceHealth(int value){
        if(BRICK_ENABLED) {
            BRICK_HEALTH = BRICK_HEALTH - value;

            set_Skin(BRICK_HEALTH);
            myView.setImage(image);

            if (BRICK_HEALTH <= 0) {
                myView.setImage(null);
                BRICK_ENABLED = false;
            }
        }
    }

    public void set_Skin(int health){
        /*
        if(PowerUp.equals("strength")){
            image = brickStrengthImage;
        }
        else if(PowerUp.equals("time")){
            image = brickTimeImage;
        }
        else if(PowerUp.equals("length")){
            image = brickLengthImage;
        }
        else if(PowerUp.equals("health")){
            image = brickHealthImage;
        }

         */

        if(health == 1){
            image = brick1Image;
        }
        else if (health == 2){
            image = brick2Image;
        }
        else{
            image = brick3Image;
        }
    }

    public String getPowerUp(){
        return PowerUp;
    }

    public int getXLoc(){
        return myXLoc;
    }
    public int getYLoc(){
        return myYLoc;
    }

    /**
     * Internal View of Brick
     */
    public Node getView () {
        return myView;
    }
}

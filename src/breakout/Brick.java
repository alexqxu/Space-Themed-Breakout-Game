package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
    public int BRICK_HEALTH;
    public boolean BRICK_ENABLED = true;

    private final String BRICK1_IMAGE = "brickh1.png";
    private final String BRICK2_IMAGE = "brickh2.png";
    private final String BRICK3_IMAGE = "brickh3.png";

    Image brick1Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
    Image brick2Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK2_IMAGE));
    Image brick3Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK3_IMAGE));

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

    public void reduceHealthTo1(){
        if(BRICK_ENABLED){
            BRICK_HEALTH = 1;
            set_Skin(BRICK_HEALTH);
            myView.setImage(image);
        }
    }

    public void set_Skin(int health){
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

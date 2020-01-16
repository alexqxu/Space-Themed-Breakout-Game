package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Brick {
    public int BRICK_HEALTH;
    public boolean BRICK_ENABLED = true;

    private static final String BRICK1_IMAGE = "brick8.gif";
    private static final String BRICK2_IMAGE = "brick7.gif";
    private static final String BRICK3_IMAGE = "brick6.gif";

    Image brick1Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
    Image brick2Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK2_IMAGE));
    Image brick3Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK3_IMAGE));
    Image image;

    private ImageView myView;

    public Brick(int health, int locX, int locY){
        set_Skin(health);
        myView = new ImageView(image);

        int size = 50;
        myView.setFitWidth(size);

        myView.setX(locX);
        myView.setY(locY);

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
        if(health == 1){
            image = brick1Image;
        }
        else if (health == 3){
            image = brick2Image;
        }
        else{
            image = brick3Image;
        }
    }

    /**
     * Internal View of Brick
     */
    public Node getView () {
        return myView;
    }
}

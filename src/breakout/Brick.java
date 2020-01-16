package breakout;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
    public int BRICK_HEALTH;
    public boolean BRICK_ENABLED = true;

    private ImageView myView;

    public Brick(Image image, int health, int screenWidth, int screenHeight, int locX, int locY){
        myView = new ImageView(image);
        int size = screenWidth/8;
        myView.setFitWidth(size);

        myView.setX(locX);
        myView.setY(locY);

        BRICK_HEALTH = health;
    }

    public void reduceHealth(int value){
        if(BRICK_ENABLED) {
            BRICK_HEALTH = BRICK_HEALTH - value;
            if (BRICK_HEALTH <= 0) {
                myView.setImage(null);
                BRICK_ENABLED = false;
            }
        }
    }

    /**
     * Internal View of Brick
     */
    public Node getView () {
        return myView;
    }


}

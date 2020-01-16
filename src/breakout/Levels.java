package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Levels {

    public static final String LEVEL_1_FILENAME = "Level_1.txt";
    public static final String LEVEL_2_FILENAME = "Level_2.txt";
    public static final String LEVEL_3_FILENAME = "Level_3.txt";
    private static final String BRICK1_IMAGE = "brick3.gif";

    private File levelFile;
    private Scanner sc;

    public void readFile(int val) throws FileNotFoundException {
        String level_val;
        if(val == 1){
            level_val = LEVEL_1_FILENAME;
        }
        else if (val==2){
            level_val = LEVEL_2_FILENAME;
        }
        else{
            level_val = LEVEL_3_FILENAME;
        }

        levelFile = new File(level_val);
        sc = new Scanner(levelFile);
        sc.useDelimiter(" ");
    }

    public void get_Values(){ //Refactor Later
    }

    public List<Brick> generate_Bricks(int num){
        int xLoc = 0;
        int yLoc = 0;

        Image brickImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
        List<Brick> result = new ArrayList<>();

        for (int k = 0; k < num; k++) {

            String val = sc.next();

            Brick b = new Brick(brickImage, Integer.parseInt(val), xLoc, yLoc);
            result.add(b);

            xLoc += 50;

            if (xLoc == 600) {
                xLoc = 0;
                yLoc += 50;
            }
        }

        return result;
    }

}
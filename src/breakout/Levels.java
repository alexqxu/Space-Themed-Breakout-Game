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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Levels {

    public static final String LEVEL_1_FILENAME = "resources\\Level_1.txt";
    public static final String LEVEL_2_FILENAME = "resources\\Level_2.txt";
    public static final String LEVEL_3_FILENAME = "resources\\Level_3.txt";

    //private static final String BRICK1_IMAGE = "brick8.gif";
    //private static final String BRICK2_IMAGE = "brick7.gif";
    //private static final String BRICK3_IMAGE = "brick6.gif";

    //private File levelFile;
    //private Scanner sc;

    /*
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

        levelFile = new File("C:\\Users\\alexx\\Documents\\Classes\\Spring 2020\\CS 308\\game_aqx\\resources\\Level_1.txt");
        sc = new Scanner(levelFile);
        sc.useDelimiter(" ");
    }

     */

    public void get_Values(){ //Refactor Later
    }

    public static String readFileAsString(int val)throws Exception
    {
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
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(level_val)));
        return data;
    }

    public List<Brick> generate_Bricks() throws Exception {
        String file = readFileAsString(1);
        String[] data = file.split(" ");

        int xLoc = 0;
        int yLoc = 0;
        //String val = sc.next();

        //Image brick1Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
        //Image brick2Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK2_IMAGE));
        //Image brick3Image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK3_IMAGE));


        List<Brick> result = new ArrayList<>();

        /*
        while(val != null) {
            Brick b = new Brick(brickImage, Integer.parseInt(val.trim()), xLoc, yLoc);
            result.add(b);

            xLoc += 50;

            if (xLoc == 600) {
                xLoc = 0;
                yLoc += 50;
            }
            val = sc.next();
        }

         */

        for(String d : data){
            int brickValue = Integer.parseInt(d.trim());

            if(brickValue != 0) {
                Brick b = new Brick(brickValue, xLoc, yLoc);
                result.add(b);
            }

            xLoc += 75;

            if (xLoc == 600) {
                xLoc = 0;
                yLoc += 25;
            }
        }

        return result;
    }

}
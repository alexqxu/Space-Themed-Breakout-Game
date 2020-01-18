package breakout;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class brickLevel {

    public static final String LEVEL_1_FILENAME = "resources\\Level_1.txt";
    public static final String LEVEL_2_FILENAME = "resources\\Level_2.txt";
    public static final String LEVEL_3_FILENAME = "resources\\Level_3.txt";

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

    public List<Brick> generate_Bricks(int level_val) throws Exception {
        String file = readFileAsString(level_val);
        String[] data = file.split(" ");

        int xLoc = 0;
        int yLoc = 0;

        List<Brick> result = new ArrayList<>();

        for(String d : data){
            int brickValue = Integer.parseInt(d.trim());

            if(brickValue == 11) {                              //MIGHT CHANGE THIS LATER TO CHECK LETTERS, TO MAKE DESIGNING LEVELS MORE INTUITIVE.
                Brick b = new Brick(brickValue/10, xLoc, yLoc, "strength");
                result.add(b);
            }
            else if(brickValue == 12){
                Brick b = new Brick(brickValue/10, xLoc, yLoc, "time");
                result.add(b);
            }
            else if(brickValue == 13){
                Brick b = new Brick(brickValue/10, xLoc, yLoc, "length");
                result.add(b);
            }
            else if(brickValue == 14){
                Brick b = new Brick(brickValue/10, xLoc, yLoc, "health");
                result.add(b);
            }

            else if(brickValue != 0) {
                Brick b = new Brick(brickValue, xLoc, yLoc, "none");
                result.add(b);
            }

            xLoc += 100;

            if (xLoc == 600) {
                xLoc = 0;
                yLoc += 40;
            }
        }

        return result;
    }

}
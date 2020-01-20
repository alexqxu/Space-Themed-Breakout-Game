package breakout;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Imports the text files used to configure the bricks in each level.
 * @author Alex Xu
 */
public class brickLevelGenerator {

    public static final String LEVEL_1_FILENAME = "resources\\Level_1.txt";
    public static final String LEVEL_2_FILENAME = "resources\\Level_2.txt";
    public static final String LEVEL_3_FILENAME = "resources\\Level_3.txt";

    private int startingXLocation = 0;
    private int startingYLocation = 0;

    /**
     * Reads text files and returns a string of the characters in the file
     * @param level the level desired
     * @return
     * @throws Exception
     */
    public static String readFileAsString(int level)throws Exception
    {
        String level_val;
        if(level == 1){
            level_val = LEVEL_1_FILENAME;
        }
        else if (level==2){
            level_val = LEVEL_2_FILENAME;
        }
        else{
            level_val = LEVEL_3_FILENAME;
        }
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(level_val)));
        return data;
    }

    /**
     * Generates bricks according to the information inputted
     * @param level_val level desired
     * @return a List of Bricks
     */
    public List<Brick> generate_Bricks(int level_val) throws Exception {
        String file = readFileAsString(level_val);
        String[] data = file.split(" ");
        List<Brick> result = new ArrayList<>();
        for(String d : data){
            int brickValue = Integer.parseInt(d.trim());
            if(brickValue >= 10) {
                Brick b;
                if (brickValue % 10 == 1) {
                    b = new Brick(brickValue / 10, startingXLocation, startingYLocation, "strength");
                } else if (brickValue % 10 == 2) {
                    b = new Brick(brickValue / 10, startingXLocation, startingYLocation, "time");
                } else if (brickValue % 10 == 3) {
                    b = new Brick(brickValue / 10, startingXLocation, startingYLocation, "length");
                } else{
                    b = new Brick(brickValue / 10, startingXLocation, startingYLocation, "health");
                }
                result.add(b);
            }
            else if(brickValue != 0) {
                Brick b = new Brick(brickValue, startingXLocation, startingYLocation, "none");
                result.add(b);
            }
            refreshRenderLocation();
        }
        return result;
    }

    private void refreshRenderLocation() {
        startingXLocation += 100;
        if (startingXLocation == 600) {
            startingXLocation = 0;
            startingYLocation += 40;
        }
    }
}
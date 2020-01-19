package breakout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class switchScreen{

    Stage myStage;
    Scene scene1;

    int prevLevel; //THIS MAY BE REFACTORED LATER SO THAT IT ACCESSES A PUBLIC VARIABLE FROM THE GAMEPLAY.
    int nextLevel;
    int myScore;

    public void setLevelVals(int pLevel, int nLevel){
        prevLevel = pLevel;
        nextLevel = nLevel;
    }

    public void set_Score(int score){
        myScore = score;
    }

    public void set_Stage(Stage s){
        myStage = s;
    }


    public Scene start_Scene(){
        if(nextLevel > 0) {
            scene1 = new Scene(setUpLayout(), 700, 600);
        }
        else if(nextLevel == 0){
            scene1 = new Scene(finish_Screen(), 700, 600);
        }
        return scene1;
    }

    private Group setUpLayout() {
        Group layout = new Group();

        Label label1 = new Label("You beat LEVEL " + prevLevel + "! Move on to LEVEL " + nextLevel + "?");

        Label label2 = new Label("Your Current Score Is: " + myScore);
        label2.setLayoutY(50);

        Button okButton = new Button();
        okButton.setText("OK");
        okButton.setLayoutY(100);

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //PUT ACTION HERE
                GamePlay nextGame = new GamePlay();
                nextGame.set_Level(nextLevel);
                nextGame.set_Score(myScore);
                try {
                    nextGame.start(myStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Define layout

        layout.getChildren().addAll(label1, label2, okButton);

        return layout;
    }

    public Group finish_Screen(){
        Group layout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream("YouWin700x600.png"));
        ImageView backgroundView = new ImageView(backgroundImage);

        Button returnHomeButton = new Button();
        returnHomeButton.setText("Return Home");

        returnHomeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main newGame = new Main();
                newGame.start(myStage);
            }
        });

        layout.getChildren().addAll(backgroundView, returnHomeButton);
        return layout;
    }
}

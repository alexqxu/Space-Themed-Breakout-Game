package breakout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        Label label1 = new Label("You beat LEVEL " + prevLevel + "! Move on to LEVEL " + nextLevel + "?");
        Label label2 = new Label("Your Current Score Is: " + myScore);

        Button okButton = new Button();
        okButton.setText("OK");

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
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, label2, okButton);

        scene1 = new Scene(layout1, 700, 600);

        return scene1;
    }

    //public Scene finish_Screen(){
    //   return new Scene = ;
    //}
}

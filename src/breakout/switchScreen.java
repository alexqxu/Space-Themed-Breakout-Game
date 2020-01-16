package breakout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class switchScreen{

    Scene scene1;

    int prevLevel;
    int nextLevel;

    public void setLevelVals(int pLevel, int nLevel){
        prevLevel = pLevel;
        nextLevel = nLevel;
    }
    public Scene start_Scene(){
        Label label1 = new Label("You beat LEVEL " + prevLevel + "! Move on to LEVEL " + nextLevel + "?");

        Button okButton = new Button();
        okButton.setText("OK");

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //PUT ACTION HERE
            }
        });

        //Define layout
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, okButton);

        scene1 = new Scene(layout1, 600, 600);

        return scene1;
        //window.setScene(scene1);
        //window.setTitle("Breakout Game");
        //window.show();
    }
}

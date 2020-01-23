CompSci 308: Game Project Analysis
===================
Name : Alex Xu
NetID: aqx

Design Review
=======

### Status

**What parts of the code did you spend the most time on (e.g., debugging, adding to, rewriting, or even just thinking about)?**

The part of the code I spent the most time on was debugging and rewriting my code. Working on this project was the first time I 
used JavaFX, so sometimes when I encountered a bug (typically after adding a feature), I was unsure as to what went wrong (or 
could have possibly gone wrong). So, I spent a lot of time researching how JavaFX works, and reading through my code to see 
where the bug may have been, and what might have caused it. For example, when I was working on my project, I encountered 
bug where my Powerup graphics would not show up on the screen. I later found out that the bug was caused by adding my Powerups 
to the Group that held all of the graphical elements before adding my Brick objects to that Group. Since the objects are drawn 
on the screen in the order in which they are added to the group, the Bricks were covering up the graphics for my Powerups.

Rewriting and refactoring my code also took a lot of time for me, since this was the first time I actually deeply thought 
about the *design* of my code, rather than just functionality. After reviewing what Dr. Duvall had mentioned in lecture and 
the assigned readings, I went through my code line-by-line to see what I could do to make the design of my code better (while 
preserving functionality). For example, I eliminated magic variables and reduced longer methods into shorter ones that are 
single-purpose.

**Where is your code not DRY (i.e., where does duplication exist within your code)?**

There is duplication in parts of my code that are responsible for creating the Groups that are used to display the 'Game Win' and 
'Game Loss' screens. For example, the following code is responsible for returning a Group that represents the 'Game Win' screen:

```java
private Group finish_Screen(){
        play_WinAudio();
        Group layout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(winBackgroundPath));
        ImageView backgroundView = new ImageView(backgroundImage);
        Button returnHomeButton = new Button();
        returnHomeButton.setText("Return Home");
        returnHomeButton.setLayoutX(returnHomeButtonXLocation);
        returnHomeButton.setLayoutY(returnHomeButtonYLocation);
        returnHomeButton.setOnAction(event -> {
            winAudioPlayer.stop();
            mainMenu newGame = new mainMenu();
            newGame.start(myStage);
        });
        layout.getChildren().addAll(backgroundView, returnHomeButton);
        return layout;
    }
```

...and this is the code that is responsible for returning a Group representing the screen the player sees when the game is lost.

```java
private Group lose_Screen(){
        play_LossAudio();
        Group layout = new Group();
        Image backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(loseBackgroundPath));
        ImageView backgroundView = new ImageView(backgroundImage);
        Button returnHomeButton = new Button();
        returnHomeButton.setText("Return Home");
        returnHomeButton.setLayoutX(returnHomeButtonXLocation);
        returnHomeButton.setLayoutY(returnHomeButtonYLocation);
        returnHomeButton.setOnAction(event -> {
            lossAudioPlayer.stop();
            mainMenu newGame = new mainMenu();
            newGame.start(myStage);
        });
        layout.getChildren().addAll(backgroundView, returnHomeButton);
        return layout;
    }
```

There is quite a bit of duplication in these two sets of code, in terms of calling the sequence of code that sets up the next dialogue screen. 
For example, except for the audio that is played and the path of the image background, the button, its display text, and its location are 
designed to be identical for these two methods, and in terms of functionality. Thus, if I had more time to work on my code/further refactor, I would 
instead design a method that calls the portion of the duplicated code, and the background music/background image path can be passed in as parameters to 
the newly refactored method. This way, the duplication can be minimized/removed.

**What is the most important bug remaining in the program?**

The most important remaining bug in my program is that when the paddle is in motion (the player is pressing down the left 
or right arrow keys), and the ball is approaching the paddle at an angle, there is a chance that upon the ball's collision 
with the paddle, the ball will 'get stuck' on the side of the paddle, and begin moving erratically within and around the paddle 
(oscillating up and down) for a period of time up to 2 seconds (especially if the ball is trapped between the paddle and the side 
wall boundaries). Then, eventually the ball will be released again. I believe that if given more time, I can fix this bug 
by adding conditions that modify ball behavior when it hits the side of the paddle.

**Describe two methods in detail:**

**Describe your specific efforts to make your code as readable as possible.**


### Design


### Alternate Designs

Here is another way to look at my design:

![This is cool, too bad you can't see it](crc-example.png "An alternate design")


### Conclusions
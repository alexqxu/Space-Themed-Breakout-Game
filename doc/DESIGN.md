CompSci 308: Game Project DESIGN
===================
Name : Alex Xu

NetID: aqx

*(This was an individual project, so I completed all aspects of the project.)*

###What are the project's design goals, specifically what kinds of new features did you want to make easy to add?

The project's design goal was to create an implementation of a game based on the classic Breakout Game, in a way that allows for flexibility
in adding new features. For example, I designed my code that allows for relatively easy design of new game levels, by modifying
the text files that are responsible for telling the game what bricks should be placed on each level.

Furthermore, I designed my game so that different components of the game, such as the ball, paddle, Powerups, and bricks, are separated into
different classes (Bouncer, Paddle, and Brick classes, respectively). This makes it easy to modify certain parameters/behavior in the game.
For example, if in the future the size of the ball was desired to be bigger, the value for the private instance variable
BOUNCER_SIZE can be increased. Other such values that can be adjusted without breaking the functionality of the game include
(among others) initialBouncerSpeed, horizontalSize (Brick class), verticalSize (Brick class), PADDLE_COLOR, and PADDLE_LENGTH.
I also aimed to name my variables in the most self-explanatory way possible, so that someone reading my code would be able
to understand what each variable is responsible for. I also extended this naming to my methods as well, and tried to make them
as clear as possible.

Another benefit of having separated classes, or classes that are each responsible for a single aspect of the game, is that
multiple copies of those objects can be easily created. For example, it is currently very easy for my game to handle multiple
Powerups at the same time, as each Powerup is its own object (as opposed to being hard-coded in the main game loop). If one
wanted to modify what Powerups were available on a given level, it is a relatively easy process as only the text file responsible
for telling the game where certain Powerups are placed (or associated with which bricks) is needed. Furthermore, the creation of
many different bricks based off an initial configuration text file would not have been possible if the bricks were hard-coded
into the main class individually. This idea can be extended to other objects as well, such as the ball and game paddle. Although they
are not current features of my game, I wanted to make it easy to add multiple balls and/or multiple paddles in the future.
Multiple balls (triggered by a new type of Powerup, perhaps) would make gameplay more dynamic and interesting, while
multiple paddles could be an interesting implementation of multiplayer gameplay.

Lastly, although currently my Powerups class is a single class responsible for all types of Powerups (currently four), I plan on
refactoring my code (as part of my Code Masterpiece) into an abstract class and subclasses. This new implementation would
make it easier to add new types of Powerups, as only a new subclass would be needed (and its associated attributes, like
appearance and behavior) to make that possible. Currently if one would like to add a new type of Powerup to the game, they
would have to add it to the single Powerup class, and add a conditional case. Before coming into CS 308 I did not think much about
writing code in this way, as I was more focused on functionality rather than code design. However, after this past week's
lectures and assigned readings, I've developed a better understanding and appreciation for software design concepts (including
abstraction and inheritance, which I plan to use to refactor my code).

###Describe the high-level design of your project, focusing on the purpose and interaction of the core classes?

The mainMenu class is the "Main" class of my game that should be run. It is responsible for constructing the appearance
and functionality of the Splash Screen and Rules Screen (screen that displays the rules of the game). The rules screen is a
Scene object, and the stage object can be set between the splash screen and the rules screen (depending on button actions).

When the "Start" button is clicked (to start playing the game), a new instance of the GamePlay class is created, with
setter methods passing in information including the initial level, initial score, and initial number of player lives. Then, the stage
object is passed to the start() method of GamePlay, which starts the game.

In the GamePlay class, the two most important methods called by the start() method are setupGame() and attachGameLoop().
setUpGame() initializes the paddle, ball, bricks, and Powerups (each of which are objects of their own classes), and 
places them in the scene. To do this, it is aided by the brickLevelGenerator class, which reads the text level configuration
text files and returns a list of Bricks. Because each Brick contains information regarding its position, the scene is able
to place the objects into the scene in their desired locations. attachGameLoop() creates and starts the timeline object, named
animation. This timeline object has keyframes that run the contents of the step() method at every keyframe.

Within the step() method, game physics are checked for (collisions with balls, bricks, paddles, and powerups), and abilities
from Powerups and cheatcodes are added/removed. Methods of certain objects can be called during this process; for example,
if a ball hits a brick, the method reduceHealth is called, which reduces the health of that brick and updates its skin to reflect
a lower health value. Mouse input and keyboard input are also checked, which are used to launch the ball
at the beginning of every level and move the paddle. Game stats, such as Lives Left and Score are also updated, which means that the game must
count how many bricks are left and check if the ball has fallen beneath the screen at every keyframe.

The positions of every movable object in the game (paddle, ball, and Powerups) are updated every keyframe as well. This is done
by modifying the location information of each moved object, by using the velocity values of each of those objects.

The Last thing that is checked in every keyframe is whether or not the player has beat the level. If the player has beat the
level, a new dialogueScreen object is created and displayed. Information that should be passed onto the next level, such as
the number of player lives left and the score, are also passed into the dialogueScreen object. The dialogueScreen object is
responsible for creating and displaying the interstitial dialogue window in-between windows that displays game stats and
allows the player to make a decision as to whether or not they want to continue to the next level. These screens are constructed
similarly to the Splash and Rules screens. When the player clicks on the OK button to move on to the next level, a new instance
of GamePlay is created, with those aforementioned variables passed in.

If the player has lost, a dialogueScreen is created, but this time it displays the Game Over message and gives the user the
option to go back to the Splash screen (with previous game stats erased). If the player has beat the final level (there are no
more levels left), the dialogueScreen will instead construct and display the Game Win screen (which tells the player that they
have won the game), and also give them the option to go back to the Splash screen.

###What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features?

As mentioned previously, the decision to make the Paddle, Bouncer (ball), bricks, and Powerups their own class greatly simplified
the design of my project, since it was easier to create new instances of these objects rather than to hard code their attributes
as part of the main game loop. Furthermore, by having separate classes for the splash Screen, interstitial screens, and the 
actual gameplay, it was easier to conceptualize and design the "flow" of the game, in terms of the player progression in the
game. With different classes and readable methods + variables, new features such as multiple balls, paddles, cheat codes, or new Powerups can be
implemented relatively easily (especially after I refactor my Powerups class as part of my Code Masterpiece). Adjustments to sizing
and appearances can also be easily changed by modifying stance variable values. This impacted the ease of implementation of
several core features, such as different Powerups and paddle abilities.

However, there are some aspects of my project that I designed to be not as flexible (stemmed from my assumptions), to simplify the code's design. For example,
the core mechanic of the game, which includes scoring points by hitting bricks, winning levels by clearing bricks, losing lives
by having the ball fall through the bottom of the screen, are core game mechanics that can not be easily changed without making
relatively significant changes to how the project was set up. These game mechanics can be modified and adjusted (such as redefining
what it means to beat a level), but significant portions of code would need to be replaced, especially because of dependencies (such as the fact that
whether or not a player loses a game is dependant on how many player lives are left). If I had designed these aspects of
the game to be flexible as well, I believe the project would become much larger since it would have to be designed to handle
many different possibilities for even the core game mechanics. Since that was not required in this project, I made the assumption
that these core game mechanics would remain the same, and that additional features would be focused on diversifying gameplay
with different kinds of objects.

###Describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline?

* **Additional features that can be implemented / modified:**

    * **Cheat codes:** To add a new cheat code in addition to the ones I already included, a new conditional statement needs to be added to the main game loop, specifically within the
handleKeyInput() class. This checks for a key press for the new cheat code. Then, depending on the behavior desired for the cheat
code, new methods might need to be created in either the Brick, Paddle, or Bouncer classes to accommodate whatever it is that needs
to be accomplished. For example, one of the cheat codes I implemented was the cheat code P. When pressed, it makes the paddle's width
wide wide enough to cover the entire play area. To do implement it, I created a new conditional in the handleKeyInput() class, and
created a method within the Paddle class, setMaxPaddleLength(), which set the paddle to the length of the play area.

    * **Powerups:** In its current implementation, all of the different types of Powerups are handled by a single powerUp class.
So, to add new Powerups, a new skin needs to be added to the class, and a new conditional needs to be added to switch between
the Skins, in the set_Type() method. Then, the Powerup's behavior needs to be defined in the main game loop, in the GamePlay class.
However, I think this current implementation of my powerUp class can be refactored, especially after learning about inheritance through
lecture and class readings last week. My plan is to create an abstract powerUps class, and make subclasses that represent
the different types of powerUps available. This way, making a new powerUp would just mean adding a new subclass that extends
the abstract powerUp class.

    * **Music and Sounds:** Changing/adding music to the game is as simple as changing the file path for the music/sounds. Currently, I have
background music playing for the splash screen and rules screen, and have sound effects for brick-ball collisions, winning the game,
and losing the game. Those sound effects can easily be switched out for other sound files. In addition, music or sound effects
can be added to places that currently do not have sound, by adding a MediaPlayer object in the same fashion as it is currently implemented
in the mainMenu class, for example. Then, wherever/whenever sound is desired, the play() method can be called on that MediaPlayer.

* **Features included in my Game PLAN, but ultimately not included in the game due to time constraint:**

    * **Multiple Balls:** Multiple balls can be implemented by using a List of Bouncer objects in the GamePlay class, replacing the single
    Bouncer object that is currently implemented. Because the Bouncers are their own class, more Bouncers can be made relatively easily.
    The only updates that would need to be made would be to change all the places in the code where currently the single Bouncer object
    is referenced. These references should be replaced with a for-each loop instead. In addition, the method calcLives(), which
    calculates how many player lives are left, would need to be updated so that the player should lose a life only when there are 
    no more balls left on the screen (in other words, all of them have fallen through the bottom of the screen). This multiple balls
    feature could be implemented with a new cheat code or Powerup that activates the feature.
    
    * Types of bricks beyond different Health and Powerup associations: Currently, I have three types of bricks (normal bricks, bricks
    with multiple lives, and bricks that hold powerups), which is the number required by the game specifications. However, if one
    were to make new types of bricks with radically different behaviors (such as a moving brick), a subclass would need to be created
    to represent that brick with a certain velocity. That subclass could also overwrite methods in the super Brick class, such as health,
    with the goal of making that brick indestructible, for example.
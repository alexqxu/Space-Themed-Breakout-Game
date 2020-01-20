game
====

This project implements the game of Breakout.

Name: Alex Xu (aqx)

### Timeline

Start Date: 1/10/20

Finish Date: 1/19/20

Hours Spent: 38

### Resources Used

Online Resources:

* Game Art:
    * Game Side Panel: https://p7.hiclipart.com/preview/110/711/694/video-television-set-high-definition-television-cool-video-borders.jpg
    * Game Background: https://www.lefthudson.com/wp-content/uploads/2019/11/retro-video-game-background-unique-neon-drive-80s-arcade-game-a-game-by-fraoula-inspiration-of-retro-video-game-background.jpg
    * Game Background 2: https://i.pinimg.com/originals/b1/bd/c1/b1bdc1ae539dcbd1a7c33cef3e5f2d9a.gif

* Sprite Art:
    * Health Power Up: http://pixelartmaker.com/art/2ede25037e61e96.png
    * Ball Slowing Power Up: https://i.pinimg.com/originals/db/0f/37/db0f37b499292ac88330787fad668fd8.png
    * Length Power Up: https://art.pixilart.com/67db7cfcdfb1dbd.png
    * Strength Power Up: http://pixelartmaker.com/art/8754a526942cc0b.png

 * Menu Art:
    * Arrow Keys: https://mpng.pngfly.com/20180502/qve/kisspng-computer-keyboard-arrow-keys-computer-icons-page-u-5ae939f140e7c1.4426757415252341612659.jpg
    * Mouse: https://icon2.cleanpng.com/20180320/xfw/kisspng-computer-mouse-point-and-click-mouse-button-comput-left-mouse-button-5ab0edc8804937.3287984615215446485255.jpg
    
* Tutorials:
    * Playing Sound in JavaFX: https://stackoverflow.com/questions/23202272/how-to-play-sounds-with-javafx
    * JavaFX image and Imageview: https://o7planning.org/en/11127/javafx-image-and-imageview-tutorial
    * Dr. Duvall's example_lab code
    
* Game sounds:
    * Incompetech.com
    * Game Win: https://www.youtube.com/watch?v=Kp4Um4AS-5U
    * Game Lose: https://www.youtube.com/watch?v=dqPxDYdbgoc
    
### Running the Program

**Main class:** mainMenu.java

**Data files needed (in resources folder):**
* Text Files for Level Generation:
    * Level_1.txt
    * Level_2.txt
    * Level_3.txt
    
* Game Art / Sprites:
    * ball.gif
    * brickh1.png
    * brickh2.png
    * brickh3.png
    * healthPowerUp.png
    * lengthPowerUp.png
    * strengthPowerUp.png
    * timePowerUp.png
    * ClickToBegin.gif
    * UseArrowKeys.gif
    * game_Background700x600.png
    * GameOver700x600.png
    * HomeMenuBackground700x600.png
    * RulesScreenBackground700x600.png
    * YouWin700x600.png
    
* Game Sounds:
    * 3909-industrial-cinematic-by-kevin-macleod.mp3
    * GameLoseSound.mp3
    * pong_beep.wav
    * winGameEffect.mp3

Key/Mouse inputs:

* Use Mouse to Navigate through menus
* Use Mouse to AIM
* Use Left mouse-click to SHOOT the ball
* Use Left and Right Arrow Keys to move the paddle

Cheat keys:

* L : Add a life
* R : Reset ball and paddle to original location
* 1 - 2 : Jump to a certain level
* P : Make Paddle HUGE
* D : Reduce Health of All Bricks on Screen to ONE

Known Bugs:

Through testing the game, it was discovered that in the unlikely event that the ball is hit with the side of a moving paddle, it may get “trapped” inside the paddle temporarily, and behave somewhat
erratically when released. However, this glitch is fixed when the paddle and ball is reset, either through losing a life or
by cheat key.

Extra credit:
* Added game sounds and music (background music for home menu, hitting sounds for bricks, Game Win and Game Lose sounds)
* Added ability to aim with the mouse before launching the ball (the X velocity of the ball depends on where the player clicks to 
launch the ball), to give the player more freedom to develop strategies
* Ball bounces off bricks differently depending on what part of the brick the ball hits
* Text files that are used to generate brick configurations were made in such a way so that the designer can
decide where to place power-ups, instead of random generation. This makes designing levels more interseting.
* Made the game harder as levels progress, with more bricks closer to the bottom of the screen and more complex geometries
* Additional power ups than what was required
* Additional cheat codes than what was required

### Notes/Assumptions

* Assumed that "three different kinds of bricks" can mean that there are bricks that are destroyed after
one hit (regular), destroyed after multiple hits, and drop power ups. I have implemented these three.

* Assumed that three different paddle abilities can include that the ball bounces differently depending on which third
of the paddle it lands on, reset to original location via cheat code, change size by cheat code (super big), or change size
by power up (slightly bigger). I have implemented these features.

* Text file input format is as follows:
    * Every number must be separated by a space, and in one single line.
    * The first digit of the number (1-3) represents the health of that brick
    * The second digit (1-4) represents the power up.
        * 1: Strength - Ball size increases
        * 2: Time - Ball slows down
        * 3: Length - Paddle length increases
        * 4: Health - Add a life
    * The game automatically reads the file and lays the bricks. There are six bricks per row and this
    process is automatic.

### Impressions
I thought that this project was quite difficult, given that I had zero experience in JavaFX before this project.
That meant that it took me quite a long time to get up to speed regarding how to use JavaFX and how it works, before 
starting to design my game and to guage what is possible. In addition, because of this, I was unsure of some aspects of my 
game plan, becuase I did not know what was possible/what could be easy or hard to implement. As a suggestion for future improvement,
perhaps assigning JavaFX tutorials before assigning the project plan assignment would be beneficial for students new to 
JavaFX, and not give them a disadvantage as a result. Thank you for grading my project!
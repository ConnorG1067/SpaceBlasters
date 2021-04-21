package com.mycompany.SpaceBlasters;

//IMPORTED MODUELS
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.apache.commons.lang3.ArrayUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**Space Blasters
 * 2021-04-19
 * Created by: Connor Gomes
 * ICS4U
 * URL To User Guide: https://docs.google.com/document/d/1RmOjR_zSOS7YLQ-J6ifmUTX9KkuLlcVln0J-UbsEqCA/view
 */

//Extending JPanel and implementing ActionListener
public class Game extends JPanel implements ActionListener{
    
    //---IMAGES---//
    Image player = new ImageIcon("ship.png").getImage();
    Image enemy = new ImageIcon("enemy.png").getImage();
    Image bg = new ImageIcon("SpaceBackground.jpg").getImage();
    
    //---TIMER---//
    Timer timer;
    
    //---ENEMY INTS---//
    int enemyRandomX = 0;
    int enemyVelY = 1;
    int enemyY = 100;
    int deleteEnemyXIndex;
    int enemyXLocation[] = new int [5];
    
    //---COLLISION DETECTION---//
    int add[] = {0, 1, 2, 3, 4, 5, 6, 7, 8 ,9 ,10 ,11 ,12 ,13 ,14 ,15 ,16, 17, 18, 19, 20, 21 ,22 ,23 ,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
    int adjustableX;
    int originalX;
    Boolean collision = false;    
    
    //---PLAYER INTS---//
    int playerX = 475, playerY = 900;
    
    //---BULLET INTS---//
    int bulletX;
    int bulletY = playerY-15;
    int reloadY = playerY-15;
    int bulletMove = 1;
    int bulletReload;

    //Bullet velocity
    int bulletYVel = 20;

    //---TEXT INTS---//
    int lives = 5;
    int kills = 0;
    int waveText = 1;
    
    //---GUI TEXT---//
    JLabel titleText = new JLabel("Space Blasters");
    Font fontTitle = new Font("Verdana", Font.BOLD, 32);
    
    //---GENERAL VARIABLES---//
    int wave = 1;
    int enemiesLeft = 5;
    
   

    //Enumeration to determine game state
    public static enum STATE{
        //Three different states which will be used in different classes
        MENU,
        GAME,
        GAMEOVERMENU
    };
    
    //Setting the STATE enumeration to STATE.MENU
    public static STATE State = STATE.MENU;
    
    // --- CLASSES --- //
    //Menu Class
    private Menu menu = new Menu(); 
    //Game over class
    private GameoverScreen gameoverScreen = new GameoverScreen();
    
    
    //Creating method play which plays the music for the program
    void play(String Path) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        //Creating a local file called file in this method play
        File file = new File(Path);
        //Creating an audioInputStream, and getting the audio from the file
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        //Creating the clip of the file
        Clip clip = AudioSystem.getClip();
        //Opening the clip
        clip.open(audioStream);
        
        //Changing the volume of the audio
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        //Reduce volume by 15 decibels.
        gainControl.setValue(-15.0f); 
        //Starting the clip (audio)
        clip.start();
        //Looping the clip which is about 15 minutes for constant music
        clip.loop(Clip.LOOP_CONTINUOUSLY);          
    }
    
    
    Game(){
        //Setting the size of the window to 1000x1000 pixels
        this.setPreferredSize(new Dimension(1000,1000));
        //Creating a timer which will run every 10 milliseconds, and will repeat this (actionPerformed method)
        timer = new Timer(10, this);
        //Adding a mouseListener which calls the MenuMouseInput Class
        this.addMouseListener(new MenuMouseInput());       
        //Starting my timer
        timer.start();
        
        
        
        //Creating a for loop to create 5 enemies on the screen
        for(int i = 0; i<5; i++){
            //Setting enemyRandomX to a random value from 0 - 975 which represents the pixels on the x axis
            enemyRandomX = (int) (Math.random()*975);
            //setting the enemyXLocation array to add an enemyRandomX value for a random x coordinate
            enemyXLocation[i] = enemyRandomX;
                
        }  
        
        //Try
        try {
            //Call the play method created previously
            play("NoCopyRightMusic.wav");
        //Catch
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
    
    //linearSearch method which takes an array and a number
    void linearSearch(int arr[], int num){
        //Run a for loop while i is < the arrays length
        for(int i = 0; i<arr.length; i++){
            //If the number is found within the array jump into if
            if(arr[i] == num){
                //Set deleteEnemyXIndex to the index the number was found in
                deleteEnemyXIndex = i;
            }
        }
    }

    //Creating a paint method to paint the actual graphics to the screen
    @Override
    public void paint(Graphics g){
        //Set the brush to black
        g.setColor(Color.BLACK);
        //Fill a rectangle to cover the entire screen black
        g.fillRect(0, 0, 1000, 1000);
        
        //If the state of the enumeration is set to game run the game graphics
        if(State == STATE.GAME){
            //Casting g to Graphics2D
            Graphics2D g2D = (Graphics2D) g;
            
            //Drawing the background Image to the screen
            g2D.drawImage(bg, 0, 100 , null);


            // -----HUD----- //
            //Setting g2D to red
            g2D.setColor(Color.red);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Space Blasters", 375, 50);
            
            //Setting the font to a new font
            g.setFont(new Font("Arial", Font.PLAIN, 32)); 
            //Putting lives with the number of lives in the top right of the screen
            g2D.drawString("Lives: " + lives, 850, 30);
            //Putting kills with the number of kills in the top right of the screen
            g2D.drawString("Kills: " + kills, 850, 60);
            //Putting waves with the number of waves in the top left of the screen
            g2D.drawString("Waves: " + waveText, 50, 30);
            //Putting highest kills with the XML value of the highest kills the user has
            g2D.drawString("Highest kills: " + menu.killsXML, 50, 60);

            // -----DRAWING THE BULLET -----//
            //Setting the colour to green
            g2D.setColor(Color.green);
            //If the bullet has not been shot meaning bullet relaod is less than or equal to 0 jump into if
            if(bulletReload >= 0){
                //bulletX is always equal to the player+38 on the x (+38 to set bullet in the right spot visually)
                bulletX = playerX+38;  
                //Draw the bullet with the updating bulletX to follow the ship
                g2D.fillRect(bulletX, reloadY, 10,60);

            }
            
            
            // ----- DRAWING THE PLAYER -----//
            //Draw the player to the screen
            g2D.drawImage(player, playerX, playerY, null);

            // ----- COLLISION DETECTION/ENEMY DRAWINGS -----//
            //Creating a for loop to scan through the array
            for(int i = 0; i<enemyXLocation.length; i++){
                //If a collision boolean is equal to false keep drawing the enemies
                // ----- NO COLLISION -----//
                if(collision == false){
                    //Draw each enemy with there recorded x value to maintain a steady path to the bottom of the screen
                    g2D.drawImage(enemy, enemyXLocation[i], enemyY, null);
                //If the collision boolean is not false, meaning its true a collision has occured
                //// ----- COLLISIONN -----//
                }else{
                    //Perform a linearSearch to determine which index X location was shot
                    //Array: enemyXLocation     Number: OrginialX (This variable is specificed later in the code once a collision occurs)
                    linearSearch(enemyXLocation, originalX);
                    //Setting the array to the same array but with the removed index because it was shot 
                    enemyXLocation = ArrayUtils.remove(enemyXLocation, deleteEnemyXIndex);
                    
                    //If a collision occurs chose a random value from 0-9
                    int tenPercent = (int) (Math.random()*9);
                    //If the random value is equal to 0 (10% chance) jump into if
                    if(tenPercent == 0){
                        //Give the user an extra life
                        lives++;
                    }
                    //Collision equals false so this doesnt keep occuring
                    collision = false;
                }    
            }
            
            //If the enumeration is equal to menu call the render method in the Menu class
            }else if (State == STATE.MENU){
                //Calling the render method in menu with g (Graphics)
                menu.render(g);        
            //If the enumeration is equal to GAMEOVERMENU call the render method in the gameoverScreen class
            }else if (State == STATE.GAMEOVERMENU){
                //Calling the render method gameoverScreen with g (Graphics)
                gameoverScreen.render(g);
            }
    }
    

    //Using Timer to use action performed every 10 milliseconds
    @Override    
    public void actionPerformed(ActionEvent arg0) {
        //If the state of the enumeration is equal to Game jump into if
        if(State == STATE.GAME){            
            
            //----- ENEMY ANIMATION -----//
            //Set the enemyY value to enemyVelY. This means the enemy will move 1 pixel downwards every 10 milliseconds
            enemyY = enemyY + enemyVelY;


            
            //If bulletMove is greater than 1 meaning the user shot a bullet jump into if
            if(bulletMove > 1){
                //reloadY is subtracted by 20 pixels every 10 milliseconds, meaning it is moving up at a velocity of 20 pixels/per 10 milliseconds
                reloadY = reloadY - bulletYVel;
                //if reloadY is less than 100, meaning it is off the screen jump into if
                if(reloadY < 100){
                   //Set the reloadY to the playerY - 15 to put it on the player
                   reloadY = playerY-15;
                   //set bulletMove to 1 to beak out of the statement
                   bulletMove = 1;
                }
            }
             
            
            //If all enemies pass y = 875 jump into if
            if(enemyY > 875){
                //Player loses a life
                lives--;
                
                //----- SPAWNING ENEMIES X VALUES -----//
                //Making a for loop to run until the enemyXLocation.length is less than 5
                for(int i = 0; enemyXLocation.length<5; i++){
                    //Make new random X values and set them to enemyRandomX
                    enemyRandomX = (int) (Math.random()*975);
                    //Set the array to the array with the enemyRandomX until there are 5 elements in the array meaning there is 5 enemies
                    enemyXLocation = ArrayUtils.add(enemyXLocation, enemyRandomX);
                }
                
                //----- WAVE INCREMENTER -----//
                //If wave is equal to 10 jump into if
                if(wave == 10){
                    //Set the enemy velocity to the same velocity + 1, meaning every 10 waves increase the difficulty
                    enemyVelY = enemyVelY++;
                    //Set wave to 1 to prevent constant increase in difficulty
                    wave = 1;

                //Anything else meaning the wave is less than 10
                }else{
                    //Increment wave
                    wave++;
                    //Increment waveText for the HUD
                    waveText++;
                }

                //Set the enemyY back to the top of the screen to decent again
                enemyY = 100;
            }
            
            //If all enemies are shot
            if(enemyXLocation.length == 0){
                //----- SPAWNING ENEMIES X VALUES -----//                
                //Making a for loop to run until the enemiesLeft is less than 5
                for(int i = 0; i<enemiesLeft; i++){
                    //Make new random X values and set them to enemyRandomX
                    enemyRandomX = (int) (Math.random()*975);
                    //Set the array to the array list with the enemyRandomX until there are 5 elements in the array meaning there is 5 enemies
                    enemyXLocation = ArrayUtils.add(enemyXLocation, enemyRandomX);

                }
                //----- WAVE INCREMENTER -----//                
                //If wave is equal to 10 jump into if
                if(wave == 10){
                    //Set the enemy velocity to the same velocity + 1, meaning every 10 waves increase the difficulty
                    enemyVelY = enemyVelY + 1;
                    //Set wave to 1 to prevent constant increase in difficulty
                    wave = 1;

                //Anything else meaning the wave is less than 10
                }else{
                    //Increment wave
                    wave++;
                    //Increment waveText for the HUD
                    waveText++;
                }

                //Set the enemyY back to the top of the screen
                enemyY = 100;
            }

            // ----- CHECKING FOR COLLISIONS ----- //
            
            //Creating a for loop to run p while it is less than the array add.length
            //add[] is delcared before but here is the array
            // DECLARED PREVIOUSLY, For Reference: add[] = {0, 1, 2, 3, 4, 5, 6, 7, 8 ,9 ,10 ,11 ,12 ,13 ,14 ,15 ,16, 17, 18, 19, 20, 21 ,22 ,23 ,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
            for(int p = 0; p<add.length; p++){
                //Creating another for loop to determine enemyXLocation.length
                for(int z = 0; z<enemyXLocation.length; z++){
                    //AdjustableX represents the x coordinate plus add[p]
                    //The reasoning for adding the enemyXLocation[z] and add[p] is because the alien is visually 0-40 pixels long.
                    //If I did not add the two values even if the bullet touches the alien the probablility of a technical collision occuring at the exact pixel is unlikely.
                    adjustableX = enemyXLocation[z] + add[p];
                    //If the bulletX value is equal to adjustableX from 0-40 pixels and reloadY is less than or equal to enemyY meaning the reload is above the alien a collision has occured
                    if((bulletX == adjustableX) && (reloadY <= enemyY)) {
                        //OriginalX is equal to enemyXLocationZ without add[p] to determine where this value is in enemyXLocation array for deletion
                        originalX = enemyXLocation[z];
                        //reloadY is equal to the playerY - 15
                        reloadY = playerY-15;
                        //bulletMove is equal to 1 to prevent shooting but prepare for next shot
                        bulletMove = 1;
                        //kills incrments to show a collision has occured
                        kills++;
                        //collision = true to determine which one was shot in the paint method
                        collision = true;
                    }

                }

            }

            //Repaint the method every 10 milliseconds
            repaint();
            
            // --- USER DIES --- //
            
            //If lives == 0 meaning the user has died jump into if
            if(lives == 0){
                
                // --- READ/WRITE FOR KILLS --- //
                //If kills is greater than the killsXML value which is read from an XML file jump into if
                if(kills>Integer.parseInt(menu.killsXML)){
                    //menu.killsXML is equal to the string value of kills meaning a new highest kills has occured
                    menu.killsXML = String.valueOf(kills);
                    //Try
                    try {
                        //Call the writeXML method in the class menu to rewite the new XML
                        menu.writeXML();
                    //Catching configuration Exception
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    //Catching TranformerException
                    } catch (TransformerException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // --- READ/WRITE FOR WAVES --- //
                //If the waveText is greater than the wavesXML value jump into if
                if(waveText>Integer.parseInt(menu.wavesXML)){
                    //wavesXML is now equal to waveText string value
                    menu.wavesXML = String.valueOf(waveText);
                    //Try
                    try {
                        //Write to the XML file with the new variable
                        menu.writeXML();
                    //Catch configurationException
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    //Catch transformerException
                    } catch (TransformerException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //Setting the killsPostGame variable to kills
                gameoverScreen.killsPostGame = kills;
                //Setting the wavesPostGame to waveText
                gameoverScreen.wavesPostGame = waveText;
                //Setting the state of the enumeration to GAMEOVERMENU
                State = STATE.GAMEOVERMENU;
                
                //Setting all the modified variables to their defualt value
                enemyVelY = 1;
                lives = 5;
                waveText = 1;
                kills = 0;

            }
        }
    }
}

package com.mycompany.SpaceBlasters;

//IMPORTED MODUELS

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**Space Blasters
 * 2021-04-19
 * Created by: Connor Gomes
 * ICS4U
 * URL To User Guide: https://docs.google.com/document/d/1RmOjR_zSOS7YLQ-J6ifmUTX9KkuLlcVln0J-UbsEqCA/view
 */

//Creating a class GameFrame which is the frame of the entire game which extends JFrame and implements KeyListener
public class GameFrame extends JFrame implements KeyListener{
    //Calling the game class and setting it to game
    Game game = new Game();
    
    
    GameFrame(){
        // ----- SETTING UP THE JFRAME ----- // 
        //Adding the game
        this.add(game);
        //Setting the title
        this.setTitle("Space Blasters");
        //Setting the DefaultCloseOperation to exit on close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Adding keyListener to the JFrame
        this.addKeyListener(this);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

              
    }
    
    
    //Listening for keysTyped
    @Override
    public void keyTyped(KeyEvent e) {
        //If the user clicks a jump into if
        if(e.getKeyChar() == 'a'){
            //Set the playerX position to 15 pixels left
            game.playerX = game.playerX - 15;
            //If the playerX is less than 0 (left of the screen) jump into if
            if(game.playerX < 0){
               //Set the playerX to the right side of the screen
               game.playerX = 1000; 
            }
        //If the user clicks d jump into else if
        }else if(e.getKeyChar() == 'd'){
            //Set the playerX position to 15 pixels right
            game.playerX = game.playerX + 15;
            //If the playerX is greater than 1000 meaning off the right side of the screen jump into if
            if(game.playerX > 1000){
               //Set the playerX to -30 or the left side of the screen
               game.playerX = -30; 
            }
        //if the user clicks space jump into else if
        }else if (e.getKeyChar() == ' '){
            //Set the bullet move to increment which allows it to move up
            game.bulletMove++;

            
        }
    }
    
    
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    
    //Key released method
    @Override
    public void keyReleased(KeyEvent e) {
        //If the user releases the spacebar
        if(e.getKeyChar() == ' '){
            //Set bulletReload to increment which draws the bullet on the ship
            game.bulletReload++;
            
            
        }
    }
    
    
    
}

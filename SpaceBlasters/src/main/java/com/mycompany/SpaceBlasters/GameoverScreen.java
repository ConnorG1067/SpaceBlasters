package com.mycompany.SpaceBlasters;

//IMPORTS
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**Space Blasters
 * 2021-04-19
 * Created by: Connor Gomes
 * ICS4U
 * URL To User Guide: https://docs.google.com/document/d/1RmOjR_zSOS7YLQ-J6ifmUTX9KkuLlcVln0J-UbsEqCA/view
 */

public class GameoverScreen {
    
    //Calling the menu class and setting it to variable menu
    Menu menu = new Menu();
    
    //Creating two variables for kills and waves post game
    int killsPostGame;
    int wavesPostGame;
    
    //FONTS 
    Font fnt0 = new Font("arial", Font.BOLD, 50);
    Font fnt1 = new Font("arial", Font.BOLD, 30);
    Font fnt2 = new Font("arial", Font.BOLD, 18);
    
    
    //Render method that uses graphics G
    public void render(Graphics g){
        
        //Read the XML document using the method readXML in the menu class
        menu.readXML();

        //Draw the background of the image from the menu class
        g.drawImage(menu.backgroundMenu, 0, 0, null);
        
        //Casting Graphics g to Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        //Setting the thickness to 3
        g2d.setStroke(new java.awt.BasicStroke(3));
        //Setting the colour to white
        g2d.setColor(Color.WHITE);
        
        //Draw the three rectangles play, help and quit
        g2d.draw(menu.playButton);
        g2d.draw(menu.helpButton);
        g2d.draw(menu.quitButton);
        
        
        //Set the font to fnt0
        g.setFont(fnt0);
        //Set the colour to red
        g.setColor(Color.RED);
        //Draw the string game over for the title
        g.drawString("GAME OVER!", 375, 100);
        
        //Set the font to fnt2
        g.setFont(fnt2);
        //Draw the play again text
        g.drawString("Play Again", menu.playButton.x+4, menu.playButton.y+35);
        //Set the font to fnt1
        g.setFont(fnt1);
        //Draw the help text
        g.drawString("Help", menu.helpButton.x+19, menu.helpButton.y+35);
        //Draw the quit text
        g.drawString("Quit", menu.quitButton.x+19, menu.quitButton.y+35);
        
        //Set the colour to white
        g.setColor(Color.WHITE);
        //Draw a rectangle 400x200 at points 325,450
        g.drawRect(325, 450, 400, 200);
        //Set the colour to black
        g.setColor(Color.BLACK);
        //Draw the XML highscores of the user
        g.drawString("High Score" , 450, 500);
        g.drawString("Kills: " + menu.killsXML, 385, 550);
        g.drawString("Waves: " + menu.wavesXML, 565, 550);
        
        //Set the colour to white
        g.setColor(Color.WHITE);
        //Draw a new rect exactly the same but 700 on the y axis
        g.drawRect(325, 700, 400, 200);
        
        //Set the colour to black
        g.setColor(Color.BLACK);
        //Draw the post game stats
        g.drawString("Post Game Score" , 425, 750);
        g.drawString("Kills: " + killsPostGame, 385, 800);
        g.drawString("Waves: " + wavesPostGame, 565, 800);
    }
}

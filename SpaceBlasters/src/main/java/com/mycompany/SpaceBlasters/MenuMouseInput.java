package com.mycompany.SpaceBlasters;


//IMPORTS

import com.mycompany.SpaceBlasters.Game.STATE;
import static com.mycompany.SpaceBlasters.Game.State;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**Space Blasters
 * 2021-04-19
 * Created by: Connor Gomes
 * ICS4U
 * URL To User Guide: https://docs.google.com/document/d/1RmOjR_zSOS7YLQ-J6ifmUTX9KkuLlcVln0J-UbsEqCA/view
 */

//Implementing mouseListener for when the user clicks buttons
public class MenuMouseInput implements MouseListener {

    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    
    @Override
    //When the user presses the a mouse button
    public void mousePressed(MouseEvent e) {
        //If the state of the game is either MENU or GAMEOVERMENU allow for mouse input for buttons rendered by either class
        if(State == STATE.MENU || State == STATE.GAMEOVERMENU){
            //Getting the X and Y value of where the mouse was clicked
            int mouseX = e.getX();
            int mouseY = e.getY();

            //Play Button
            //If the click is greater than or equal to 475 and less than or equal to 575 jump into if
            if(mouseX >= 475 && mouseX <= 575){
                //If the y click is greater than or equal to 150 and less than or equal to 100 jump into if
                if(mouseY >= 150 && mouseY <= 200){
                    //Set the state to Game meaning the user would like to play the game
                    Game.State = Game.STATE.GAME;

                }
            }

            //Help Button
            //If the click is greater than or equal to 475 and less than or equal to 575 jump into if
            if(mouseX >= 475 && mouseX <= 575){
                Desktop d = Desktop.getDesktop();
                //If the y click is greater than or equal to 250 and less than or equal to 300 jump into if
                if(mouseY >= 250 && mouseY <= 300){
                    //Creating a string array for the user options
                    String[] option = new String[] {"Continue", "User Guide"};

                    //Open a prompt to help the user understand the game
                    int helpPrompt = JOptionPane.showOptionDialog(null, "Welcome to Space Blasters!\nUse A and D to move and Space bar to shoot enemies advancing upon your base.\nEvery enemy killed provides a 10 percent chance for a bonus life.\nSurvive as many waves as you can!\n\nFor more information please refer to the user guide.", "Help", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);

                    //If the user selects User Guide option jump into if
                    if(helpPrompt == 1){
                        //Try
                        try {
                            //Open the user guide document
                            d.browse(new URI("https://docs.google.com/document/d/1RmOjR_zSOS7YLQ-J6ifmUTX9KkuLlcVln0J-UbsEqCA/edit?usp=sharing"));
                        //Catch URISyntax
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(MenuMouseInput.class.getName()).log(Level.SEVERE, null, ex);
                        //Catch IOException
                        } catch (IOException ex) {
                            Logger.getLogger(MenuMouseInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }


            //Quit Button
            //If the click is greater than or equal to 475 and less than or equal to 575 jump into if
            if(mouseX >= 475 && mouseX <= 575){
                //If the y click is greater than or equal to 350 and less than or equal to 400 jump into if
                if(mouseY >= 350 && mouseY <= 400){
                    //Exit the application
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}

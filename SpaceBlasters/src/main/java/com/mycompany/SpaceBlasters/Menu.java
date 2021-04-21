package com.mycompany.SpaceBlasters;

//IMPORTS
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**Space Blasters
 * 2021-04-19
 * Created by: Connor Gomes
 * ICS4U
 * URL To User Guide: https://docs.google.com/document/d/1RmOjR_zSOS7YLQ-J6ifmUTX9KkuLlcVln0J-UbsEqCA/view
 */

public class Menu {
    
    // --- VARIABLES --- //
    //Background image
    Image backgroundMenu = new ImageIcon("menuBG.png").getImage();
    //XML File
    File personalBestFile = new File("personalBest.xml");
    
    //Rectangles for the buttons
    public Rectangle playButton = new Rectangle(475, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(475, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(475, 350, 100, 50);
    
    //XML variables which are modified in the Game.java
    String killsXML = "0";
    String wavesXML = "0";
    
    //FONTS
    Font fnt0 = new Font("arial", Font.BOLD, 50);
    Font fnt1 = new Font("arial", Font.BOLD, 30);

    //Render method which calls Graphics 
    public void render(Graphics g){
        
        //Call the readXML method which reads an XML file
        readXML();
        
        //Draw the background the the backgroundMenu Image
        g.drawImage(backgroundMenu, 0, 0, null);
        
        //Casting Graphics g to Graphics2D
        Graphics2D g2d = (Graphics2D) g;
        //Setting the colour to white
        g2d.setColor(Color.WHITE);
        //Setting the thickness to 3
        g2d.setStroke(new java.awt.BasicStroke(3));
        
        //Draw the three rectangles play, help and quit
        g2d.draw(playButton);
        g2d.draw(helpButton);
        g2d.draw(quitButton);
        
        //Set the font to fnt0
        g.setFont(fnt0);
        //Set the colour to red
        g.setColor(Color.RED);
        //Draw Space Blasters to the screen
        g.drawString("Space Blasters", 350, 70);
        
        //Set the font to fnt1
        g.setFont(fnt1);
        //Draw the text to the cooresponding rectangles
        //Draw Play text
        g.drawString("Play", playButton.x+19, playButton.y+35);
        //Draw help text
        g.drawString("Help", helpButton.x+19, helpButton.y+35);
        //Draw quit text
        g.drawString("Quit", quitButton.x+19, quitButton.y+35);
        
        //Set the colour to white
        g.setColor(Color.WHITE);
        //Draw a rectangle 400x200 at points 325,450
        g.drawRect(325, 450, 400, 200);
        //Set the colour to black
        g.setColor(Color.BLACK);
        //Draw the XML highscores of the user
        g.drawString("High Score" , 450, 500);
        g.drawString("Kills: " + killsXML, 385, 550);
        g.drawString("Waves: " + wavesXML, 565, 550);
        
        
    }
    
    void writeXML() throws ParserConfigurationException, TransformerException {
        //Creating a document variable doc
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        //enables information to read from XML file 
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        //Creating the root element and naming it stats
        Element rootElement = doc.createElement("Stats");
        //Creating an element kills and setting the text to Kills
        Element killsXMLSub = doc.createElement("Kills");
        //Creating an element waves and setting the text to waves
        Element wavesXMLSub = doc.createElement("Waves");

        //Writing the killsXML to the XML file
        killsXMLSub.appendChild(doc.createTextNode(killsXML));
        //Writing the wavesXML to the XML file
        wavesXMLSub.appendChild(doc.createTextNode(wavesXML));

        //Appending rootElement to doc
        doc.appendChild(rootElement);
        //Appending killsXML to rootElement (stats)
        rootElement.appendChild(killsXMLSub);
        //Appending wavesXML to rootElement (stats)
        rootElement.appendChild(wavesXMLSub);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        //makes sure the XML file is formatted nicely with indentation
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //makes sure the XML file is formatted nicely with indent space of 4 
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        //creates the file called output
        StreamResult result = new StreamResult(personalBestFile);
        //Writing to the file
        transformer.transform(source, result);     
    }
    
    void readXML(){
        try {
            //Prepare the document builder and set it to dbf
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //d = dbf.newDocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Creating a new document and setting it to the new file personalBest.xml
            Document doc = db.parse(new File("personalBest.xml"));   //load xml file 
            doc.getDocumentElement().normalize();
            NodeList stats = doc.getElementsByTagName("Stats");   

            Node killsNode = stats.item(0);
            Element statsElement = (Element) killsNode;
                  
            //Reading the kills element text
            NodeList killsElementList = statsElement.getElementsByTagName("Kills");
            Element killsElement = (Element) killsElementList.item(0);
            NodeList killsList = killsElement.getChildNodes();
            killsXML = ((Node) killsList.item(0)).getNodeValue().trim();

            //Reading the waves element text
            NodeList wavesElementList = statsElement.getElementsByTagName("Waves");
            Element wavesElement = (Element) wavesElementList.item(0);
            NodeList wavesList = wavesElement.getChildNodes();
            wavesXML = ((Node) wavesList.item(0)).getNodeValue().trim();


            
        }catch(Exception e){}
    }
}

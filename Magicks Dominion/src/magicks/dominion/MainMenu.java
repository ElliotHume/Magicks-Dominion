/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.awt.Font;
import static magicks.dominion.Game.backgroundMusic;
import static magicks.dominion.Game.push;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.io.*;
import java.net.*;

/**
 *
 * @author elliot
 */
public class MainMenu extends BasicGameState {
   /** The ID given to this state */
   public int ID = 0;
   /** The font to write the message with */
   public TrueTypeFont font;
   public TrueTypeFont titleFont;
   /** The game holding this state */
   private StateBasedGame game;
   
   public MainMenu(int id) {
       this.ID = id;
   }
   
   
   /**
    * @see org.newdawn.slick.state.BasicGameState#getID()
    */
   public int getID() {
      return ID;
   }

   public static Image background;
   public static Image playButton;
   public static Image hostButton;
   public static Image joinButton;
   public Rectangle playButtonRect;
   public Rectangle hostButtonRect;
   public Rectangle joinButtonRect;
   public int mouseX;
   public int mouseY;
   public boolean playClicked;
   public boolean ipMode;
   public boolean joinClicked;
   public boolean hostClicked;
   public static String playMode;
   public static String IP = "";
   public static ServerSocket serverSocket;
   public static Socket clientSocket;
    
   @Override
   public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        Font awtFont = new Font("Times New Roman", Font.BOLD, 30);
        font = new TrueTypeFont(awtFont, false);
        Font awtFont2 = new Font("Times New Roman", Font.BOLD, 60);
        titleFont  = new TrueTypeFont(awtFont2, false);
        background = new Image("images/environment/backgroundtester.png");
        playButton = new Image("images/environment/play_button.png");
        playButtonRect = new Rectangle(539,417,320,60);
        hostButton = new Image("images/environment/host_button.png");
        hostButtonRect = new Rectangle(1078,417,320,60);
        joinButton = new Image("images/environment/join_button.png");
        joinButtonRect = new Rectangle(719,417,320,60);
        backgroundMusic = new Music("images/environment/backgroundMusic.wav");
        //backgroundMusic.loop(1, (float) 0.01);
   }

   @Override
   public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString("Magicks Dominion", 719+102, 313-23);
        g.setColor(Color.white);
        g.drawString("Magicks Dominion", 719+100, 313-25);
        if (!playClicked){
            playButton.draw(539, 417);
        } else {
            joinButton.draw(719, 417);
            hostButton.draw(1078, 417);
            if (ipMode){
                g.setColor(Color.black);
                g.fillRect(719,625,681,60);
                g.setColor(Color.white);
                g.draw(new Rectangle(719-1,625-1,682,61));
                if (IP.length() > 0){
                    g.setFont(font); 
                    g.drawString(IP, 719+6, 625+13);
                }
                if (joinClicked){
                    g.setColor(Color.white);
                    g.draw(joinButtonRect);
                } else if (hostClicked) {
                    g.setColor(Color.white);
                    g.draw(hostButtonRect);
                }
            }
        }
   }

   @Override
    public void keyPressed(int key, char c){
        if (key != 14 && (Character.isAlphabetic(c) || Character.isDigit(c) || key == 39 || key == 52)){
            IP = IP+c;
        }
    }
    
   @Override
   public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        Input input = gc.getInput();
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        if (input.isKeyPressed(Input.KEY_ESCAPE)){
            if (playClicked){
                playClicked = false;
                ipMode = false;
            } else {
                gc.exit();
            }
        } else if(input.isKeyPressed(Input.KEY_BACK) && ipMode){
            if (IP.length() > 0) {
                IP = IP.substring(0, IP.length()-1);
            }
        } else if(input.isKeyPressed(Input.KEY_ENTER) && ipMode){
            if (playMode == "host"){
                int hostPort = Integer.parseInt(IP);
                try {
                    serverSocket =new ServerSocket(hostPort);
                    clientSocket = serverSocket.accept(); 
                } catch (Exception e) {
                    System.err.println("Server Error: " + e.getMessage());
                    System.err.println("Localized: " + e.getLocalizedMessage());
                    System.err.println("Stack Trace: " + e.getStackTrace());
                    System.out.println("To String: " + e.toString());
                }
            } else if (playMode == "join"){
                String[] splitIP = IP.split(":");
                int joinPort = Integer.parseInt(splitIP[1]);
                try {
                    clientSocket = new Socket(splitIP[0], joinPort);
                } catch (UnknownHostException e) {
                    System.err.println("Don't know about host " + splitIP[0]);
                    System.exit(1);
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection to " +
                        e.toString());
                    System.exit(1);
                } 
            }
            push = true;
            game.enterState(1);
        }
        if (input.isMousePressed(0)){
            if (playButtonRect.contains(mouseX, mouseY) && !playClicked){
                playClicked = true;
            }
            if (joinButtonRect.contains(mouseX, mouseY) && playClicked){
                playMode = "join";
                IP = "localhost:25565";
                joinClicked = true;
                hostClicked = false;
                ipMode = true;
            } else if (hostButtonRect.contains(mouseX, mouseY) && playClicked){
                playMode = "host";
                IP = "25565";
                joinClicked = false;
                hostClicked = true;
                ipMode = true;
                
            }
        }
    }
}
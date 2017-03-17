/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.awt.Font;
import java.util.ArrayList;
import static magicks.dominion.Game.player;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author elliot
 */
public class ClassSelection extends BasicGameState {
   /** The ID given to this state */
   public int ID = 1;
   /** The font to write the message with */
   public TrueTypeFont textFont;
   public TrueTypeFont titleFont;
   /** The game holding this state */
   private StateBasedGame game;
   public static int mouseX;
   public static int mouseY;
   public static Image background;
   public static Image classBackground;
   public static Image chooseButton;
   public Boolean showChooseButton = false;
   public Rectangle chooseRect;
   public Class selectedClass;
   public static int classesNumber = 9; 
   public ArrayList<Class> classList = new ArrayList<Class>();
   public int scrollX = 0;
   public int scrollY = 0;
   
   public ClassSelection(int id) {
       this.ID = id;
   }
   
   
   /**
    * @see org.newdawn.slick.state.BasicGameState#getID()
    */
   public int getID() {
      return ID;
   }
   
   @Override
   public void init(GameContainer gc, StateBasedGame game) throws SlickException {
       Font awtFont2 = new Font("Times New Roman", Font.BOLD, 50);
       titleFont  = new TrueTypeFont(awtFont2, false);
       Font awtFont1 = new Font("Times New Roman", Font.PLAIN, 20);
       textFont  = new TrueTypeFont(awtFont1, false);
       background = new Image("images/environment/class_selection_background.png");
       classBackground = new Image("images/environment/neutralClass_bkg.png");
       chooseButton = new Image("images/environment/aiamancyClass_bkg.png");
       chooseRect = new Rectangle(1650, 700, 200, 40);
       
       for (int i=1 ; i <= classesNumber ; i++){
            String str = "class"+String.valueOf(i);
            classList.add(new Class(str));
        }
       
   }

   @Override
   public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
       background.draw(0, 0);
       g.setFont(titleFont);
       g.setColor(Color.black);
       g.drawString("Class tile selection", 30, 3);
       g.setColor(Color.white);
       g.drawString("Class tile selection", 31, 4);
       for (int i=0 ; i<(classesNumber/3) ; i++){
           for (int j=0 ; j < 3 ; j++){
               classList.get(j+(i*3)).draw(g, 85+scrollX+(j*300), 120+scrollY+(i*125),textFont, titleFont);
           }
       }
       if (showChooseButton){
           chooseButton.draw(1650, 700);
           g.setColor(Color.black);
           g.setFont(textFont);
           g.drawString("Choose class", 1655, 705);
       }
       if (player.classTiles.size() > 0){
           for (Integer i=0 ; i < player.classTiles.size(); i++){
                classBackground.draw(1650, 900+(i*75));
                g.drawString(player.classTiles.get(i).displayName, 1653, 903+(i*75));
           }
       }
   }
   
   @Override
   public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
       for (int k=0 ; k<(3) ; k++){
           for (int j=0 ; j < 3 ; j++){
               classList.get(j+(k*3)).define_hitbox(85+scrollX+(j*300), 120+scrollY+(k*125));
           }
        }
       
       Input input = gc.getInput();
       mouseX = input.getMouseX();
       mouseY = input.getMouseY();
       if (input.isKeyPressed(1)){
           game.enterState(0);
       } else if (input.isKeyPressed(Input.KEY_ENTER)){
            for (Class c : player.classTiles){
                c.action(player); 
            }
           game.enterState(2);
       }
       if (input.isMousePressed(0)){
           if (showChooseButton && selectedClass != null){
               if (chooseRect.contains(mouseX, mouseY)){
                   player.classTiles.add(selectedClass);
               }
           }
           Boolean undoSelected = true;
           for (Class c : classList){
               c.selected = false;
               if (c.check_hitbox(mouseX, mouseY)){
                   c.selected = true;
                   selectedClass = c;
                   showChooseButton = true;
                   undoSelected = false;
               }
           }
           if (undoSelected){
               selectedClass = null;
               showChooseButton = false;
           }
        }
   }
}
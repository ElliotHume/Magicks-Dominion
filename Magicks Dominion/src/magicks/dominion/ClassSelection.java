/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.awt.Font;
import static magicks.dominion.Game.playerWin;
import static magicks.dominion.MagicksDominion.SCREENHEIGHT;
import static magicks.dominion.MagicksDominion.SCREENWIDTH;
import static magicks.dominion.MainMenu.background;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
   public TrueTypeFont font;
   public TrueTypeFont titleFont;
   /** The game holding this state */
   private StateBasedGame game;
   public static int mouseX;
   public static int mouseY;
   
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
       Font awtFont2 = new Font("Times New Roman", Font.BOLD, 60);
       titleFont  = new TrueTypeFont(awtFont2, false);
   }

   @Override
   public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
       background.draw(0, 0);
       g.setFont(titleFont);
       g.setColor(Color.black);
       g.drawString("Class tile selection", SCREENWIDTH/3+102, SCREENHEIGHT/7-23);
       g.setColor(Color.white);
       g.drawString("Class tile selection", SCREENWIDTH/3+100, SCREENHEIGHT/7-25);
   }
   
   @Override
   public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
       Input input = gc.getInput();
       mouseX = input.getMouseX();
       mouseY = input.getMouseY();
       if (input.isKeyPressed(1)){
           game.enterState(0);
       } else if (input.isKeyPressed(Input.KEY_ENTER)){
           game.enterState(2);
       }
   }
}
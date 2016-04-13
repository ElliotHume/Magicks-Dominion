/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author elliot
 */
public class MainMenu extends BasicGameState {
   /** The ID given to this state */
   public int ID = 1;
   /** The font to write the message with */
   private Font font;
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

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
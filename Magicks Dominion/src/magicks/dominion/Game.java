/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author elliot
 */
public class Game extends BasicGameState {
   /** The ID given to this state */
   public int ID = 0;
   /** The font to write the message with */
   private Font font;
   /** The game holding this state */
   private StateBasedGame game;


   public int getID() {
      return ID;
   }
   
   public Game(int id) {
       this.ID = id;
   }
   
    public static Image background;
    public static Image hudBackground;
    public static Image flashingHud;
    public static Image blankTile;
    public static Image mountainTile;
    public static Image rockResource;
    public static Image oceanTile;
    public static Image waterResource;
    public static Image grassTile;
    public static Image earthResource;
    public static Image controlledTile;
    public static Image chosenTile;
    public static Image chosenTile2;
    public static Image chosenTile3;
    public static Image chosenTile4;
    public Map board;
    public Player player;
    public List<String> classTiles = new ArrayList<String>();
    public int mouseX = 0;
    public int mouseY = 0;
    public int hudx = 20;
    public int hudy = 30;
    public Tile selectedTile;
    public int startingEarth = 100;
    public int startingRock = 100;
    public int startingWater = 100;
    public boolean hudDrag = false;
    public boolean once = true;
    public boolean hudFlash = false;
    public boolean flashHud = false;
    public int flashCount = 0;
    public Timer resourceTimer = new Timer();
    public Timer flashTimer = new Timer();
    public Timer turnTimer = new Timer();
    
    
   @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        //GUI
            background = new Image("images/environment/resized_game_background.png");
            hudBackground = new Image("images/environment/hud_background.png");
            flashingHud = new Image("images/environment/hud_flash.png");
            
            //TILES & RESOURCES
            blankTile = new Image("images/environment/tile.png");
            mountainTile = new Image("images/environment/mountain_tile.png");
            rockResource = new Image("images/environment/rock_resource.png");
            oceanTile = new Image("images/environment/ocean_tile.png");
            waterResource = new Image("images/environment/water_resource.png");
            grassTile = new Image("images/environment/grass_tile.png");
            earthResource = new Image("images/environment/earth_resource.png");
            controlledTile = new Image("images/environment/controlled_tile.png");
            chosenTile = new Image("images/environment/chosen_tile.png");
            chosenTile2 = new Image("images/environment/chosen_tile2.png");
            chosenTile3 = new Image("images/environment/chosen_tile3.png");
            chosenTile4 = new Image("images/environment/chosen_tile4.png");
            
            //MAP
            board = new Map();
            board.biome_gen();
            
            //PLAYER
            classTiles.add("Default");
            player = new Player(startingEarth,startingRock,startingWater, 200, classTiles);
    }


    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        //TIMER CREATION
            if (once){
                once = false;
                resourceTimer.scheduleAtFixedRate( new TimerTask() {
                    @Override
                    public void run() {
                        ArrayList<Tile> clone = new ArrayList<Tile>(player.controlledTiles);
                        for (Tile tile : clone){
                            switch(tile.biome){
                                case "grass":
                                    player.E += 20;
                                    break;
                                case "mountain":
                                    player.R += 20;
                                    break;
                                case "ocean":
                                    player.W += 20;
                                    break;
                                default:
                                    break;
                            }
                        }
                        hudFlash = true;
                        flashCount = 0;
                    }
                }, 20*1000, 20*1000);
                
                flashTimer.scheduleAtFixedRate( new TimerTask() {
                        @Override
                        public void run() {
                            if (hudFlash && (flashCount < 8)){
                                flashHud = !flashHud;
                                flashCount += 1;
                            } else {
                                hudFlash = false;
                                flashCount = 0;
                            }
                        }
                    }, 0, 500);
                
                turnTimer.scheduleAtFixedRate( new TimerTask() {
                        @Override
                        public void run() {
                            ArrayList<Tile> toRemove = new ArrayList();
                            for (Tile tile : player.chosenTiles){
                                if (tile.playerControl <= 4){
                                    tile.playerControl += 1;
                                }
                                
                                if (tile.playerControl >= 4){
                                    toRemove.add(tile);
                                    player.add_tile(tile);
                                    tile.playerControl = 0;
                                }
                            }
                            
                            for (Tile tile : toRemove){
                                player.remove_path(board.board, tile);
                            }
                        }
                    }, 20/*5*/*1000, 20/*5*/*1000);
            }
            
            Input input = gc.getInput();
            if (input.isMouseButtonDown(0)){
                mouseX = input.getMouseX();
                mouseY = input.getMouseY();
                if (board.get_tile(mouseX, mouseY) != null){
                    selectedTile = board.get_tile(mouseX,mouseY);
                }
                if ((mouseX >= hudx) && (mouseX <= hudx+480) && (mouseY >= hudy) && (mouseY <= hudy+128)){
                    hudDrag = true;
                }
            } else {
                hudDrag = false;
            }
            
            if (input.isKeyPressed(Input.KEY_B)){
                board.biome_gen();
            } else if (input.isKeyPressed(Input.KEY_W)){
                board.wipe_biomes();
            } else if (input.isKeyPressed(Input.KEY_RETURN)){
                if (player.tileChoices > 0 && (!player.controlledTiles.contains(selectedTile))){
                    player.add_tile(selectedTile);
                }
            } else if (input.isKeyPressed(Input.KEY_BACK)){
                if ((player.controlledTiles.size() > 0) && (player.controlledTiles.contains(selectedTile))){
                    player.remove_tile(board.board, selectedTile);
                } else if ((player.chosenTiles.size() > 0) && (player.chosenTiles.contains(selectedTile))){
                    player.remove_path(board.board, selectedTile);
 
                }
                
            } else if (input.isKeyPressed(Input.KEY_C)){
                player.choose_tile(board.board, selectedTile);
            }
            
            if (hudDrag){
                mouseX = input.getMouseX();
                mouseY = input.getMouseY();
                hudx = mouseX;
                hudy = mouseY;
            }
    }
            
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //background.draw(0,0);
                board.display_tiles();
                if (selectedTile != null){
                    selectedTile.draw_circle(g);
                }
                player.display_controlled_tiles();
                player.display_chosen_tiles();
                
                // HUD
                if (!flashHud){
                    hudBackground.draw(hudx,hudy);
                } else {
                    flashingHud.draw(hudx,hudy);
                }
                earthResource.draw(hudx+10,hudy+10);
                String earthResources = ":"+String.valueOf(player.E);
                g.drawString(earthResources, hudx+45, hudy+18);
                waterResource.draw(hudx+105,hudy+10);
                String waterResources = ":"+String.valueOf(player.W);
                g.drawString(waterResources, hudx+140, hudy+18);
                rockResource.draw(hudx+200,hudy+10);
                String rockResources = ":"+String.valueOf(player.R);
                g.drawString(rockResources, hudx+235, hudy+18);
    }
}
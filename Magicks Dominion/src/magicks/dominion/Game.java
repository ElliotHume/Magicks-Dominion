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
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
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


   @Override
   public int getID() {
      return ID;
   }
   
   public Game(int id) {
       this.ID = id;
   }
   
    public static Image background;
    public static Image hudBackground;
    public static Image tileMenuBackground;
    public static Image ritualMenuBackground;
    public static Image flashingHud;
    public static Image blankTile;
    public static Image mountainTile;
    public static Image rockResource;
    public static Image oceanTile;
    public static Image waterResource;
    public static Image grassTile;
    public static Image earthResource;
    public static Image desertTile;
    public static Image arcaneResource;
    public static Image controlledTile;
    public static Image chosenTile;
    public static Image chosenTile2;
    public static Image chosenTile3;
    public static Image chosenTile4;
    public static Image conquerButton;
    public static Image millButton;
    public static Image pathButton;
    public static Image unpathButton;
    public static Image aiamancyButton;
    public static Image ephemancyButton;
    public static Image koromancyButton;
    public Rectangle tileHudRect;
    public Rectangle ritualHudRect;
    public Rectangle conquerButtonRect;
    public Rectangle pathButtonRect;
    public Rectangle aiamancyButtonRect;
    public Rectangle ephemancyButtonRect;
    public Rectangle koromancyButtonRect;
    public Map board;
    public Player player;
    public List<String> classTiles = new ArrayList<>();
    public int mouseX = 0;
    public int mouseY = 0;
    public int hudx = 20;
    public int hudy = 30;
    public int tHudx = 1511;
    public int tHudy = 5;
    public int xdiff;
    public int ydiff;
    public Tile selectedTile;
    public double startingEarth = 100;
    public double startingRock = 100;
    public double startingWater = 100;
    public double startingArcane = 0;
    public boolean hudDrag = false;
    public boolean tHudDrag = false;
    public boolean once = true;
    public boolean hudFlash = false;
    public boolean flashHud = false;
    public boolean gainSound = false;
    public boolean pathButtonEnabled = false;
    public boolean unpathButtonEnabled = false;
    public boolean conquerButtonEnabled = false;
    public boolean millButtonEnabled = false;
    public boolean aiamancyButtonOutline = false;
    public boolean ephemancyButtonOutline = false;
    public boolean koromancyButtonOutline = false;
    public boolean aiamancyButtonSelected = false;
    public boolean ephemancyButtonSelected  = false;
    public boolean koromancyButtonSelected  = false;
    public int flashCount = 0;
    public Timer resourceTimer = new Timer();
    public Timer flashTimer = new Timer();
    public Timer turnTimer = new Timer();
    public static Sound gatheringSound;
    public static Sound gainTileSound;
    public static Music backgroundMusic;
    
    
   @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        //GUI
            
            background = new Image("images/environment/resized_game_background.png");
            hudBackground = new Image("images/environment/hud_background.png");
            tileMenuBackground = new Image("images/environment/tile_menu_background.png");
            tileHudRect = new Rectangle(hudx, hudy, 640, 256);
            ritualMenuBackground = new Image("images/environment/ritual_menu_background.png");
            ritualHudRect = new Rectangle(tHudx, tHudy, 640, 1150);
            flashingHud = new Image("images/environment/hud_flash.png");
            gatheringSound = new Sound("images/environment/gathering.wav");
            gainTileSound = new Sound("images/environment/gainTile.wav");
            conquerButton = new Image("images/environment/conquer_button.png");
            millButton = new Image("images/environment/mill_button.png");
            conquerButtonRect = new Rectangle(tHudx+140,tHudy+216,120,30);
            pathButton = new Image("images/environment/path_button.png");
            unpathButton = new Image("images/environment/unpath_button.png");
            pathButtonRect = new Rectangle(tHudx+10,tHudy+216,120,30);
            aiamancyButton = new Image("images/environment/aiamancy_button.png");
            ephemancyButton = new Image("images/environment/ephemancy_button.png");
            koromancyButton = new Image("images/environment/koromancy_button.png");
            aiamancyButtonRect = new Rectangle(tHudx+15,tHudy+75,160,30);
            ephemancyButtonRect = new Rectangle(tHudx+235,tHudy+75,161,31);
            koromancyButtonRect = new Rectangle(tHudx+464,tHudy+75,161,31);
            backgroundMusic = new Music("images/environment/backgroundMusic.wav");
            //backgroundMusic.loop(1, (float) 0.01);
            
            //TILES & RESOURCES
            blankTile = new Image("images/environment/tile.png");
            mountainTile = new Image("images/environment/mountain_tile.png");
            rockResource = new Image("images/environment/rock_resource.png");
            oceanTile = new Image("images/environment/ocean_tile.png");
            waterResource = new Image("images/environment/water_resource.png");
            grassTile = new Image("images/environment/grass_tile.png");
            earthResource = new Image("images/environment/earth_resource.png");
            desertTile = new Image("images/environment/desert_tile.png");
            arcaneResource = new Image("images/environment/ephesos_resource.png");
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
            player = new Player(startingEarth,startingRock,startingWater,startingArcane, 2, classTiles);
            
            resourceTimer.scheduleAtFixedRate( new TimerTask() {
                @Override
                public void run() {
                    ArrayList<Tile> clone = new ArrayList<>(player.controlledTiles);
                    for (Tile tile : clone){
                        tile.decayCounter += 1;
                        switch(tile.biome){
                            case "Grass":
                                player.E += (20*tile.resourceReturn)*(player.resourceGain*player.earthResourceGain);
                                if (tile.resourceReturn > 0.25 && tile.decayCounter == 5){
                                    tile.decayCounter = 0;
                                    tile.resourceReturn -= player.decayRate;
                                }
                                break;
                            case "Mountain":
                                player.R += (20*tile.resourceReturn)*(player.resourceGain*player.rockResourceGain);
                                if (tile.resourceReturn > 0.25 && tile.decayCounter == 5){
                                    tile.decayCounter = 0;
                                    tile.resourceReturn -= player.decayRate;
                                }
                                break;
                            case "Ocean":
                                player.W += (20*tile.resourceReturn)*(player.resourceGain*player.waterResourceGain);
                                if (tile.resourceReturn > 0.25 && tile.decayCounter == 5){
                                    tile.decayCounter = 0;
                                    tile.resourceReturn -= player.decayRate;
                                }
                                break;
                            case "Desert":
                                double r = Math.random();
                                if (r < 0.2){
                                    player.E += (10)*(player.resourceGain*player.earthResourceGain);
                                } else if (r >= 0.2 && r < 0.4){
                                    player.R += (10)*(player.resourceGain*player.rockResourceGain);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    hudFlash = true;
                    flashCount = 0;
                    gatheringSound.play(1f,0.5f);
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
                                gainSound = true;
                                tile.playerControl = 0;
                            }
                        }
                        for (Tile tile : toRemove){
                            player.remove_path(board.board, tile);
                        }
                    }
                }, 20*player.conquerRate, 20*player.conquerRate);
    }


    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        if (gainSound){
            gainSound = false;
            gatheringSound.stop();
            gainTileSound.play(1f, 0.5f);
        }
        
        Input input = gc.getInput();
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        if (input.isMousePressed(0)){
            
            if (aiamancyButtonRect.contains(mouseX, mouseY)){
                if (!aiamancyButtonSelected){
                    aiamancyButtonSelected = true;
                    koromancyButtonSelected = false;
                    ephemancyButtonSelected = false;
                } else {
                    aiamancyButtonSelected = false;
                }
            } else if (ephemancyButtonRect.contains(mouseX, mouseY)){
                if (!ephemancyButtonSelected){
                    aiamancyButtonSelected = false;
                    koromancyButtonSelected = false;
                    ephemancyButtonSelected = true;
                } else {
                    ephemancyButtonSelected = false;
                }
            } else if (koromancyButtonRect.contains(mouseX, mouseY)){
                if (!koromancyButtonSelected){
                    aiamancyButtonSelected = false;
                    koromancyButtonSelected = true;
                    ephemancyButtonSelected = false;
                } else {
                    koromancyButtonSelected = false;
                }
            }
            
            if (selectedTile != null){
                if (conquerButtonRect.contains(mouseX, mouseY) && (conquerButtonEnabled || millButtonEnabled)){
                    if (player.tileChoices > 0 && !player.controlledTiles.contains(selectedTile) && !player.chosenTiles.contains(selectedTile)){
                        player.add_tile(selectedTile);
                    } else if (player.controlledTiles.contains(selectedTile)){
                        player.remove_tile(board.board, selectedTile);
                    }
                } else if (pathButtonRect.contains(mouseX, mouseY) && (pathButtonEnabled || unpathButtonEnabled)){
                    if (selectedTile.chosen){
                        player.remove_path(board.board, selectedTile);
                    } else {
                        player.choose_tile(board.board, selectedTile);
                    }
                }
            }
        }
        
        if (!aiamancyButtonSelected && !ephemancyButtonSelected && !koromancyButtonSelected){
            if (aiamancyButtonRect.contains(mouseX, mouseY)){
                aiamancyButtonOutline = true;
                koromancyButtonOutline = false;
                ephemancyButtonOutline = false;
            } else {
                aiamancyButtonOutline = false;
            }
            if (ephemancyButtonRect.contains(mouseX, mouseY)){
                ephemancyButtonOutline = true;
                koromancyButtonOutline = false;
                aiamancyButtonOutline = false;
            } else {
                ephemancyButtonOutline = false;
            }
            if (koromancyButtonRect.contains(mouseX, mouseY)){
                koromancyButtonOutline = true;
                ephemancyButtonOutline = false;
                aiamancyButtonOutline = false;
            } else {
                koromancyButtonOutline = false;
            }
        }
        
        if (input.isMouseButtonDown(0)){
            if (board.get_tile(mouseX, mouseY) != null){
                selectedTile = board.get_tile(mouseX,mouseY);
            }
            if ((mouseX >= hudx) && (mouseX <= hudx+480) && (mouseY >= hudy) && (mouseY <= hudy+128)){
                xdiff = mouseX - hudx;
                ydiff = mouseY - hudy;
                hudDrag = true;
                tHudDrag = false;
            }
            
            if (aiamancyButtonSelected || ephemancyButtonSelected || koromancyButtonSelected){
                if (ritualHudRect.contains(mouseX, mouseY)){
                    xdiff = mouseX - tHudx;
                    ydiff = mouseY - tHudy;
                    tHudDrag = true;
                    hudDrag = false;
                }
            } else {
                if (tileHudRect.contains(mouseX,mouseY)){
                    xdiff = mouseX - tHudx;
                    ydiff = mouseY - tHudy;
                    tHudDrag = true;
                    hudDrag = false;
                }
            }
        } else {
            hudDrag = false;
            tHudDrag = false;
        }

        if (input.isKeyPressed(Input.KEY_B)){
            board.biome_gen();
        } else if (input.isKeyPressed(Input.KEY_N)){
            board.wipe_biomes();
        } else if (input.isKeyPressed(Input.KEY_R)){
            if (player.tileChoices > 0 && !player.controlledTiles.contains(selectedTile) && !player.chosenTiles.contains(selectedTile)){
                player.add_tile(selectedTile);
            } else if ((player.controlledTiles.size() > 0) && (player.controlledTiles.contains(selectedTile))){
                player.remove_tile(board.board, selectedTile);
            }
        } else if (input.isKeyPressed(Input.KEY_BACK)){
            if ((player.controlledTiles.size() > 0) && (player.controlledTiles.contains(selectedTile))){
                player.remove_tile(board.board, selectedTile);
            } else if ((player.chosenTiles.size() > 0) && (player.chosenTiles.contains(selectedTile))){
                player.remove_path(board.board, selectedTile);

            }
        } else if (input.isKeyPressed(Input.KEY_E)){
            if (!player.chosenTiles.contains(selectedTile)){
                player.choose_tile(board.board, selectedTile);
            } else if ((player.chosenTiles.size() > 0) && (player.chosenTiles.contains(selectedTile))){
                player.remove_path(board.board, selectedTile);

            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)){
            gc.exit();
        } else if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)){
            if (selectedTile.y != 0 && selectedTile.x != 21){
                if(selectedTile.y == 1 && selectedTile.x < 19){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord-28);
                } else if ((selectedTile.y == 2 && selectedTile.x != 20)){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord-28);
                } else if (selectedTile.y == 20 && selectedTile.x != 20){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord-28);
                } else if (selectedTile.y == 21 && selectedTile.x != 19){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord-28);
                } else if (selectedTile.y > 2 && selectedTile.y < 20){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord-28);
                }
            }
        } else if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)){
            if (selectedTile.y != 21 && selectedTile.x != 0){
                if(selectedTile.x == 1 && selectedTile.y < 19){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord+84);
                } else if (selectedTile.x > 1){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord+84);
                }
            }
        } else if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_D)){
            if (selectedTile.x != 21){
                if (selectedTile.x == 19 && selectedTile.y != 0 && selectedTile.y != 21){
                    selectedTile = board.get_tile(selectedTile.xCoord+64, selectedTile.yCoord-14);
                } else if ( selectedTile.x == 20 && selectedTile.y != 20 && selectedTile.y != 1){
                    selectedTile = board.get_tile(selectedTile.xCoord+64, selectedTile.yCoord-14);
                } else if (selectedTile.x < 19){
                    selectedTile = board.get_tile(selectedTile.xCoord+64, selectedTile.yCoord-14);
                }
            } 
        } else if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_A)){
            if (selectedTile.x != 0){
                    selectedTile = board.get_tile(selectedTile.xCoord-32, selectedTile.yCoord+56);
            }
        } else if (input.isKeyPressed(Input.KEY_Z)){
            if (!aiamancyButtonSelected){
                aiamancyButtonSelected = true;
                koromancyButtonSelected = false;
                ephemancyButtonSelected = false;
            } else {
                aiamancyButtonSelected = false;
            }
        } else if (input.isKeyPressed(Input.KEY_X)){
            if (!ephemancyButtonSelected){
                aiamancyButtonSelected = false;
                koromancyButtonSelected = false;
                ephemancyButtonSelected = true;
            } else {
                ephemancyButtonSelected = false;
            }
        } else if (input.isKeyPressed(Input.KEY_C)){
            if (!koromancyButtonSelected){
                aiamancyButtonSelected = false;
                koromancyButtonSelected = true;
                ephemancyButtonSelected = false;
            } else {
                koromancyButtonSelected = false;
            }
        }
        

        if (selectedTile != null){
            if (player.tileChoices > 0 && !player.controlledTiles.contains(selectedTile) && !player.chosenTiles.contains(selectedTile)){
                conquerButtonEnabled = true;
                millButtonEnabled = false;
            } else if (player.controlledTiles.contains(selectedTile)){
                conquerButtonEnabled = false;
                millButtonEnabled = true;
            } else {
                conquerButtonEnabled = false;
                millButtonEnabled = false;
            }
            
            if (!player.controlledTiles.contains(selectedTile) && selectedTile.possible_path(board.board, player.controlledTiles) && !selectedTile.is_path(board.board, player.controlledTiles)){
                if (!selectedTile.chosen){
                    pathButtonEnabled = true;
                    unpathButtonEnabled = false;
                } else {
                    unpathButtonEnabled = true;
                    pathButtonEnabled = false;
                }
            } else {
                pathButtonEnabled = false;
                unpathButtonEnabled = false;
            }
        }
        
        if (hudDrag){
            hudx = mouseX-xdiff;
            hudy = mouseY-ydiff;
        }
        if (tHudDrag){
            tHudx = mouseX-xdiff;
            tHudy = mouseY-ydiff;
            tileHudRect = new Rectangle(hudx, hudy, 640, 256);
            ritualHudRect = new Rectangle(tHudx, tHudy, 640, 1150);
            conquerButtonRect = new Rectangle(tHudx+140,tHudy+216,120,30);
            pathButtonRect = new Rectangle(tHudx+10,tHudy+216,120,30);
            aiamancyButtonRect = new Rectangle(tHudx+15,tHudy+75,160,30);
            ephemancyButtonRect = new Rectangle(tHudx+235,tHudy+75,161,31);
            koromancyButtonRect = new Rectangle(tHudx+464,tHudy+75,161,31);
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
        String earthResources = ":"+String.valueOf((int)player.E);
        g.drawString(earthResources, hudx+45, hudy+18);
        waterResource.draw(hudx+105,hudy+10);
        String waterResources = ":"+String.valueOf((int)player.W);
        g.drawString(waterResources, hudx+140, hudy+18);
        rockResource.draw(hudx+200,hudy+10);
        String rockResources = ":"+String.valueOf((int)player.R);
        g.drawString(rockResources, hudx+235, hudy+18);
        arcaneResource.draw(hudx+295,hudy+10);
        String arcaneResources = ":"+String.valueOf((int)player.A);
        g.drawString(arcaneResources, hudx+330, hudy+18);
        
        // TILE CONTROL MENU
        if (aiamancyButtonSelected || ephemancyButtonSelected || koromancyButtonSelected){
            ritualMenuBackground.draw(tHudx, tHudy);
            conquerButtonEnabled = false;
            millButtonEnabled = false;
            pathButtonEnabled = false;
            unpathButtonEnabled = false;
        } else {
            tileMenuBackground.draw(tHudx, tHudy);
        }
        aiamancyButton.draw(tHudx+15, tHudy+75);
        if (aiamancyButtonSelected || ephemancyButtonSelected || koromancyButtonSelected){
            g.setColor(Color.red);
        }
        if (aiamancyButtonOutline || aiamancyButtonSelected){
            g.draw(aiamancyButtonRect);
        } else if (ephemancyButtonOutline || ephemancyButtonSelected){
            g.draw(ephemancyButtonRect);
        } else if (koromancyButtonOutline || koromancyButtonSelected){
            g.draw(koromancyButtonRect);
        }
        g.setColor(Color.white);
        
        ephemancyButton.draw(tHudx+235, tHudy+75);
        koromancyButton.draw(tHudx+465, tHudy+75);
        if (selectedTile != null){
            selectedTile.display_information(tHudx, tHudy, player, g);
            if (conquerButtonEnabled){
                conquerButton.draw(tHudx+140, tHudy+216);
            } else if (millButtonEnabled) {
                millButton.draw(tHudx+140, tHudy+216);
            }
            
            if (pathButtonEnabled){
                pathButton.draw(tHudx+10, tHudy+216);
            } else if (unpathButtonEnabled){
                unpathButton.draw(tHudx+10, tHudy+216);
            }
        }
    }
    
}
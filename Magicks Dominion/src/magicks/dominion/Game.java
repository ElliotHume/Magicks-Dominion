/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import static magicks.dominion.MainMenu.IP;
import static magicks.dominion.MainMenu.clientSocket;
import static magicks.dominion.MainMenu.playMode;
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
 * @author elliot
 */

public class Game extends BasicGameState {
   /** The ID given to this state */
   public int ID = 2;
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
    public static Image battleTile;
    public static Image blankTile;
    public static Image mountainTile;
    public static Image rockResource;
    public static Image forestTile;
    public static Image woodResource;
    public static Image fieldsTile;
    public static Image cropResource;
    public static Image oceanTile;
    public static Image waterResource;
    public static Image grassTile;
    public static Image earthResource;
    public static Image desertTile;
    public static Image arcaneResource;
    public static Image bloodResource;
    public static Image controlledTile;
    public static Image enemyControlledTile;
    public static Image chosenTile;
    public static Image chosenTile2;
    public static Image chosenTile3;
    public static Image chosenTile4;
    public static Image enemyChosenTile;
    public static Image enemyChosenTile2;
    public static Image enemyChosenTile3;
    public static Image enemyChosenTile4;
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
    public Player enemyPlayer;
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
    public double startingWood = 100;
    public double startingCrop = 100;
    public double startingArcane = 0;
    public double startingBlood = 0;
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
    public boolean pushBoard;
    public boolean push = true;
    public boolean leaveScreen = false;
    public int flashCount = 0;
    public Timer resourceTimer = new Timer();
    public Timer flashTimer = new Timer();
    public Timer turnTimer = new Timer();
    public static Sound gatheringSound;
    public static Sound gainTileSound;
    public static Music backgroundMusic;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public static Rectangle rockButton;
    public static Rectangle woodButton;
    public static Rectangle waterButton;
    public static Rectangle cropButton;
    public static Boolean rockButtonSelected = false;
    public static Boolean woodButtonSelected = false;
    public static Boolean waterButtonSelected = false;
    public static Boolean cropButtonSelected = false;
    public static String battleRock = "";
    public static String battleWood = "";
    public static String battleWater = "";
    public static String battleCrop = "";
    public static Boolean playerWin;
    
    
   @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        
            //GUI
            background = new Image("images/environment/resized_game_background.png");
            hudBackground = new Image("images/environment/hud_background.png");
            tileMenuBackground = new Image("images/environment/tile_menu_background.png");
            tileHudRect = new Rectangle(tHudx, tHudy, 640, 256);
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
            aiamancyButtonRect = new Rectangle(tHudx+15,tHudy+100,160,30);
            ephemancyButtonRect = new Rectangle(tHudx+235,tHudy+100,161,31);
            koromancyButtonRect = new Rectangle(tHudx+464,tHudy+100,161,31);
            backgroundMusic = new Music("images/environment/backgroundMusic.wav");
            //backgroundMusic.loop(1, (float) 0.01);
            rockButton = new Rectangle(tHudx+60,tHudy+58,50,20);
            woodButton = new Rectangle(tHudx+160,tHudy+58,50,20);
            waterButton = new Rectangle(tHudx+260,tHudy+58,50,20);
            cropButton = new Rectangle(tHudx+360,tHudy+58,50,20);
            
            //TILES & RESOURCES
            blankTile = new Image("images/environment/tile.png");
            battleTile = new Image("images/environment/battle_tile.png");
            mountainTile = new Image("images/environment/mountain_tile.png");
            rockResource = new Image("images/environment/rock_resource.png");
            forestTile = new Image("images/environment/forest_tile.png");
            woodResource = new Image("images/environment/wood_resource.png");
            fieldsTile = new Image("images/environment/fields_tile.png");
            cropResource = new Image("images/environment/crop_resource.png");
            oceanTile = new Image("images/environment/ocean_tile.png");
            waterResource = new Image("images/environment/water_resource.png");
            grassTile = new Image("images/environment/grass_tile.png");
            earthResource = new Image("images/environment/earth_resource.png");
            desertTile = new Image("images/environment/desert_tile.png");
            arcaneResource = new Image("images/environment/ephesos_resource.png");
            bloodResource = new Image("images/environment/amnion_resource.png");
            controlledTile = new Image("images/environment/controlled_tile.png");
            enemyControlledTile = new Image("images/environment/enemy_controlled_tile.png");
            chosenTile = new Image("images/environment/chosen_tile.png");
            chosenTile2 = new Image("images/environment/chosen_tile2.png");
            chosenTile3 = new Image("images/environment/chosen_tile3.png");
            chosenTile4 = new Image("images/environment/chosen_tile4.png");
            enemyChosenTile = new Image("images/environment/enemy_chosen_tile.png");
            enemyChosenTile2 = new Image("images/environment/enemy_chosen_tile2.png");
            enemyChosenTile3 = new Image("images/environment/enemy_chosen_tile3.png");
            enemyChosenTile4 = new Image("images/environment/enemy_chosen_tile4.png");
            
            //MAP
            board = new Map();
            board.biome_gen();
            selectedTile = board.board.get(0);
            
            //PLAYER
            classTiles.add("Default");
            player = new Player(startingEarth, startingRock, startingWater, startingWood, startingCrop, startingArcane, startingBlood, 2, classTiles);
            
    }

    @Override
    public void keyPressed(int key, char c){
        if ((rockButtonSelected || woodButtonSelected || waterButtonSelected || cropButtonSelected) && (selectedTile.isBattle)){
            if (Character.isDigit(c)){
                if (rockButtonSelected){
                    battleRock = battleRock+c;
                } else if (woodButtonSelected){
                    battleWood = battleWood+c;
                } else if (waterButtonSelected){
                    battleWater = battleWater+c;
                } else if (cropButtonSelected){
                    battleCrop = battleCrop+c;
                }
            }
            if (key == 28){
                if (rockButtonSelected){
                    if (battleRock == ""){
                        battleRock = "0";
                    }
                    selectedTile.battle.update_rock(player, battleRock);
                    battleRock = "";
                    rockButtonSelected = false;
                } else if (woodButtonSelected){
                    if (battleWood == ""){
                        battleWood = "0";
                    }
                    selectedTile.battle.update_wood(player, battleWood);
                    battleWood = "";
                    woodButtonSelected = false;
                } else if (waterButtonSelected){
                    if (battleWater == ""){
                        battleWater = "0";
                    }
                    selectedTile.battle.update_water(player, battleWater);
                    battleWater = "";
                    waterButtonSelected = false;
                } else if (cropButtonSelected){
                    if (battleCrop == ""){
                        battleCrop = "0";
                    }
                    selectedTile.battle.update_crop(player, battleCrop);
                    battleCrop = "";
                    cropButtonSelected = false;
                }
            }
        }
        if (c == 'b'){
            board.biome_gen();
        } else if (c == 'n'){
            board.wipe_biomes();
        } else if (c == 'r'){
            if (player.tileChoices > 0 && !player.controlledTiles.contains(selectedTile) && !player.chosenTiles.contains(selectedTile)){
                player.add_tile(selectedTile, enemyPlayer);
            } else if ((player.controlledTiles.size() > 0) && (player.controlledTiles.contains(selectedTile))){
                player.remove_tile(board.board, selectedTile);
            }
        } else if (key == 14){
            if ((rockButtonSelected || woodButtonSelected || waterButtonSelected || cropButtonSelected) && (selectedTile.isBattle)){
                if (rockButtonSelected){
                    if (battleRock.length() > 0) {
                        battleRock = battleRock.substring(0, battleRock.length()-1);
                    }
                } else if (woodButtonSelected){
                    if (battleWood.length() > 0) {
                        battleWood = battleWood.substring(0, battleWood.length()-1);
                    }
                } else if (waterButtonSelected){
                    if (battleWater.length() > 0) {
                        battleWater = battleWater.substring(0, battleWater.length()-1);
                    }
                } else if (cropButtonSelected){
                    if (battleCrop.length() > 0) {
                        battleCrop = battleCrop.substring(0, battleCrop.length()-1);
                    }
                }
            } else {
                if ((player.controlledTiles.size() > 0) && (player.controlledTiles.contains(selectedTile))){
                    player.remove_tile(board.board, selectedTile);
                } else if ((player.chosenTiles.size() > 0) && (player.chosenTiles.contains(selectedTile))){
                    player.remove_path(board.board, selectedTile);

                }
            }
        } else if (c == 'e'){
            if (!player.chosenTiles.contains(selectedTile)){
                player.choose_tile(board.board, selectedTile, enemyPlayer);
            } else if ((player.chosenTiles.size() > 0) && (player.chosenTiles.contains(selectedTile))){
                player.remove_path(board.board, selectedTile);

            }
        } else if (key == 1){
            IP = "";
            if (playMode == "join"){
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (Exception e) {
                    System.err.println("Server Error: " + e.getMessage());
                    System.err.println("Localized: " + e.getLocalizedMessage());
                    System.err.println("Stack Trace: " + e.getStackTrace());
                    System.err.println("To String: " + e.toString());
                }
            }
            playMode = "";
            leaveScreen = true;
        } else if (key == 200 || c == 'w'){
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
        } else if (key == 208 || c == 's'){
            if (selectedTile.y != 21 && selectedTile.x != 0){
                if(selectedTile.x == 1 && selectedTile.y < 19){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord+84);
                } else if (selectedTile.x > 1){
                    selectedTile = board.get_tile(selectedTile.xCoord+32, selectedTile.yCoord+84);
                }
            }
        } else if (key == 205 || c == 'd'){
            if (selectedTile.x != 21){
                if (selectedTile.x == 19 && selectedTile.y != 0 && selectedTile.y != 21){
                    selectedTile = board.get_tile(selectedTile.xCoord+64, selectedTile.yCoord-14);
                } else if ( selectedTile.x == 20 && selectedTile.y != 20 && selectedTile.y != 1){
                    selectedTile = board.get_tile(selectedTile.xCoord+64, selectedTile.yCoord-14);
                } else if (selectedTile.x < 19){
                    selectedTile = board.get_tile(selectedTile.xCoord+64, selectedTile.yCoord-14);
                }
            } 
        } else if (key == 203 || c == 'a'){
            if (selectedTile.x != 0){
                    selectedTile = board.get_tile(selectedTile.xCoord-32, selectedTile.yCoord+56);
            }
        } else if (c == 'z'){
            if (!aiamancyButtonSelected){
                aiamancyButtonSelected = true;
                koromancyButtonSelected = false;
                ephemancyButtonSelected = false;
            } else {
                aiamancyButtonSelected = false;
            }
        } else if (c == 'x'){
            if (!ephemancyButtonSelected){
                aiamancyButtonSelected = false;
                koromancyButtonSelected = false;
                ephemancyButtonSelected = true;
            } else {
                ephemancyButtonSelected = false;
            }
        } else if (c == 'c'){
            if (!koromancyButtonSelected){
                aiamancyButtonSelected = false;
                koromancyButtonSelected = true;
                ephemancyButtonSelected = false;
            } else {
                koromancyButtonSelected = false;
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        
        if (leaveScreen){
            leaveScreen = false;
            game.enterState(0);
        }
        

        if (push){
            // CLIENT/HOST CONNECTION
            System.out.println("Pushing object streams...");
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (Exception e) {
                    System.err.println("Server Error: " + e.getMessage());
                    System.err.println("Localized: " + e.getLocalizedMessage());
                    System.err.println("Stack Trace: " + e.getStackTrace());
                    System.err.println("To String: " + e.toString());
            }
            push = false;
            if (playMode == "host"){
                try {
                    out.writeObject(board);
                } catch (Exception e) {
                        System.err.println("Server Error: " + e.getMessage());
                        System.err.println("Localized: " + e.getLocalizedMessage());
                        System.err.println("Stack Trace: " + e.getStackTrace());
                        System.err.println("To String: " + e.toString());
                }
            } else {
                try {
                    board = (Map)in.readObject();
                } catch (Exception e) {
                        System.err.println("Server Error: " + e.getMessage());
                        System.err.println("Localized: " + e.getLocalizedMessage());
                        System.err.println("Stack Trace: " + e.getStackTrace());
                        System.err.println("To String: " + e.toString());
                }
            }
            
            // TIMERS
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
                            case "Forest":
                                player.L += (20*tile.resourceReturn)*(player.resourceGain*player.woodResourceGain);
                                if (tile.resourceReturn > 0.25 && tile.decayCounter == 5){
                                    tile.decayCounter = 0;
                                    tile.resourceReturn -= player.decayRate;
                                }
                                break;
                            case "Fields":
                                player.C += (20*tile.resourceReturn)*(player.resourceGain*player.cropResourceGain);
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
                    player.update_battles(board.board, enemyPlayer);
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
                        ArrayList<Tile> clonea = new ArrayList<>(player.chosenTiles);
                        
                        for (Tile tile : clonea){
                            if (tile.playerControl <= 4){
                                tile.playerControl += 1;
                            }

                            if (tile.playerControl >= 4){
                                toRemove.add(tile);
                                player.remove_path(board.board, tile);
                                player.add_tile(tile,enemyPlayer);
                                gainSound = true;
                                tile.playerControl = 0;
                            }
                        }
                        /*for (Tile tile : toRemove){
                            player.remove_path(board.board, tile);
                        }*/
                    }
                }, 20*player.conquerRate, 20*player.conquerRate);
            
        }
        
        //SEND AND RECIEVE INFO
        try {
            out.reset();
            out.writeObject(player);
            enemyPlayer = (Player)in.readObject(); 
            //System.out.println("pushed");
        } catch (Exception e) {
                System.err.println("Server Error: " + e.getMessage());
                System.err.println("Localized: " + e.getLocalizedMessage());
                System.err.println("Stack Trace: " + e.getStackTrace());
                System.err.println("To String: " + e.toString());
        }
        
        //COP A WIN
        if (enemyPlayer.controlledTiles.isEmpty() && enemyPlayer.tileChoices == 0){
            playerWin = true;
            try{
                out.close();
                in.close();
                clientSocket.close();
            } catch (Exception e){
                System.err.println(e.toString());
            }
            game.enterState(2);
        } else if (player.controlledTiles.isEmpty() && player.tileChoices == 0){
            playerWin = false;
            try{
                out.close();
                in.close();
                clientSocket.close();
            } catch (Exception e){
                System.err.println(e.toString());
            }
            game.enterState(2);
        }
        
        
        if (gainSound){
            gainSound = false;
            gatheringSound.stop();
            gainTileSound.play(1f, 0.5f);
        }
        
        ArrayList<Battle> clones = new ArrayList<>(enemyPlayer.battles);
        ArrayList<Tile> clonere = new ArrayList<>(player.chosenTiles);
        if (!clones.isEmpty()){
            for (Battle battle : clones){
                for(Tile tile : clonere){
                    /*System.out.println(tile.x);
                    System.out.println(tile.y);
                    System.out.println();
                    System.out.println(battle.tile.x);
                    System.out.println(battle.tile.y);*/
                    if (tile.x == battle.tile.x && tile.y == battle.tile.y){
                        player.battles.add(new Battle(tile));
                        player.remove_path(board.board, tile);
                    }
                }
            }
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
                        player.add_tile(selectedTile, enemyPlayer);
                    } else if (player.controlledTiles.contains(selectedTile)){
                        player.remove_tile(board.board, selectedTile);
                    }
                } else if (pathButtonRect.contains(mouseX, mouseY) && (pathButtonEnabled || unpathButtonEnabled)){
                    if (selectedTile.chosen){
                        player.remove_path(board.board, selectedTile);
                    } else {
                        player.choose_tile(board.board, selectedTile, enemyPlayer);
                    }
                }
            }
            
            if (rockButton.contains(mouseX,mouseY)){
                rockButtonSelected = true;
                woodButtonSelected = false;
                waterButtonSelected = false;
                cropButtonSelected = false;
                battleRock = "";
                battleWood = "";
                battleWater = "";
                battleCrop = "";
            } else if (woodButton.contains(mouseX,mouseY)){
                rockButtonSelected = false;
                woodButtonSelected = true;
                waterButtonSelected = false;
                cropButtonSelected = false;
                battleRock = "";
                battleWood = "";
                battleWater = "";
                battleCrop = "";
            } else if (waterButton.contains(mouseX,mouseY)){
                rockButtonSelected = false;
                woodButtonSelected = false;
                waterButtonSelected = true;
                cropButtonSelected = false;
                battleRock = "";
                battleWood = "";
                battleWater = "";
                battleCrop = "";
            } else if (cropButton.contains(mouseX,mouseY)){
                rockButtonSelected = false;
                woodButtonSelected = false;
                waterButtonSelected = false;
                cropButtonSelected = true;
                battleRock = "";
                battleWood = "";
                battleWater = "";
                battleCrop = "";
            } else {
                rockButtonSelected = false;
                woodButtonSelected = false;
                waterButtonSelected = false;
                cropButtonSelected = false;
                battleRock = "";
                battleWood = "";
                battleWater = "";
                battleCrop = "";
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
            tileHudRect = new Rectangle(tHudx, tHudy, 640, 256);
            ritualHudRect = new Rectangle(tHudx, tHudy, 640, 1150);
            conquerButtonRect = new Rectangle(tHudx+140,tHudy+216,120,30);
            pathButtonRect = new Rectangle(tHudx+10,tHudy+216,120,30);
            aiamancyButtonRect = new Rectangle(tHudx+15,tHudy+100,160,30);
            ephemancyButtonRect = new Rectangle(tHudx+235,tHudy+100,161,31);
            koromancyButtonRect = new Rectangle(tHudx+464,tHudy+100,161,31);
            rockButton = new Rectangle(tHudx+60,tHudy+58,55,20);
            woodButton = new Rectangle(tHudx+160,tHudy+58,55,20);
            waterButton = new Rectangle(tHudx+260,tHudy+58,55,20);
            cropButton = new Rectangle(tHudx+360,tHudy+58,55,20);
        }
    }
            
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //background.draw(0,0);
        
        board.display_tiles();
        if (selectedTile != null){
            selectedTile.draw_circle(g);
        }
        player.display_controlled_tiles(false);
        player.display_chosen_tiles(false);
        enemyPlayer.display_controlled_tiles(true);
        enemyPlayer.display_chosen_tiles(true);
        player.display_battles();

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
        woodResource.draw(hudx+10,hudy+60);
        String woodResources = ":"+String.valueOf((int)player.L);
        g.drawString(woodResources, hudx+45, hudy+68);
        cropResource.draw(hudx+105,hudy+60);
        String cropResources = ":"+String.valueOf((int)player.C);
        g.drawString(cropResources, hudx+140, hudy+68);
        arcaneResource.draw(hudx+295,hudy+10);
        String arcaneResources = ":"+String.valueOf((int)player.A);
        g.drawString(arcaneResources, hudx+330, hudy+18);
        bloodResource.draw(hudx+390,hudy+10);
        String bloodResources = ":"+String.valueOf((int)player.B);
        g.drawString(bloodResources, hudx+425, hudy+18);
        
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
        aiamancyButton.draw(tHudx+15, tHudy+100);
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
        
        ephemancyButton.draw(tHudx+235, tHudy+100);
        koromancyButton.draw(tHudx+465, tHudy+100);
        if (selectedTile != null && selectedTile.isBattle == false){
            selectedTile.display_information(tHudx, tHudy, player, enemyPlayer, g);
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
        } else if (selectedTile.isBattle){
            selectedTile.display_information(tHudx, tHudy, player, enemyPlayer,  g);
        }
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static magicks.dominion.Game.blankTile;
import static magicks.dominion.Game.grassTile;
import static magicks.dominion.Game.mountainTile;
import static magicks.dominion.Game.oceanTile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import java.util.HashSet;
import java.util.Set;
import static magicks.dominion.Game.desertTile;
import static magicks.dominion.Game.earthResource;
import static magicks.dominion.Game.rockResource;
import static magicks.dominion.Game.waterResource;
import org.newdawn.slick.Color;

/**
 *
 * @author elliot
 */
public class Tile {
    public int x;
    public int y;
    public int xCoord;
    public int yCoord;
    public String biome;
    public boolean controlled;
    public boolean chosen;
    public boolean pathChosen;
    public int playerControl;
    public Tile pathTile;
    public float resourceReturn;
    public int decayCounter;
    
    public Tile(int x,int y, int xCoord, int yCoord){
        this.x = x;
        this.y = y;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.biome = "None";
        this.controlled = false;
        this.chosen = false;
        this.pathChosen = false;
        this.playerControl = 0;
        this.resourceReturn = 1;
        this.decayCounter = 0;
    }
    
    public void draw_tile(){
        switch (this.biome) {
            case "Grass":
                grassTile.draw(this.xCoord,this.yCoord);
                break;
            case "Mountain":
                mountainTile.draw(this.xCoord,this.yCoord);
                break;
            case "Ocean":
                oceanTile.draw(this.xCoord,this.yCoord);
                break;
            case "Desert":
                desertTile.draw(this.xCoord,this.yCoord);
                break;
            default:
                blankTile.draw(this.xCoord,this.yCoord);
                break;
        }
    }
    
    public void draw_circle(Graphics g){
        g.setColor(Color.red);
        g.draw(new Circle(this.xCoord+32,this.yCoord+28,28));
        g.setColor(Color.white);
        if (this.chosen){
            g.drawString(String.valueOf(this.playerControl), this.xCoord+27, this.yCoord+18);
        }
    }
    
    Tile check_tile(int mouseX, int mouseY){
        if (Math.sqrt( Math.pow((mouseX-(this.xCoord+32)),2) + Math.pow((mouseY-(this.yCoord+28)),2)) <= 28){
            return this;
        }
        return null;
    } 

    String get_biome(List<Tile> board, int desertCount) {
        if ((this.x == 0) && (this.y == 0)){
            return "Mountain";
        } else if ((this.x == 8) && (this.y == 8)){
            return "Ocean";
        } else if ((this.x == 10) && (this.y == 3)){
            return "Grass";
        } else if ( ((this.x == 16) && (this.y == 8)) || ((this.x == 3) && (this.y == 3)) ){
            return "Desert";
        }
        Set<String> chanceSet = new HashSet<>();
        
        for (Tile tile : board){
            if ((tile.x == this.x-1) && ((tile.y == this.y) || (tile.y == this.y+1))){
                if (tile.biome != "Desert"){
                    chanceSet.add(tile.biome);
                } else {
                    if (desertCount < 14){
                        //System.out.println(String.valueOf(desertCount));
                        chanceSet.add(tile.biome);
                    } else {
                        chanceSet.add("None");
                    }
                }
            } else if ((tile.x == this.x) && ((tile.y == this.y+1) || (tile.y == this.y-1))){
                if (tile.biome != "Desert"){
                    chanceSet.add(tile.biome);
                } else {
                    if (desertCount < 14){
                        //System.out.println(String.valueOf(desertCount));
                        chanceSet.add(tile.biome);
                    } else {
                        chanceSet.add("None");
                    }
                }
            } else if ((tile.x == this.x+1) && ((tile.y == this.y) || (tile.y == this.y-1))){
                if (tile.biome != "Desert"){
                    chanceSet.add(tile.biome);
                } else {
                    if (desertCount < 14){
                        //System.out.println(String.valueOf(desertCount));
                        chanceSet.add(tile.biome);
                    } else {
                        chanceSet.add("None");
                    }
                }
            }
        }
        Random random = new Random();
        int randomInt = random.nextInt(chanceSet.size());
        String[] chanceList = chanceSet.toArray(new String[0]);
        return chanceList[randomInt];
    }

    boolean controlled_check(List<Tile> board, List<Tile> controlledTiles, boolean remove){
        Tile base;
        for (Tile tile : board){
            if (!remove){
                if ((tile.x == this.x-1) && ((tile.y == this.y) || (tile.y == this.y+1)) && (controlledTiles.contains(tile) && (!tile.pathChosen))){
                    tile.pathChosen = true;
                    this.chosen = true;
                    tile.pathTile = this;
                    return true;
                } else if ((tile.x == this.x) && ((tile.y == this.y+1) || (tile.y == this.y-1)) && (controlledTiles.contains(tile) && (!tile.pathChosen))){
                    tile.pathChosen = true;
                    this.chosen = true;
                    tile.pathTile = this;
                    return true;
                } else if ((tile.x == this.x+1) && ((tile.y == this.y) || (tile.y == this.y-1)) && (controlledTiles.contains(tile) && (!tile.pathChosen))){
                    tile.pathChosen = true;
                    this.chosen = true;
                    tile.pathTile = this;
                    return true;
                }
            } else {
                if ((tile.x == this.x-1) && ((tile.y == this.y) || (tile.y == this.y+1)) && (controlledTiles.contains(tile) && (tile.pathTile == this))){
                    tile.pathChosen = false;
                    this.chosen = false;
                    this.playerControl = 0;
                    tile.pathTile = null;
                    return true;
                } else if ((tile.x == this.x) && ((tile.y == this.y+1) || (tile.y == this.y-1)) && (controlledTiles.contains(tile) && (tile.pathTile == this))){
                    tile.pathChosen = false;
                    this.chosen = false;
                    this.playerControl = 0;
                    tile.pathTile = null;
                    return true;
                } else if ((tile.x == this.x+1) && ((tile.y == this.y) || (tile.y == this.y-1)) && (controlledTiles.contains(tile) && (tile.pathTile == this))){
                    tile.pathChosen = false;
                    this.chosen = false;
                    this.playerControl = 0;
                    tile.pathTile = null;
                    return true;
                }
            }
        }
        return false;
    }

    boolean possible_path(List<Tile> board, List<Tile> controlledTiles) {
        for (Tile tile : board){
            if ((tile.x == this.x-1) && ((tile.y == this.y) || (tile.y == this.y+1)) && (controlledTiles.contains(tile))){
                return true;
            } else if ((tile.x == this.x) && ((tile.y == this.y+1) || (tile.y == this.y-1)) && (controlledTiles.contains(tile))){
                return true;
            } else if ((tile.x == this.x+1) && ((tile.y == this.y) || (tile.y == this.y-1)) && (controlledTiles.contains(tile))){
                return true;
            }
        }
        return false;
    }
    
    boolean is_path(List<Tile> board, List<Tile> controlledTiles) {
        List<Tile> possibilities = new ArrayList<>();
        for (Tile tile : board){
            if ((tile.x == this.x-1) && ((tile.y == this.y) || (tile.y == this.y+1)) && (controlledTiles.contains(tile)/* && tile.pathTile != null */ && this != tile.pathTile )){
                possibilities.add(tile);
                //return true;
            } if ((tile.x == this.x) && ((tile.y == this.y+1) || (tile.y == this.y-1)) && (controlledTiles.contains(tile) && this != tile.pathTile )){
                possibilities.add(tile);
                //return true;
            } if ((tile.x == this.x+1) && ((tile.y == this.y) || (tile.y == this.y-1)) && (controlledTiles.contains(tile) && this != tile.pathTile )){
                possibilities.add(tile);
                //return true;
            }
        }
        int counter = 0;
        for (Tile tile : possibilities){
            if (tile.pathTile == null){
                return false;
            } else {
                counter += 1;
                //System.out.println(String.valueOf(counter));
            }
        }
        if (counter != 0){
            return true;
        } else {
            return false;
        }
    }

    public void display_information(int x, int y, Player player, Graphics g) {
        g.setColor(Color.lightGray);
        g.drawString("Position: ("+String.valueOf(this.x) +"," + String.valueOf(this.y)+")", x+10, y+15);
        g.drawString("Biome: "+ this.biome, x+180, y+15);
        
        switch(this.biome){
            case "Grass":
                g.drawString("Resource return: "+String.valueOf((20*this.resourceReturn)*(player.resourceGain*player.earthResourceGain)), x+350, y+15);
                earthResource.draw(x+575, y+8);
                break;
            case "Ocean":
                g.drawString("Resource return: "+String.valueOf((20*this.resourceReturn)*(player.resourceGain*player.waterResourceGain)), x+350, y+15);
                waterResource.draw(x+575, y+8);
                break;
            case "Mountain":
                g.drawString("Resource return: "+String.valueOf((20*this.resourceReturn)*(player.resourceGain*player.rockResourceGain)), x+350, y+15);
                rockResource.draw(x+575, y+8);
                break;
            case "Desert":
                g.drawString("Resource return: "+String.valueOf(10), x+350, y+15);
                earthResource.draw(x+538, y+8);
                g.drawString("or", x+575, y+15);
                rockResource.draw(x+600, y+8);
                break;
            default:
                break;
        }
    }

}

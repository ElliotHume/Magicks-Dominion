/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

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
            case "grass":
                grassTile.draw(this.xCoord,this.yCoord);
                break;
            case "mountain":
                mountainTile.draw(this.xCoord,this.yCoord);
                break;
            case "ocean":
                oceanTile.draw(this.xCoord,this.yCoord);
                break;
            default:
                blankTile.draw(this.xCoord,this.yCoord);
                break;
        }
    }
    
    public void draw_circle(Graphics g){
        g.setColor(Color.red);
        g.drawString(String.valueOf(this.x), 10, 10);
        g.drawString(String.valueOf(this.y), 50, 10);
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

    String get_biome(List<Tile> board) {
        if ((this.x == 0) && (this.y == 0)){
            return "mountain";
        } else if ((this.x == 8) && (this.y == 8)){
            return "ocean";
        } else if ((this.x == 10) && (this.y == 3)){
            return "grass";
        }
        Set<String> chanceSet = new HashSet<>();
        
        for (Tile tile : board){
            if ((tile.x == this.x-1) && ((tile.y == this.y) || (tile.y == this.y+1))){
                chanceSet.add(tile.biome);
            } else if ((tile.x == this.x) && ((tile.y == this.y+1) || (tile.y == this.y-1))){
                chanceSet.add(tile.biome);
            } else if ((tile.x == this.x+1) && ((tile.y == this.y) || (tile.y == this.y-1))){
                chanceSet.add(tile.biome);
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

}

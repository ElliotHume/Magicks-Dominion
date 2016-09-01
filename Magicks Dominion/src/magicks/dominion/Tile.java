/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static magicks.dominion.Game.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import java.util.HashSet;
import java.util.Set;
import org.newdawn.slick.Color;

/**
 *
 * @author elliot
 */
public class Tile implements Serializable {
    public int x;
    public int y;
    public int xCoord;
    public int yCoord;
    public String biome;
    public String id;
    public boolean controlled;
    public boolean chosen;
    public boolean pathChosen;
    public boolean isBattle;
    public int playerControl;
    public Tile pathTile;
    public Battle battle;
    public Battle enemyBattle;
    public float resourceReturn;
    public int decayCounter;
    
    public Tile(int x,int y, int xCoord, int yCoord){
        this.x = x;
        this.y = y;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.biome = "None";
        this.id = String.valueOf(x)+String.valueOf(y);
        this.controlled = false;
        this.chosen = false;
        this.pathChosen = false;
        this.isBattle = false;
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
            case "Fields":
                fieldsTile.draw(this.xCoord,this.yCoord);
                break;
            case "Forest":
                forestTile.draw(this.xCoord,this.yCoord);
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
        if (((this.x == 0) && (this.y == 0)) || ((this.x == 18) && (this.y == 3))){
            return "Mountain";
        } else if ((this.x == 8) && (this.y == 8)){
            return "Ocean";
        } else if ((this.x == 10) && (this.y == 3)){
            return "Grass";
        } else if ( ((this.x == 16) && (this.y == 8)) || ((this.x == 3) && (this.y == 3)) ){
            return "Desert";
        } else if ( ((this.x == 5) && (this.y == 1)) || ((this.x == 8) && (this.y == 13)) ){
            return "Fields";
        } else if ( ((this.x == 18) && (this.y == 8)) || ((this.x == 3) && (this.y == 12)) ){
            return "Forest";
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

    public void display_information(int x, int y, Player player, Player enemy, Graphics g) {
        g.setColor(Color.lightGray);
        g.drawString("Position: ("+String.valueOf(this.x) +"," + String.valueOf(this.y)+")", x+10, y+15);
        g.drawString("Biome: "+ this.biome, x+180, y+15);
        
        if (!this.isBattle){
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
                case "Fields":
                    g.drawString("Resource return: "+String.valueOf((20*this.resourceReturn)*(player.resourceGain*player.cropResourceGain)), x+350, y+15);
                    cropResource.draw(x+575, y+8);
                    break;
                case "Forest":
                    g.drawString("Resource return: "+String.valueOf((20*this.resourceReturn)*(player.resourceGain*player.woodResourceGain)), x+350, y+15);
                    woodResource.draw(x+575, y+8);
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
        } else {
            ArrayList<Battle> clones = new ArrayList<>(enemy.battles);
            for (Battle enemyBattles : clones){
                if(enemyBattles.tile.x == this.x && enemyBattles.tile.y == this.y){
                    enemyBattle = enemyBattles;
                }
            }
            if (enemyBattle == null){
                enemyBattle = this.battle;
            }
            if (rockButtonSelected){
                g.setColor(Color.white);
                g.fillRect(x+61,y+59,54,19);
                g.setColor(Color.lightGray);
                g.drawString(battleRock,x+68,y+58);
            } else if (woodButtonSelected){
                g.setColor(Color.white);
                g.fillRect(x+161, y+59, 54, 19);
                g.setColor(Color.lightGray);
                g.drawString(battleWood,x+168,y+58);
            } else if (waterButtonSelected){
                g.setColor(Color.white);
                g.fillRect(x+261, y+59, 54, 19);
                g.setColor(Color.lightGray);
                g.drawString(battleWater,x+268,y+58);
            } else if (cropButtonSelected){
                g.setColor(Color.white);
                g.fillRect(x+361, y+59, 54, 19);
                g.setColor(Color.lightGray);
                g.drawString(battleCrop,x+368,y+58);
            }
            
            rockResource.draw(x+25, y+50);
            if (!rockButtonSelected){
                g.drawString(":"+String.valueOf(this.battle.R), x+60,y+58);
            }
            woodResource.draw(x+125, y+50);
            if (!woodButtonSelected){
                g.drawString(":"+String.valueOf(this.battle.L), x+160,y+58);
            }
            waterResource.draw(x+225, y+50);
            if (!waterButtonSelected){
                g.drawString(":"+String.valueOf(this.battle.W), x+260,y+58);
            }
            cropResource.draw(x+325, y+50);
            if (!cropButtonSelected){
                g.drawString(":"+String.valueOf(this.battle.C), x+360,y+58);
            }
            
            g.setColor(Color.orange);
            g.drawString(String.valueOf(enemyBattle.R), x+68,y+40);
            g.drawString(String.valueOf(enemyBattle.L), x+168,y+40);
            g.drawString(String.valueOf(enemyBattle.W), x+268,y+40);
            g.drawString(String.valueOf(enemyBattle.C), x+368,y+40);
            
            g.setColor(Color.black);
            g.draw(rockButton);
            g.draw(woodButton);
            g.draw(waterButton);
            g.draw(cropButton);
            
            //progress bar
            g.drawRect(x+400, y+14, 200, 20);
            g.setColor(Color.red);
            g.fillRect(x+401, y+15, 199, 19);
            g.setColor(Color.green);
            g.fillRect(x+401, y+15, (float) (this.battle.total*5), 19);
        }
    }

}

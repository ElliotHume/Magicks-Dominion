/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static magicks.dominion.Game.battleTile;
import static magicks.dominion.Game.chosenTile;
import static magicks.dominion.Game.chosenTile2;
import static magicks.dominion.Game.chosenTile3;
import static magicks.dominion.Game.chosenTile4;
import static magicks.dominion.Game.controlledTile;
import static magicks.dominion.Game.enemyChosenTile;
import static magicks.dominion.Game.enemyChosenTile2;
import static magicks.dominion.Game.enemyChosenTile3;
import static magicks.dominion.Game.enemyChosenTile4;
import static magicks.dominion.Game.enemyControlledTile;

/**
 ** @author Elliot Hume
 **/

public class Player implements Serializable {
    public double E; //earth
    public double R; //rock
    public double W; //water
    public double L; //log
    public double C; //crop
    public double A; //arcane
    public double B; //blood
    public int tileChoices;
    public List<Class> classTiles = new ArrayList<>();
    public List<Tile> controlledTiles = new ArrayList<>();
    public List<Tile> chosenTiles = new ArrayList<>();
    public List<Battle> battles = new ArrayList<>();
    public float decayRate;
    public int conquerRate;
    public float resourceGain;
    public int resourceTime;
    public float earthResourceGain;
    public float waterResourceGain;
    public float rockResourceGain;
    public float cropResourceGain;
    public float woodResourceGain;
    public float bloodResourceGain;
    public float arcaneResourceGain;
    public boolean canMill  = true;
    
    public Player(double E, double R, double W, double L, double C, double A, double B, int tileChoices, List classTiles){
        this.E = E; //earth
        this.R = R; //rock
        this.W = W; //water
        this.L = L; //log
        this.C = C; //crop
        this.A = A; //arcane
        this.B = B; //blood
        this.tileChoices = tileChoices;
        this.classTiles = classTiles;
        this.conquerRate = 1 * 1000;
        this.resourceGain = 1;
        this.resourceTime = 1000;
        this.earthResourceGain = 1;
        this.rockResourceGain = 1;
        this.waterResourceGain = 1;
        this.cropResourceGain = 1;
        this.woodResourceGain = 1;
        this.arcaneResourceGain = 1;
        this.bloodResourceGain = 1;
        this.decayRate = (float) (0.25);

    }
    
    public void display_controlled_tiles(boolean enemy){
        if (controlledTiles.size() > 0){
            ArrayList<Tile> cloner = new ArrayList<>(this.controlledTiles);
            for (Tile tile : cloner){
                if (enemy){
                    enemyControlledTile.draw(tile.xCoord,tile.yCoord);
                } else {
                    controlledTile.draw(tile.xCoord,tile.yCoord);
                }
            }
        }
    }
    
    public void display_chosen_tiles(boolean enemy){
        if (chosenTiles.size() > 0){
            ArrayList<Tile> clones = new ArrayList<>(this.chosenTiles);
            for (Tile tile : clones){
                switch (tile.playerControl) {
                    case 0:
                        if (enemy){
                            enemyChosenTile.draw(tile.xCoord,tile.yCoord);
                        } else {
                            chosenTile.draw(tile.xCoord,tile.yCoord);
                        }
                        break;
                    case 1:
                        if (enemy){
                            enemyChosenTile2.draw(tile.xCoord,tile.yCoord);
                        } else {
                            chosenTile2.draw(tile.xCoord,tile.yCoord);
                        }
                        break;
                    case 2:
                        if (enemy){
                            enemyChosenTile3.draw(tile.xCoord,tile.yCoord);
                        } else {
                            chosenTile3.draw(tile.xCoord,tile.yCoord);
                        }
                        break;
                    default:
                        if (enemy){
                            enemyChosenTile4.draw(tile.xCoord,tile.yCoord);
                        } else {
                            chosenTile4.draw(tile.xCoord,tile.yCoord);
                        }
                        break;
                }
            }
        }
    }
    
    public void display_battles(){
        if (battles.size() > 0){
            ArrayList<Battle> cloned = new ArrayList<>(this.battles);
            for (Battle battle : cloned){
                battleTile.draw(battle.tile.xCoord,battle.tile.yCoord);
            }
        }
    }

    public void remove_tile(List<Tile> board, Tile selectedTile) {
        if (this.controlledTiles.contains(selectedTile)){
            if (selectedTile.pathChosen){
                this.remove_path(board, selectedTile.pathTile);
            }
            this.controlledTiles.removeAll(Collections.singleton(selectedTile));
            switch(selectedTile.biome){
                case "Grass":
                    if (selectedTile.playerControl <= 2){
                        this.E += 100;
                    } else {
                        this.E += 50;
                    }
                    break;
                case "Mountain":
                    if (selectedTile.playerControl <= 2){
                        this.R += 100;
                    } else {
                        this.R += 50;
                    }
                    break;
                case "Ocean":
                    if (selectedTile.playerControl <= 2){
                        this.W += 100;
                    } else {
                        this.W += 50;
                    }
                    break;
                case "Fields":
                    if (selectedTile.playerControl <= 2){
                        this.C += 100;
                    } else {
                        this.C += 50;
                    }
                    break;
                case "Forest":
                    if (selectedTile.playerControl <= 2){
                        this.L += 100;
                    } else {
                        this.L += 50;
                    }
                    break;
                case "Desert":
                    this.E += 25;
                    this.R += 25;
                    break;
                default:
                    break;
            }
        }
        
    }

    public void add_tile(Tile selectedTile, Player enemy) {
            boolean enemyControlled = false;
            boolean enemyChosen = false;
            
            ArrayList<Tile> clonee = new ArrayList<>(enemy.controlledTiles);
            ArrayList<Tile> clonew = new ArrayList<>(enemy.chosenTiles);
            
            for (Tile tile : clonee){
                if (selectedTile.x == tile.x && selectedTile.y == tile.y){
                    enemyControlled = true;
                }
            }
            
            for (Tile tile : clonew){
                if (selectedTile.x == tile.x && selectedTile.y == tile.y){
                    enemyChosen = true;
                }
            }
            
            if (!this.controlledTiles.contains(selectedTile) && !this.chosenTiles.contains(selectedTile) && !enemyControlled && !enemyChosen){
                this.controlledTiles.add(selectedTile);
                selectedTile.controlled = true;
                this.A += 10*this.arcaneResourceGain;
                if (tileChoices > 0){
                    this.tileChoices -= 1;
                }
            }
    }
    
    public void choose_tile(List<Tile> board, Tile selectedTile, Player enemy){
        boolean enemyControlled = false;
        boolean enemyChosen = false;
        ArrayList<Tile> clonez = new ArrayList<>(enemy.controlledTiles);
        ArrayList<Tile> clonex = new ArrayList<>(enemy.chosenTiles);
            for (Tile tile : clonez){
                if (selectedTile.x == tile.x && selectedTile.y == tile.y){
                    enemyControlled = true;
                }
            }
            for (Tile tile : clonex){
                if (selectedTile.x == tile.x && selectedTile.y == tile.y){
                    enemyChosen = true;
                }
            }
            if (!this.chosenTiles.contains(selectedTile) && !this.controlledTiles.contains(selectedTile) && !enemyControlled && !enemyChosen){
                if (selectedTile.controlled_check(board, controlledTiles, false)){
                    this.chosenTiles.add(selectedTile);
                }
            } else if (enemyChosen){
                this.battles.add(new Battle(selectedTile));
            }
    }

    public void remove_path(List<Tile> board, Tile selectedTile) {
        if (this.chosenTiles.contains(selectedTile)){
            if (selectedTile.controlled_check(board, controlledTiles, true)){
                this.chosenTiles.removeAll(Collections.singleton(selectedTile));
            }
        }
    }
    
    public void update_battles(List<Tile> board, Player enemy){
        ArrayList<Battle> clonea = new ArrayList<>(this.battles);
        ArrayList<Battle> clones = new ArrayList<>(enemy.battles);
        for (Battle playerBattle: clonea){
            for (Battle enemyBattle: clones){
                if ((playerBattle.tile.x == enemyBattle.tile.x) && (playerBattle.tile.y == enemyBattle.tile.y)){
                    playerBattle.battle_calculator(board, this, enemy, enemyBattle);
                }
            }
        }
    }
}

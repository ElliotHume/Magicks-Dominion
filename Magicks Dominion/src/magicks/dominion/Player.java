/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static magicks.dominion.Game.chosenTile;
import static magicks.dominion.Game.chosenTile2;
import static magicks.dominion.Game.chosenTile3;
import static magicks.dominion.Game.chosenTile4;
import static magicks.dominion.Game.controlledTile;

/**
 ** @author Elliot Hume
 **/

public class Player {
    public double E;
    public double R;
    public double W;
    public double A;
    public int tileChoices;
    public List<String> classTiles = new ArrayList<>();
    public List<Tile> controlledTiles = new ArrayList<>();
    public List<Tile> chosenTiles = new ArrayList<>();
    public float decayRate;
    public int conquerRate;
    public float resourceGain;
    public float earthResourceGain;
    public float waterResourceGain;
    public float rockResourceGain;
    
    public Player(double E, double R, double W, double A, int tileChoices, List classTiles){
        this.E = E;
        this.R = R;
        this.W = W;
        this.A = A;
        this.tileChoices = tileChoices;
        this.classTiles = classTiles;
        this.conquerRate = 1 * 1000;
        this.resourceGain = 1;
        this.earthResourceGain = 1;
        this.rockResourceGain = 1;
        this.waterResourceGain = 1;
        this.decayRate = (float) (0.25);

    }
    
    public void display_controlled_tiles(){
        if (controlledTiles.size() > 0){
            ArrayList<Tile> cloner = new ArrayList<>(this.controlledTiles);
            for (Tile tile : cloner){
                controlledTile.draw(tile.xCoord,tile.yCoord);
            }
        }
    }
    
    public void display_chosen_tiles(){
        if (chosenTiles.size() > 0){
            ArrayList<Tile> clones = new ArrayList<>(this.chosenTiles);
            for (Tile tile : clones){
                switch (tile.playerControl) {
                    case 0:
                        chosenTile.draw(tile.xCoord,tile.yCoord);
                        break;
                    case 1:
                        chosenTile2.draw(tile.xCoord,tile.yCoord);
                        break;
                    case 2:
                        chosenTile3.draw(tile.xCoord,tile.yCoord);
                        break;
                    default:
                        chosenTile4.draw(tile.xCoord,tile.yCoord);
                        break;
                }
            }
        }
    }

    public void remove_tile(List<Tile> board, Tile selectedTile) {
        if (classTiles.contains("Default")){
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
                case "Desert":
                    this.E += 25;
                    this.R += 25;
                    break;
                default:
                    break;
            }
        }
        
    }

    public void add_tile(Tile selectedTile) {
        if (classTiles.contains("Default")){
            if ((!this.controlledTiles.contains(selectedTile))){
                this.controlledTiles.add(selectedTile);
                selectedTile.controlled = true;
                this.A += 10;
                if (tileChoices > 0){
                    this.tileChoices -= 1;
                }
            }
        }
    }
    
    public void choose_tile(List<Tile> board, Tile selectedTile){
        if (classTiles.contains("Default")){
            if ((!this.chosenTiles.contains(selectedTile)) && (!this.controlledTiles.contains(selectedTile))){
                if (selectedTile.controlled_check(board, controlledTiles, false)){
                    this.chosenTiles.add(selectedTile);
                }
            }
        }
    }

    public void remove_path(List<Tile> board, Tile selectedTile) {
        if (classTiles.contains("Default")){
            if (selectedTile.controlled_check(board, controlledTiles, true)){
                this.chosenTiles.removeAll(Collections.singleton(selectedTile));
            }
        }
    }
}

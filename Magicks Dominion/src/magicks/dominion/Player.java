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
 *
 * @author elliot
 */
public class Player {
    public int E;
    public int R;
    public int W;
    public int tileChoices;
    public List<String> classTiles = new ArrayList<String>();
    public List<Tile> controlledTiles = new ArrayList<Tile>();
    public List<Tile> chosenTiles = new ArrayList<Tile>();
    
    public Player(int E, int R, int W, int tileChoices, List classTiles){
        this.E = E;
        this.R = R;
        this.W = W;
        this.tileChoices = tileChoices;
        this.classTiles = classTiles;
    }
    
    public void display_controlled_tiles(){
        if (controlledTiles.size() > 0){
            for (Tile tile : controlledTiles){
                controlledTile.draw(tile.xCoord,tile.yCoord);
            }
        }
    }
    
    public void display_chosen_tiles(){
        if (chosenTiles.size() > 0){
            for (Tile tile : chosenTiles){
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
            this.tileChoices += 1;
        }
    }

    public void add_tile(Tile selectedTile) {
        if (classTiles.contains("Default")){
            if ((!this.controlledTiles.contains(selectedTile))){
                this.controlledTiles.add(selectedTile);
                selectedTile.controlled = true;
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

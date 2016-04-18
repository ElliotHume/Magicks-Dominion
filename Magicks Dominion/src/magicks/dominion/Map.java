/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author elliot
 */
public class Map {
    public int xBegin = 35;
    public int yBegin = 596;
    public List<Tile> board = new ArrayList<>();
    

    public Map(){
        
        for (int y = 0; y<=22; y++){
            if (y < 20) {
                xBegin = 55+(y*47);
                yBegin = 596+(y*28);
            } else if (y == 20) {
                xBegin = 55+((y+1)*47);
                yBegin = 596+((y-1)*28);
            } else if (y == 21){
                xBegin = 55+((y+2)*47);
                yBegin = 596+((y-2)*28);
            }
            if ((y == 1) || (y == 20)){
                for (int x=0; x <= 20; x++){
                    board.add(new Tile(x,y,(xBegin+(x*(47))), (yBegin+(x*(-28)))));
                }
            } else if ((y >= 2) && (y != 21) && (y != 22)){
                for (int x=0; x <= 21; x++){
                    board.add(new Tile(x,y,(xBegin+(x*(47))), (yBegin+(x*(-28)))));
                }
            } else {
                for (int x=0; x <= 19; x++){
                    board.add(new Tile(x,y,(xBegin+(x*(47))), (yBegin+(x*(-28)))));
                }
            }
        }
        //this.board = board;
    }
    
    public void display_tiles() {
        for (Tile tile : board) {
            tile.draw_tile();
        }
    }
    
    Tile get_tile(int mouseX, int mouseY){
        for (Tile tile : board) {
            if (tile.check_tile(mouseX, mouseY) != null){
                return tile;
            }
        }
        return null;
    }
    
    public void biome_gen(){
        boolean filled = false;
        for (Tile tile : board){
            tile.biome = tile.get_biome(board);
        }
        while (!filled){
            filled = true;
            for (Tile tile : board){
                if (tile.biome == "None"){
                    tile.biome = tile.get_biome(board);
                }
            }
            for(Tile tile : board){
                if(tile.biome == "None"){
                    filled = false;
                }
            }
        }
        
    }
    
    public void wipe_biomes(){
        for (Tile tile : board){
            tile.biome = "None";
        }
    }
    
    
}

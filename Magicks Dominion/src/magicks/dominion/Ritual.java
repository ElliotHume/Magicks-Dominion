/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author elliot
 */
public class Ritual implements Serializable {

    public String name;
    public ArrayList<String> effect;
    
    public Ritual(String name){
        this.name = name;
        this.effect = this.get_effect();
    }
    
    public ArrayList<String> get_effect(){
        ArrayList c = new ArrayList();
        switch(this.name){
            case("Reforestation"):
                c.add("protect a tile from calamities but prevent that tile from spreading");
                c.add("");
                c.add("requirement(s): control 3 adjacent tiles");
                return c;
            case("Wild Ivy"):
                return c;
            case("Will of the Goddess"):
                return c;
            case("Rocky Shore"):
                return c;
            case("Ecocycle"):
                return c;
            case("Natural Growth"):
                return c;
            case("Harvester's Boon"):
                return c;
        }
        return null;
    }
    
    public void cast(Player player, Tile tile){
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.io.Serializable;
import java.util.List;
import static magicks.dominion.Game.gainTileSound;
import static magicks.dominion.Game.gatheringSound;

/**
 *
 * @author elliot
 */
public class Battle implements Serializable {
    public Tile tile;
    public int total = 20;
    public int R = 0;
    public int L = 0;
    public int W = 0;
    public int C = 0;
    
    public Battle(Tile tile){
        this.tile = tile;
        tile.isBattle = true;
        tile.battle = this;
    }
    public void update_rock(Player player, String newValue){
        if ((Integer.valueOf(newValue) - this.R)<=player.R){
            player.R -= (Integer.valueOf(newValue) - this.R);
            this.R = Integer.valueOf(newValue);
        }
    }
    public void update_wood(Player player, String newValue){
        if ((Integer.valueOf(newValue) - this.L)<=player.L){
            player.L -= (Integer.valueOf(newValue) - this.L);
            this.L = Integer.valueOf(newValue);
        }
    }
    public void update_water(Player player, String newValue){
        if ((Integer.valueOf(newValue) - this.W)<=player.W){
            player.W -= (Integer.valueOf(newValue) - this.W);
            this.W = Integer.valueOf(newValue);
        }
    }
    public void update_crop(Player player, String newValue){
        if ((Integer.valueOf(newValue) - this.C)<=player.C){
            player.C -= (Integer.valueOf(newValue) - this.C);
            this.C = Integer.valueOf(newValue);
        }
    }
    
    public void battle_calculator(List<Tile> board, Player player, Player enemy, Battle enemyBattle){
        double power = (this.R*0.1)+(this.L*0.11)+(this.W*0.15)+(this.C*0.16);
        double enemyPower = (enemyBattle.R*0.1)+(enemyBattle.L*0.11)+(enemyBattle.W*0.15)+(enemyBattle.C*0.16);
        this.total += (power - enemyPower);
        if(this.total >= 40){
            this.tile.battle = null;
            this.tile.isBattle = false;
            player.remove_path(board, tile);
            player.battles.remove(this);
            
            player.add_tile(this.tile,enemy);
            gatheringSound.stop();
            gainTileSound.play(1f, 0.5f);
        }
        if (this.total <= 0){
            player.battles.remove(this);
            this.tile.battle = null;
            this.tile.isBattle = false;
        }
    }
}

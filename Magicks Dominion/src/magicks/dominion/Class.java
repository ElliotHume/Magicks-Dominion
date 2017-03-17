/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicks.dominion;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static magicks.dominion.ClassSelection.classBackground;
import static magicks.dominion.Game.push;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/*
 * @author elliot
 */
public class Class implements Serializable {

    public String name;
    public String displayName;
    public ArrayList<String> description;
    public Method method;
    public Rectangle hitbox;
    public Boolean selected;
    //public Font awtFont2 = new Font("Times New Roman", Font.PLAIN, 20);
    //public TrueTypeFont nameFont  = new TrueTypeFont(awtFont2, false);
    //public Font awtFont1 = new Font("Times New Roman", Font.BOLD, 50);
    //public TrueTypeFont selectedFont  = new TrueTypeFont(awtFont1, false);
    
    
    public Class(String name){
        this.name = name;
        this.displayName = find_displayName();
        this.description = find_description();
        this.selected = false;
    }
    
    public void draw(Graphics g, int x, int y, TrueTypeFont nameFont, TrueTypeFont selectedFont){
        classBackground.draw(x,y);
        g.setFont(nameFont);
        g.setColor(Color.black);
        g.drawString(displayName, x+15,y+8);
        if (this.selected){
            g.setColor(Color.white);
            g.setFont(selectedFont);
            g.drawString(displayName, 1621, 11);
            g.setColor(Color.black);
            g.drawString(displayName, 1620, 10);
            g.setColor(Color.white);
            if (this.description != null){
                for (int i=0; i < this.description.size(); i++){
                    g.setFont(nameFont);
                    g.drawString(description.get(i), 1625, 102+(25*i));
                }
            }
        }
    }
    public final String find_displayName(){
        switch (this.name){
            case ("class1"):
                return "Warlike nature";
            case ("class2"):
                return "Emperium";
            case ("class3"):
                return "Native";
            case ("class4"):
                return "Warsong";
            case ("class5"):
                return "Wizard";
            case ("class6"):
                return "Warlock";
            case ("class7"):
                return "Adept";
            case ("class8"):
                return "Farseer";
            case ("class9"):
                return "GODMODE";
        }
        return null;
    }
    
    public final ArrayList<String> find_description(){
        ArrayList c = new ArrayList();
        switch (this.name){
            case ("class1"):
                c.add("+ 1.15x conquer rate");
                c.add("-  Player cannot mill tiles");
                return c;
            case ("class2"):
                c.add("+ 1.20x conquer rate");
                c.add("-  1 less starting tile choice");
                return c;
            case ("class3"):
                c.add("+ 1 extra starting tile choice");
                return c;
            case ("class4"):
                c.add("+ 1.10x conquer rate");
                return c;
            case ("class5"):
                c.add("+ 2.00x Ephesos resource gain");
                c.add("-  0.50x Amnion resource gain");
                return c;
            case ("class6"):
                c.add("+ 2.00x Amnion resource gain");
                c.add("-  0.50x Ephesos resource gain");
                return c;
            case ("class7"):
                c.add("+ 50 starting Ephesos resource");
                c.add("+ 50 starting Amnion resource");
                return c;
            case ("class8"):
                c.add("+ 10.00x resource gain");
                c.add("-  Wait 5x longer for resource");
                return c;
            case ("class9"):
                c.add("GODMODE");
                return c;
        }
        return null;
    }
    
    public void action(Player player){
        switch (this.name) {
            case "class1":
                player.canMill = false;
                player.conquerRate *= 0.85;
                return;
            case "class2":
                if (player.tileChoices > 0){
                    player.tileChoices -= 1;
                }  
                player.conquerRate *= 0.8;
                return;
            case "class3":
                player.tileChoices += 1;
                return;
            case "class4":
                player.conquerRate *= 0.9;
                return;
            case "class5":
                player.arcaneResourceGain *= 2;
                player.bloodResourceGain *= 0.5;
                return;
            case "class6":
                player.arcaneResourceGain *= 0.5;
                player.bloodResourceGain *= 2;
                return;
            case "class7":
                player.A += 50;
                player.B += 50;
                return;
            case "class8":
                player.resourceTime *= 5;
                player.resourceGain *= 10;
                push = true;
                break;
            case "class9":
                player.resourceGain = 50;
                player.resourceTime *= 0.5;
                player.conquerRate *= 0.5;
                return;
        }
    }
    
    public void define_hitbox(int x, int y){
        this.hitbox = new Rectangle(x,y,200,40);
    }
    
    public Boolean check_hitbox(int x, int y){
        if (this.hitbox.contains(x, y)){
            return true;
        } else {
            return false;
        }
    }
}

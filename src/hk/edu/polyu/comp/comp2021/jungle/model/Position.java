package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;

public class Position implements Serializable {
    int x;
    int y;
    Beast beast;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
        beast = null;
    }

    public Beast Occupy(Beast beast){
        Beast original = this.beast;
        // if the position is occupied, kill the beast

        if(this.beast != null){
            this.beast.setAvalaible(false);
        }

        this.beast = beast;
        return original;
    }

    public void release(){
        this.beast = null;
    }
    public boolean IsOccupied(){
        if(beast != null){
            return true;
        }
        return false;
    }

    public Beast getBeast(){
        return this.beast;
    }
}

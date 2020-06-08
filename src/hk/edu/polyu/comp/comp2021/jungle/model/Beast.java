package hk.edu.polyu.comp.comp2021.jungle.model;

import javax.net.ssl.SSLSession;
import java.io.Serializable;

public class Beast implements Serializable {
    private int x;
    private int y;
    private boolean available;
    private int priority;
    private int ownership; // mark the ownership 1 for the bottom team, 2 for the above team

    public Beast(boolean available, int x, int y, int priority, int ownership) {
        this.available = available;
        this.x = x;
        this.y = y;
        this.priority = priority;
        this.ownership = ownership;
    }

    public boolean isAvalaible(){
        return available;
    }

    public void setAvalaible(Boolean available){
        this.available = available;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getValue(){
        return this.priority;
    }

    public int getOwnership() {
        return this.ownership;
    }


    public boolean isValid(Terrain terrain, int fromx, int fromy, int tox, int toy) {

        // No movement since from-coordinates == to-coordinates
        if (fromx == tox && fromy == toy) {
            System.out.println("No movement");
            return false;
        }


        // can only move 1 spot version y
        if((toy != (fromy + 1)  && tox == fromx)    &&  (toy != (fromy - 1)  && tox == fromx)){

            System.out.println("to-y is wrong");
            return false;
        }


        // can only move 1 spot version x
        if((tox != (fromx + 1) && toy == fromy)     &&  (tox != (fromx - 1) && toy == fromy)){
            System.out.println("to-x is wrong");
            return false;
        }


        if(tox != fromx){
            if(toy != fromy){
                System.out.println("Irregular movement detected");
                return false;
            }
        }

        if(toy != fromy){
            if(tox != fromx){
                System.out.println("Irregular movement detected");
                return  false;
            }
        }



        // The piece is out of the map
        if (tox < 0 || tox > 6 || fromx < 0 || fromx > 6 || toy < 0 || toy > 8 || fromy < 0 || fromy > 8) {
            System.out.println("Out of bounds");
            return false;
        }


        // from aroundriver to river isValid conditions for all beasts excluding rat/lion/tiger(need to override this condition for these beasts)

        if (fromy == 2 && fromx != 0 && fromx != 6 && fromx != 3) {
            if (toy == fromy + 1) {
                System.out.println("Cannot go to river");
                return false;
            }
        }

        if (fromy == 6 && fromx != 0 && fromx != 6 && fromx != 3) {
            if (toy == fromy - 1) {
                System.out.println("Cannot go to river");
                return false;
            }
        }


        if ((fromx == 0 || fromx == 3 || fromx == 6) && (fromy == 3 || fromy == 4 || fromy == 5)) {
            if (tox == fromx + 1 || tox == fromx - 1) {
                System.out.println("Cannot go to river");
                return false;
            }
        }

        // priorities. OVERRIDE THIS IN RAT
        if (terrain.positions[tox][toy].getBeast() != null) {
            if (terrain.positions[fromx][fromy].getBeast().getValue() < terrain.positions[tox][toy].getBeast().getValue()) {
                // if a piece moves to trap, its rank is priority to 0
                if ((tox == 2 && toy == 0) || (tox == 3 && toy == 1) || (tox == 4 && toy == 0)) {
                    return true;
                }
                if ((tox == 2 && toy == 8) || (tox == 3 && toy == 7) || (tox == 4 && toy == 8)) {
                    return true;
                }
                System.out.println("targetbeast has higher priority");
                return false;
            }
            if(terrain.positions[fromx][fromy].getBeast().getValue() == 8){
                if(terrain.positions[tox][toy].getBeast().getValue() == 1){
                    if ((tox == 2 && toy == 0) || (tox == 3 && toy == 1) || (tox == 4 && toy == 0)) {
                        return true;
                    }
                    if ((tox == 2 && toy == 8) || (tox == 3 && toy == 7) || (tox == 4 && toy == 8)) {
                        return true;
                    }
                    System.out.println("Elephant cant eat rat");
                    return false;
                }
            }
        }



        return true;
    }
}



// bikin isValid2() di Terrain (take into account existence of beast in to-coordinates)
        /*
      	Position p = terrain1.getSpot(tox, toy);

        Beast targetbeast = p.getbeast();

  			if (targetbeast.priority > this.priority) {
        	return false;
        }

				if (targetbeast.ownership == this.ownership) {
        	return false;
        }
        */
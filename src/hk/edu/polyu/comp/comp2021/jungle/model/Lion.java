package hk.edu.polyu.comp.comp2021.jungle.model;

public class Lion extends Beast {
    public Lion(boolean available, int x, int y, int priority, int ownership) {
        super(available, x, y, priority, ownership);
    }


    @Override
    public boolean isValid(Terrain terrain, int fromx, int fromy, int tox, int toy) {

        // [General Test] No movement since from-coordinates == to-coordinates
        if (fromx == tox && fromy == toy) {
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


        // [General Test] The piece is out of the map
        if (tox < 0 || tox > 6 || fromx < 0 || fromx > 6 || toy < 0 || toy > 8 || fromy < 0 || fromy > 8) {
            System.out.println("[RAT][General Test][Error] Out of map");
            return false;
        }



        /*
       FROM aroundriver TO river isValid conditions for Lion:
        1. Aroundriver lion cannot move to river if there is a beast in Leap Path
        */
        boolean upwardleapdetected = false;
        boolean downwardleapdetected = false;
        boolean leftwardleapdetected = false;
        boolean rightwardleapdetected = false;

        // FROM horizontal aroundriver (y = 2 block) TO river
        if (fromy == 2 && fromx != 0 && fromx != 6 && fromx != 3) {
            if (toy == fromy + 4) {
                upwardleapdetected = true;
                for(int i = (fromy+1); i <= (fromy+3); i++) {   // Leap Path
                    if (terrain.positions[fromx][i].getBeast() != null) {
                        System.out.println("Beast found at x = " + fromx + " y = " + i);
                        System.out.println("[LION][From aroundriver to river][UPWARD LEAP][ERROR] Cannot leap if there is a beast");
                        return false;
                    }
                }
            }
            if (toy == fromy + 1) {
                System.out.println("[LION][From aroundriver to river][ERROR] Lion must perform a leap accross river. Please adjust input.");
                return false;
            }
        }

        // FROM horizontal aroundriver (y = 6 block) TO river
        if (fromy == 6 && fromx != 0 && fromx != 6 && fromx != 3) {
            if (toy == fromy - 4) {
                downwardleapdetected = true;
                for(int i = (fromy-1); i >= (fromy-3); i--) {   // Leap Path
                    if (terrain.positions[fromx][i].getBeast() != null) {
                        System.out.println("[LION][From aroundriver to river][DOWNWARD LEAP][ERROR] Cannot leap if there is a beast");
                        return false;
                    }
                }
            }
            if( toy == fromy -1){
                System.out.println("[LION][From aroundriver to river][ERROR] Lion must perform a leap accross river. Please adjust input.");
                return false;
            }
        }

        // FROM vertical aroundriver TO river
        if ((fromx == 0 || fromx == 3 || fromx == 6) && (fromy == 3 || fromy == 4 || fromy == 5)) {
            if (tox == fromx + 3) {
                rightwardleapdetected = true;
                for(int i = (fromx+1); i <= fromx + 2; i++){
                    if (terrain.positions[i][fromy].getBeast() != null) {
                        System.out.println("[LION][From aroundriver to river][RIGHTWARD LEAP][ERROR] Cannot leap if there is a beast");
                        return false;
                    }
                }
            }
            if(tox == fromx - 3) {
                leftwardleapdetected = true;
                for(int i = (fromx-1); i >= fromx - 2; i--){
                    if (terrain.positions[i][fromy].getBeast() != null) {
                        System.out.println("[LION][From aroundriver to river][LEFTWARD LEAP][ERROR] Cannot leap if there is a beast");
                        return false;
                    }
                }
            }
            if (tox == fromx-1 || tox == fromx+1) {
                System.out.println("[LION][From aroundriver to river][ERROR] Lion must perform a leap across river. Please adjust input.");
                return false;
            }
        }




        // [PRIORITY TEST] OVERRIDE THIS IN RAT
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
        }

        if (upwardleapdetected || downwardleapdetected || rightwardleapdetected || leftwardleapdetected){
            System.out.println("[LION][SPECIAL SKILL] Leap completed.");
            return true;
        }
        // [General Test] can only move 1 spot version y
        if((toy != (fromy + 1)  && tox == fromx)    &&  (toy != (fromy - 1)  && tox == fromx)){

            System.out.println("[LION][General Test][Error] to-y is wrong");
            return false;
        }


        // [General Test] can only move 1 spot version x
        if((tox != (fromx + 1) && toy == fromy)     &&  (tox != (fromx - 1) && toy == fromy)){
            System.out.println("[LION][General Test][Error] to-x is wrong");
            return false;
        }


        return true;
    }
}


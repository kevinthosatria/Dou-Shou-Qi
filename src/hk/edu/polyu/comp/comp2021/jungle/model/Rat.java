package hk.edu.polyu.comp.comp2021.jungle.model;

public class Rat extends Beast{
    public Rat(boolean available, int x, int y, int priority, int ownership) {
        super(available, x, y, priority, ownership);
    }

    @Override
    public boolean isValid(Terrain terrain, int fromx, int fromy, int tox, int toy) {

        // [General Test] No movement since from-coordinates == to-coordinates
        if (fromx == tox && fromy == toy) {
            return false;
        }


        // [General Test] can only move 1 spot version y
        if((toy != (fromy + 1)  && tox == fromx)    &&  (toy != (fromy - 1)  && tox == fromx)){

            System.out.println("[RAT][General Test][Error] to-y is wrong");
            return false;
        }


        // [General Test] can only move 1 spot version x
        if((tox != (fromx + 1) && toy == fromy)     &&  (tox != (fromx - 1) && toy == fromy)){
            System.out.println("[RAT][General Test][Error] to-x is wrong");
            return false;
        }



        // [General Test] The piece is out of the map
        if (tox < 0 || tox > 6 || fromx < 0 || fromx > 6 || toy < 0 || toy > 8 || fromy < 0 || fromy > 8) {
            System.out.println("[RAT][General Test][Error] Out of map");
            return false;
        }






        /*
       FROM aroundriver TO river isValid conditions for Rat:
        1. Aroundriver rat cannot move to river if there is a beast(rat) in target position
        */

        // FROM horizontal aroundriver (y = 2 block) TO river
        if (fromy == 2 && fromx != 0 && fromx != 6 && fromx != 3) {
            if (toy == fromy + 1) {
                if (terrain.positions[tox][toy].getBeast() != null) {
                    System.out.println("[RAT][ERROR][From aroundriver TO river] Cannot eat beast from aroundriver to river");
                    return false;
                }
            }
        }

        // FROM horizontal aroundriver (y = 6 block) TO river
        if (fromy == 6 && fromx != 0 && fromx != 6 && fromx != 3) {
            if (toy == fromy - 1) {
                if (terrain.positions[tox][toy].getBeast() != null) {
                    System.out.println("[RAT][ERROR][From aroundriver TO river] Cannot eat beast from aroundriver to river");
                    return false;
                }
            }
        }

        // FROM vertical aroundriver TO river
        if ((fromx == 0 || fromx == 3 || fromx == 6) && (fromy == 3 || fromy == 4 || fromy == 5)) {
            if (tox == fromx + 1 || tox == fromx - 1) {
                if (terrain.positions[tox][toy].getBeast() != null) {
                    System.out.println("[RAT][ERROR][From aroundriver TO river] Cannot eat beast from aroundriver to river");
                    return false;
                }
            }
        }


        /*
        FROM river to aroundriver isValid conditions for Rat
        1. River rat cannot move to aroundriver if there is a beast(rat/elephant) in target position
         */

        // FROM river TO aroundriver
        if ((fromy == 3 || fromy == 4 || fromy == 5)    &&      (fromx == 1 || fromx == 2 || fromx == 4 || fromx == 5)){
            System.out.println("[RAT] From-coordinates is river");
            if (toy == 2 || toy == 6 || tox == 0 || tox == 3 || tox == 6) { // rmmbr that to-coordinates/from-coordinates relationship is already checked to be generally valid from [General Test]
                System.out.println("[RAT] To-coordinates are aroundriver");
                if (terrain.positions[tox][toy].getBeast() != null) {
                    System.out.println("[RAT][ERROR][From river TO aroundriver] Cannot eat beast from river to aroundriver");
                    return false;
                }
            }
        }






        // [Priority Test]. OVERRIDE THIS IN RAT
        if (terrain.positions[tox][toy].getBeast() != null) {
            if (terrain.positions[fromx][fromy].getBeast().getValue() < terrain.positions[tox][toy].getBeast().getValue()) {
                // if a piece moves to trap, its rank is priority to 0
                if ((tox == 2 && toy == 0) || (tox == 3 && toy == 1) || (tox == 4 && toy == 0)) {  // to-coordinates is trap
                    System.out.println("[RAT] Killed a targetbeast residing in trap");
                    return true;
                }
                if ((tox == 2 && toy == 8) || (tox == 3 && toy == 7) || (tox == 4 && toy == 8)) {   // to-coordinates is trap
                    System.out.println("[RAT] Killed a targetbeast residing in trap");
                    return true;
                }

                if (terrain.positions[tox][toy].getBeast().getValue() == 8) {   // targetbeast is elephant
                    System.out.println("[RAT][SPECIAL SKILL] Elephant targetbeast detected");
                    return true;
                }
                System.out.println("[RAT][Priority Test][ERROR] Cannot kill targetbeast");
                return false;
            }
        }


        return true;
    }
}

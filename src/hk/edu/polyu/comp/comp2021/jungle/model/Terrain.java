package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;

public class Terrain implements Serializable {
    public Position[][] positions;
    private boolean win;  // condition for winning

    public Terrain(){
        win = false;
        positions = new Position[7][9];

        for(int i = 0; i <= 6; i++){
            for(int j = 0; j <= 8; j++){
                positions[i][j] = new Position(i, j);
            }
        }

    }


    public void initialize(User u){
        // put the piece in the place
        for(int i = 0; i < u.getBeasts().size(); i++){

            positions[u.getBeasts().get(i).getX()][u.getBeasts().get(i).getY()].Occupy(u.getBeasts().get(i));
        }
    }


    public boolean execute(User u){     // execute a commamnd based on the parameter of which user



        Command cmd = u.getCommand();
        if(cmd.fromx < 0 || cmd.fromx > 6){
            System.out.println("From-coordinates out of bounds, and no beast");
            return false;
        }
        else if(cmd.fromy < 0 || cmd.fromy > 8){
            System.out.println("From-coordinates out of bounds, and no beast");
            return false;
        }
        Beast beast = positions[cmd.fromx][cmd.fromy].getBeast();


        if(beast == null){
            System.out.println("No beast detected at from-coordinates");
            printer();
            return false;
        }

        // if player tries to move a beast that is not his
        if(beast.getOwnership() != u.red ) {
            System.out.println("Move your own beast you beast!");
            printer();
            return false;
        }

        // check if the move is valid for the beast or not
        if (!beast.isValid(this, cmd.fromx, cmd.fromy, cmd.tox, cmd.toy)){
            System.out.println("thou hast moved wrongly");
            printer();
            return false;
        }

        // check for friendly fire & priorities
        if(positions[cmd.tox][cmd.toy].getBeast() != null) {
            if (positions[cmd.tox][cmd.toy] != null && positions[cmd.tox][cmd.toy].getBeast().getOwnership() == beast.getOwnership()) {
                System.out.println("No friendly fire allowed.");
                printer();
                return false;
            }
        }


        // change the position
        positions[cmd.tox][cmd.toy].Occupy(beast);
        positions[cmd.fromx][cmd.fromy].release();

        // check win
        if(beast.getOwnership() == 1 && cmd.tox == 3 && cmd.toy == 8){
            win = true;
        }
        else if(beast.getOwnership() == 2 && cmd.tox == 3 && cmd.toy == 0){
            win = true;
        }



        System.out.println("Turn completed");
        return true;
    }

    public void printer(){
       for (int j = 8; j >= 0; j-- ) { // y

           System.out.print("\n"+j);


            for (int i = 0; i <= 6; i++){   // x
                if(positions[i][j].getBeast() != null){
                    System.out.print(" " + positions[i][j].getBeast().getValue() + " ");
                }
                else if((i == 3 && j == 8) || (i == 3 && j == 0)){
                    System.out.print(" D ");
                }
                else if((i == 2 || i == 1 || i == 4 || i == 5 ) && (j > 2 && j < 6)){
                    System.out.print(" ~ ");
                }
                else if(((i == 2 || i == 4) && (j == 0 || j == 8)) || (i == 3 && (j == 1 || j == 7))){
                    System.out.print(" X ");
                }
                else{
                    System.out.print(" - ");
                }

            }
       }
       System.out.print("\n ");
       for(int i = 0; i < 7; i++){
           System.out.print(" " + i + " ");
       }

    }





    public boolean win(){
        return win;
    }

}

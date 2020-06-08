package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User implements Serializable {

    public int red;
    public String name;
    private List<Beast> beasts = new ArrayList<>();
    private Command cmd;


    public User(int red) {
        this.red = red;
        initializeBeasts();
        Scanner scanz = new Scanner(System.in);
        System.out.println("Enter player name: ");
        this.name = scanz.nextLine();
    }

    public List<Beast> getBeasts() {
        return beasts;
    }

    public void setCommand(Command cmd){
        this.cmd = cmd;
    }

    public Command getCommand(){
        return this.cmd;
    }
    public void initializeBeasts() {
        if (this.red == 1){ // bottom team, we call this red team 
            beasts.add(new Elephant(true, 0, 2, 8, 1) );
            beasts.add(new Lion(true, 6, 0, 7, 1));
            beasts.add(new Tiger(true, 0, 0, 6, 1));
            beasts.add(new Leopard(true, 4, 2, 5, 1));
            beasts.add(new Wolf(true, 2, 2, 4, 1));
            beasts.add(new Dog(true, 5, 1, 3, 1));
            beasts.add(new Cat(true, 1, 1, 2, 1));
            beasts.add(new Rat(true, 6, 2, 1, 1));
        }
        else{
            beasts.add(new Elephant(true, 6, 6, 8, 2));
            beasts.add(new Lion(true, 0, 8, 7, 2));
            beasts.add(new Tiger(true, 6, 8, 6, 2));
            beasts.add(new Leopard(true, 2, 6, 5, 2));
            beasts.add(new Wolf(true, 4, 6, 4, 2));
            beasts.add(new Dog(true, 1, 7, 3, 2));
            beasts.add(new Cat(true, 5, 7, 2, 2));
            beasts.add(new Rat(true, 0, 6, 1, 2));
        }
    }
}


//test

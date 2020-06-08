package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;

public class Command implements Serializable {

    int fromx, fromy, tox, toy;

    public Command(int fromx, int fromy, int tox, int toy){
        this.fromx = fromx;
        this.fromy = fromy;
        this.tox = tox;
        this.toy = toy;
    }
}
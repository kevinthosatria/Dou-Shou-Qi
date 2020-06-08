package hk.edu.polyu.comp.comp2021.jungle;

import hk.edu.polyu.comp.comp2021.jungle.model.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int menu = 99;

        while (menu != 0) {
            System.out.println("JUNGLEGAME MENU:");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("0. Exit");

            Scanner menuscanner = new Scanner(System.in);

            menu = menuscanner.nextInt();

            if (menu == 1) {
                JungleGame game = new JungleGame();

                game.gameStart();
            } else if (menu == 2) {
                JungleGame game = new JungleGame();
                menuscanner.nextLine();
                System.out.println("Input file path of file to be loaded: ");
                String filepath = menuscanner.nextLine();
                File fd = new File(filepath);
                game.loadGame(fd);

            } else if (menu == 0){
                break;
            } else {
                System.out.println("Invalid menu input");
            }

        }


    }


}

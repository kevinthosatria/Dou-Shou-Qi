package hk.edu.polyu.comp.comp2021.jungle.model;


import java.io.*;
import java.util.Scanner;

public class JungleGame implements Serializable {
    Terrain terrain;
    User p1;
    User p2;
    boolean newer = true;

    public JungleGame(){
        terrain = new Terrain();
    }

    public boolean GenerateUsers(User u){
        if(p1 == null){
            this.p1 = u;
        }
        else if(p2 == null){
            this.p2 = u;
        }
        else{
            return false;
        }
        terrain.initialize(u);
        return true;
    }
    public void saveGame(File file){
        try{
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(p1);
            objectStream.writeObject(p2);
            objectStream.writeObject(terrain);

            objectStream.close();
            fileStream.close();
        }
        catch(Exception e){
            System.out.println("Failed to save game data");
        }
    }

    public void loadGame(File file) throws IOException, ClassNotFoundException {

        FileInputStream fileStream = new FileInputStream(file);
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        p1 = (User) objectStream.readObject();
        p2 = (User) objectStream.readObject();
        terrain = (Terrain) objectStream.readObject();

        System.out.println("Load successful! ");
        newer = false;
        gameStart();
    }
    public void turns(User u){
        // Make a valid command
        boolean executable = false;

        while(!executable) { // if execute returns false, take new input and set it as new command for user u
            // input  = take input here w scanner
            Scanner input = new Scanner(System.in);
            System.out.println("\n Input the from x: ");
            int fromx = input.nextInt();
            System.out.println("\n Input the from y:");
            int fromy = input.nextInt();
            System.out.println("\n Input the destination x- coordinate:");
            int tox = input.nextInt();
            System.out.println("\n Input the destination y-coordinate");
            int toy = input.nextInt();
            Command cmd = new Command(fromx, fromy, tox, toy);
            u.setCommand(cmd);
            executable = terrain.execute(u);
            // execute method returns false if it doesn't pass isValid tests, it returns true after passing test and moving the pieces
        }

        // turn ends automatically if executable == true
    }

    public void gameStart(){
        // enter some name here with scanner
        if(newer) {
            GenerateUsers(new User(1));
            GenerateUsers(new User(2));
        }
        while (true){
            // check if p1 have any beast left
            int counter = 0;
            for(int i = 0; i < p1.getBeasts().size(); i++){
                if(!p1.getBeasts().get(i).isAvalaible()){
                    counter++;
                }
            }
            // all p1 beast are dead
            if(counter == 8){
                System.out.println("P2 "+ p2.name + " Wins!");
                break;
            }
            // reset counter
            counter = 0;


            terrain.printer();

            System.out.println("\nIt is now P1 " + p1.name + ", bottom side turn." );
            System.out.println(p1.name + " if you want to save the game, press 1"
                    +   "\n if you want to quit the game, press 2"
                    +   "\n if you want to continues, press anything");

            Scanner ingamemenuscanner = new Scanner(System.in);

            int ingamemenu = ingamemenuscanner.nextInt();

            if (ingamemenu == 1){
                System.out.println("Please type the file path for the save file, the file extension should be .ser");
                ingamemenuscanner.nextLine();
                String filepath = ingamemenuscanner.nextLine();
                File fd = new File(filepath);
                saveGame(fd);
            }

            if(ingamemenu == 2){
                System.exit(1);
            }

            turns(p1);
            if(this.terrain.win()){
                // print p1 win
                System.out.println("P1 "+ p1.name + " Wins!");
                break;
            }
            // check if p2 have any beast left
            for(int i = 0; i < p2.getBeasts().size(); i++){
                if(!p2.getBeasts().get(i).isAvalaible()){
                    counter++;
                }
            }
            // all p2 beast are dead
            if(counter == 8){
                System.out.println("P1 "+ p1.name + " Wins!");
                break;
            }


            terrain.printer();
            System.out.println("\nIt is now P2 " + p2.name + ", upper side turn." );
            System.out.println(p2.name + " if you want to save the game, press 1 \n"
                    +   "if you want to quit the game, press 2"
                    +   "if you want to continues, press anything");
            Scanner ingamemenuscanner2 = new Scanner(System.in);
            int ingamemenu2 = ingamemenuscanner2.nextInt();

            if (ingamemenu2 == 1){
                System.out.println("Please type the file path for the save file, the file extension should be .ser");
                ingamemenuscanner2.nextLine();
                String filepath = ingamemenuscanner2.nextLine();
                File fd = new File(filepath);
                saveGame(fd);
            }

            if(ingamemenu2 == 2){
                System.exit(1);
            }


            turns(p2);
            if(this.terrain.win()){
                // print p2 win
                System.out.println("P2 " + p2.name + " Wins!");
                break;
            }


        }

    }
}
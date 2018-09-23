//Call GRID
//Waves and Win Conditionz

import java.util.*;


class Game{
   private int turncounter;
   private boolean victory=true;
   private List<Person> personlist = new ArrayList<Person>();
   private List<Person> player2list = new ArrayList<Person>();
   private Person player1person1 = new Person();
	private Grid gd = new Grid();

   //This method runs a level of the game on the text file "levelnumb.txt"
   Game run(String levelnumb){
      //Grid gd = new Grid();
      Level lvl = new Level();
      Game gm = new Game();
      
      gm.initialiselvl();
      lvl.filetogrid(levelnumb, gd);
      gm.createPerson("Human_1", 0, 3, gd);
      gm.createPerson("Human_1", 0, 5, gd);
      gm.createPerson("Human_1", 0, 7, gd);
      gm.createPerson("Alien_1", 14, 3, gd);
      gm.createPerson("Alien_1", 14, 5, gd);
      gm.createPerson("Alien_1", 14, 7, gd);
      
      // Output to Console -- Testing purposes
      gd.passmapfromgrid();

      while(victory==false){
         //gm.turn();
         turncounter++;

         victory=true; //temporary - so it doesnt keep looping until there is a victory condition
      }
   return(gm);
	}

   //method to extract the grid from a specific game while also implementing the function "run()"
   int[][] playgame(Game newgame){
      int temp[][] = new int[10][15];
      newgame.run("resources/level1");
      temp=newgame.passgrid();
      return(temp);
   }

   //method to insert a Person into the player list. we don't need a method to delete heros or enemies 
   //as they can just stay in the array list with no health
   // need to set the type and the position (i and j)
   // then proceeds to insert the hero into the grid
   void createPerson(String newtype, int j, int i, Grid gd){
      Person pn = new Person();
      pn.settype(newtype);
      pn.startuptype();
      //pn.changeposition(i, j);
      personlist.add(pn);
      player1person1=pn;
      if(newtype=="Human_1"){
         gd.setpacell(i, j, 2);   //2 represents a friendly character on the grid
      }
      else if(newtype=="Alien_1"){
         gd.setpacell(i, j, 3);   //3 represents an enemy on the grid
      }
   }

   // method to retrieve a Persons's profile from the person list specified by their position - by copying it over and then
   // returning that object...testing mostly for stats or something
   Person retrieveprofile(int x, int y){
      Person pn = new Person();
      int[] position = new int[2];
      int n=0;
      return(pn);
   }

   //initialize a level: reset turn counter to 0 and victory to false
   void initialiselvl(){
      turncounter=1;
      victory=false;
   }

   //to get the turn-counter
   int gettc(){
      int temp=0;
      temp=turncounter;
      return(temp);
   }

   //A getter to take the grid and pass it from the grid class up the tree
   int[][] passgrid(){
      int temp[][] = new int[10][15];
      temp=gd.passmapfromgrid();
      return(temp);
   }
}

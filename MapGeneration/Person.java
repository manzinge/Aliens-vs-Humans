//Character profiles/stats -- Only thing implemented in Display is allegiance and position(which side youre on, humans or aliens) 

import java.util.*;
import java.io.*;
import java.lang.*;

class Person{
   private int allegiance; //1=neutral, 2=player1, 3=player2
   private String type;
   private int health; //if health == 0 then the person dies
   private int position[] = new int[2]; //unique at present
   private int attack;
   private int movement;
   
   //A setter to change the type of 
   void settype(String newtype){
      type=newtype;
      startuptype();
   }

   //a simple method to insert the 'stats' of a particular type of character
   void startuptype(){
      if(type.equals("Human_1")){
         allegiance = 2;
         health = 150;
         attack = 40;
         movement = 3;

      }
      else if(type.equals("HumanCaptain_2")){
         allegiance = 2;
         health = 100;
         attack = 30;
         movement = 4;

      }
      else if(type.equals("Alien_1")){
         allegiance = 3;
         health = 150;
         attack = 40;
         movement = 3;

      }
      else if(type.equals("AlienCaptain_2")){
         allegiance = 3;
         health = 100;
         attack = 30;
         movement = 4;

      }

   }
   
   //getters and setters for adapting and returning the stats related to characters: (not currently used in the game)
   void changehealth(int deltah){
      health=health-deltah;
   }

   void changeposition(int i, int j){
      position[0]=i;
      position[1]=j;
   }

   int getallegiance(){
      return(allegiance);
   }

   int[] getposition(){
      return(position);
   }
   
   //Does nothing
   int getattack(){
      return(attack);
   }
   
   //Does nothing
   int gethealth(){
      return(health);
   }
   
   int getmovement(){
      return(movement);
   }

}
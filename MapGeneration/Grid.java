//This class deals with the 'grid' (playarea) which is the 10x15 cell array in which our game happens. 
import java.util.*;
import java.io.*;
import java.lang.*;

class Grid{
	private int playarea[][] = new int[10][15];

   //initialize the playarea and set every cell to empty (0)
	void initializepa(){
      int i, j;
      for(i=0; i<10; i++){
         for(j=0; j<15; j++){
            playarea[i][j]=0;
         }
      }
	}

   //to return the value in a cell of the playarea
	int getpacell(int i, int j){
      int temp=0;
      temp=playarea[i][j];
      return(temp);
	}

   //to set the value in a cell of the playarea
   void setpacell(int i, int j, int temp){
      playarea[i][j]=temp;
   }

   //to pass an instance of the 2d array
   int[][] passmapfromgrid(){
      int i, j;
      for(i=0; i<10; i++){
         for(j=0; j<15; j++){
         }
      }
      return(playarea);
   }
   
}


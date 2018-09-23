//This class deals with calculating the possible movement of characters and inputting this result into the grid on which the game is played.
import java.util.*;
import java.io.*;
import java.lang.*;

class Movement{

    /*calcuateMovement calculates all possible paths of movement for a human sitting on 
     *grid square (i,j). There is a 4 step for loop to work through the N,S,E,W directions for 
     *possible movement. It checks if these are empty and if they are, marks them in the grid with a number 4. it then repeats
     *this process in a nested manner for the steps from this area. 
     * */
    int[][] calculateMovement(int i, int j, int grd[][]){
        int x=0, y=0, k=0, l=0, m=0, i2=0, j2=0, i3=0, j3=0, i4=0, j4=0, temp=0;
        for(k=0; k<4; k++){
        	i2=i+((k-2)%2);
            j2=j+((k-1)%2);
            if(bounds(i2, j2) && (grd[i2][j2]==0 || grd[i2][j2]==4)){
                grd[i2][j2]=4;
                for(l=0; l<4; l++){
                    i3=i2+((l-2)%2);
                    j3=j2+((l-1)%2);
                    if(bounds(i3, j3) && (grd[i3][j3]==0 || grd[i3][j3]==4)){
                		grd[i3][j3]=4;
                        for(m=0; m<4; m++){
                            i4=i3+((m-2)%2);
                            j4=j3+((m-1)%2);
                            if(bounds(i4, j4) && (grd[i4][j4]==0 || grd[i4][j4]==4)){
                				grd[i4][j4]=4;
                            }
                        }
                    }
                }
            }
        }
        return(grd);
    }

    //the following method, bounds(), takes an x-y coordinate input and checks to see if it is within the grid
    boolean bounds(int a, int b){
        if(a>=0 && a<10 && b>=0 && b<15){
            return(true);
        }
        else{
            return(false);
        }
    }
}

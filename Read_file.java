/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Mekaal Swerhun, 0664033
 */
public class Read_file {
    String input_file = null;
    BufferedReader Reader;

    public Read_file(String file_name){
        input_file = file_name;
    }
    
    public boolean open(){
        boolean good;
        try{//Try to read the file
            FileReader fileReader = new FileReader(input_file);//Create reader from input_file
            Reader = new BufferedReader(fileReader);   
            good = true;
        }catch(FileNotFoundException ex) { //Catch incase the file is missing
            System.out.println("Unable to open file '" + input_file + "'");  
            good = false;
        }
        return good;
    }
    
    public String file_read_line(int target){ //Should be 1 to go to next line
        String line = null;
        int i =0; 
        try{//Try to read the file
            while((line = Reader.readLine()) != null){//While there is a line to be read
                i++;
                //System.out.println(line);
                if(i == target){
                    //System.out.println("Found");
                    return line;
                }
            }//End of while           
        }
        catch(IOException ex) { //Catch incase the file cant be read
             System.out.println("Error reading file '"  + input_file + "'");                
        }
        System.out.println("EOF");
        return "EOF";//Default output 
    }

    public boolean close(){
        boolean closed = false;
        try{//Try to see the file
            Reader.close(); //closes. 
            closed = true;
        }catch(IOException ex) { //Catch incase the file cant be read
             System.out.println("Error with file '"  + input_file + "'");                
        }
        return closed;
    }    
}

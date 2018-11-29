import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author mekaal
 */

public class Write_file {
    String output_file = null;
    
    public Write_file(String file_name){
        output_file = file_name;
    }
    
    public void Enter(String name, int new_score) throws IOException{
        if(name.isEmpty()==false){ //Makes sure a name was inputted
            
            //Replace spaces with underscores
            if(name.contains(" ") == true){
                name = name.replace(" ", "_");
            }
            //If Longer than 26 characters trim it
            if(name.length() >26)
                name = name.substring(0, 26);
            //Double check for spaces
            if(name.contains(" ") == false){
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(output_file, true)));//Opens file to append to
                writer.println(name + " " + new_score);//Write the name inputted and then their score
                writer.close();
            }else{
                 System.out.println("No spaces allowed use '_' instead!");
            }
        }else{
            System.out.println("Name not printed!");             
        }   
    }
}

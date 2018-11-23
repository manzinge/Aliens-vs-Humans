

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author mekaal
 */
public class ScoreBoard {
    Stage window; //primary window
    Scene Scoreboard_scene; //scenes
    int score = 0;//Players current score (Have to figure out how to pass the score from another class while starting this one)
    String score_file = "Scores_List.txt";//File containing score data
    boolean Testing = true;
    
    public void set_score(int score){
        this.score = score;
   
    }
    
   // @Override
    public void start(Stage primaryStage){
        
        final int scene_height = 360;
        final int scene_width = 640;
        final int left_margin = 25;
        final int scores_shown = 10; //How many scores to display in the highscores
        String last_score = "0";
        Pane Scoreboard_Pane = new Pane();
     
        //Code for testing score
        if(Testing == true){
            Random score_test = new Random();    
            score = score_test.nextInt(100)+1;
        }
        //Buttons
        Button enter_btn = new Button();
            enter_btn.setText("Enter");
            enter_btn.setPrefSize(100, 20); //Size of button (X,Y)
            enter_btn.setLayoutX(scene_width -120); //X Position
            enter_btn.setLayoutY(scene_height -75); //Y Position
            
        //exit button
        Button exit_btn = new Button();
            exit_btn.setText("Exit");
            exit_btn.setPrefSize(100, 20); //Size of button (X,Y)
            exit_btn.setLayoutX(scene_width -120); //X Position
            exit_btn.setLayoutY(scene_height -50); //Y Position
            exit_btn.setOnAction(e -> System.exit(0)); //Exit program when clicked
        
        //Input 
        TextField input_field = new TextField();
            input_field.setLayoutX(left_margin);
            input_field.setLayoutY(scene_height -75);
            input_field.setPrefSize(scene_width -175, 20);//scene_width -175 to go up to the enter button
        
        //Display
        TextArea display_area = new TextArea();
            display_area.setLayoutX(left_margin);
            display_area.setLayoutY(50);
            display_area.setPrefSize(scene_width-50, 225);
            display_area.setEditable(false); //Used to display so shouldn't edit scores
           
            for(int i = 0; i<scores_shown; i++){//Write a new high score from the file 
                //last_score=display_area.getText();
                display_area.appendText(i+1 + ". ");    
                //display_area.appendText(Read(i));//By input order
                
                //Find first occurance of :
                int s = display_area.getText().indexOf(":");
                if(s==-1)//If none set to 0
                    s=0;
                
                //Find first occurence of new line
                int new_line = display_area.getText().indexOf("\n");
                if(new_line==-1) //if none set to 1
                    new_line=1;
  
               //Display scores by input order
               display_area.appendText(Read(i));//By input order
         
         
               /*
                last_score = display_area.getText().substring(s+1, new_line); //Get string between : and newline
                 System.out.println( display_area.getText().substring(s+1, new_line) + " end");//Debugging
                if(last_score.matches(".*\\d+.*")){//If it contains a number (regex)
                    display_area.appendText(Read(Integer.parseInt(last_score)));//By Score value
                    System.out.println("last " +last_score);//Debugging
                }else{
                    last_score = "0";//Else first to enter so last score is 0
                    display_area.appendText(Read(Integer.parseInt(last_score)));//By Score value 
                }
               */
                display_area.appendText("\n");
            }
           
            
        //Labels
        Label lbl_title = new Label("High Scores");
            lbl_title.setLayoutX(scene_width/3 +10);//Tries to get it centered taking the fact that /2 would put the first letter at the center into account. 
            lbl_title.setLayoutY(10);
            lbl_title.setFont(new Font("Arial", 30));//Change Font to desired one 
            lbl_title.setTextFill(Color.BROWN);//Looks more like red to me but also change to deseried one
 
        Label lbl_score_label = new Label();
            lbl_score_label.setLayoutX(left_margin);
            lbl_score_label.setLayoutY(scene_height-50);//Inline with exit button
            lbl_score_label.setText("Score: " + score);
            
        //Button Actions
        //Enter Button
        enter_btn.setOnAction(e -> Enter(input_field.getText(), score));
         
        
        //Add all components to pane
        Scoreboard_Pane.getChildren().addAll(enter_btn, exit_btn, input_field, display_area, lbl_title, 
                                                lbl_score_label);
        //create scene
        Scoreboard_scene = new Scene(Scoreboard_Pane, scene_width, scene_height); 
     
        
        //Display scoreboard in window
        primaryStage.setTitle("Project: Aliens");
        primaryStage.setScene(Scoreboard_scene);
        primaryStage.show();

     }
     
    //Will read only one line fomr a file
    /*###Should try and find a more optimized way as currently it reads through all the file lines
    *   before it reachs the newest one and returning it. (Couldn't directly append to the textArea since the method is out of its scope) 
    */
    //changed line_num to score
    //Will need to change the statement to look for the highest score value and place that... If not should simplify and optimize it
    public String Read(int last_score){
        int current_score=0; //Current value to compare to
      
        String line = null;
        try{//Try to read the file
            FileReader fileReader = new FileReader(score_file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null){//While there is a line to be read
                if(last_score == current_score){ //If the line being read is the line needed (Will have to change to to return the max score that is less than last_score)
                    bufferedReader.close(); //closes file. 
                    return line;   //Returns the line
                }else{
                    //current_score = 2;
                }
                current_score++;
            }//End of while           
        }
        catch(FileNotFoundException ex) { //Catch incase the file is missing
            System.out.println("Unable to open file '" + score_file + "'");                    
        }
        catch(IOException ex) { //Catch incase the file cant be read
             System.out.println("Error reading file '"  + score_file + "'");                
        }
    
        return "No Score at this point";//Default output 
    }
    
    //Appends to the file
    public void Enter(String name, int score){
        if(name.isEmpty()==false){ //Makes sure a name was inputted
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(score_file, true)));//Opens file to append to
                writer.println(name + " : " + score);//Write the name inputted and then their score
                writer.close(); 
            } catch (IOException e) {
                System.out.println("Error with file '"  + score_file + "'"); 
            }
        }else{
            System.out.println("Name not printed!");
            
        }
        
    }
    
    
         /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        launch(args);
    }
    */

}

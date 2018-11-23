

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 *
 * @author mekaal
 */
public class ScoreBoard {
    Stage window; //primary window
    Scene Scoreboard_scene; //scenes
    //Size of window
    final double scene_height = main_menu.screenSize.height*0.7;
    final double scene_width = main_menu.screenSize.width*0.4;
    Image back_ground = new Image("file:starry_background.png");
    
    
    Button enter_btn;
    Label[] lbl_score_list = new Label[10]; //How many score labels
    Label lbl_info = new Label();
    
    int new_score = 0;//Players current score (Have to figure out how to pass the score from another class while starting this one)
    String score_file = "Scores_List.txt";//File containing score data
    boolean Testing = true;

    
    public void set_score(int new_score){
        this.new_score = new_score;
    }
    
   // @Override
    public void start(Stage primaryStage){
        ImageView scores_background = new ImageView(back_ground);
        //final int scene_height = 600;
        //final int scene_width = 600;
        final int left_margin = 25;

        Pane Scoreboard_Pane = new Pane();
     
        //Code for testing score
        if(Testing == true){
            Random score_test = new Random();    
            new_score = score_test.nextInt(100)+1;
        }
        //Buttons
        enter_btn = new Button();
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
            exit_btn.setOnAction(e -> window.setScene(main_menu.mainMenuScene)); //Exit program when clicked
        //Input 
        TextField input_field = new TextField();
            input_field.setLayoutX(left_margin);
            input_field.setLayoutY(scene_height -75);
            input_field.setPrefSize(scene_width -175, 20);//scene_width -175 to go up to the enter button
    

        
        //Set up initial score labels
        for(int i = 0; i <lbl_score_list.length; i++){
            Label lbl_score = new Label();
            lbl_score.setLayoutX(scene_width/3 +40);
            lbl_score.setLayoutY(100+i*scene_height/30);
            lbl_score.setMinWidth(scene_width);
            //lbl_score.setAlignment(Pos.CENTER);
            lbl_score.setTextFill(Color.RED);
            lbl_score.setFont(Font.font("Arial", FontWeight.BOLD, scene_height/40));//Change Font to desired one 
            lbl_score.setText((i+1) + ". No Score");
            lbl_score_list[i] = lbl_score;
        }
        
        //Display the scores
        Display();
        
        //Labels
        Label lbl_title = new Label("High Scores!");
            //lbl_title.setLayoutX(scene_width/3 +10);//Tries to get it centered taking the fact that /2 would put the first letter at the center into account. 
            lbl_title.setLayoutY(10);
            lbl_title.setMinWidth(scene_width);
            lbl_title.setAlignment(Pos.CENTER);
            lbl_title.setFont(new Font("Arial", 30));//Change Font to desired one 
            lbl_title.setTextFill(Color.GREEN);//Looks more like red to me but also change to deseried one
        Label lbl_score_label = new Label();
            lbl_score_label.setLayoutX(left_margin);
            lbl_score_label.setLayoutY(scene_height-50);//Inline with exit button
            lbl_score_label.setText("Score: " + new_score);
            lbl_score_label.setTextFill(Color.WHITE);
        
        //###Label info###
            //lbl_info.setLayoutX(scene_width/4-10);
            lbl_info.setLayoutY(50);
            lbl_info.setMinWidth(scene_width);
            lbl_info.setAlignment(Pos.CENTER);
            lbl_info.setTextFill(Color.WHITE);
            lbl_info.setFont(Font.font("Arial", FontWeight.BOLD, scene_height/60));//Change Font to desired one 
            lbl_info.setText("Please enter your name below if you're registering a high score.");
            
        //Button Actions
        //Enter Button
        enter_btn.setOnAction(e -> Enter(input_field.getText(), new_score));
         
        
        //Add all components to pane
        Scoreboard_Pane.getChildren().addAll(scores_background, enter_btn, exit_btn, input_field, lbl_title, 
                                                lbl_score_label,lbl_info);
        for(int i = 0; i <lbl_score_list.length; i++) //Add scores
            Scoreboard_Pane.getChildren().add(lbl_score_list[i]);
        
        //create scene
        Scoreboard_scene = new Scene(Scoreboard_Pane, scene_width, scene_height); 
     
        
        //Display scoreboard in window
        primaryStage.setTitle("Project: Aliens");
        primaryStage.setScene(Scoreboard_scene);
        primaryStage.show();

     }
     
    public void Display(){
        int score = 0;
        int last_score =0;
        int[] score_order = new int[11]; //Order to display score        
        String[] name_order = new String[11];//Order to display name
        String[] split; //Splitting of line
        String line = null; //Line being read
        String name = null;
        String last_name = null;
        
        //Read data
        Read_file read = new Read_file(score_file); 
        read.open();//Open buffered reader

        while(true){
            line = read.file_read_line(1);//Read next line
            if(line == "EOF")//Break out of loop if end of file is reached
                 break;
            if(Testing == true){System.out.println(line);} //If testing output read line to console
            
            split = line.split(" ");//Split at a space
            score = Integer.parseInt(split[1]); 
            name = split[0];
            //Sort Data
            for(int i = 0; i < lbl_score_list.length; i++){
                if(score > score_order[i]){
                       last_score = score_order[i];
                       last_name = name_order[i];
                       score_order[i]=score;
                       name_order[i]=name;
                       name=last_name;
                       score=last_score;
                }    
            }
        }  
        read.close();//Close the reader
        
        //Display data on labels
        for(int i = 0; i<lbl_score_list.length; i++){
            if(name_order[i] == null){
                lbl_score_list[i].setText(i+". No score at this position!");
            }else{
                lbl_score_list[i].setText(i+". " + name_order[i] + " has " + score_order[i]);
            }
        }        
        
        
    }
    
    
    //Appends to the file
    public void Enter(String name, int new_score){
        if(name.isEmpty()==false){ //Makes sure a name was inputted
            if(name.contains(" ") == false){
                try {
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(score_file, true)));//Opens file to append to
                    writer.println(name + " " + new_score);//Write the name inputted and then their score
                    writer.close(); 
                    enter_btn.setDisable(true);
                    Display();
                } catch (IOException e) {
                    System.out.println("Error with file '"  + score_file + "'"); 
                }
            }else{
                 System.out.println("No spaces allowed use '_' instead!");
                 lbl_info.setText("No spaces allowed use '_' instead!");
            }
        }else{
            System.out.println("Name not printed!");   
            lbl_info.setText("Name not printed!");
        }
        
    }
    

}

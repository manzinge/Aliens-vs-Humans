import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 * @author Ryan
 */
public class main_menu extends Application{
    Stage window; //primary window
    Scene mainMenuScene, settingsScene, scoreboardScene; //scenes
    
    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        
        //MAIN MENU SCENE
        Pane mainMenuPane = new Pane();
        //game title splash here??
        
        //end of game title splash
        //start button
        Button start_btn = new Button();
        start_btn.setText("Start Game");
        start_btn.setLayoutX(280); //hard-coded X-value to change
        start_btn.setLayoutY(250); //hard-coded Y-value to change
        start_btn.setOnAction(e -> window.setScene(settingsScene));
        //end of start button
        //exit button
        Button exit_btn = new Button();
        exit_btn.setText("Exit");
        exit_btn.setLayoutX(300); //hard-coded X-value to change
        exit_btn.setLayoutY(280); //hard-coded Y-value to change
        exit_btn.setOnAction(e -> System.exit(0));
        //end of exit button
        //scoreboard button
        Button score_btn = new Button("Scoreboard");
        score_btn.setLayoutX(280);
        score_btn.setLayoutY(220);
        score_btn.setOnAction(e -> 
        		window.setScene(scoreboardScene)
        		//Application.launch(ScoreBoard.class)
        );
        //end of scoreboard button
        mainMenuPane.getChildren().addAll(start_btn, exit_btn, score_btn);
        mainMenuScene = new Scene(mainMenuPane, 600, 600); //create scene
        //END OF MAIN MENU SCENE
        
        //SETTINGS SCENE
        Pane settingsPane = new Pane();
        //back button
        Button back_button = new Button("Back");
        back_button.setLayoutX(120);
        back_button.setLayoutY(280);
        back_button.setOnAction(e -> window.setScene(mainMenuScene));
        settingsPane.getChildren().add(back_button);
        //end of back button
        //game map
        Label mapLabel = new Label("Select Map:");
        mapLabel.setLayoutX(20);
        mapLabel.setLayoutY(20);
        Rectangle gameMapPreview = new Rectangle(260,10,340,210);
        gameMapPreview.setFill(Color.WHITE);
        gameMapPreview.setStroke(Color.BLACK);
        Label gameMapPreviewLabel = new Label("Map Preview");
        gameMapPreviewLabel.setLayoutX(400);
        gameMapPreviewLabel.setLayoutY(220);
        //map radio buttons
        final ToggleGroup mapGroup = new ToggleGroup();
        //map 1 button
        RadioButton mapRadio1 = new RadioButton("Map 1");
        mapRadio1.setToggleGroup(mapGroup);
        mapRadio1.setUserData(1);
        mapRadio1.setLayoutX(50);
        mapRadio1.setLayoutY(40);
        mapRadio1.setSelected(true);
        //map 2 button
        RadioButton mapRadio2 = new RadioButton("Map 2");
        mapRadio2.setToggleGroup(mapGroup);
        mapRadio2.setUserData(2);
        mapRadio2.setLayoutX(50);
        mapRadio2.setLayoutY(60);
        //map 3 button
        RadioButton mapRadio3 = new RadioButton("Map 3");
        mapRadio3.setToggleGroup(mapGroup);
        mapRadio3.setUserData(3);
        mapRadio3.setLayoutX(50);
        mapRadio3.setLayoutY(80);
        //set map preview image with radio buttons
        //map preview images
        Image map1Image = new Image("file:map1.png");
        Image map2Image = new Image("file:map2.jpg");
        Image map3Image = new Image("file:map3.jpg");
        //image patterns to fill rectangle with
        ImagePattern map1Pattern = new ImagePattern(map1Image);
        ImagePattern map2Pattern = new ImagePattern(map2Image);
        ImagePattern map3Pattern = new ImagePattern(map3Image);
        //set preview with radio buttons
        gameMapPreview.setFill(map1Pattern);
        mapRadio1.setOnAction(e ->
        	gameMapPreview.setFill(map1Pattern)
        );
        mapRadio2.setOnAction(e ->
        	gameMapPreview.setFill(map2Pattern)
		);
        mapRadio3.setOnAction(e ->
    		gameMapPreview.setFill(map3Pattern)
		);
        //end of map preview images
        //end of map radio buttons
        settingsPane.getChildren().addAll(mapRadio1, mapRadio2, mapRadio3, mapLabel, gameMapPreview, gameMapPreviewLabel);
        //end of game map
        //game wave
        Label gameWaveLabel = new Label("Number of Waves:");
        gameWaveLabel.setLayoutX(20);
        gameWaveLabel.setLayoutY(120);
        ComboBox<String> gameWaveChoice = new ComboBox();
        gameWaveChoice.getItems().addAll("1", "2", "3", "4", "5");
        gameWaveChoice.setPromptText("Default: 2");
        gameWaveChoice.setValue("Default: 2");
        gameWaveChoice.setLayoutX(50);
        gameWaveChoice.setLayoutY(140);
        settingsPane.getChildren().addAll(gameWaveLabel, gameWaveChoice);
        //end of game wave
        //starting humans
        Label startingHumansLabel = new Label("Number of starting Humans:");
        startingHumansLabel.setLayoutX(20);
        startingHumansLabel.setLayoutY(170);
        ComboBox<String> startingHumansChoice = new ComboBox();
        startingHumansChoice.getItems().addAll("1", "2", "3", "4", "5");
        startingHumansChoice.setPromptText("Default: 4");
        startingHumansChoice.setValue("Default: 4");
        startingHumansChoice.setLayoutX(50);
        startingHumansChoice.setLayoutY(190);
        settingsPane.getChildren().addAll(startingHumansLabel, startingHumansChoice);
        //end of starting humans
        //starting aliens
        Label startingAliensLabel = new Label("Number of starting Aliens:");
        startingAliensLabel.setLayoutX(20);
        startingAliensLabel.setLayoutY(220);
        ComboBox<String> startingAliensChoice = new ComboBox();
        startingAliensChoice.getItems().addAll("1", "2", "3", "4", "5");
        startingAliensChoice.setPromptText("Default: 3");
        startingAliensChoice.setValue("Default: 3");
        startingAliensChoice.setLayoutX(50);
        startingAliensChoice.setLayoutY(240);
        settingsPane.getChildren().addAll(startingAliensLabel, startingAliensChoice);
        //end of starting aliens
        //save settings
        //settings object to save settings
        GameSettings gameSettings = new GameSettings();
        //save settings button
        Button saveSettingsButton = new Button("Save and Start!");
        saveSettingsButton.setLayoutX(20);
        saveSettingsButton.setLayoutY(280);
        //error labels for no entry
        Label gameWaveErrorLabel = new Label("Please enter a game wave amount.");
        gameWaveErrorLabel.setTextFill(Color.RED);
        gameWaveErrorLabel.setLayoutX(200);
        gameWaveErrorLabel.setLayoutY(260);
        gameWaveErrorLabel.setVisible(false);
        Label startingHumansErrorLabel = new Label("Please enter a starting amount of humans.");
        startingHumansErrorLabel.setTextFill(Color.RED);
        startingHumansErrorLabel.setLayoutX(200);
        startingHumansErrorLabel.setLayoutY(280);
        startingHumansErrorLabel.setVisible(false);
        Label startingAliensErrorLabel = new Label("Please enter a starting amount of aliens.");
        startingAliensErrorLabel.setTextFill(Color.RED);
        startingAliensErrorLabel.setLayoutX(200);
        startingAliensErrorLabel.setLayoutY(300);
        startingAliensErrorLabel.setVisible(false);
        //save button action
        saveSettingsButton.setOnAction(e -> {
            int emptySettings = 0;
            //save map
            if(mapGroup.getSelectedToggle() != null){
                if(mapGroup.getSelectedToggle() == mapRadio1){
                    //set map to map 1
                    gameSettings.gameMapID = 1;
                    System.out.println("Game Map Selected: " + gameSettings.gameMapID);
                }else if(mapGroup.getSelectedToggle() == mapRadio2){
                    //set map to map 2
                    gameSettings.gameMapID = 2;
                    System.out.println("Game Map Selected: " + gameSettings.gameMapID);
                }else if(mapGroup.getSelectedToggle() == mapRadio3){
                    //set map to map 3
                    gameSettings.gameMapID = 3;
                    System.out.println("Game Map Selected: " + gameSettings.gameMapID);
                }else{
                    //no radio button selected somehow
                    //shouldn't have to worry about this
                    System.out.println("Somehow selected no radio button, error?");
                    emptySettings++;
                }
            }
            
            //save number of game waves
            if(gameWaveChoice.getValue().toString().equals("Default: 2")) {
            	gameSettings.numGameWaves = 2; //default value
            }else {
                gameSettings.numGameWaves = Integer.parseInt(gameWaveChoice.getValue().toString()); //input value
            }
            System.out.println("Waves: " + gameSettings.numGameWaves);
            

            //save starting number of humans
            if(startingHumansChoice.getValue().toString().equals("Default: 4")) {
            	gameSettings.numStartingHumans = 4; //default value
            }else {
            	gameSettings.numStartingHumans = Integer.parseInt(startingHumansChoice.getValue().toString()); //input value
            }
            System.out.println("Starting Humans: " + gameSettings.numStartingHumans);
           

            //save starting number of aliens
            if(startingAliensChoice.getValue().toString().equals("Default: 3")) {
            	gameSettings.numStartingAliens = 3;
            }else {
            	gameSettings.numStartingAliens = Integer.parseInt(startingAliensChoice.getValue().toString());
            }
            System.out.println("Starting Aliens: " + gameSettings.numStartingAliens);
            
            if(emptySettings > 0){
                //if there are empty settings, do not allow game to start
                System.out.println("There are empty settings; game cannot start");
            }else{
                //START GAME FROM HERE WITH SETTINGS GIVEN ABOVE
                System.out.println("All settings are filled; starting game...");
                
               try{
                TestBoard game = new TestBoard(gameSettings.numStartingAliens, gameSettings.numStartingHumans, gameSettings.gameMapID);
               }catch(IOException ex) {
            	   ex.printStackTrace();
               }
            }
        });
        //end of save settings button
        settingsPane.getChildren().addAll(saveSettingsButton, gameWaveErrorLabel, startingHumansErrorLabel, startingAliensErrorLabel);
        //end of save settings
        settingsScene = new Scene(settingsPane, 600, 600); //create scene
        //END OF SETTINGS SCENE
        
        //SCOREBOARD SCENE
        Pane scoreboardPane = new Pane();
        //back button
        Button backFromScoresButton = new Button("Back");
        backFromScoresButton.setLayoutX(5);
        backFromScoresButton.setLayoutY(290);
        backFromScoresButton.setOnAction(e -> window.setScene(mainMenuScene));
        //end of back button
        
        scoreboardPane.getChildren().addAll(backFromScoresButton);
        scoreboardScene = new Scene(scoreboardPane, 600, 600);
        //END OF SCOREBOARD SCENE
        
        //display
        window.setTitle("Project: Aliens");
        window.setScene(mainMenuScene);
        window.setResizable(false);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void showScoreboard() {
    	Application.launch(ScoreBoard.class);
    }
    
   
}
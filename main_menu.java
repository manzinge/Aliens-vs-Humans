import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

/**
 *
 * @author Ryan
 */
public class main_menu extends Application{
    Stage window; //primary window
    Scene mainMenuScene, settingsScene, scoreboardScene; //scenes
    
    //scene sizes
    int sceneWidth = 600;
    int sceneHeight = 600;
    
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double height = screenSize.height*0.7;
	public static double width = screenSize.width*0.4;
    
    Image backgroundImage = new Image("file:starry_background.png");
    
    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        //MAIN MENU SCENE
        Pane mainMenuPane = new Pane();
        //game title splash here
        Image gameTitleImage = new Image("file:aliens_title.png");
        ImagePattern gameTitlePattern = new ImagePattern(gameTitleImage);
        Rectangle gameTitleSplash = new Rectangle(width/4,height/14,width/2,height/4);
        gameTitleSplash.setFill(gameTitlePattern);
        //end of game title splash
        //start button
        Button start_btn = new Button();
        start_btn.setPrefSize(width/6, height/14);
        start_btn.setText("Start Game");
        start_btn.setLayoutX(width/2.3); //hard-coded X-value to change
        start_btn.setLayoutY(height/2.5); //hard-coded Y-value to change
        start_btn.setOnAction(e -> window.setScene(settingsScene));
        //end of start button
        //exit button
        Button exit_btn = new Button();
        exit_btn.setPrefSize(width/6, height/14);
        exit_btn.setText("Exit");
        exit_btn.setLayoutX(width/2.3); //hard-coded X-value to change
        exit_btn.setLayoutY(height/1.8); //hard-coded Y-value to change
        exit_btn.setOnAction(e -> System.exit(0));
        //end of exit button
        //scoreboard button
        Button score_btn = new Button("Scoreboard");
        score_btn.setPrefSize(width/6, height/14);
        score_btn.setLayoutX(width/2.3);
        score_btn.setLayoutY(height/2.1);
        score_btn.setOnAction(e -> 
        		window.setScene(scoreboardScene)
        		//Application.launch(ScoreBoard.class)
        );
        //end of scoreboard button
        //scene background
        ImageView mainMenuBG = new ImageView(backgroundImage);
        //end of scene background
        mainMenuPane.getChildren().addAll(mainMenuBG, start_btn, exit_btn, score_btn, gameTitleSplash);
        mainMenuScene = new Scene(mainMenuPane, width, height); //create scene
        //END OF MAIN MENU SCENE
        
        //SETTINGS SCENE
        Pane settingsPane = new Pane();
        //back button
        Button back_button = new Button("Back");
        back_button.setLayoutX(width/60);
        back_button.setLayoutY(height/60);
        back_button.setOnAction(e -> window.setScene(mainMenuScene));
        //end of back button
        //game map
        Label mapLabel = new Label("Select Map:");
        mapLabel.setLayoutX(width/60);
        mapLabel.setLayoutY(height/14);
        mapLabel.setTextFill(Color.WHITE);
        Rectangle gameMapPreview = new Rectangle(width/3,10,width/2,height/3);
        gameMapPreview.setFill(Color.WHITE);
        gameMapPreview.setStroke(Color.BLACK);
        Label gameMapPreviewLabel = new Label("Map Preview");
        gameMapPreviewLabel.setLayoutX(width/1.85);
        gameMapPreviewLabel.setLayoutY(height/2.8);
        gameMapPreviewLabel.setTextFill(Color.WHITE);
        //map radio buttons
        final ToggleGroup mapGroup = new ToggleGroup();
        //map 1 button
        RadioButton mapRadio1 = new RadioButton("Map 1");
        mapRadio1.setToggleGroup(mapGroup);
        mapRadio1.setUserData(1);
        mapRadio1.setLayoutX(width/30);
        mapRadio1.setLayoutY(height/10);
        mapRadio1.setSelected(true);
        mapRadio1.setTextFill(Color.WHITE);
        //map 2 button
        RadioButton mapRadio2 = new RadioButton("Map 2");
        mapRadio2.setToggleGroup(mapGroup);
        mapRadio2.setUserData(2);
        mapRadio2.setLayoutX(width/30);
        mapRadio2.setLayoutY(height/7);
        mapRadio2.setTextFill(Color.WHITE);
        //map 3 button
        RadioButton mapRadio3 = new RadioButton("Map 3");
        mapRadio3.setToggleGroup(mapGroup);
        mapRadio3.setUserData(3);
        mapRadio3.setLayoutX(width/30);
        mapRadio3.setLayoutY(height/5.4);
        mapRadio3.setTextFill(Color.WHITE);
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
        //end of game map
        //game wave
        Label gameWaveLabel = new Label("Number of Waves:");
        gameWaveLabel.setLayoutX(width/60);
        gameWaveLabel.setLayoutY(height/4);
        gameWaveLabel.setTextFill(Color.WHITE);
        ComboBox<String> gameWaveChoice = new ComboBox();
        gameWaveChoice.getItems().addAll("1", "2", "3", "4", "5");
        gameWaveChoice.setPromptText("Default: 2");
        gameWaveChoice.setValue("Default: 2");
        gameWaveChoice.setLayoutX(width/45);
        gameWaveChoice.setLayoutY(height/3.6);
        //end of game wave
        //starting humans
        Label startingHumansLabel = new Label("Number of starting Humans:");
        startingHumansLabel.setLayoutX(width/60);
        startingHumansLabel.setLayoutY(height/3);
        startingHumansLabel.setTextFill(Color.WHITE);
        ComboBox<String> startingHumansChoice = new ComboBox();
        startingHumansChoice.getItems().addAll("1", "2", "3", "4", "5");
        startingHumansChoice.setPromptText("Default: 4");
        startingHumansChoice.setValue("Default: 4");
        startingHumansChoice.setLayoutX(width/45);
        startingHumansChoice.setLayoutY(height/2.75);
        //end of starting humans
        //starting aliens
        Label startingAliensLabel = new Label("Number of starting Aliens:");
        startingAliensLabel.setLayoutX(width/60);
        startingAliensLabel.setLayoutY(height/2.4);
        startingAliensLabel.setTextFill(Color.WHITE);
        ComboBox<String> startingAliensChoice = new ComboBox();
        startingAliensChoice.getItems().addAll("1", "2", "3", "4", "5");
        startingAliensChoice.setPromptText("Default: 3");
        startingAliensChoice.setValue("Default: 3");
        startingAliensChoice.setLayoutX(width/45);
        startingAliensChoice.setLayoutY(height/2.25);
        //end of starting aliens
        //save settings
        //settings object to save settings
        GameSettings gameSettings = new GameSettings();
        //save settings button
        Button saveSettingsButton = new Button("Save and Start!");
        saveSettingsButton.setLayoutX(width/45);
        saveSettingsButton.setLayoutY(height/1.7);
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
            	window.close();
                TestBoard game = new TestBoard(gameSettings.numStartingAliens, gameSettings.numStartingHumans, gameSettings.gameMapID);
               }catch(IOException ex) {
            	   ex.printStackTrace();
               }
            }
        });
        //end of save settings button
        //scene background
        ImageView settingsBG = new ImageView(backgroundImage);
        //end of scene background
        settingsPane.getChildren().addAll(settingsBG, startingHumansLabel, startingHumansChoice,
        		mapRadio1, mapRadio2, mapRadio3,
        		mapLabel, gameMapPreview, gameMapPreviewLabel,
        		startingAliensLabel, startingAliensChoice, saveSettingsButton,
        		gameWaveLabel, gameWaveChoice, back_button);
        //end of save settings
        settingsScene = new Scene(settingsPane, width, height); //create scene
        //END OF SETTINGS SCENE
        
        //SCOREBOARD SCENE
        Pane scoreboardPane = new Pane();
        //back button
        Button backFromScoresButton = new Button("Back");
        backFromScoresButton.setLayoutX(width/60);
        backFromScoresButton.setLayoutY(height/1.1);
        backFromScoresButton.setPrefSize(width/10, height/20);
        backFromScoresButton.setOnAction(e -> window.setScene(mainMenuScene));
        //end of back button
        //scene background
        ImageView scoreboardBG = new ImageView(backgroundImage);
        //end of scene background
        scoreboardPane.getChildren().addAll(scoreboardBG, backFromScoresButton);
        scoreboardScene = new Scene(scoreboardPane, width, height);
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
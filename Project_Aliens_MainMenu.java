package project_aliens_mainmenu;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Ryan
 */
public class Project_Aliens_MainMenu extends Application {
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
        score_btn.setOnAction(e -> window.setScene(scoreboardScene));
        //end of scoreboard button
        mainMenuPane.getChildren().addAll(start_btn, exit_btn, score_btn);
        mainMenuScene = new Scene(mainMenuPane, 640, 360); //create scene
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
        final ToggleGroup mapGroup = new ToggleGroup();
        RadioButton mapRadio1 = new RadioButton("Map 1");
        mapRadio1.setToggleGroup(mapGroup);
        mapRadio1.setUserData(1);
        mapRadio1.setLayoutX(50);
        mapRadio1.setLayoutY(40);
        mapRadio1.setSelected(true);
        RadioButton mapRadio2 = new RadioButton("Map 2");
        mapRadio2.setToggleGroup(mapGroup);
        mapRadio2.setUserData(2);
        mapRadio2.setLayoutX(50);
        mapRadio2.setLayoutY(60);
        RadioButton mapRadio3 = new RadioButton("Map 3");
        mapRadio3.setToggleGroup(mapGroup);
        mapRadio3.setUserData(3);
        mapRadio3.setLayoutX(50);
        mapRadio3.setLayoutY(80);
        Rectangle gameMapPreview = new Rectangle(260,10,340,210);
        gameMapPreview.setFill(Color.WHITE);
        gameMapPreview.setStroke(Color.BLACK);
        Label gameMapPreviewLabel = new Label("Map Preview");
        gameMapPreviewLabel.setLayoutX(400);
        gameMapPreviewLabel.setLayoutY(220);
        settingsPane.getChildren().addAll(mapRadio1, mapRadio2, mapRadio3, mapLabel, gameMapPreview, gameMapPreviewLabel);
        //end of game map
        //game wave
        Label gameWaveLabel = new Label("Number of Waves:");
        gameWaveLabel.setLayoutX(20);
        gameWaveLabel.setLayoutY(120);
        ChoiceBox gameWaveChoice = new ChoiceBox(FXCollections.observableArrayList(
            "1", "2", "3", "4", "5")
        );
        gameWaveChoice.setLayoutX(50);
        gameWaveChoice.setLayoutY(140);
        settingsPane.getChildren().addAll(gameWaveLabel, gameWaveChoice);
        //end of game wave
        //starting humans
        Label startingHumansLabel = new Label("Number of starting Humans:");
        startingHumansLabel.setLayoutX(20);
        startingHumansLabel.setLayoutY(170);
        ChoiceBox startingHumansChoice = new ChoiceBox(FXCollections.observableArrayList(
            "5", "6", "7", "8", "9", "10")
        );
        startingHumansChoice.setLayoutX(50);
        startingHumansChoice.setLayoutY(190);
        settingsPane.getChildren().addAll(startingHumansLabel, startingHumansChoice);
        //end of starting humans
        //starting aliens
        Label startingAliensLabel = new Label("Number of starting Aliens:");
        startingAliensLabel.setLayoutX(20);
        startingAliensLabel.setLayoutY(220);
        ChoiceBox startingAliensChoice = new ChoiceBox(FXCollections.observableArrayList(
            "3", "4", "5", "6", "7", "8")
        );
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
            if(gameWaveChoice.getValue() != null){
                //choice is not null
                gameWaveErrorLabel.setVisible(false);
                gameSettings.numGameWaves = Integer.parseInt(gameWaveChoice.getValue().toString());
                System.out.println("Waves: " + gameSettings.numGameWaves);
            }else{
                //no choice was made, ask for user to make choice
                gameWaveErrorLabel.setVisible(true);
                emptySettings++;
            }

            //save starting number of humans
            if(startingHumansChoice.getValue() != null){
                //choice is not null
                startingHumansErrorLabel.setVisible(false);
                gameSettings.numStartingHumans = Integer.parseInt(startingHumansChoice.getValue().toString());
                System.out.println("Starting Humans: " + gameSettings.numStartingHumans);
            }else{
                //no choice made, ask user to make choice
                startingHumansErrorLabel.setVisible(true);
                emptySettings++;
            }

            //save starting number of aliens
            if(startingAliensChoice.getValue() != null){
                //choice is not null
                startingAliensErrorLabel.setVisible(false);
                gameSettings.numStartingAliens = Integer.parseInt(startingHumansChoice.getValue().toString());
                System.out.println("Starting Aliens: " + gameSettings.numStartingAliens);
            }else{
                //no choice made, ask user to make choice
                startingAliensErrorLabel.setVisible(true);
                emptySettings++;
            }
            
            if(emptySettings > 0){
                //if there are empty settings, do not allow game to start
                System.out.println("There are empty settings; game cannot start");
            }else{
                //START GAME FROM HERE WITH SETTINGS GIVEN ABOVE
                System.out.println("All settings are filled; starting game...");
            }
        });
        //end of save settings button
        settingsPane.getChildren().addAll(saveSettingsButton, gameWaveErrorLabel, startingHumansErrorLabel, startingAliensErrorLabel);
        //end of save settings
        settingsScene = new Scene(settingsPane, 640, 320); //create scene
        //END OF SETTINGS SCENE
        
        //SCOREBOARD SCENE
        Pane scoreboardPane = new Pane();
        //back button
        Button backFromScoresButton = new Button("Back");
        backFromScoresButton.setLayoutX(5);
        backFromScoresButton.setLayoutY(290);
        backFromScoresButton.setOnAction(e -> window.setScene(mainMenuScene));
        //end of back button
        //what the fuck do I add here
        scoreboardPane.getChildren().addAll(backFromScoresButton);
        scoreboardScene = new Scene(scoreboardPane, 640, 320);
        //END OF SCOREBOARD SCENE
        
        //display
        window.setTitle("Project: Aliens");
        window.setScene(mainMenuScene);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

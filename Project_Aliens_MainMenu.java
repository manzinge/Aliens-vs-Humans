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
    Scene mainMenuScene, settingsScene; //scenes
    
    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        
        //MAIN MENU SCENE
        Pane mainMenuPane = new Pane();
        //start button
        Button start_btn = new Button();
        start_btn.setText("Start Game");
        start_btn.setLayoutX(280); //hard-coded X-value to change
        start_btn.setLayoutY(150); //hard-coded Y-value to change
        start_btn.setOnAction(e -> window.setScene(settingsScene));
        mainMenuPane.getChildren().add(start_btn);
        //end of start button
        //exit button
        Button exit_btn = new Button();
        exit_btn.setText("Exit");
        exit_btn.setLayoutX(300); //hard-coded X-value to change
        exit_btn.setLayoutY(180); //hard-coded Y-value to change
        exit_btn.setOnAction(e -> System.exit(0));
        mainMenuPane.getChildren().add(exit_btn);
        //end of exit button
        mainMenuScene = new Scene(mainMenuPane, 640, 360); //create scene
        //END OF MAIN MENU SCENE
        
        //SETTINGS SCENE
        Pane settingsPane = new Pane();
        //back button
        Button back_button = new Button("Back");
        back_button.setLayoutX(240);
        back_button.setLayoutY(390);
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
        Button saveSettingsButton = new Button("Save and Start!");
        saveSettingsButton.setLayoutX(20);
        saveSettingsButton.setLayoutY(280);
        saveSettingsButton.setOnAction(e -> {
            //save map
            if(mapGroup.getSelectedToggle() != null){
                if(mapGroup.getSelectedToggle() == mapRadio1){
                    //set map to map 1
                    gameSettings.gameMapID = 1;
                    System.out.println("Game Map Selected: Map 1");
                }else if(mapGroup.getSelectedToggle() == mapRadio2){
                    //set map to map 2
                    gameSettings.gameMapID = 2;
                    System.out.println("Game Map Selected: Map 2");
                }else if(mapGroup.getSelectedToggle() == mapRadio3){
                    //set map to map 3
                    gameSettings.gameMapID = 3;
                    System.out.println("Game Map Selected: Map 3");
                }else{
                    //no radio button selected somehow
                    //shouldn't have to worry about this
                    System.out.println("Somehow selected no radio button, error?");
                    System.exit(1);
                }
            }
            
            //save number of game waves
            if(gameWaveChoice.getValue() != null){
                //choice is not null
                gameSettings.numGameWaves = Integer.parseInt(gameWaveChoice.getValue().toString());
                System.out.println("Waves: " + gameSettings.numGameWaves);
            }
        });
        settingsPane.getChildren().addAll(saveSettingsButton);
        //end of save settings
        settingsScene = new Scene(settingsPane, 640, 320); //create scene
        //END OF SETTINGS SCENE
        
        
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

import javafx.application.Application;
import javafx.geometry.Insets;
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
public class main_menu{
	Stage window; //primary window
	Scene mainMenuScene, settingsScene, scoreboardScene; //scenes
	ScoreBoard score = new ScoreBoard(); //Create scoreboard (scene from another class)
	//scene sizes
	int sceneWidth = 600;
	int sceneHeight = 600;

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double height = screenSize.height*0.7;
	public static double width = screenSize.width*0.4;

	Image backgroundImage = new Image("MenuIcons\\starry_background.png");
	Image startButtonImage = new Image("MenuIcons\\startButtonImage.png");
	Image scoreButtonImage = new Image("MenuIcons\\scoreButtonImage.png");
	Image exitButtonImage = new Image("MenuIcons\\exitButtonImage.png");
	Image backButtonImage = new Image("MenuIcons\\backButtonImage.png");


	public void start(Stage primaryStage){
		window = primaryStage;
		//MAIN MENU SCENE
		Pane mainMenuPane = new Pane();
		//game title splash here
		Image gameTitleImage = new Image("MenuIcons\\\\aliens_title.png");
		ImagePattern gameTitlePattern = new ImagePattern(gameTitleImage);
		Rectangle gameTitleSplash = new Rectangle(width/4,height/14,width/2,height/4);
		gameTitleSplash.setFill(gameTitlePattern);
		//end of game title splash

		//start button
		Button start_btn = new Button();
		setbutton(start_btn);
		start_btn.setLayoutY(height/2.5);
		start_btn.setGraphic(new ImageView(startButtonImage));
		start_btn.setOnAction(e -> window.setScene(settingsScene));
		//end of start button

		//exit button
		Button exit_btn = new Button();
		setbutton(exit_btn);
		exit_btn.setLayoutY(height/1.8);
		exit_btn.setGraphic(new ImageView(exitButtonImage));
		exit_btn.setOnAction(e -> System.exit(0));
		//end of exit button

		//scoreboard button
		Button score_btn = new Button();
		setbutton(score_btn);
		score_btn.setLayoutY(height/2.1);
		score_btn.setGraphic(new ImageView(scoreButtonImage));
		score_btn.setOnAction(e -> 
			score.start(primaryStage));	//Load the scoreboard	
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
		Button back_button = new Button();
		back_button.setLayoutX(width/60);
		back_button.setLayoutY(height/60);
		back_button.setGraphic(new ImageView(backButtonImage));
		back_button.setPadding(Insets.EMPTY);
		back_button.setOnAction(e -> window.setScene(mainMenuScene));
		//end of back button
		//game map
		Label mapLabel = new Label("Select Map:");
		mapLabel.setLayoutX(width/60);
		mapLabel.setLayoutY(height/11.5);
		mapLabel.setTextFill(Color.WHITE);

		Rectangle gameMapPreview = new Rectangle(width/3,150,width/2,height/3);
		gameMapPreview.setFill(Color.WHITE);
		gameMapPreview.setStroke(Color.BLACK);

		Label gameMapPreviewLabel = new Label("Map Preview");
		gameMapPreviewLabel.setLayoutX(width/1.85);
		gameMapPreviewLabel.setLayoutY(height/2.8);
		gameMapPreviewLabel.setTextFill(Color.WHITE);

		//map radio buttons
		final ToggleGroup mapGroup = new ToggleGroup();
		RadioButton[] mapradios = new RadioButton[4];
		for(int i = 1;i<=3;i++) {
			RadioButton test = new RadioButton();
			test.setToggleGroup(mapGroup);
			test.setUserData(i);
			test.setLayoutX(width/30);
			test.setTextFill(Color.WHITE);
			test.setText("Map : "+i);
			mapradios[i] = test;
		}
		mapradios[1].setLayoutY(height/8);
		mapradios[1].setSelected(true);
		mapradios[2].setLayoutY(height/6);
		mapradios[3].setLayoutY(height/4.75);

		//set map preview image with radio buttons
		//map preview images


		Image map1Image = new Image("Maps\\Map1.jpg");
		Image map2Image = new Image("Maps\\Map2.jpg");
		Image map3Image = new Image("Maps\\Map3.jpg");


		//image patterns to fill rectangle with
		ImagePattern map1Pattern = new ImagePattern(map1Image);
		ImagePattern map2Pattern = new ImagePattern(map2Image);
		ImagePattern map3Pattern = new ImagePattern(map3Image);
		//set preview with radio buttons
		gameMapPreview.setFill(map1Pattern);
		mapradios[1].setOnAction(e ->	gameMapPreview.setFill(map1Pattern));
		mapradios[2].setOnAction(e ->	gameMapPreview.setFill(map2Pattern));
		mapradios[3].setOnAction(e ->	gameMapPreview.setFill(map3Pattern));

		//end of map preview images
		//end of map radio buttons
		//end of game map
		//game wave

		Label[] choices = new Label[3];
		for(int i = 0;i<3;i++) {
			Label test = new Label();
			test.setLayoutX(width/60);
			test.setTextFill(Color.WHITE);
			choices[i] = test;
		}
		choices[0].setText("Number of Waves:");
		choices[0].setLayoutY(height/4);
		choices[1].setText("Number of starting Humans");
		choices[1].setLayoutY(height/3);
		choices[2].setText("Number of Starting Aliens");
		choices[2].setLayoutY(height/2.4);

		ComboBox<String> gameWaveChoice = new ComboBox();
		gameWaveChoice.setPromptText("Default: 2");
		gameWaveChoice.setValue("Default: 2");
		gameWaveChoice.setLayoutY(height/3.6);
		combobox(gameWaveChoice);

		ComboBox<String> startingHumansChoice = new ComboBox();
		startingHumansChoice.setPromptText("Default: 4");
		startingHumansChoice.setValue("Default: 4");
		startingHumansChoice.setLayoutY(height/2.75);
		combobox(startingHumansChoice);

		ComboBox<String> startingAliensChoice = new ComboBox();
		startingAliensChoice.setPromptText("Default: 3");
		startingAliensChoice.setValue("Default: 3");
		startingAliensChoice.setLayoutY(height/2.25);
		combobox(startingAliensChoice);


		//end of starting aliens
		//save settings
		//settings object to save settings
		GameSettings gameSettings = new GameSettings();
		//save settings button
		Button saveSettingsButton = new Button();
		saveSettingsButton.setLayoutX(width/45);
		saveSettingsButton.setLayoutY(height/1.7);
		saveSettingsButton.setGraphic(new ImageView(startButtonImage));
		saveSettingsButton.setPadding(Insets.EMPTY);
		//save button action
		saveSettingsButton.setOnAction(e -> {
			//save map
			if(mapGroup.getSelectedToggle() != null){
				if(mapGroup.getSelectedToggle() == mapradios[1]){
					//set map to map 1
					gameSettings.gameMapID = 1;
					System.out.println("Game Map Selected: " + gameSettings.gameMapID);
				}else if(mapGroup.getSelectedToggle() == mapradios[2]){
					//set map to map 2
					gameSettings.gameMapID = 2;
					System.out.println("Game Map Selected: " + gameSettings.gameMapID);
				}else if(mapGroup.getSelectedToggle() == mapradios[3]){
					//set map to map 3
					gameSettings.gameMapID = 3;
					System.out.println("Game Map Selected: " + gameSettings.gameMapID);
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

			//START GAME FROM HERE WITH SETTINGS GIVEN ABOVE
			System.out.println("All settings are filled; starting game...");

			try{
				window.close();
				Gamewindow game = new Gamewindow(gameSettings.numStartingAliens, gameSettings.numStartingHumans, gameSettings.gameMapID, gameSettings.numGameWaves);
			}catch(IOException ex) {
				ex.printStackTrace();
			}

		});
		//end of save settings button
		//scene background
		ImageView settingsBG = new ImageView(backgroundImage);
		//end of scene background
		settingsPane.getChildren().addAll(settingsBG, choices[1], startingHumansChoice,
				mapradios[1], mapradios[2], mapradios[3],
				mapLabel, gameMapPreview, gameMapPreviewLabel,
				choices[2], startingAliensChoice, saveSettingsButton,
				choices[0], gameWaveChoice, back_button);
		//end of save settings
		settingsScene = new Scene(settingsPane, width, height); //create scene
		//END OF SETTINGS SCENE

		//SCOREBOARD SCENE
		Pane scoreboardPane = new Pane();
		//back button
		Button backFromScoresButton = new Button();
		backFromScoresButton.setLayoutX(width/60);
		backFromScoresButton.setLayoutY(height/1.1);
		backFromScoresButton.setPrefSize(width/10, height/20);
		backFromScoresButton.setGraphic(new ImageView(backButtonImage));
		backFromScoresButton.setPadding(Insets.EMPTY);
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
	public void combobox(ComboBox<String> cb) {
		cb.getItems().addAll("1", "2", "3", "4", "5");
		cb.setLayoutX(width/45);
	}
	public void setbutton(Button bt) {
		bt.setPrefSize(width/6, height/14);
		bt.setLayoutX(width/2.3);
		bt.setPadding(Insets.EMPTY);
	}
}

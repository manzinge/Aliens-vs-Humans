import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class load_main extends Application{

	static Stage window;
	@Override
	public void start(Stage primaryStage){
		window = primaryStage;
		main_menu mainmenu = new main_menu();
		
		mainmenu.start(window);
		
		
		window.setTitle("Project: Aliens");
		//window.setScene(mainMenuScene);
		window.setResizable(false);
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

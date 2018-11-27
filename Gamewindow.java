
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Gamewindow extends JComponent{
	//Images
	ImageIcon  sleep_human = new ImageIcon(getClass().getResource("GameIcons\\\\sleep_human.png"));
	ImageIcon  human = new ImageIcon(getClass().getResource("GameIcons\\\\human.png"));

	//Attributes related to the Window, the Size of the window
	public static JFrame board = new JFrame("Testwindow");
	static int buttonsx = 8;
	static int buttonsy = 8;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double height = screenSize.height*0.7;
	public static double width = screenSize.width*0.4;

	//Game attributes
	public static Unit[][] unit = new Unit[8][8];
	public static int aliencount;
	public static int waves_left;
	public static int humancount;
	public static int map;
	private boolean game_on;


	//Constructor to create a new Gamewindow that creates the new Frame and the Units
	public Gamewindow(int aliencount,int humancount,int map, int waves) throws IOException
	{
		//Assigning the values to the local variables
		Gamewindow.aliencount = aliencount;
		Gamewindow.humancount = humancount;
		Gamewindow.map = map;
		Gamewindow.waves_left = waves;

		//Creating the Units on the board and refreshing the Window to show all the Units
		board = Map.createAndShowGui(map);
		setupunits(buttonsx,buttonsy,humancount);
		createwave(aliencount);
		board.setLayout(null);
		board.revalidate();
		board.repaint();

		//Starting the actual game control algorithm
		gametime(humancount, aliencount);
	}
	//Check if the tile is water or dirt to either make them moveable or not
	public static void checkmoveable() {
		for(int i=0;i<buttonsx;i++) {
			for(int j=0;j<buttonsy;j++) {
				if(Map.map.get(j+(i*8)) == 0) {
					unit[i][j].moveable = false;
				}
				else {
					unit[i][j].moveable = true;
				}
			}
		}
	}
	//Creating the basic 8x8 grid
	private void setupunits(int buttonsx, int buttonsy, int humancount) throws IOException{
		int x = 0,y=0;
		for(int i=0;i<buttonsx;i++) {
			for(int j=0;j<buttonsy;j++) {
				unit[i][j] = new Unit(0);
				unit[i][j].setLocation(x,y);
				board.add(unit[i][j]);
				x=x+(int)Math.round(width/buttonsx);
			}
			y=y+(int)Math.round(height/buttonsy);
			x=0;
		}
		for(int i=0;i<humancount;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2)+6;
			if(unit[yr][xr].gettype() == 1) {i--;}
			else
			{
				board.remove(unit[yr][xr]);
				unit[yr][xr] = new Unit(1);
				unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
				board.add(unit[yr][xr]);
			}
		}
		checkmoveable();
		spawnresource(2);
	}
	public static void spawnresource(int amount) throws IOException {
		for(int i=0;i<amount;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2)+3;
			if(unit[yr][xr].moveable == false || unit[yr][xr].gettype() == 3) {
				i--;
			}
			else {
				board.remove(unit[yr][xr]);
				unit[yr][xr] = new Unit(3);
				unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
				board.add(unit[yr][xr]);
			}
		}
	}
	public void createwave(int aliencount) throws IOException {
		for(int i=0;i<aliencount;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2);
			if(unit[yr][xr].gettype()==2) {i--;}
			else {
				board.remove(unit[yr][xr]);
				unit[yr][xr] = new Unit(2);
				unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
				board.add(unit[yr][xr]);
			}
		}
	}

	private void gametime(int humans, int aliens) throws IOException{
		int human_team_moves = humans * 2; //Human moves remaining (per turn)
		int alien_team_moves = aliens;  //Alien moves remaining (per turn)
		int humans_left=0; //Humans left at turn end
		int aliens_left=0; //Aliens left at turn end
		int human_count = humans; //Humans on board
		int alien_count = aliens; //Aliens on board
		boolean human_turn =true;

		//Initial team moves
		unit[0][0].set_Human_moves(human_team_moves);
		unit[0][0].set_Alien_moves(alien_team_moves);

		while(game_on = true){//Game loop
			//Check for unit actions left
			int aliens_remaining = 0;
			for(int i=0;i<buttonsx;i++) {//Go through the board checking if buttons have remaining moves
				for(int j=0;j<buttonsy;j++) {

					if(unit[i][j].getMoves() == 0){
						unit[i][j].set_usable(false); //Unit can no longer perform actions
						if(unit[i][j].gettype()==1){
							unit[i][j].setIcon(sleep_human); //Show that the human is "sleeping"
						}
					}    

					if(unit[i][j].gettype() == 2 && unit[i][j].gethealth() > 0) {
						aliens_remaining++;
					}
				}
			}
			if(aliens_remaining == 0) {
				System.out.println("NO REMAINING ALIENS");
				game_on = false;
			}

			//Aliens turn
			if(unit[0][0].get_Human_moves() == 0 && unit[0][0].get_Alien_moves() != 0){
				human_turn = false;
				for(int i=0;i<buttonsx;i++) {//Go through the board checking if buttons have remaining moves
					for(int j=0;j<buttonsy;j++) {
						if(unit[i][j].gettype()==2 && unit[i][j].getMoves() == 1){//if unit is alien and has a move                                             
							unit[i][j].AI_Find_Target(j, i);//Find target for alien at j,i
						}
					}
				}
				human_turn = true;

			}

			//Reset Aliens turn
			if(unit[0][0].get_Alien_moves() <=0){
				for(int i=0;i<buttonsx;i++) {//Go through the board checking if buttons have remaining moves
					for(int j=0;j<buttonsy;j++) {
						if(unit[i][j].gettype()==2){  
							unit[i][j].set_Moves(1); //Adds a new action point to aliens
							unit[i][j].set_usable(true); //Reenable the unit
							aliens_left++;  
						}
					}
				}
				unit[0][0].set_Alien_moves(aliens_left); //Reset team moves
				alien_count = aliens_left; //Any die?
				aliens_left = 0;    
			}

			//Reset/Start Human turn
			if(unit[0][0].get_Human_moves() <= 0 && human_turn == true){//If humans all out of moves
				for(int i=0;i<buttonsx;i++) {//Go through the board 
					for(int j=0;j<buttonsy;j++) {
						if(unit[i][j].gettype()==1){ //Reenable and add moves to each human once their moves are over
							unit[i][j].set_Moves(2); 
							unit[i][j].set_usable(true); //Reenable the unit
							unit[i][j].setIcon(human);//Show that all humans are "awake" again
							humans_left++; 
						}
					}
				}   

				unit[0][0].set_Human_moves(humans_left * 2);  //Set team moves to amount of humans * movement points
				human_count = humans_left; //Any die?
				humans_left = 0;//Reset humans left
				// System.out.println("alien team " + unit[0][0].get_Alien_moves());
			}         

			//End of Game
			if(human_count == 0) {
				try {
					JOptionPane.showMessageDialog(board, "\"You were defeated by the Aliens!");
					game_on = false;
					Thread.sleep(1000);
					System.exit(0);
					break;
				}catch(Exception ex) {
					ex.getStackTrace();
				}
			}
			if(alien_count == 0) {
				try {
					JOptionPane.showMessageDialog(board, "\"You have managed to repel the invaders!");
					game_on = false;
					Thread.sleep(1000);
					System.exit(0);
					break;
				}catch(Exception ex) {
					ex.getStackTrace();
				}
			}

		}//End of while loop
		Gamewindow.waves_left--;

		//After game ends

		if(Gamewindow.waves_left > 0) {
			System.out.println("End of wave");
			createwave(aliencount);
			game_on = true;
		}else {
			System.out.println("End of game");
		}
	}
}


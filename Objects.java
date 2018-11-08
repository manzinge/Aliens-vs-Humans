import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
public class Objects {

}
class Unit extends JButton {
	private boolean debug = false; //Turn off for less console stuff
	private boolean hasResource = false;
	private int radius=1;
	private int type; //0=Button, 1=Human, 2=Alien, 99=death
	private int health;
	private int strength;
	private int moves;
	private static int Human_team_moves;
	private static int Alien_team_moves;
	private boolean usable; //Can this unit do actions
	//Temporary attributes
	private int temphealth;
	private int tempstrength;
	private int tempx;
	private int tempy;
	private int tempmove;
	private boolean tempusable;
	//Icons
	BufferedImage human = ImageIO.read(getClass().getResource("Human.png"));
	BufferedImage alien = ImageIO.read(getClass().getResource("Alien.png"));
	BufferedImage death = ImageIO.read(getClass().getResource("death.png"));
	//Listeners
	show sh = new show();
	move mv = new move();
	attack att = new attack();

	public Unit(int type) throws IOException {			//A Constructor that creates a different type of Unit based on the type (Button, Human or Alien)
		this.type = type;
		switch(type) {
		case 0:createButton();break;
		case 1:createHuman();break;
		case 2:createAlien();break;
		default:System.out.println("There was an error while creating a new Button/Human/Alien! (The parameter is not reachable!)");
		}	
	}
	private void setSizeb() {
		this.setSize((int)Math.round(TestBoard.width/TestBoard.buttonsx),(int)Math.round(TestBoard.height/TestBoard.buttonsy));
	}
	private void createButton() {						//Method used to create a normal Button
		this.setSizeb();
		this.setVisible(false);
		this.type = 0;
		this.setIcon(null);
		this.setEnabled(true);
		this.removeActionListener(sh);
		this.removeActionListener(mv);
		this.health = -100;
		this.strength = -1;
		this.moves = -1;
	}
	private void createdeath() {
		this.type = 99;
		this.usable = false;
		this.setVisible(true);
		this.setSizeb();
		this.health = -100;
		this.strength = -1;
		this.moves = -1;
		this.setIcon(new ImageIcon(death));
	}
	private void createHuman() throws IOException {		//Setting variables for a new Human (this is only called when spawning new Humans)
		this.moves = 2; //How many actions can be perfomed in a turn
		this.usable = true;
		this.type = 1;
		this.setSizeb();
		this.setVisible(true);
		this.health = 3;
		this.strength=1;

		this.setIcon(new ImageIcon(human));
		this.addActionListener(sh);
	}

	private void createAlien() throws IOException{			//Method used to create an Alien (this is only called when spawning new Aliens)
		this.moves = 1; //How many actions can be perfomed in a turn
		this.usable = false;	//Aliens don't start
		this.type = 2;
		this.setSizeb();
		this.setVisible(true);
		this.health=4;
		this.strength=2;
		this.setIcon(new ImageIcon(alien));              
		//Alien_AI mind = new Alien_AI();
	}
	public int get_Human_moves() { //Get human team moves
		return Human_team_moves;
	}
	public void set_Human_moves(int team_moves) { //Set moves human team has left
		Human_team_moves = team_moves;
	}
	public int get_Alien_moves() { //Get human team moves
		return Alien_team_moves;
	}
	public void set_Alien_moves(int team_moves) { //Set moves human team has left
		Alien_team_moves = team_moves;
	}        
	public boolean get_usable() { //Get if unit is used
		return usable;
	}
	public void set_usable(boolean used) { //Set if unit usable
		usable = used;
	}         
	public int gettype() {
		return this.type;
	}
	private void heal() {						//Only humans can heal in the zone in the back?
		this.health++;
	}
	private void takeDamage(int dmg){	//If the unit should take damage(fight) call this function with the strength of the opponent
		this.health = health-dmg;
	}
	private int gethealth() {
		return this.health;
	}
	private int getstrength() {
		return this.strength;
	}
	public int getMoves() {    //Returns the moves
		return this.moves;
	}
	public void set_Moves(int new_moves) {    //Returns the moves
		moves = new_moves;
	}        
	private void gatherResource() {				//Method to pick up resource, makes unit slower -> less range
		this.hasResource = true;
		this.radius--;
	}
	private void dropResource() {				//Method to drop Resource (secure it)
		this.hasResource = false;
		this.radius++;
	}
	private void consumeResource() {			//Method to consume the Resource, makes the alien stronger by one point and increases radius?
		hasResource = false;
		strength++;
		radius++;
	}

	class show implements ActionListener {											//Action Listener class to show options where the player can go
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<TestBoard.buttonsx;i++) {
				for(int j=0;j<TestBoard.buttonsy;j++) {
					if(e.getSource() == TestBoard.unit[i][j] && TestBoard.unit[i][j].usable == true) {						//Figuring out which object/Button was pressed

						for(int g = i-radius;g<=i+radius;g++){
							for(int b=j-radius;b<=j+radius;b++) {
								/*if(TestBoard.unit[i][j].moves == 0){ //Disable if out of moves //This just causes it to disappear
                                                                    TestBoard.unit[i][j].setEnabled(false);   
                                                                }else *///Else would go to below line

								if(g<0 || g>TestBoard.buttonsx-1 || b<0 || b>TestBoard.buttonsx-1) {						//Takes action on all Buttons within the radius except if they are out of the border

								}
								else {
									if(TestBoard.unit[g][b].type == 1) {				//Action that will occur if there is another human around (make not clickable)
										TestBoard.unit[g][b].setEnabled(false);

									}
									else if(TestBoard.unit[g][b].type == 2) {			//Action that will occur if there is an Alien around (Add attack listener and temporarily store values)
										TestBoard.unit[g][b].addActionListener(att);
										TestBoard.unit[g][b].setBackground(Color.RED);	//Setting background color of enemy to red, to signalize a potential danger
										temphealth = TestBoard.unit[i][j].health;
										tempstrength = TestBoard.unit[i][j].strength;
										tempx = i;
										tempy = j;
										tempmove = TestBoard.unit[i][j].moves;
										tempusable = TestBoard.unit[i][j].usable;                                                                                
									}
									else {												//Action that will occur if there is just a button with nothing on it
										TestBoard.unit[g][b].setVisible(true);			//Actions include: Making it visibile -> clickable, storing temp values, and adding a listener
										TestBoard.unit[g][b].setText("MOVE");
										temphealth = TestBoard.unit[i][j].health;
										tempstrength = TestBoard.unit[i][j].strength;
										TestBoard.unit[g][b].addActionListener(mv);
										tempmove = TestBoard.unit[i][j].moves;
										tempusable = TestBoard.unit[i][j].usable;
									}
								}
							}
						}
						TestBoard.unit[i][j].createButton();						//The initiale human button gets transformed into a button
						break;
					}

				}
			}
		}
	}
	class attack implements ActionListener {										//Action Listener to attack enemies
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<TestBoard.buttonsx;i++) {
				for(int j=0;j<TestBoard.buttonsy;j++) {	
					if(e.getSource() == TestBoard.unit[i][j]) {						//Figuring out the clicked object/Jbutton
						try {
							TestBoard.unit[tempx][tempy].createHuman();				//Creating a human where the old one was -> Human does not move when attacking
							TestBoard.unit[tempx][tempy].health = temphealth;		//Adding the necessary values to it like health and strenght
							TestBoard.unit[tempx][tempy].strength = tempstrength;
							TestBoard.unit[tempx][tempy].moves = tempmove -1;
							TestBoard.unit[tempx][tempy].usable = tempusable;
							TestBoard.unit[i][j].takeDamage(TestBoard.unit[tempx][tempy].getstrength());	//Clicked object receives damage (Human strength)
							TestBoard.unit[i][j].set_Human_moves(Human_team_moves-1); //Updates total team moves							
							clearBoard();											//Board gets cleared to fix any issues with wrong variable assignings
							System.out.println("Alien Attacked by Human! Alien has " +TestBoard.unit[i][j].gethealth() +" health left!");
						} catch(Exception ex) {
							System.out.println(ex);
						}
						break;
					}
				}
			}
		}
	}
	class move implements ActionListener {											//Action listener to move the character to an empty space
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<TestBoard.buttonsx;i++) {
				for(int j=0;j<TestBoard.buttonsy;j++) {			
					if(e.getSource() == TestBoard.unit[i][j]) {						//Figuring out the clicked object/JButton
						try {
							TestBoard.unit[i][j].createHuman();						//Creating a new human at that position
							TestBoard.unit[i][j].health = temphealth;				//Assigning the values of the "old" human to the "new" one
							TestBoard.unit[i][j].strength = tempstrength;
							TestBoard.unit[i][j].moves = tempmove -1;                                                        TestBoard.unit[tempx][tempy].moves = tempmove;
							TestBoard.unit[tempx][tempy].usable = tempusable;
							TestBoard.unit[i][j].set_Human_moves(Human_team_moves-1); //Updates total team moves
							clearBoard();											//Board gets cleared to fix any issues with wrong variable assignings
							System.out.println("Human: Postion is : "+i +" and "+j + " Health = " +TestBoard.unit[i][j].gethealth() +" Strenght is : " +TestBoard.unit[i][j].getstrength() + " Moves remaining: " +TestBoard.unit[i][j].moves 
									+ " Team moves left: " +Human_team_moves + " Alien team moves: " +Alien_team_moves); 
						} catch(Exception ex) {
							System.out.println(ex);
						}
						break;
					}
				}
			}
		}
	}

	protected void clearBoard() throws InterruptedException {										//Method to clear the board and remove existing action listeners
		for(int i=0;i<TestBoard.buttonsx;i++) {
			for(int j=0;j<TestBoard.buttonsy;j++) {
				TestBoard.unit[i][j].setBackground(null);			//To reset the red color of enemies
				TestBoard.unit[i][j].removeActionListener(mv);		//Removing moving listeners (The other buttons that weren't clicked)



				if(TestBoard.unit[i][j].health <= 0 && TestBoard.unit[i][j].health > -99 ) {				//Things to do if there is an unit with no health left -> replace it with buttons and remove listeners
					if(TestBoard.unit[i][j].type == 1){//Fixes loop waiting for a human to move that has just been killed
						Human_team_moves -= 2;
					}
					if(TestBoard.unit[i][j].type == 2){//Fixes loop waiting for a alien to move that has just been killed
						Alien_team_moves -= 1;
					}                                        
					TestBoard.unit[i][j].removeActionListener(att);
					TestBoard.unit[i][j].removeActionListener(sh);
					TestBoard.unit[i][j].createdeath();
				}
				else if(TestBoard.unit[i][j].type == 99){
					TestBoard.unit[i][j].createButton();
				}
				if(TestBoard.unit[i][j].type == 0) {				//Things to do if the unit is a button -> refreshing the button
					TestBoard.unit[i][j].createButton();
				}
				else if(TestBoard.unit[i][j].type == 1) {			//Things to do if the unit is a human -> enabling it (in case there were humans around)
					TestBoard.unit[i][j].setEnabled(true);			//Obviously make it visible to make it possible for the user to interact with it
					TestBoard.unit[i][j].setVisible(true);
					TestBoard.unit[i][j].setText(null);
				}
				else {												//Things to do if the unit is an alien -> removing any listeners and making it visible
					TestBoard.unit[i][j].setVisible(true);
					TestBoard.unit[i][j].removeActionListener(sh);
					TestBoard.unit[i][j].removeActionListener(att);
				}
			}
		}
		//System.out.println("Board was cleared!");
	}

	//Finds target AI
	public void AI_Find_Target(int start_x, int start_y){
		int target_x = start_x;
		int target_y = start_y;


		for(int i=0;i<TestBoard.buttonsx;i++) { //Go through board
			for(int j=0;j<TestBoard.buttonsy;j++) {	 
				if(TestBoard.unit[i][j].gettype() == 1){ //If its a human
					//System.out.println("i " + i + " j" + j );
					//Calculate Row
					if(start_x > j){
						target_x--;
					}else if(start_x < j){
						target_x++;
					}else{ }//Stay in that row

					//Calculate column
					if(start_y > i){
						target_y--;
					}else if(start_y < i){
						target_y++;
					}else{  } //Stay in that column

					if(debug == true)
						System.out.println("ALien at X:" + start_x + " Y:" + start_y +" Target is at: X:" + j + " Y:" + i ); //test


					if(TestBoard.unit[target_y][target_x].gettype() == 2){ //Check for other aliens
						System.out.println("Staying still, friendly in the way");
						TestBoard.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves

					}else if(target_y == start_y && target_x == start_x){//If AI decides it shouldnt move 
						System.out.println("Staying still");
						TestBoard.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves

						/* }else if(target_y == i && target_x == j){//Attack
                             TestBoard.unit[i][j].health -= TestBoard.unit[start_y][start_x].strength;
                             System.out.println("Human Attacked!");
                             TestBoard.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves
						 */
					}else if(TestBoard.unit[target_y][target_x].gettype()==1){//Attack
						TestBoard.unit[target_y][target_x].health -= TestBoard.unit[start_y][start_x].strength;
						System.out.println("Human Attacked by Alien! Human has " +TestBoard.unit[target_y][target_x].gethealth() +" health left!");
						TestBoard.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves     (doesn' matter what tile does it)                  
					}else{//Moving
						TestBoard.unit[i][j].AI_move(start_x, start_y, target_y, target_x);
					}
					break;//Get out of the loop target is found

				} 
			}
			if(start_x != target_x || start_y != target_y)//Break out of outer loop
				break;
		}

	}


	//Updates the tile Map somewhat buggy currently
	public void AI_move(int start_x, int start_y, int target_y, int target_x){
		if(debug == true)
			System.out.println("Moving from X:" + start_x +" Y:" + start_y +" to Y:" +target_y + " X:" +target_x);
		if(TestBoard.unit[start_y][start_x].moves > 0){

			temphealth = TestBoard.unit[start_y][start_x].health;
			tempstrength = TestBoard.unit[start_y][start_x].strength;
			tempx = start_x;
			tempy = start_y;
			tempmove = TestBoard.unit[start_y][start_x].moves;
			tempusable = TestBoard.unit[start_y][start_x].usable; 
			//Y is first?

					try {
						Thread.sleep(400);
						TestBoard.unit[target_y][target_x].createAlien();			//Creating a new Alien at that position
						TestBoard.unit[target_y][target_x].health = temphealth;		//Assigning the values of the "old" Alien to the "new" one
						TestBoard.unit[target_y][target_x].strength = tempstrength;
						TestBoard.unit[target_y][target_x].moves = tempmove -1;                                                        
						TestBoard.unit[tempx][tempy].moves = tempmove;
						TestBoard.unit[tempx][tempy].usable = tempusable;
						TestBoard.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves
						clearBoard();								//Board gets cleared to fix any issues with wrong variable assignings

						System.out.println("Alien: Health = " +TestBoard.unit[target_y][target_x].gethealth() +" Strenght is : " +TestBoard.unit[target_y][target_x].getstrength() + " Moves remaining: " +TestBoard.unit[target_y][target_x].moves 
								+ " Team moves left: " +Alien_team_moves + "Human moves left:" + Human_team_moves); 
						TestBoard.unit[start_y][start_x].createButton();						//The initiale button gets transformed into a button
					} catch(Exception ex) {
						System.out.println(ex);
					}  

		}
	}//End of move
}

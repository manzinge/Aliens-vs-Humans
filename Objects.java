import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
class Unit extends JButton {
	private boolean debug = false; //Turn off for less console stuff
	private boolean hasResource = false;
	private boolean isTargeted;
	public boolean moveable = true;

	private int radius=1;
	private int type; //0=Button, 1=Human, 2=Alien, 3=Resource, 99=death
	private int health;
	private int strength;
	private int moves;
	private static int Human_team_moves;
	private static int Alien_team_moves;
	private boolean factories;
	private boolean usable; //Can this unit do actions
	//Temporary attributes
	private boolean tempres;
	private int temphealth;
	private int tempstrength;
	private int tempx;
	private int tempy;
	private int tempmove;
	private boolean tempusable;
	//Icons
	BufferedImage factory = ImageIO.read(getClass().getResource("GameIcons\\\\factories.png"));
	BufferedImage human = ImageIO.read(getClass().getResource("GameIcons\\\\Human.png"));
	BufferedImage alien = ImageIO.read(getClass().getResource("GameIcons\\\\Alien.png"));
	BufferedImage resource = ImageIO.read(getClass().getResource("GameIcons\\\\resource.png"));
	BufferedImage death = ImageIO.read(getClass().getResource("GameIcons\\\\death.png"));
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
		case 3:createResource();break;
		default:System.out.println("There was an error while creating a new Button/Human/Alien! (The parameter is not reachable!)");break;
		}	
	}
	private void basicsetup() {
		this.setSize((int)Math.round(Gamewindow.width/(Gamewindow.buttonsx*1.05)),(int)Math.round(Gamewindow.height/(Gamewindow.buttonsy*1.05)));
		this.setBorderPainted(false);
		this.hasResource = false;
	}
	private void createButton() {						//Method used to create a normal Button
		this.basicsetup();
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
		this.basicsetup();
		this.health = -100;
		this.strength = -1;
		this.moves = -1;
		this.setIcon(new ImageIcon(death));
	}
	private void createHuman() throws IOException {		//Setting variables for a new Human (this is only called when spawning new Humans)
		this.moves = 2; //How many actions can be perfomed in a turn
		this.usable = true;
		this.type = 1;
		this.basicsetup();
		this.setVisible(true);
		this.health = 3;
		this.strength=1;
		this.isTargeted = false;
		this.setBackground(Color.GREEN);
		this.setIcon(new ImageIcon(human));
		this.addActionListener(sh);
	}

	private void createAlien() throws IOException{			//Method used to create an Alien (this is only called when spawning new Aliens)
		this.moves = 1; //How many actions can be perfomed in a turn
		this.usable = false;	//Aliens don't start
		this.type = 2;
		this.basicsetup();
		this.setVisible(true);
		this.health=4;
		this.strength=2;
		this.setIcon(new ImageIcon(alien));
		this.setBackground(Color.GREEN);
		//Alien_AI mind = new Alien_AI();
	}
	private void createResource() throws IOException {
		this.basicsetup();
		this.setVisible(true);
		this.hasResource = true;
		this.type = 3;
		this.health = 999;
		this.strength = 0;
		this.setIcon(new ImageIcon(resource));
	}
	private void healthcolor() {
		switch(this.gethealth()) {
		case 1:this.setBackground(Color.RED);break;
		case 2:this.setBackground(Color.ORANGE);break;
		case -100:this.setBackground(Color.BLACK);break;
		default:this.setBackground(Color.GREEN);break;
		}
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
	private void takeDamage(int dmg){	//If the unit should take damage(fight) call this function with the strength of the opponent
		this.health = health-dmg;
	}
	public int gethealth() {
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

	class show implements ActionListener {											//Action Listener class to show options where the player can go
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<Gamewindow.buttonsx;i++) {
				for(int j=0;j<Gamewindow.buttonsy;j++) {
					if(e.getSource() == Gamewindow.unit[i][j] && Gamewindow.unit[i][j].usable == true) {						//Figuring out which object/Button was pressed

						for(int g = i-radius;g<=i+radius;g++){
							for(int b=j-radius;b<=j+radius;b++) {
								/*if(TestBoard.unit[i][j].moves == 0){ //Disable if out of moves //This just causes it to disappear
                                                                    TestBoard.unit[i][j].setEnabled(false);   
                                                                }else *///Else would go to below line
								if(g<0 || g>Gamewindow.buttonsx-1 || b<0 || b>Gamewindow.buttonsx-1) { }					//Takes action on all Buttons within the radius except if they are out of the border
								else {
									if(Gamewindow.unit[g][b].type == 1 || Gamewindow.unit[g][b].moveable == false) {				//Action that will occur if there is another human around / Tile is not moveable (make not clickable)
										Gamewindow.unit[g][b].setEnabled(false);

									}
									else if(Gamewindow.unit[g][b].type == 2) {			//Action that will occur if there is an Alien around (Add attack listener and temporarily store values)
										Gamewindow.unit[g][b].addActionListener(att);
										Gamewindow.unit[g][b].setBackground(Color.RED);	//Setting background color of enemy to red, to signalize a potential danger
										//Setting temporary variables
										temphealth = Gamewindow.unit[i][j].health;
										tempstrength = Gamewindow.unit[i][j].strength;
										tempx = i;
										tempy = j;
										tempmove = Gamewindow.unit[i][j].moves;
										tempusable = Gamewindow.unit[i][j].usable;                                                                                
									}
									else {												//Action that will occur if there is just a button with nothing on it
										if(Gamewindow.unit[g][b].hasResource == true && Gamewindow.unit[i][j].hasResource == true) {
											Gamewindow.unit[g][b].setEnabled(false);
										}
										Gamewindow.unit[g][b].addActionListener(mv);
										Gamewindow.unit[g][b].setVisible(true);			//Actions include: Making it visibile -> clickable, storing temp values, and adding a listener
										Gamewindow.unit[g][b].setText("MOVE");
										//Setting temporary variables
										tempres = Gamewindow.unit[i][j].hasResource;
										temphealth = Gamewindow.unit[i][j].health;
										tempstrength = Gamewindow.unit[i][j].strength;
										tempmove = Gamewindow.unit[i][j].moves;
										tempusable = Gamewindow.unit[i][j].usable;
									}
								}
							}
						}
						Gamewindow.unit[i][j].createButton();						//The initial human button gets transformed into a button
						break;
					}
				}
			}
		}
	}
	class attack implements ActionListener {										//Action Listener to attack enemies
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<Gamewindow.buttonsx;i++) {
				for(int j=0;j<Gamewindow.buttonsy;j++) {	
					if(e.getSource() == Gamewindow.unit[i][j]) {						//Figuring out the clicked object/Jbutton
						try {
							Gamewindow.unit[tempx][tempy].createHuman();				//Creating a human where the old one was -> Human does not move when attacking
							//Assigning the values from the Human before
							Gamewindow.unit[tempx][tempy].health = temphealth;
							Gamewindow.unit[tempx][tempy].strength = tempstrength;
							Gamewindow.unit[tempx][tempy].moves = tempmove -1;
							Gamewindow.unit[tempx][tempy].usable = tempusable;
							Gamewindow.unit[tempx][tempy].hasResource = tempres;

							Gamewindow.unit[i][j].takeDamage(Gamewindow.unit[tempx][tempy].getstrength());	//Clicked object receives damage (Human strength)
							Gamewindow.unit[i][j].set_Human_moves(Human_team_moves-1); //Updates total team moves

							clearBoard();											//Board gets cleared to fix any issues with wrong variable assignings
							System.out.println("Alien Attacked by Human! Alien has " +Gamewindow.unit[i][j].gethealth() +" health left!");
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
			for(int i=0;i<Gamewindow.buttonsx;i++) {
				for(int j=0;j<Gamewindow.buttonsy;j++) {			
					if(e.getSource() == Gamewindow.unit[i][j]) {						//Figuring out the clicked object/JButton
						try {
							if(Gamewindow.unit[i][j].gettype() == 3) {
								tempres = true;
							}
							Gamewindow.unit[i][j].createHuman();						//Creating a new human at that position
							//Assign the temp variables from the Human before
							Gamewindow.unit[i][j].hasResource = tempres;
							Gamewindow.unit[i][j].health = temphealth;				//Assigning the values of the "old" human to the "new" one
							Gamewindow.unit[i][j].strength = tempstrength;
							Gamewindow.unit[i][j].moves = tempmove -1;     
							Gamewindow.unit[tempx][tempy].moves = tempmove;
							Gamewindow.unit[tempx][tempy].usable = tempusable;
							Gamewindow.unit[i][j].set_Human_moves(Human_team_moves-1); //Updates total team moves
							clearBoard();											//Board gets cleared to fix any issues with wrong variable assignings
							System.out.println("Human: Postion is : "+i +" and "+j + " Health = " +Gamewindow.unit[i][j].gethealth() +" Strenght is : " +Gamewindow.unit[i][j].getstrength() + " Resource : "+Gamewindow.unit[i][j].hasResource + " Moves remaining: " +Gamewindow.unit[i][j].moves 
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

	public void clearBoard() throws InterruptedException, IOException {										//Method to clear the board and remove existing action listeners
		int safezone = 0;
		for(int i=0;i<Gamewindow.buttonsx;i++) {
			for(int j=0;j<Gamewindow.buttonsy;j++) {
				Gamewindow.unit[i][j].setBackground(null);			//To reset the red color of enemies
				Gamewindow.unit[i][j].removeActionListener(mv);		//Removing moving listeners (The other buttons that weren't clicked)
				if(Gamewindow.unit[i][j].hasResource == true && Gamewindow.unit[i][j].type == 1) {
					if(i==7) {
						Gamewindow.unit[i][j].strength++;
						Gamewindow.unit[i][j].hasResource = false;
						JOptionPane.showMessageDialog(Gamewindow.board, "\"Your human just got stronger!");
					}
					safezone++;
				}
				if(Gamewindow.unit[i][j].health <= 0 && Gamewindow.unit[i][j].health > -99 ) {				//Things to do if there is an unit with no health left -> replace it with buttons and remove listeners
					if(Gamewindow.unit[i][j].type == 1){//Fixes loop waiting for a human to move that has just been killed
						Human_team_moves -= 2;
						Gamewindow.score -= 50; //Lose score for human dieing
					}
					if(Gamewindow.unit[i][j].type == 2){//Fixes loop waiting for a alien to move that has just been killed
						Gamewindow.score += 50; //Score for killing alien
						Alien_team_moves -= 1;
					}                                        
					Gamewindow.unit[i][j].removeActionListener(att);
					Gamewindow.unit[i][j].removeActionListener(sh);
					Gamewindow.unit[i][j].createdeath();
					if(Gamewindow.unit[i][j].hasResource == true) {
						Gamewindow.spawnresource(1);
					}
				}
				else if(Gamewindow.unit[i][j].type == 99){
					Gamewindow.unit[i][j].createButton();
					Gamewindow.unit[i][j].setBackground(Color.BLACK);
				}
				if(Gamewindow.unit[i][j].type == 0) {				//Things to do if the unit is a button -> refreshing the button
					Gamewindow.unit[i][j].createButton();
				}
				else if(Gamewindow.unit[i][j].type == 1) {			//Things to do if the unit is a human -> enabling it (in case there were humans around)
					Gamewindow.unit[i][j].setEnabled(true);			//Obviously make it visible to make it possible for the user to interact with it
					Gamewindow.unit[i][j].setVisible(true);
					Gamewindow.unit[i][j].setText(null);
					Gamewindow.unit[i][j].healthcolor();
				}
				else if(Gamewindow.unit[i][j].type == 3) {
					Gamewindow.unit[i][j].setEnabled(true);
					Gamewindow.unit[i][j].setVisible(true);
					Gamewindow.unit[i][j].setText(null);
				}
				else {												//Things to do if the unit is an alien -> removing any listeners and making it visible
					Gamewindow.unit[i][j].setVisible(true);
					Gamewindow.unit[i][j].removeActionListener(sh);
					Gamewindow.unit[i][j].removeActionListener(att);
					Gamewindow.unit[i][j].healthcolor();
				}
			}
		}
		if(safezone > 0) {
			Map.zonelabel.setText("Bring the resources back");
		}
		else {
			Map.zonelabel.setText("Try to gather some resources");
		}
	}
	
	//Finds target AI
	public void AI_Find_Target(int start_x, int start_y){
		int target_x = start_x;
		int target_y = start_y;
		
		int humansFound = 0;
		int humanIndex = 0;
		int[] targetsX = new int[5];
		int[] targetsY = new int[5];
		int[] targetsWeight = new int[5];
		
		
		
		//find all possible targets
		for(int i=0;i<Gamewindow.buttonsx;i++) { //Go through board
			for(int j=0;j<Gamewindow.buttonsy;j++) {	 
				if(Gamewindow.unit[i][j].gettype() == 1){ //If it's a human

					//human x and y values added to arrays
					targetsX[humanIndex] = i;
					targetsY[humanIndex] = j;
					
					//calculate weight of possible target found
					int targetWeight = 0;
					if(Gamewindow.unit[i][j].hasResource == true) {
						targetWeight += 2; //add 2 unit of weight to the human if they hold a resource
					}
					if(Gamewindow.unit[i][j].health < 2) {
						targetWeight++; //add 1 unit of weight to human if they have low health
					}
					if(Gamewindow.unit[i][j].isTargeted == true) {
						targetWeight -= 4; //subtract 1 unit of weight from human if already targeted
					}
					targetsWeight[humanIndex] = targetWeight;
					
					System.out.println("Human found at:" + targetsX[humanIndex] + "," + targetsY[humanIndex]);
					System.out.println("Weight of this human: " + targetsWeight[humanIndex]);
					
					humansFound++;
					humanIndex++;
				}
			}
		}
		
		int bestTarget = 0;
		int highestWeight = -99;
		//find best target (highest weight)
		for(int i = 0; i < humansFound; i++) {
			
			if(targetsWeight[i] > highestWeight) {
				highestWeight = targetsWeight[i];
				bestTarget = i;
			}
		}
		
		//Calculate Row of best target
		if(start_x > targetsY[bestTarget]){ 
			target_x--; 
		}else if(start_x < targetsY[bestTarget]){ 
			target_x++;
		}else{ } //Stay in that row

		//Calculate column
		if(start_y > targetsX[bestTarget]){ 
			target_y--; 
		}else if(start_y < targetsX[bestTarget]){ 
			target_y++;
		}else{  } //Stay in that column

		//set unit to targeted
		Gamewindow.unit[targetsX[bestTarget]][targetsY[bestTarget]].isTargeted = true;
		
		System.out.println("ALien at X:" + start_x + " Y:" + start_y +" Target is at: X:" + targetsY[bestTarget] + " Y:" + targetsX[bestTarget] ); //test


		if(Gamewindow.unit[target_y][target_x].gettype() == 2){ //Check for other aliens
			System.out.println("Staying still, friendly in the way");
			Gamewindow.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves

		}else if(target_y == start_y && target_x == start_x){//If AI decides it shouldnt move 
			System.out.println("Staying still");
			Gamewindow.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves
		}else if(Gamewindow.unit[target_y][target_x].gettype()==1){//Attack
			Gamewindow.unit[target_y][target_x].health -= Gamewindow.unit[start_y][start_x].strength;
			System.out.println("Human Attacked by Alien! Human has " +Gamewindow.unit[target_y][target_x].gethealth() +" health left!");
			Gamewindow.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves (doesn't matter what tile does it)                  
		}else{//Moving
			Gamewindow.unit[targetsX[bestTarget]][targetsY[bestTarget]].AI_move(start_x, start_y, target_y, target_x);
		}
	}


	//Updates the tile Map somewhat buggy currently
	public void AI_move(int start_x, int start_y, int target_y, int target_x){
		if(debug == true)
			System.out.println("Moving from X:" + start_x +" Y:" + start_y +" to Y:" +target_y + " X:" +target_x);
		if(Gamewindow.unit[start_y][start_x].moves > 0){
			temphealth = Gamewindow.unit[start_y][start_x].health;
			tempstrength = Gamewindow.unit[start_y][start_x].strength;
			tempx = start_x;
			tempy = start_y;
			tempmove = Gamewindow.unit[start_y][start_x].moves;
			tempusable = Gamewindow.unit[start_y][start_x].usable; 
			try {
				Thread.sleep(400);

				Gamewindow.unit[target_y][target_x].createAlien();			//Creating a new Alien at that position
				Gamewindow.unit[target_y][target_x].health = temphealth;		//Assigning the values of the "old" Alien to the "new" one
				Gamewindow.unit[target_y][target_x].strength = tempstrength;
				Gamewindow.unit[target_y][target_x].moves = tempmove -1;                                                        
				Gamewindow.unit[tempx][tempy].moves = tempmove;
				Gamewindow.unit[tempx][tempy].usable = tempusable;
				Gamewindow.unit[target_y][target_x].set_Alien_moves(Alien_team_moves-1); //Updates total team moves
				clearBoard();								//Board gets cleared to fix any issues with wrong variable assignings

				System.out.println("Alien: Health = " +Gamewindow.unit[target_y][target_x].gethealth() +" Strenght is : " +Gamewindow.unit[target_y][target_x].getstrength() + " Moves remaining: " +Gamewindow.unit[target_y][target_x].moves 
						+ " Team moves left: " +Alien_team_moves + "Human moves left:" + Human_team_moves); 

				Gamewindow.unit[start_y][start_x].createButton();						//The initiale button gets transformed into a button
			} catch(Exception ex) {
				System.out.println(ex);
			}  
		}
	}//End of move
}

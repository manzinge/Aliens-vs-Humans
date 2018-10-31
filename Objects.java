import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

class Unit extends JButton {
	private boolean hasResource = false;
	private int radius=1;
	private int type; //0=Button, 1=Human, 2=Alien, 99=death
	private int health;
	private int strength;
	private int temphealth;
	private int tempstrength;
	private int tempx;
	private int tempy;

	BufferedImage human = ImageIO.read(getClass().getResource("Human.jpg"));
	BufferedImage alien = ImageIO.read(getClass().getResource("Alien.jpg"));
	BufferedImage death = ImageIO.read(getClass().getResource("death.png"));
	
	show sh = new show();
	move mv = new move();
	attack att = new attack();

	public Unit(int type) throws IOException {			//A Constructor that creates a different type of Unit based on the type (Button, Human or Alien)
		this.type = type;
		switch(type) {
		case 0:createButton();break;
		case 1:createHuman();break;
		case 2:createAlien();break;
		default:System.out.println("There was an error while creating a new Button/Human/Alien!");
		}	
	}
	private void createButton() {						//Method used to create a normal Button
		this.setSize(100,100);
		this.setVisible(false);
		this.type = 0;
		this.setIcon(null);
		this.setEnabled(true);
		this.removeActionListener(sh);
		this.removeActionListener(mv);
		this.health = -1;
		this.strength = -1;
	}
	private void createdeath() {
		this.type = 99;
		this.setVisible(true);
		this.setSize(100,100);
		this.health = -1;
		this.strength = -1;
		this.setIcon(new ImageIcon(death));
		System.out.println("Ja moin oida");
	}
	private void createHuman() throws IOException {		//Setting variables for a new Human (this is only called when spawning new Humans)
		this.type = 1;
		this.setSize(100,100);
		this.setVisible(true);
		this.health = 3;
		this.strength=1;
		this.setIcon(new ImageIcon(human));
		this.addActionListener(sh);
	}

	private void createAlien() throws IOException{			//Method used to create an Alien (this is only called when spawning new Aliens)
		this.setSize(100,100);
		this.setVisible(true);
		this.health=4;
		this.strength=1;
		this.setIcon(new ImageIcon(alien));
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
					if(e.getSource() == TestBoard.unit[i][j]) {						//Figuring out which object/Button was pressed

						for(int g = i-1;g<=i+radius;g++){
							for(int b=j-1;b<=j+radius;b++) {
								if(g<0 || g>7 || b<0 || b>7) {						//Takes action on all Buttons within the radius except if they are out of the border
									break;
								}
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
								}
								else {												//Action that will occur if there is just a button with nothing on it
									TestBoard.unit[g][b].setVisible(true);			//Actions include: Making it visibile -> clickable, storing temp values, and adding a listener
									TestBoard.unit[g][b].setText("MOVE");		
									temphealth = TestBoard.unit[i][j].health;
									tempstrength = TestBoard.unit[i][j].strength;
									TestBoard.unit[g][b].addActionListener(mv);
								}
							}
						}
						TestBoard.unit[i][j].createButton();						//The initiale human button gets transformed into a button
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
							TestBoard.unit[i][j].takeDamage(TestBoard.unit[tempx][tempy].getstrength());	//Clicked object receives damage (Human strength)
							clearBoard();											//Board gets cleared to fix any issues with wrong variable assignings
						} catch(Exception ex) {
							System.out.println(ex);
						}
					}
				}
			}
		}
	}
	class move implements ActionListener {											//Action listener to move the character to an empty space
		public void actionPerformed(ActionEvent e){
			for(int i=0;i<TestBoard.buttonsx;i++) {
				for(int j=0;j<TestBoard.buttonsy;j++) {			
					if(e.getSource() == TestBoard.unit[i][j]) {						//Figuring out the clicked object/Jbutton
						try {
							TestBoard.unit[i][j].createHuman();						//Creating a new human at that position
							TestBoard.unit[i][j].health = temphealth;				//Assigning the values of the "old" human to the "new" one
							TestBoard.unit[i][j].strength = tempstrength;
							clearBoard();											//Board gets cleared to fix any issues with wrong variable assignings
							System.out.println("Postion is : "+i +" and "+j + " Health = " +TestBoard.unit[i][j].gethealth() +" Strenght is : " +TestBoard.unit[i][j].getstrength());
						} catch(Exception ex) {
							System.out.println(ex);
						}
					}
				}
			}
		}
	}

	private void clearBoard() throws InterruptedException {										//Method to clear the board and remove existing action listeners
		for(int i=0;i<TestBoard.buttonsx;i++) {
			for(int j=0;j<TestBoard.buttonsy;j++) {
				TestBoard.unit[i][j].setBackground(null);			//To reset the red color of enemies
				TestBoard.unit[i][j].removeActionListener(mv);		//Removing moving listeners (The other buttons that weren't clicked)
				if(TestBoard.unit[i][j].health == 0) {				//Things to do if there is an unit with no health left -> replace it with buttons and remove listeners
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
				}
				else {												//Things to do if the unit is an alien -> removing any listeners and making it visible
					TestBoard.unit[i][j].setVisible(true);
					TestBoard.unit[i][j].removeActionListener(sh);
					TestBoard.unit[i][j].removeActionListener(att);
				}
			}
		}
		System.out.println("Board was cleared!");
	}
}

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestBoard extends JComponent{
	public static Image backgroundImage;
        ImageIcon  sleep_human = new ImageIcon(getClass().getResource("sleep_human.jpg")); //Probably want to change this to something that looks better
        ImageIcon  human = new ImageIcon(getClass().getResource("human.jpg"));
        
	public static Unit[][] unit = new Unit[8][8];
	public static JFrame board = new JFrame("Testwindow");
	static int buttonsx = 8;
	static int buttonsy = 8;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double height = screenSize.height*0.7;
	public static double width = screenSize.width*0.4;
        private boolean game_on = true;
	public TestBoard(int aliencount,int humancount,int map) throws IOException
	{
		setupwindow(map);
		setupbuttons(buttonsx,buttonsy,humancount,aliencount);
		board.revalidate();
		board.repaint();
                gametime(humancount, aliencount);
	}
	//Setting up the play window
	public static void setupwindow(int map) throws IOException {
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setResizable(true);
		board.setVisible(false);
		board.setSize(new Dimension((int)Math.round(width),(int)Math.round(height)+29));
		board.setLocationRelativeTo(null);		
		board.setContentPane(new JPanel(new BorderLayout()) {
			@Override public void paintComponent(Graphics g) {
				switch(map) {
				//The standard form is drawImage(image,startframelocationx,startframelocationy,endframelocationx,endframelocationy,startimagelocationx,startimagelocationy,endimagelocationx,endimagelocationy,observer(this)
				//The only parameters that have to be adjusted are the last 2 numbers!! Because the indicate the size of the mapimage (390,474,390)! Only edit those values!
				case 1:try {
					backgroundImage = javax.imageio.ImageIO.read(new File("map1.png"));
					g.drawImage(backgroundImage, 0, 0, (int)Math.round(width),(int)Math.round(height), 0, 0, 390, 390, this);
				} catch (IOException e1) {
					e1.printStackTrace();
				}break;
				case 2:try {
					backgroundImage = javax.imageio.ImageIO.read(new File("map2.jpg"));
					g.drawImage(backgroundImage, 0, 0, (int)Math.round(width),(int)Math.round(height), 0, 0, 474, 474, this);
				}catch(IOException e2) {
					e2.printStackTrace();
				}break;
				case 3:try {
					backgroundImage = javax.imageio.ImageIO.read(new File("map3.jpg"));
					g.drawImage(backgroundImage, 0, 0, (int)Math.round(width),(int)Math.round(height), 0, 0, 390, 390, this);
				}catch(IOException e3) {
					e3.printStackTrace();
				}break;
				default:System.out.println("There was an error while creating the game background/Map!");break;
				}
			}
		});
		board.setLayout(null);
		board.show();
	}
	//Setting up the buttons -> this method is necessary until "Further steps"
	public static void setupbuttons(int buttonsx,int buttonsy,int humancount, int aliencount) throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
		//Further steps
		//Spawning humans
		for(int i=0;i<humancount;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2)+6;
			if(unit[yr][xr].gettype() == 1)
			{
				i--;
			}
			else
			{
				board.remove(unit[yr][xr]);
				unit[yr][xr] = new Unit(1);
				unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
				board.add(unit[yr][xr]);
			}
		}
		//Spawning Alien
		for(int i=0;i<aliencount;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2);
			if(unit[yr][xr].gettype()==2) {
				i--;
			}
			else {
				board.remove(unit[yr][xr]);
				unit[yr][xr] = new Unit(2);
				unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
				board.add(unit[yr][xr]);
			}
		}
	}
        
        public void gametime(int humans, int aliens){
            int human_team_moves = humans * 2;
            int humans_left=0;
            boolean human_turn =true;
            
            unit[0][0].set_Human_moves(human_team_moves);
            while(game_on = true){//Game loop
                for(int i=0;i<buttonsx;i++) {//Go through the board checking if buttons have remaining moves
			for(int j=0;j<buttonsy;j++) {
				if(unit[i][j].getMoves() == 0){
                                    unit[i][j].set_usable(false); //Unit can no longer perform actions
                                    unit[i][j].setIcon(sleep_human); //Show that the human is "sleeping"
                                }
                                if(unit[i][j].gettype()==1){
                                    human_team_moves += unit[i][j].getMoves();
                                    
                                }
                             
			}
		}
                if(unit[0][0].get_Human_moves() == 0 && human_turn == true){//If humans all out of moves
                    for(int i=0;i<buttonsx;i++) {//Go through the board 
			for(int j=0;j<buttonsy;j++) {
				if(unit[i][j].gettype()==1){ //Reenable and add moves to each human once their moves are over
                                    unit[i][j].set_Moves(2); 
                                    unit[i][j].set_usable(true); //Reenable the unit
                                    humans_left++;
                                    unit[i][j].setIcon(human);//Show that all humans are "awake" again
                                }
                                
                             
			}
                    }   
                   unit[0][0].set_Human_moves(humans_left * 2);  //Set team moves to amount of humans * movement points
                   humans_left = 0;//Reset humans left
                }
            
            }
        }
}


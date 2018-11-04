import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;

public class TestBoard {
	public static Unit[][] unit = new Unit[8][8];
	public static JFrame board = new JFrame("Testwindow");
	static int buttonsx = 8;
	static int buttonsy = 8;
	public TestBoard(int aliencount,int humancount) throws IOException
	{
		setupwindow();
		setupbuttons(buttonsx,buttonsy,humancount,aliencount);
		board.revalidate();
		board.repaint();
	}
	//Setting up the play window
	public static void setupwindow() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.height*0.7;
		double width = screenSize.width*0.4;
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setResizable(false);
		board.setVisible(true);
		board.setSize(new Dimension((int)Math.round(width),(int)Math.round(height)+29));
		board.setLocationRelativeTo(null);
		board.setLayout(null);
	}
	//Setting up the buttons -> this method is necessary until "Further steps"
	public static void setupbuttons(int buttonsx,int buttonsy,int humancount, int aliencount) throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.height*0.7;
		double width = screenSize.width*0.4;
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
			board.remove(unit[yr][xr]);
			unit[yr][xr] = new Unit(1);
			unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
			board.add(unit[yr][xr]);
		}
		//Spawning Alien
		for(int i=0;i<aliencount;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2);
			board.remove(unit[yr][xr]);
			unit[yr][xr] = new Unit(2);
			unit[yr][xr].setLocation((int)Math.round(xr*(width/buttonsx)),(int)Math.round(yr*(height/buttonsy)));
			board.add(unit[yr][xr]);
		}
	}
}


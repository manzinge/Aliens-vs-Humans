import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;

public class TestBoard {
	public static Unit[][] unit = new Unit[8][8];
	public static JFrame board = new JFrame("Testwindow");
	static int buttonsx = 8;
	static int buttonsy = 8;

	public static void main(String[] args) throws IOException {
		setupwindow();
		setupbuttons(buttonsx,buttonsy);
		board.revalidate();
		board.repaint();
	}
	public static void setupwindow() {
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setResizable(false);
		board.setVisible(true);
		board.setSize(buttonsx*100,buttonsy*100 + 29);
		board.setLocationRelativeTo(null);
		board.setLayout(null);
	}
	
	public static void setupbuttons(int buttonsx,int buttonsy) throws IOException {
		int x = 0,y=0;
		for(int i=0;i<buttonsx;i++) {
			for(int j=0;j<buttonsy;j++) {
				unit[i][j] = new Unit(0);
				unit[i][j].setLocation(x,y);
				board.add(unit[i][j]);
				x=x+100;
			}
			y=y+100;
			x=0;
		}

		for(int i=0;i<2;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2)+6;
			board.remove(unit[yr][xr]);
			unit[yr][xr] = new Unit(1);
			unit[yr][xr].setLocation(xr*100, yr*100);
			board.add(unit[yr][xr]);
		}
		for(int i=0;i<2;i++) {
			Random rand = new Random();
			int xr = rand.nextInt(8);
			int yr = rand.nextInt(2);
			board.remove(unit[yr][xr]);
			unit[yr][xr] = new Unit(2);
			unit[yr][xr].setLocation(xr*100, yr*100);
			board.add(unit[yr][xr]);
		}
	}
}

